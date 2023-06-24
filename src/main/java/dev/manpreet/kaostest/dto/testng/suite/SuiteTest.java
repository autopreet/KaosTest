package dev.manpreet.kaostest.dto.testng.suite;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "test")
public class SuiteTest {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(localName = "class")
    @JacksonXmlElementWrapper(localName = "classes")
    private List<SuiteClass> classes_;
    @JacksonXmlProperty(localName = "package")
    @JacksonXmlElementWrapper(localName = "packages")
    private List<SuitePackage> packages_;
}
