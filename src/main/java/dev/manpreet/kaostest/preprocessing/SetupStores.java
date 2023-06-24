package dev.manpreet.kaostest.preprocessing;

import dev.manpreet.kaostest.stores.Store;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class SetupStores {

    private Store store;

    public SetupStores() {
        store = Store.getInstance();
    }

    public void addTests(String packageOrClassName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage(packageOrClassName)
                .setScanners(Scanners.MethodsAnnotated));
        Set<Method> testMethods = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
        Set<String> testMethodNames = testMethods.stream()
                .map(method -> method.getDeclaringClass() + "." + method.getName())
                .collect(Collectors.toSet());
    }

    private void updateStore(String testMethodName) {
        String testClassName = testMethodName.substring(0, testMethodName.lastIndexOf("."));
        String testPackageName = testClassName.substring(9, testClassName.lastIndexOf("."));
        store.addNewTest(testPackageName, testClassName, testMethodName);
    }
}
