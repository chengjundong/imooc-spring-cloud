# Learn SpringCloud
To learn SpringCloud from class [SpringCloud Finchley(M2+RELEASE+SR2)微服务实战](https://coding.imooc.com/class/187.html)  
Teacher leads us to walk through important Spring Cloud components & practice by implementing a WeChat order app.  
I write a User information service instead, generated by Swagger.

# Dependency Version
I use different external resource version with Teachers. Maybe I will run into some problem, it is okay for me to explore SpringCloud.
- Java Version: 11.0.2
- SpringBoot Version: 2.7.9
## How to know spring-cloud version for one spring-boot version
### Find in from SpringCloud doc
Go to [SpringCloud](https://spring.io/projects/spring-cloud) and find release train.  
- Left is SpringCloud version is naming based on London subway station.  
- Right is SpringBoot version
### Find it from a real project
If you need know the suitable spring-cloud version for a special spring-boot version, you could use [Spring Start](https://start.spring.io/).  
1. Choose which SpringBoot version you need, like SpringBoot 2.7.9
2. Add a cloud dependency, like Eureka server
3. Click __EXPLORE__ button in bottom, so you can browse the generated POM file
4. Find `spring-cloud.version` and GAV of `spring-cloud-dependencies`, copy them into your dependency management

# System Architecture
TBA

## Server Ports
Since we might need start several micro-service components including Eureka-Server, Zuul API gateway, Application Server, etc.  
We need let me listen to different server port.  

| Sever                | Port  |
|----------------------|-------|
| Eureka-Server        | 8761  |
| Zuul                 | 10030 |
| User Write Service#1 | 8080  |
| User Read Service#1  | 9030  |
| User Score Service#1 | 9040  |
| Config Server        | 8100  |

## Open H2 data base console
We have enabled H2 console for in-mem H2 database for debug.

1. launch user-server
2. use [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/) to open H2 console
3. find username & password in `/user-server/src/main/resource/application.properties`, then login
4. query data
