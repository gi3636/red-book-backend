package com.example.red.book.netty;

import com.example.red.book.config.NettyProperties;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@Slf4j
public class ServerBoot {
    @Autowired
    ServerBootstrap serverBootstrap;
    @Resource
    NioEventLoopGroup bossGroup;
    @Resource
    NioEventLoopGroup workerGroup;
    @Autowired
    NettyProperties nettyProperties;

    /**
     * 开机启动
     *
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // 绑定端口启动
        serverBootstrap.bind(nettyProperties.getPort()).sync();
        serverBootstrap.bind(nettyProperties.getPortSalve()).sync();
        log.info("启动Netty多端口服务器: {},{}", nettyProperties.getPort(), nettyProperties.getPortSalve());
    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void close() throws InterruptedException {
        log.info("关闭Netty服务器");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
