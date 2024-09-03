package dev.manpreet.kaostest.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.manpreet.kaostest.KaosException;
import dev.manpreet.kaostest.dto.testng.suite.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Holder for some utility methods required to process the suite XML and the classes defined within.
 */
@Slf4j
public class SuiteXMLUtils {

    private SuiteXMLUtils() {}

    public static Suite deserializeSuiteXML(String xmlPath) {
        if (xmlPath == null || xmlPath.isBlank()) {
            log.error("Suite XML path is null or blank");
            throw new KaosException("Suite XML path is null or blank");
        }
        File xmlFile = new File(xmlPath);
        if (!(xmlFile.exists() && xmlFile.canRead())) {
            log.error("Cannot read provided XML at: " + xmlPath);
            log.error("File exists: " + xmlFile.exists());
            log.error("File is readble: " + xmlFile.canRead());
            throw new KaosException("Cannot read provided XML at: " + xmlPath);
        }
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
            return xmlMapper.readValue(xmlFile, Suite.class);
        } catch (IOException e) {
            log.error("Exception occurred while deserialising provided suite XML", e);
            throw new KaosException("Cannot deserialize provided XML at: " + xmlPath);
        }
    }

    public static void validateSuite(Suite suite) {
        log.debug(suite.toString());
        if (suite.getName() == null || suite.getName().isBlank()) {
            throw new KaosException("Suite name was not provided in the Suite XML");
        }
        List<SuiteClass> testClasses = suite.getAllTestClasses();
        List<SuitePackage> testPackages = suite.getAllTestPackages();
        if (testClasses.isEmpty() && testPackages.isEmpty()) {
            throw new KaosException("No classes or packages defined in the Suite XML");
        }
        List<String> allTestClasses = new ArrayList<>();
        testClasses.forEach(eachClass -> {
            if (eachClass.getName() == null || eachClass.getName().isBlank()) {
                throw new KaosException("Invalid test class reference with missing name in the Suite XML");
            }
            allTestClasses.add(eachClass.getName());
        });
        testPackages.forEach(eachPackage -> {
            if (eachPackage.getName() == null || eachPackage.getName().isBlank()) {
                throw new KaosException("Invalid test package reference with missing name in the Suite XML");
            }
        });
        //No way to check if packages specified in suite are valid
        areAllClassesValid(allTestClasses, "Test");
        List<String> classCoordinates;
        if (suite.getListener() != null && !suite.getListener().isEmpty()) {
            classCoordinates = suite.getListener().stream().
                    map(SuiteListener::getClassName).
                    collect(Collectors.toList());
            areAllClassesValid(classCoordinates, "Listener");
        }
    }

    private static void areAllClassesValid(List<String> classNames, String classType) {
        for (String eachClassName: classNames) {
            try {
                Class.forName(eachClassName);
            } catch (ClassNotFoundException e) {
                throw new KaosException(classType + " class " + eachClassName + " does not exist", e);
            }
        }
    }

//    public static List<String> getAllTestClasses(Suite suite) {
//        return suite.getTest().getClass_().stream().
//                map(SuiteClass::getName).
//                collect(Collectors.toList());
//        return new ArrayList<>();
//    }
//
//    public static List<String> getAllListenerClasses(Suite suite) {
//        return suite.getListener().stream().
//                map(SuiteListener::getClassName).
//                collect(Collectors.toList());
//    }
//
    public static List<Class<?>> getClassFromName(List<String> classNames) {
        List<Class<?>> classes = new ArrayList<>();
        classNames.forEach(name -> {
            try {
                classes.add(Class.forName(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classes;
    }
}
