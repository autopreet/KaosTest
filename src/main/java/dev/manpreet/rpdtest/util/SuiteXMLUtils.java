package dev.manpreet.rpdtest.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.manpreet.rpdtest.RPDException;
import dev.manpreet.rpdtest.dto.xml.Suite;
import dev.manpreet.rpdtest.dto.xml.SuiteClass;
import dev.manpreet.rpdtest.dto.xml.SuiteListener;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class SuiteXMLUtils {

    public static Suite deserializeSuiteXML(String xmlPath) {
        if (xmlPath == null || xmlPath.isBlank()) {
            log.error("Suite XML path is null or blank");
            throw new RPDException("Suite XML path is null or blank");
        }
        File xmlFile = new File(xmlPath);
        if (!(xmlFile.exists() && xmlFile.canRead())) {
            log.error("Cannot read provided XML at: " + xmlPath);
            log.error("File exists: " + xmlFile.exists());
            log.error("File is readble: " + xmlFile.canRead());
            throw new RPDException("Cannot read provided XML at: " + xmlPath);
        }
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(xmlFile, Suite.class);
        } catch (IOException e) {
            log.error("Exception occurred while deserialising provided suite XML", e);
            throw new RPDException("Cannot deserialize provided XML at: " + xmlPath);
        }
    }

    public static boolean isSuiteValid(Suite suite) {
        if (suite.getName() == null && suite.getTest().getName() == null) {
            log.warn("None of suite name or test name was provided");
        }
        if (suite.getTest() == null || suite.getTest().getClasses() == null) {
            log.error("No tests specified");
            return false;
        }
        if (suite.getTest().getClasses().getClasses() == null || suite.getTest().getClasses().getClasses().isEmpty()) {
            log.error("No tests specified");
            return false;
        }
        List<String> classCoordinates;
        if (suite.getListeners() != null && suite.getListeners().getListener() != null && !suite.getListeners().getListener().isEmpty()) {
            classCoordinates = suite.getListeners().getListener().stream().
                    map(SuiteListener::getClassName).
                    collect(Collectors.toList());
            if (!areAllClassesValid(classCoordinates, "listener")) {
                log.error("One of the listener classes specified is invalid");
                return false;
            }
        }
        classCoordinates = suite.getTest().getClasses().getClasses().stream().
                map(SuiteClass::getName).
                collect(Collectors.toList());
        return areAllClassesValid(classCoordinates, "test");
    }

    private static boolean areAllClassesValid(List<String> classNames, String classType) {
        if (classNames.stream().anyMatch(Objects::isNull)) {
            log.error("One or more class names in " + classType + " classes is null.");
            return false;
        }
        for (String eachClassName: classNames) {
            try {
                Class.forName(eachClassName);
            } catch (ClassNotFoundException e) {
                log.error("Class with specified coordinates " + eachClassName + " not found in " + classType + " classes", e);
                return false;
            }
        }
        return true;
    }

    public static List<String> getAllTestClasses(Suite suite) {
        return suite.getTest().getClasses().getClasses().stream().
                map(SuiteClass::getName).
                collect(Collectors.toList());
    }
}
