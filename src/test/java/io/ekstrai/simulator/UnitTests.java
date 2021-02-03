package io.ekstrai.simulator;


import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.models.SubQueue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UnitTests {



    private static final Logger LOG = LoggerFactory.getLogger(UnitTests.class);
    private static final ObjectMapper MAP = new ObjectMapper();


    private static final String primaryConnectionKey =
            "Endpoint=sb://ekstraisb00.servicebus.windows.net/;" +
                    "SharedAccessKeyName=RootManageSharedAccessKey;" +
                    "SharedAccessKey=GpTjswshvclzsEKyM92bJRk0qH7l0m8QbmS/UHrvWaQ=";
    private static final String queueName = "machineevents";
    private static final String subName = "machineeventsub";
    private static final String topicName = "machineevents";


    private static final ServiceBusReceiverClient client =  new ServiceBusClientBuilder()
            .connectionString(primaryConnectionKey)
            .receiver()
            .topicName(topicName)
            .subscriptionName(subName)
            .subQueue(SubQueue.DEAD_LETTER_QUEUE)
            .buildClient();

    @Test
    void uuid() {
        final String uuid = UUID.randomUUID().toString();
        assertTrue(uuid.length() > 30);
        assertEquals(uuid.length(), 36);
        System.out.println(uuid);
    }


    @Test
    void timestampOutput() {
        final String timestamp = OffsetDateTime.now().toString();
        LOG.info(timestamp);
        assertTrue(timestamp.contains(":00"));
        assertTrue(timestamp.contains("+"));
    }

    @Test
    void machineEventSerialization() throws JsonProcessingException {
        final MachineEvent event = new MachineEvent("XX5000.5555-001", 457, "prod://start", "1024");
        final String eventJson = MAP.writeValueAsString(event);

        assertTrue(eventJson.contains("session"));
        assertTrue(eventJson.contains("1024"));
        LOG.info(eventJson);
        LOG.info(MAP.writerWithDefaultPrettyPrinter().writeValueAsString(event));
    }

    @Test
    void environmentVariablesWiredProperly() {
//        final String test = env.getProperty("test.value");
//        LOG.info(test);
//        assertEquals(test, "testValue");
    }


    @Test
    void utilityClassMachineEventGeneratorAndSerializerWorksProperly() throws JsonProcessingException {

        final MachineEvent e = Utilities.randomEventGenerator();
        final String eJson = Utilities.machineEventSerializer(e);
        assertTrue(eJson.contains("session"));
        assertTrue(eJson.contains("stop") || eJson.contains("start"));
        LOG.info(MAP.writerWithDefaultPrettyPrinter().writeValueAsString(e));
    }





}
