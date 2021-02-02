package io.ekstrai.simulator;


import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    private final String primaryConnectionKey =
            "Endpoint=sb://ekstraisb00.servicebus.windows.net/;" +
                    "SharedAccessKeyName=RootManageSharedAccessKey;" +
                    "SharedAccessKey=GpTjswshvclzsEKyM92bJRk0qH7l0m8QbmS/UHrvWaQ=";
    private final String queueName = "machineevents";
    private final ServiceBusProcessorClient receiverClient;

    public Receiver() {

        receiverClient = new ServiceBusClientBuilder()
                .connectionString(primaryConnectionKey)
                .processor()
                .queueName(queueName)
                .processMessage(processMessage)
                .processError(processError)
                .disableAutoComplete()
                .buildProcessorClient();

    }

    @Autowired
    public void startReceiving() {
        receiverClient.start();
    }


    private Consumer<ServiceBusReceivedMessageContext> processMessage = messageContext -> {
        try {
            LOG.info("****** 1 MESSAGE RECEIVED ****** ");
            LOG.info("Message ID: " + messageContext.getMessage().getMessageId());
            LOG.info("Session ID: " + messageContext.getMessage().getMessageId());
            LOG.info("Message Body: " + messageContext.getMessage().getBody());

            messageContext.complete();

        } catch (Exception e) {

            LOG.error(e.toString());
            messageContext.abandon();
        }
    };


    private Consumer<ServiceBusErrorContext> processError = errorContext ->
            LOG.error("Error occurred while receiving message: " + errorContext.getException());


}
