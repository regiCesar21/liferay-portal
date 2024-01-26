FROM java:openjdk-8-jdk

COPY ../build/libs/*.py /opt
COPY ../docker-resources/ /opt
COPY ../src/main/resources/*.yaml /opt

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]

ENV APACHE_LIVY_VERSION 0.7.0
ENV APACHE_SPARK_VERSION 2.4.1
ENV APACHE_HADOOP_VERSION 2.7
ENV LIVY_HOME /opt/apache-livy-${APACHE_LIVY_VERSION}-incubating-bin
ENV SPARK_HOME /opt/spark-${APACHE_SPARK_VERSION}-bin-hadoop${APACHE_HADOOP_VERSION}

EXPOSE 8998

RUN cd /tmp && \
	wget -q http://mirror.cc.columbia.edu/pub/software/apache/incubator/livy/${APACHE_LIVY_VERSION}-incubating/apache-livy-${APACHE_LIVY_VERSION}-incubating-bin.zip && \
	wget -q https://archive.apache.org/dist/spark/spark-${APACHE_SPARK_VERSION}/spark-${APACHE_SPARK_VERSION}-bin-hadoop${APACHE_HADOOP_VERSION}.tgz && \
	unzip -q apache-livy-${APACHE_LIVY_VERSION}-incubating-bin.zip -d /opt/ && \
	tar fxz spark-${APACHE_SPARK_VERSION}-bin-hadoop${APACHE_HADOOP_VERSION}.tgz -C /opt --group root --no-same-owner --owner root && \
	rm apache-livy-${APACHE_LIVY_VERSION}-incubating-bin.zip && \
	rm spark-${APACHE_SPARK_VERSION}-bin-hadoop${APACHE_HADOOP_VERSION}.tgz

WORKDIR /opt