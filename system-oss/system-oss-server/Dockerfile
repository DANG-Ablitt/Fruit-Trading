FROM java:8
EXPOSE 8083

VOLUME /tmp
ADD renren-oss-server.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
