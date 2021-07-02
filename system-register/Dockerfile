FROM java:8
EXPOSE 8761

VOLUME /tmp
ADD system-register.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
