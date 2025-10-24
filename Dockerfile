FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Seoul
# 💡 headless 모드 활성화 (GUI 없는 리눅스 환경에서도 PDF 생성 가능)
ENTRYPOINT ["java","-Djava.awt.headless=true","-Dspring.profiles.active=${USE_PROFILE}","-jar","/app.jar"]

