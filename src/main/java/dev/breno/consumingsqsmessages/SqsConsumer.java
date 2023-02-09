package dev.breno.consumingsqsmessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

public class SqsConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @SqsListener("Book")
    public void bookListener(Book book) {
        logger.info("Receive message in Book queue for: {} by {}-{}.", book.title(), book.author(), book.year());
    }

    @SqsListener("Song")
    public void songListener(Song song) {
        logger.info("Receive message in Song queue for: {} by {}.", song.name(), song.author());
    }

}
