FROM java:8
EXPOSE 8087

VOLUME /tmp
ADD system-api-server.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
