package io.ekstrai.simulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public class Utilities {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Random rand = new Random();
    private static final String START = "production://start";
    private static final String STOP = "production://stop";
    private static final String ERROR = "Yarnbreak #error";




    public static String machineEventSerializer() throws JsonProcessingException {

        return MAPPER.writeValueAsString(randomEventGenerator());
    }

    public static String machineEventSerializer(MachineEvent event) {
        try {
            return MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    //counter: 000
    //stop? value = reason (verbal) : value (numeral)
    public static MachineEvent randomEventGenerator() {
        final int eventRoll = numberGenerator(0, 2);

        return new MachineEvent(
                machineNameGenerator(),
                counterGenerator(),
                (eventRoll == 0 ? START : STOP),
                (eventRoll == 0 ? String.valueOf(counterGenerator()) : ERROR)
        );
    }

    public static String prettyJson(Object o) {
        try{
            return MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }


    //XX0000.0000-000
    private static String machineNameGenerator() {

        final int first = numberGenerator(2, 7) * 1000;
        final int second = numberGenerator(2000,9000);
        final int third = numberGenerator(10, 100);

        return "AK" + first + "." + second + "-0" + third;
    }

    private static int counterGenerator() {
        return numberGenerator(100, 9000);
    }

    private static int numberGenerator(int lowIncluded, int highExcluded) {

        return rand.nextInt(highExcluded - lowIncluded) + lowIncluded;
    }



}
