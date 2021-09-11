package dev.manpreet.rpdtest.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "suite")
public class Suite {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    private SuiteTest test;
    @JacksonXmlElementWrapper(localName = "listeners")
    private List<SuiteListener> listener;
}
