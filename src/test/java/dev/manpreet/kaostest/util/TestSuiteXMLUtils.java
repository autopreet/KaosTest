package dev.manpreet.kaostest.util;

import dev.manpreet.kaostest.dto.testng.suite.Suite;
import dev.manpreet.kaostest.exception.KaosException;
import dev.manpreet.testutils.TestUtils;
import org.testng.annotations.Test;

//Test for deserializeSuiteXML method in dev.manpreet.kaostest.dto.testng.suite.TestNGXmlSuiteDeserializationTest
public class TestSuiteXMLUtils {

    @Test
    public void testAllValidSuitesArePassing() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_classes.xml"));
        SuiteXMLUtils.validateSuite(suite);
        suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_classes_packages.xml"));
        SuiteXMLUtils.validateSuite(suite);
        suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_packages.xml"));
        SuiteXMLUtils.validateSuite(suite);
        suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_singletest_classes.xml"));
        SuiteXMLUtils.validateSuite(suite);
        suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("testsuite_singletest_packages.xml"));
        SuiteXMLUtils.validateSuite(suite);
    }

    @Test(expectedExceptions = KaosException.class)
    public void testSuiteWithoutNameFails() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("invalid/no_suite_name.xml"));
        SuiteXMLUtils.validateSuite(suite);
    }

    @Test(expectedExceptions = KaosException.class)
    public void testSuiteWithoutAnyClassesPackagesFails() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("invalid/no_classes_packages.xml"));
        SuiteXMLUtils.validateSuite(suite);
    }

    @Test(expectedExceptions = KaosException.class)
    public void testSuiteWithMissingPackageName() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("invalid/missing_package_name.xml"));
        SuiteXMLUtils.validateSuite(suite);
    }

    @Test(expectedExceptions = KaosException.class)
    public void testSuiteWithMissingClassName() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("invalid/missing_class_name.xml"));
        SuiteXMLUtils.validateSuite(suite);
    }

    @Test(expectedExceptions = KaosException.class)
    public void testSuiteWithNonExistingClass() {
        Suite suite = SuiteXMLUtils.deserializeSuiteXML(TestUtils.getFilePath("invalid/non_existing_class.xml"));
        SuiteXMLUtils.validateSuite(suite);
    }
}
