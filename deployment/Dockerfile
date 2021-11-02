FROM adoptopenjdk/openjdk14-openj9:alpine-slim
VOLUME /tmp

RUN mkdir /app
ADD applications/app-service/build/libs/app-service.jar app/app-service.jar

#COPY *.jar app.jar
#RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar app.jar" ]

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar app/app-service.jar" ]