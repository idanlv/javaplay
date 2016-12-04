FROM tomcat:8.0-jre8

COPY JavaPlay.war /usr/local/tomcat/webapps/
COPY keys.json /usr/local/keys.json
COPY src/logging.properties $JAVA_HOME/lib/logging.properties

ENV JAVAPLAY_ENV=production
ENV JAVAPLAY_KEYS_FILE=/usr/local/keys.json