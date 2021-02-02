package io.ekstrai.simulator;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class ScheduledPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledPublisher.class);

    private final String primaryConnectionKey =
            "Endpoint=sb://ekstraisb00.servicebus.windows.net/;" +
                    "SharedAccessKeyName=RootManageSharedAccessKey;" +
                    "SharedAccessKey=GpTjswshvclzsEKyM92bJRk0qH7l0m8QbmS/UHrvWaQ=";
    private final String topicName = "machineevents";
    private final ServiceBusSenderClient senderClient;

    public ScheduledPublisher() {

        senderClient = new ServiceBusClientBuilder()
                .connectionString(primaryConnectionKey)
                .sender()
                .topicName(topicName)
                .buildClient();

    }

    @Scheduled(fixedRate = 10000)
    public void logFixedRate() {
        //LOG.info("Time: " + Instant.now().toString());
        LOG.info("Message is being prepared " + Instant.now().toString());
        senderClient.sendMessage(new ServiceBusMessage("Hello Test Publishing " + Instant.now().toString())
        .setMessageId(Instant.now().toString()));
        LOG.info("Message is sent " + Instant.now().toString());

    }
}
