package dev.breno.consumingsqsmessages;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.amazonaws.util.json.Jackson.toJsonString;

@RestController
public class SqsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${aws.sns.topic.book.arn}") String bookArn;
    @Value("${aws.sns.topic.song.arn}") String songArn;
    @Autowired private SnsProducer sqsProducer;

    @PostMapping("/produce/message/book")
    public void sendBookMessage(@RequestBody Book book) throws JsonProcessingException {
        String jsonString = toJsonString(book);
        sqsProducer.publish(bookArn, jsonString);
        logger.info("Published message in book topic for {}", book.title());
    }

    @PostMapping("/produce/message/song")
    public void sendSongMessage(@RequestBody Song song) throws JsonProcessingException {
        String jsonString = toJsonString(song);
        sqsProducer.publish(songArn, jsonString);
        logger.info("Published message in song topic for {}", song.name());
    }

}
