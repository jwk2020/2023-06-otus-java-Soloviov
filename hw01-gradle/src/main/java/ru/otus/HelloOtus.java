package ru.otus;

import com.google.common.base.Joiner;

/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/gradleHelloOtus-0.1.jar
 * <p>
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l gradleHelloOtus-0.1.jar
 */
public class HelloOtus {

    public static void main(String... args) {
        System.out.println(Joiner.on("_").skipNulls().join("H", "E", null, "L", "L", null, "O"));
    }
}
