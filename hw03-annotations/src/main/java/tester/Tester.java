package tester;

import tester.annotations.After;
import tester.annotations.Before;
import tester.annotations.Test;
import tester.reflection.ReflectionHelper;
import tester.result.TestResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tester {

    public static void run(String className) {
        try {
            (new Tester()).process(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void process(String className) throws ClassNotFoundException {
        var clazz = Class.forName(className);
        var methodMap = makeMethodMap(clazz);
        var testResult = runTests(clazz, methodMap);
        printResult(testResult);
    }

    private static Map<Class<?>, List<Method>> makeMethodMap(Class<?> clazz) {
        var map = new HashMap<Class<?>, List<Method>>();
        var methods = clazz.getDeclaredMethods();
        fillByAnnotation(map, methods, Before.class);
        fillByAnnotation(map, methods, After.class);
        fillByAnnotation(map, methods, Test.class);
        return map;
    }

    private static void fillByAnnotation(Map<Class<?>, List<Method>> map,
                                         Method[] methods,
                                         Class<? extends Annotation> annotation) {
        map.put(annotation, getAnnotatedMethods(methods, annotation));
    }

    private static List<Method> getAnnotatedMethods(Method[] methods, Class<? extends Annotation> annotation) {
        return Arrays.stream(methods)
                .filter((Method method) -> method.isAnnotationPresent(annotation))
                .toList();
    }

    private static TestResult runTests(Class<?> processedClass, Map<Class<?>, List<Method>> mapMethods) {
        int successCount = 0;
        int errorCount = 0;

        for (var testMethod : mapMethods.get(Test.class)) {
            var obj = ReflectionHelper.instantiate(processedClass);
            invokeMethods(obj, mapMethods.get(Before.class));

            testMethod.setAccessible(true);
            try {
                testMethod.invoke(obj);
                successCount++;
                System.out.printf("Object %s: Test OK\n", formatHashCode(obj.hashCode()));
            } catch (Exception e) {
                System.out.printf("Object %s: Test FAIL\n", formatHashCode(obj.hashCode()));
                errorCount++;
            }

            invokeMethods(obj, mapMethods.get(After.class));
            System.out.println();
        }
        return new TestResult(successCount, errorCount);
    }

    private static void invokeMethods(Object obj, List<Method> methods) {
        for (var method : methods) {
            method.setAccessible(true);
            try {
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String formatHashCode(int hashCode) {
        return Integer.toHexString(hashCode);
    }

    private static void printResult(TestResult result) {
        System.out.println("Test results");
        System.out.println("Total: " + result.getTotalCount());
        System.out.println("Successful: " + result.getSuccessCount());
        System.out.println("Failed: " + result.getErrorCount());
    }
}
