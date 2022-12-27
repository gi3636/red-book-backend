# 该镜像需要依赖的基础镜像
FROM openjdk:8
WORKDIR /root
# 将当前目录下的jar包复制到docker容器的/目录下
ADD jarfile/red-book*.jar /root/app.jar
# 声明服务运行在8080端口
EXPOSE 8080
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-jar","/root/app.jar"]


