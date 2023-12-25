package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessorSwapField11Field12Test {

    private static final String FIELD_11 = "field11";
    private static final String FIELD_12 = "field12";

    @Test
    @DisplayName("Замена местами значений field11 и field12")
    void process() {
        Processor processor = new ProcessorSwapField11Field12();
        var message = new Message.Builder(1L)
                .field11(FIELD_11)
                .field12(FIELD_12)
                .build();

        var result = processor.process(message);

        assertEquals(result.getField11(), FIELD_12);
        assertEquals(result.getField12(), FIELD_11);
    }
}