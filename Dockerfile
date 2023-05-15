FROM ubuntu:22.04

RUN mkdir /opt/web
COPY Logistics.jar /opt/web/

ENV JAVA_HOME=/usr/local/jdk-17.0.5
ENV JRE_HOME=$JAVA_HOME/jre
ENV PATH=${JAVA_HOME}/bin:$PATH

ADD jdk-17_linux-x64_bin.tar.gz /usr/local/
RUN javac --version \
    && java --version \

# 部署SpringBoot项目
EXPOSE 2333
# 切换WORKDIR
WORKDIR /opt/web/
ENTRYPOINT ["nohup","java","-jar","Logistics.jar>Logistics.log","&"]