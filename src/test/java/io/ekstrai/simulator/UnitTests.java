package io.ekstrai.simulator;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;


class UnitTests {

    @Test
    void uuid() {
        final String uuid = UUID.randomUUID().toString();
        assertTrue(uuid.length() > 30);
        assertEquals(uuid.length(), 36);
        System.out.println(uuid);
    }
}
