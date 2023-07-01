package dev.manpreet.kaostest.dto.testng.suite;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "suite")
public class Suite {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(localName = "test")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SuiteTest> tests;
    @JacksonXmlElementWrapper(localName = "listeners")
    private List<SuiteListener> listener;

    public List<SuiteClass> getAllTestClasses() {
        List<SuiteClass> testClasses = new ArrayList<>();
        if (tests != null) {
            tests.forEach(eachTestSuite -> {
                if (eachTestSuite.getClasses_() != null) {
                    testClasses.addAll(eachTestSuite.getClasses_());
                }
            });
        }
        return testClasses;
    }

    public List<SuitePackage> getAllTestPackages() {
        List<SuitePackage> testPackages = new ArrayList<>();
        if (tests != null) {
            tests.forEach(eachTestSuite -> {
                if (eachTestSuite.getPackages_() != null) {
                    testPackages.addAll(eachTestSuite.getPackages_());
                }
            });
        }
        return testPackages;
    }
}
