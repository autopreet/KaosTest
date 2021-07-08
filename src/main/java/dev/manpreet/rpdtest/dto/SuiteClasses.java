package dev.manpreet.rpdtest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "classes")
public class SuiteClasses {

    @JacksonXmlProperty(localName = "class")
    private List<SuiteClass> classes;
}
