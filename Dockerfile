# Use official Java runtime as base image
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/QuickTip-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV MONGO_URI=$MONGO_URI
ENV RAZORPAY_KEY=$RAZORPAY_KEY
ENV RAZORPAY_SECRET=$RAZORPAY_SECRET
ENV PORT=$PORT

ENTRYPOINT ["sh","-c","java -jar app.jar"]
