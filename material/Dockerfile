FROM lwieske/java-8

#容器元信息，帮助信息，Metadata，类似于代码注释
MAINTAINER 1911298402@qq.com

# 容器内部环境变量
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 创建目录
RUN mkdir -p /app

# 工作目录
WORKDIR /app

# 暴露端口
EXPOSE 8080

# 将本地Jar添加到Docker镜像中 【Jar包位置】 【容器内部位置】
ADD ./*.jar ./

# 睡眠5秒 启动Jar包
CMD sleep 5;java $JAVA_OPTS "-Dfile.encoding=UTF-8" -jar *.jar