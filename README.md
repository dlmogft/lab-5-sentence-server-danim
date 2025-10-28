# lab-5-sentence-server-danim

This is a Spring Cloud Eureka client project that is registered and discovered in the lab-4-eureka-server-danim
This project acts as a **load-balancer** of **lab-5-word-server-danim** repo executions

# Starting this client repository

The steps to used it are:
- Start the **lab-3-server-danim** repo, which loads the configuration files from the **spring-cloud-server-config-danim** Git repository
- Start the **lab-4-eureka-server-danim** repo and check it's started correctly in http://localhost:8010
- Start the different executions of **lab-5-word-server-danim** ([See README from lab-5-word-server-danim](https://github.com/dlmogft/lab-5-word-server-danim/blob/main/README.md))
- Start the class annotated with @SpringBootApplication
- Check that the sentences are shown in the URL http://localhost:8020/sentence

# Dependencies

Spring Web, Config Server, Eureka Discovery Client, Spring Boot Actuator, Spring Boot DevTools, Cloud LoadBalancer

# Tips

- The project starting class is annotated with **@SpringBootApplication** and also with @EnableDiscoveryClient to indicate that acts as an Eureka Client
- The **application.yml** config file specifies the URI of the Config Server repo **lab-3-server-danim** that loads the configuration files from the **spring-cloud-server-config-danim** Git repository
- The **SentenceController** has a **RestTemplate** autowired instance that is used to load the mask URI for every execution of **lab-5-word-server-danim** (http://SUBJECT, http://VERB, http://ARTICLE, http://ADJECTIVE and http://NOUN) that retrieve a word from every execution and mount the sentence
