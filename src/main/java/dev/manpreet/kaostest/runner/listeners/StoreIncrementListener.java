package dev.manpreet.kaostest.runner.listeners;

import dev.manpreet.kaostest.stores.Store;
import dev.manpreet.kaostest.stores.TestInstanceData;
import dev.manpreet.kaostest.stores.base.Status;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Built-in listener which increments the data store for each test execution.
 */
@Slf4j
public class StoreIncrementListener extends TestListenerAdapter {

    private final Store store;

    public StoreIncrementListener() {
        store = Store.getInstance();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        addNewInstance(result.getMethod().getQualifiedName(), Status.FAIL, result.getStartMillis(),
                result.getEndMillis());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        addNewInstance(result.getMethod().getQualifiedName(), Status.PASS, result.getStartMillis(),
                result.getEndMillis());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        addNewInstance(result.getMethod().getQualifiedName(), Status.SKIP, result.getStartMillis(),
                result.getEndMillis());
    }

    private void addNewInstance(String testMethodName, Status status, long startTimeMillis, long endTimeMillis) {
        String testClassName = testMethodName.substring(0, testMethodName.lastIndexOf("."));
        String testPackageName = testClassName.substring(0, testClassName.lastIndexOf("."));
        TestInstanceData testInstanceData = new TestInstanceData(status, startTimeMillis, endTimeMillis);
        store.getPackagesData().get(testPackageName).getTestClassesData().get(testClassName).getTestMethodsData()
                .get(testMethodName).addNewInstance(testInstanceData);
    }
}
