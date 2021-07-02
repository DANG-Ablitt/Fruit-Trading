FROM java:8
EXPOSE 8081

VOLUME /tmp
ADD renren-auth.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
