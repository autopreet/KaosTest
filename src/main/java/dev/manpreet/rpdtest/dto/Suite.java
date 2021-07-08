package dev.manpreet.rpdtest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "suite")
public class Suite {

    private SuiteTest test;
    private SuiteListeners listeners;
}
