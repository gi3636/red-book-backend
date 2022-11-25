package com.example.red.book.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerHandler extends ChannelInitializer<SocketChannel> {
    /**
     * 初始化通道以及配置对应管道的处理器
     *
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MessageDecodeHandler());
        pipeline.addLast(new MessageEncodeHandler());
        //pipeline.addLast(new ServerListenerHandler());
    }

}
