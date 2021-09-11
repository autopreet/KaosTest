package dev.manpreet.kaostest.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "listener")
public class SuiteListener {

    @JacksonXmlProperty(localName = "class-name", isAttribute = true)
    private String className;
}
