package ru.autologger;

import com.google.common.base.Joiner;
import ru.autologger.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLoggingClass() {
        InvocationHandler handler = new LogInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class LogInvocationHandler implements InvocationHandler {

        private final TestLoggingInterface loggingClass;

        private final List<Method> loggingMethods;

        LogInvocationHandler(TestLoggingInterface loggingClass) {
            this.loggingClass = loggingClass;
            loggingMethods = getAnnotatedMethods(loggingClass.getClass().getDeclaredMethods(), Log.class);
        }

        private List<Method> getAnnotatedMethods(Method[] methods, Class<? extends Annotation> annotation) {
            return Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(annotation))
                    .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (loggingMethods.contains(loggingClass.getClass().getMethod(method.getName(), method.getParameterTypes()))) {
                System.out.println("executed method: " + method.getName() + ", param: " + Joiner.on(", ").join(args));
            }
            return method.invoke(loggingClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "loggingClass=" + loggingClass + '}';
        }
    }
}
