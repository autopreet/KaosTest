package dev.manpreet.testutils;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class TestUtils {
    public static String getFilePath(String filename) {
        URL resource = TestUtils.class.getClassLoader().getResource(filename);
        File file = Paths.get(resource.getPath()).toFile();
        return file.getAbsolutePath();
    }
}
