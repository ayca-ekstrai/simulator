package io.ekstrai.simulator;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledPublisher.class);

    //TODO move to app.properties
    private static final String primaryConnectionKey =
            "Endpoint=sb://ekstraisb00.servicebus.windows.net/;" +
                    "SharedAccessKeyName=RootManageSharedAccessKey;" +
                    "SharedAccessKey=GpTjswshvclzsEKyM92bJRk0qH7l0m8QbmS/UHrvWaQ=";
    //TODO move to app.properties
    private static final String topicName = "machineevents";




    @Scheduled(fixedRate = 10000)
    public static void logFixedRate() {


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
