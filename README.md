# What is Messagery?
In the context of software architecture, Messagery refers to the component or system responsible for exchanging messages between different parts of an application or between different applications. This component acts as a communication layer that allows different parts of the software to exchange data and communicate with each other. In a microservices architecture, for example, Messagery might refer to the system that facilitates communication between microservices, such as a message broker or event-driven architecture. The goal of Messagery is often to decouple the different parts of an application, making it more flexible and scalable.

# What is Amazon SQS?
In short, it provides a way for applications to exchange messages in a scalable and reliable manner. With SQS, developers can send, store, and receive messages between different parts of their application, such as microservices, without having to worry about the underlying infrastructure. SQS also provides features such as automatic scaling, message deduplication, and message ordering to ensure that messages are delivered in the right order and without duplicates. SQS is often used in applications that need to process large amounts of data in parallel or in applications that require asynchronous communication between components.

# What is Amazon SNS?
In short, it provides a way for applications to send messages to multiple subscribers or endpoints, such as email addresses, SMS texts, or mobile devices. With SNS, developers can send messages to multiple recipients at once, making it a useful tool for sending notifications and alerts.

# Configuring steps-by-steps
1. Include dependencies in `build.gradle` file:
```groovy
implementation 'org.springframework.cloud:spring-cloud-aws-messaging:2.2.6.RELEASE'
```

2. Create an Consumer class and use @SqsListener in methods to receive messages from SQS queue.
```java
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SqsListener("YourQueue")
    public void queueListener(String message) {
        logger.info("Receive message in queue for: {}.", message);
    }
    
}
```

# References

[AWS Developer Guide](https://docs.aws.amazon.com/en_us/sns/latest/dg/Welcome.html)

# TODO

- [x] Create docker-compose.yml with Localstack service
- [x] Create script to provisioning SQS and SNS in Localstack
- [ ] Create a Message POJO
- [ ] Create a Message Consumer (pooling from Amazon SQS)
- [ ] Create a Message Producer (publish in Amazon SNS)