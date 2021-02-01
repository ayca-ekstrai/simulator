package io.ekstrai.simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class ScheduledPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledPublisher.class);

    @Scheduled(fixedRate = 5000)
    public void logFixedRate() {
        LOG.info("Time: " + Instant.now().toString());
    }
}
