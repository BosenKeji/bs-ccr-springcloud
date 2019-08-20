package cn.bosenkeji.listener;

import cn.bosenkeji.config.SqsMessagingConfig;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/8/20 14:12
 */
@Component
@EnableScheduling
public class NotificationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);
    private static final String QUEUE_NAME = "bs-ccr-test";

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    @Autowired
    SqsMessagingConfig sqsMessagingConfig;

    @Scheduled(cron = "0 */2 * ? * *")
    public void getMessage(){
        final AmazonSQS sqs = sqsMessagingConfig.amazonSQSAsync();

        while (true){
            LOGGER.info("Receiving messages from"+QUEUE_NAME+"\n");
            final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsEndPoint)
                    .withMaxNumberOfMessages(1).withWaitTimeSeconds(3);
            final List<Message> messages =sqs.receiveMessage(receiveMessageRequest).getMessages();

            for (Message message :
                    messages) {
                LOGGER.info("Body:"+message.getBody());
            }
        }
    }


}
