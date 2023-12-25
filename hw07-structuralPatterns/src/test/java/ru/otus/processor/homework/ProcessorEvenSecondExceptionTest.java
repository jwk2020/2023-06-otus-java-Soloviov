package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.exception.EvenSecondException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessorEvenSecondExceptionTest {

    public static final LocalDateTime ODD_SECOND = LocalDateTime.of(2023, Month.DECEMBER, 1, 1, 1, 1);
    public static final LocalDateTime EVEN_SECOND = LocalDateTime.of(2023, Month.DECEMBER, 1, 1, 1, 2);

    @Test
    @DisplayName("Вызов в нечетную секунду")
    public void processAtOddSecond() {
        Processor processor = new ProcessorEvenSecondException(() -> ODD_SECOND);
        assertDoesNotThrow(() -> processor.process(null));
    }

    @Test
    @DisplayName("Выбрасывание исключение при вызове в четную секунду")
    public void processAtEvenSecond() {
        Processor processor = new ProcessorEvenSecondException(() -> EVEN_SECOND);
        assertThrows(EvenSecondException.class, () -> processor.process(null));
    }
}