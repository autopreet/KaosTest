package dev.manpreet.rpdtest.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "test")
public class SuiteTest {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    private SuiteClasses classes;
}
