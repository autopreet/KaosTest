package dev.manpreet.rpdtest.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "listeners")
public class SuiteListeners {

    private SuiteListener listener;
}
