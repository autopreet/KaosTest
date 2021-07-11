package dev.manpreet.rpdtest.dto;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.manpreet.rpdtest.dto.xml.Suite;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Data
public class SuiteInput {

    private String suiteXMLPath;
    private int durationInMins;
    private int threadCount;

    public Optional<Suite> validate() {
        if (durationInMins < 5) {
            log.error("Duration for which to run the test/load cannot be less than 5 minutes.");
            return Optional.empty();
        }
        if (threadCount < 2) {
            log.error("Number of parallel threads in which to run the test/load cannot be less than 2.");
            return Optional.empty();
        }
        XmlMapper xmlMapper = new XmlMapper();
        try {
            String xmlContent = Files.readString(Paths.get(suiteXMLPath));
            Suite suite = xmlMapper.readValue(xmlContent, Suite.class);
            return Optional.of(suite);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Exception occurred when trying to read the suite XML file at " + suiteXMLPath, e);
        }
        return Optional.empty();
    }
}
