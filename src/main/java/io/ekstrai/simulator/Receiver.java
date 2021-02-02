package io.ekstrai.simulator;


import com.azure.messaging.servicebus.*;
import com.azure.messaging.servicebus.models.SubQueue;
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
    private final String subName = "machineeventsub";
    private final String topicName = "machineevents";
    //private final ServiceBusProcessorClient processorClient;
    private final ServiceBusReceiverClient receiverClient;

    public Receiver() {

//        receiverClient = new ServiceBusClientBuilder()
//                .connectionString(primaryConnectionKey)
//                .processor()
//                .queueName(queueName)
//                .processMessage(processMessage)
//                .processError(processError)
//                .disableAutoComplete()
//                .buildProcessorClient();

        receiverClient = new ServiceBusClientBuilder()
                .connectionString(primaryConnectionKey)
                .receiver()
                .topicName(topicName)
                .subscriptionName(subName)
                .subQueue(SubQueue.DEAD_LETTER_QUEUE)
                .buildClient();
    }

    @Autowired
    public void startReceiving() {
        final ServiceBusReceivedMessage message= receiverClient.peekMessage();
        LOG.info("****** 1 MESSAGE RECEIVED ****** ");
        LOG.info("Message ID: " + message.getMessageId());
        LOG.info("Session ID: " + message.getMessageId());
        LOG.info("Message Body: " + message.getBody());

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
