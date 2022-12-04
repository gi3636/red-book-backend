package com.example.red.book.service.impl;

import com.example.red.book.netty.bean.ChatMsg;
import com.example.red.book.netty.bean.DataContent;
import com.example.red.book.netty.UserConnectPool;
import com.example.red.book.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PushMsgServiceImpl {

    public void pushMsgToOne(DataContent dataContent) {
        ChatMsg chatMsg = dataContent.getChatMsg();
        Channel channel = UserConnectPool.getChannel(chatMsg.getReceiverId());
        if (Objects.isNull(channel)) {
            throw new RuntimeException("未连接socket服务器");
        }

        channel.writeAndFlush(
                new TextWebSocketFrame(
                        JsonUtils.objectToJson(chatMsg)
                )
        );
    }

    public void pushMsgToAll(DataContent dataContent) {
        ChatMsg chatMsg = dataContent.getChatMsg();
        Channel channel = UserConnectPool.getChannel(chatMsg.getReceiverId());
        UserConnectPool.getChannelGroup().writeAndFlush(
                new TextWebSocketFrame(
                        JsonUtils.objectToJson(chatMsg)
                )
        );
    }
}

