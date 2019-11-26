# Рow to set up the project

This project uses PostgreSQL. Default username is `postgres` and password is `12345`.  
If your settings are different, you need to create an `application-local.properties` file.  
File contents:  
```
spring.datasource.username=my_username
spring.datasource.password=my_password
```

# How to build the project  
From root project folder
```
gradlew clean build
```

# How to start the project  
Project's *.jar* file is located in *build/libs* folder  
For starting project with embedded tomcat from jar file:  
```
java -jar build/libs/mainsoft-intro-stankevich-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```
The project will be launched on port `43210`.
