package dev.manpreet.kaostest.stores;

import dev.manpreet.kaostest.exception.KaosException;
import dev.manpreet.kaostest.stores.base.BaseStore;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Store extends BaseStore {

    private static Store store;
    @Getter
    private Map<String, TestPackageData> packagesData;

    private Store() {
        super("ALL");
        packagesData = new HashMap<>();
    }

    public static Store getInstance() {
        if (store == null) {
            store = new Store();
        }
        return store;
    }

    public void addNewTest(String testPackage, String testClass, String testMethod) {
        if (!packagesData.containsKey(testPackage)) {
            addNewTestPackage(testPackage, testClass, testMethod);
            return;
        }
        if (!isExistingClass(testPackage, testClass)) {
            addNewTestClass(testPackage, testClass, testMethod);
            return;
        }
        if (!isExistingMethod(testPackage, testClass, testMethod)) {
            addNewTestMethod(testPackage, testClass, testMethod);
        }
    }

    public String getRandomTestClass() throws KaosException {
        String packageName = packagesData.keySet().stream().findAny().orElse("");
        String className = packagesData.get(packageName).getTestClassesData().keySet()
                .stream().findAny().orElse("");
        String fullClassName = packageName + className;
        if (fullClassName.isBlank()) {
            throw new KaosException("No packages found in the store");
        }
        log.debug("Returning random test class {}", fullClassName);
        return fullClassName;
    }

    public void clear() {
        packagesData = new HashMap<>();
    }

    private void addNewTestPackage(String testPackage, String testClass, String testMethod) {
        TestMethodData testMethodData = new TestMethodData(testMethod);
        TestClassData testClassData = new TestClassData(testClass);
        TestPackageData testPackageData = new TestPackageData(testPackage);
        testPackageData.addTestClass(testClassData);
        testClassData.addTestMethod(testMethodData);
        testPackageData.setSubscriber(this);
        packagesData.put(testPackage, testPackageData);
    }

    private boolean isExistingClass(String testPackage, String testClass) {
        return packagesData.get(testPackage).getTestClassesData().containsKey(testClass);
    }

    private void addNewTestClass(String testPackage, String testClass, String testMethod) {
        TestMethodData testMethodData = new TestMethodData(testMethod);
        TestClassData testClassData = new TestClassData(testClass);
        packagesData.get(testPackage).addTestClass(testClassData);
        testClassData.addTestMethod(testMethodData);
    }

    private boolean isExistingMethod(String testPackage, String testClass, String testMethod) {
        return packagesData.get(testPackage).getTestClassesData().get(testClass).getTestMethodsData()
                .containsKey(testMethod);
    }

    private void addNewTestMethod(String testPackage, String testClass, String testMethod) {
        TestMethodData testMethodData = new TestMethodData(testMethod);
        packagesData.get(testPackage).getTestClassesData().get(testClass).addTestMethod(testMethodData);
    }

}
