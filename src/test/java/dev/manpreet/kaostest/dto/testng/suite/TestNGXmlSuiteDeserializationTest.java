package dev.manpreet.kaostest.dto.testng.suite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import dev.manpreet.kaostest.util.SuiteXMLUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

//Also tests dev.manpreet.kaostest.util.SuiteXMLUtils.deserializeSuiteXML
public class TestNGXmlSuiteDeserializationTest {

    @Test
    public void testSuiteClassesOnly() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(getFilePath("testsuite_classes.xml"));
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
    }

    private String getFilePath(String filename) {
        URL resource = getClass().getClassLoader().getResource(filename);
        File file = Paths.get(resource.getPath()).toFile();
        return file.getAbsolutePath();
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
