package dev.breno.consumingsqsmessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SqsConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void bookListener(@Payload Book book) {
        logger.info("Receive message in Book queue for: {} by {}-{}.", book.title(), book.author(), book.year());
    }

    @SqsListener(value = "song-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void songListener(@Payload Song song) {
        logger.info("Receive message in Song queue for: {} by {}.", song.name(), song.author());
    }

}
