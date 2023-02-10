# What is Messagery?
In the context of software architecture, Messagery refers to the component or system responsible for exchanging messages between different parts of an application or between different applications. This component acts as a communication layer that allows different parts of the software to exchange data and communicate with each other. In a microservices architecture, for example, Messagery might refer to the system that facilitates communication between microservices, such as a message broker or event-driven architecture. The goal of Messagery is often to decouple the different parts of an application, making it more flexible and scalable.

# What is Amazon SQS?
In short, it provides a way for applications to exchange messages in a scalable and reliable manner. With SQS, developers can send, store, and receive messages between different parts of their application, such as microservices, without having to worry about the underlying infrastructure. SQS also provides features such as automatic scaling, message deduplication, and message ordering to ensure that messages are delivered in the right order and without duplicates. SQS is often used in applications that need to process large amounts of data in parallel or in applications that require asynchronous communication between components.

# What is Amazon SNS?
In short, it provides a way for applications to send messages to multiple subscribers or endpoints, such as email addresses, SMS texts, or mobile devices. With SNS, developers can send messages to multiple recipients at once, making it a useful tool for sending notifications and alerts.

# Configuring steps-by-steps
1. Include dependencies in `build.gradle` file:
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'
implementation 'org.springframework.cloud:spring-cloud-aws-messaging:2.2.6.RELEASE'
implementation 'io.awspring.cloud:spring-cloud-aws-messaging:2.4.4'
```

2. Configure application.yaml file:
```yaml
cloud:
  aws:
    region:
      static: ${AWS_REGION:us-east-1}
      auto: false
    credentials:
      access-key: ${AWS_ACCESS_KEY:test}
      secret-key: ${AWS_SECRET_KEY:test}
    end-point:
      uri: ${AWS_ENDPOINT:http://localhost:4566}

spring:
  autoconfigure:
    exclude:
      - org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration
      - org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration
      - org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration
```

3. Create an Consumer class and use @SqsListener in methods to receive messages from SQS queue.
```java
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SqsListener("YourQueue")
    public void queueListener(String message) {
        logger.info("Receive message in queue for: {}.", message);
    }
    
}
```

4. Configure AmazonSNS and AmazonSQS beans:
```java
@Configuration
public class AwsConfig {

    @Value("${cloud.aws.region.static}") private String awsRegion;
    @Value("${cloud.aws.end-point.uri}") private String awsEndpoint;
    @Value("${cloud.aws.credential.access-key}") private String accessKey;
    @Value("${cloud.aws.credential.secret-key}") private String secretKey;

    @Bean
    public AmazonSNS amazonSNS() {
        var endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(awsEndpoint, awsRegion);
        var credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));

        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider)
                .build();
    }

    @Bean
    public AmazonSQSAsync amazonSQS() {
        var endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(awsEndpoint, awsRegion);
        var credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));

        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider)
                .build();
    }
}
```

5. Create an Producer Class:
```java
@Component
public class SnsProducer {

    @Autowired private AmazonSNS amazonSNS;

    public void publish(String topicArn, String message) {
        PublishRequest publishRequest = new PublishRequest(topicArn, message);
        amazonSNS.publish(publishRequest);
    }
}
```

# References

[AWS Developer Guide](https://docs.aws.amazon.com/en_us/sns/latest/dg/Welcome.html)

# TODO

- [x] Create docker-compose.yml with Localstack service
- [x] Create script to provisioning SQS and SNS in Localstack
- [x] Create a Message POJO
- [x] Create a Message Consumer (pooling from Amazon SQS)
- [x] Create a Message Producer (publish in Amazon SNS)