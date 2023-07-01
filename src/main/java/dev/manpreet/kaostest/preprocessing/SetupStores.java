package dev.manpreet.kaostest.preprocessing;

import dev.manpreet.kaostest.stores.Store;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;

public class SetupStores {

    private final Store store;

    public SetupStores() {
        store = Store.getInstance();
    }

    public void addTests(String packageOrClassName, boolean isClass) {
        if (isClass) {
            packageOrClassName = packageOrClassName.substring(0, packageOrClassName.lastIndexOf("."));
        }
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage(packageOrClassName)
                .setScanners(Scanners.MethodsAnnotated));
        Set<Method> testMethods = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
        Set<String> testMethodNames = testMethods.stream()
                .map(method -> method.getDeclaringClass() + "." + method.getName())
                .collect(Collectors.toSet());
        testMethodNames.forEach(this::updateStore);
    }

    private void updateStore(String testMethodName) {
        testMethodName = testMethodName.split(" ")[1];
        String testClassName = testMethodName.substring(0, testMethodName.lastIndexOf("."));
        testMethodName = testMethodName.substring(testMethodName.lastIndexOf(".") + 1);
        String testPackageName = testClassName.substring(0, testClassName.lastIndexOf("."));
        testClassName = testClassName.substring(testClassName.lastIndexOf(".") + 1);
        store.addNewTest(testPackageName, testClassName, testMethodName);
    }
}
