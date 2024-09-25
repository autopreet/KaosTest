package dev.manpreet.kaostest.dto.testng.suite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import dev.manpreet.kaostest.util.SuiteXMLUtils;
import dev.manpreet.testutils.TestUtils;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

//Also tests dev.manpreet.kaostest.util.SuiteXMLUtils.deserializeSuiteXML
public class TestNGXmlSuiteDeserializationTest {

    @Test
    public void multipleSuitesClassesOnly() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_classes.xml"));
        assertEquals(suite.getName(), "Demo Tests");
        Map<String, List<String>> suiteTestClasses = getSuiteTestClasses(suite.getTests());
        Map<String, List<String>> suiteTestPackages = getSuiteTestPackages(suite.getTests());
        assertTrue(suiteTestPackages.isEmpty());
        assertEquals(suiteTestClasses.size(), 2);
        Set<String> classes = new HashSet<>(suiteTestClasses.get("Demo Kaos Classes"));
        assertEquals(classes.size(), 2);
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassA"));
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassB"));
        classes = new HashSet<>(suiteTestClasses.get("More Demo Kaos Classes"));
        assertEquals(classes.size(), 2);
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassA"));
        assertTrue(classes.contains("dev.manpreet.demotests.moretests.TestClassC"));
        validateListeners(suite);
    }

    @Test
    public void multipleSuitesClassesAndPackages() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_classes_packages.xml"));
        assertEquals(suite.getName(), "Demo Tests");
        Map<String, List<String>> suiteTestClasses = getSuiteTestClasses(suite.getTests());
        Map<String, List<String>> suiteTestPackages = getSuiteTestPackages(suite.getTests());
        assertEquals(suiteTestClasses.size(), 1);
        assertEquals(suiteTestPackages.size(), 1);
        Set<String> classes = new HashSet<>(suiteTestClasses.get("Demo Kaos Classes"));
        assertEquals(classes.size(), 2);
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassA"));
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassB"));
        Set<String> packages = new HashSet<>(suiteTestPackages.get("Demo Kaos Packages"));
        assertEquals(packages.size(), 1);
        assertTrue(packages.contains("dev.manpreet.demotests.moretests"));
        validateListeners(suite);
    }

    @Test
    public void multipleSuitesPackagesOnly() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_packages.xml"));
        assertEquals(suite.getName(), "Demo Tests");
        Map<String, List<String>> suiteTestClasses = getSuiteTestClasses(suite.getTests());
        Map<String, List<String>> suiteTestPackages = getSuiteTestPackages(suite.getTests());
        assertTrue(suiteTestClasses.isEmpty());
        assertEquals(suiteTestPackages.size(), 2);
        Set<String> packages = new HashSet<>(suiteTestPackages.get("Demo Kaos Packages"));
        assertEquals(packages.size(), 1);
        assertTrue(packages.contains("dev.manpreet.demotests"));
        packages = new HashSet<>(suiteTestPackages.get("More Demo Kaos Packages"));
        assertEquals(packages.size(), 2);
        assertTrue(packages.contains("dev.manpreet.demotests"));
        assertTrue(packages.contains("dev.manpreet.demotests.moretests"));
        validateListeners(suite);
    }

    @Test
    public void singleSuiteClasses() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_singletest_classes.xml"));
        assertEquals(suite.getName(), "Demo Tests");
        Map<String, List<String>> suiteTestClasses = getSuiteTestClasses(suite.getTests());
        Map<String, List<String>> suiteTestPackages = getSuiteTestPackages(suite.getTests());
        assertTrue(suiteTestPackages.isEmpty());
        assertEquals(suiteTestClasses.size(), 1);
        Set<String> classes = new HashSet<>(suiteTestClasses.get("Demo Kaos Classes"));
        assertEquals(classes.size(), 3);
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassA"));
        assertTrue(classes.contains("dev.manpreet.demotests.TestClassB"));
        assertTrue(classes.contains("dev.manpreet.demotests.moretests.TestClassC"));
        validateListeners(suite);
    }

    @Test
    public void singleSuitePackages() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_singletest_packages.xml"));
        assertEquals(suite.getName(), "Demo Tests");
        Map<String, List<String>> suiteTestClasses = getSuiteTestClasses(suite.getTests());
        Map<String, List<String>> suiteTestPackages = getSuiteTestPackages(suite.getTests());
        assertTrue(suiteTestClasses.isEmpty());
        assertEquals(suiteTestPackages.size(), 1);
        Set<String> classes = new HashSet<>(suiteTestPackages.get("Demo Kaos Packages"));
        assertEquals(classes.size(), 2);
        assertTrue(classes.contains("dev.manpreet.demotests"));
        assertTrue(classes.contains("dev.manpreet.demotests.moretests"));
        validateListeners(suite);
    }

    private void validateListeners(Suite suite) {
        Set<String> listeners = suite.getListener().stream()
                .map(SuiteListener::getClassName)
                .collect(Collectors.toSet());
        assertEquals(listeners.size(), 2);
        assertTrue(listeners.contains("dev.manpreet.demotests.listeners.TestListener"));
        assertTrue(listeners.contains("dev.manpreet.demotests.listeners.AnotherListener"));
    }

    private Map<String, List<String>> getSuiteTestClasses(List<SuiteTest> suiteTests) {
        Map<String, List<String>> suiteTestClasses = new HashMap<>();
        for (SuiteTest suiteTest : suiteTests) {
            if (suiteTest.getClasses_() == null) {
                continue;
            }
            suiteTestClasses.put(suiteTest.getName(),
                    suiteTest.getClasses_().stream().map(SuiteClass::getName).toList());
        }
        return suiteTestClasses;
    }

    private Map<String, List<String>> getSuiteTestPackages(List<SuiteTest> suiteTests) {
        Map<String, List<String>> suiteTestPackages = new HashMap<>();
        for (SuiteTest suiteTest : suiteTests) {
            if (suiteTest.getPackages_() == null) {
                continue;
            }
            suiteTestPackages.put(suiteTest.getName(),
                    suiteTest.getPackages_().stream().map(SuitePackage::getName).toList());
        }
        return suiteTestPackages;
    }
}
