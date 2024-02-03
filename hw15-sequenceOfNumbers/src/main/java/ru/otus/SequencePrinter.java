package ru.otus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SequencePrinter {
    private String last = "THREAD 2";

    public static void main(String[] args) {
        new SequencePrinter().go();
    }

    private void go() {
        new Thread(this::print, "THREAD 1").start();
        new Thread(this::print, "THREAD 2").start();
    }

    private synchronized void print() {
        var sequenceGenerator = new SequenceGenerator();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (last.equals(Thread.currentThread().getName())) {
                    this.wait();
                }
                last = Thread.currentThread().getName();
                var value = sequenceGenerator.getNext();
                log.info("Value: {}", value);
                sleep();
                this.notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static class SequenceGenerator {
        private static final int MIN = 1;
        private static final int MAX = 10;
        private int number = MIN;
        private boolean increase = true;

        public int getNext() {
            if (number == MAX) {
                increase = false;
            } else if (number == MIN) {
                increase = true;
            }
            return increase ? number++ : number--;
        }
    }
}