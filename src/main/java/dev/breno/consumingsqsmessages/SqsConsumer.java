package dev.breno.consumingsqsmessages;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.amazonaws.util.json.Jackson.fromJsonString;

@Component
public class SqsConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SqsListener(value = "${aws.sqs.queue.book.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void bookListener(@Payload String message) throws JsonProcessingException {
//        TopicMessage topicMesssage = fromJsonString(message, TopicMessage.class);
//        Book book = fromJsonString(topicMesssage.message(), Book.class);
        logger.info("Receive message in Book queue for: {}.", message);
    }

    @SqsListener(value = "${aws.sqs.queue.song.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void songListener(@Payload String message) throws JsonProcessingException {
//        TopicMessage topicMesssage = fromJsonString(message, TopicMessage.class);
//        Song song = fromJsonString(topicMesssage.message(), Song.class);
        logger.info("Receive message in Song queue for: {}.", message);
    }



}
