package com.example.red.book.netty.handler;

import com.example.red.book.model.enums.MessageActionEnum;
import com.example.red.book.netty.UserConnectPool;
import com.example.red.book.netty.bean.ChatMsg;
import com.example.red.book.netty.bean.DataContent;
import com.example.red.book.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerListenerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 当建立链接时将Channel放置在Group当中
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("有新的客户端链接：[{}]", ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        UserConnectPool.getChannelGroup().add(ctx.channel());
    }

    /**
     * 读取数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        /**
         * 1.接受到msg
         * 2.将msg转化为实体类
         * 3.解析消息类型
         * 将实体类当中的userid和连接的Channel进行对应
         * */
        String content = msg.text();
        Channel channel = ctx.channel();
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        assert dataContent != null;
        Integer action = dataContent.getAction();
        if (Objects.equals(action, MessageActionEnum.CONNECT.type)) {
            //进行关联注册
            String senderId = dataContent.getChatMsg().getSenderId();
            UserConnectPool.getChannelMap().put(senderId, channel);

            // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
            AttributeKey<String> key = AttributeKey.valueOf("userId");
            ctx.channel().attr(key).setIfAbsent(senderId);

        } else if (Objects.equals(action, MessageActionEnum.CHAT.type)) {
            /**
             * 解析你的消息，然后进行持久化，或者其他的操作，看你自己
             * */
            ChatMsg chatMsg = dataContent.getChatMsg();

            //发送消息
            Channel receiverChannel = UserConnectPool.getChannel(chatMsg.getReceiverId());
            if (receiverChannel == null) {
                //用户不在线
            } else {
                //为了保险起见你还可以在你的Group里面去查看有没有这样的Channel
                //毕竟不太能够保证原子性操作嘛，反正底层也是CurrentMap
                Channel findChannel = UserConnectPool.getChannelGroup().find(ctx.channel().id());
                if (findChannel != null) {
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(chatMsg)
                            )
                    );
                } else {
                    //离线
                }
            }

        } else if (Objects.equals(action, MessageActionEnum.SIGNED.type)) {

        } else if (Objects.equals(action, MessageActionEnum.KEEPALIVE.type)) {

        } else if (Objects.equals(action, MessageActionEnum.PULL_FRIEND.type)) {

        }


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("用户下线了:{}", ctx.channel().id().asLongText());
        // 删除通道
        UserConnectPool.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        log.info("异常：{}", cause.getMessage());
        // 删除通道
        UserConnectPool.getChannelGroup().remove(ctx.channel());
        removeUserId(ctx);
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        UserConnectPool.getChannelMap().remove(userId);
    }
}

