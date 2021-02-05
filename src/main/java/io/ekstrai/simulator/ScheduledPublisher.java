package io.ekstrai.simulator;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledPublisher.class);


    @Value("${servicebus.primarykey}")
    private String primaryConnectionKey;

    @Value("${servicebus.topicname}")
    private String topicName;




    @Scheduled(fixedRate = 10000)
    public void logFixedRate() {


        final ServiceBusSenderClient client = new ServiceBusClientBuilder()
                .connectionString(primaryConnectionKey)
                .sender()
                .topicName(topicName)
                .buildClient();

        final MachineEvent currentEvent = Utilities.randomEventGenerator();

        client.sendMessage(
                new ServiceBusMessage(Utilities.machineEventSerializer(currentEvent))
                        .setMessageId(currentEvent.getTimeStamp().toString()));

        LOG.info("Message sent successfully! ");
        LOG.info(Utilities.prettyJson(currentEvent));

        client.close();

        LOG.info("Sender client is now closed. \n");

    }


}
