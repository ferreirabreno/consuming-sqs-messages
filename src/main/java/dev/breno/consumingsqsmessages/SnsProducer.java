package dev.breno.consumingsqsmessages;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnsProducer {

    @Autowired private AmazonSNS amazonSNS;

    public void publish(String topicArn, String message) {
        PublishRequest publishRequest = new PublishRequest(topicArn, message);
        amazonSNS.publish(publishRequest);
    }

}
