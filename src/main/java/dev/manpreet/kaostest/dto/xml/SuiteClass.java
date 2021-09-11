package dev.manpreet.kaostest.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "class")
public class SuiteClass {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
}
