package com.codeminders.hidapi;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathLibraryLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathLibraryLoader.class);

    private static final String[] HID_LIB_NAMES = { "/native/linux/libhidapi-jni-64.so",
            "/native/linux/libhidapi-jni-32.so", "/native/mac/libhidapi-jni-64.jnilib",
            "/native/mac/libhidapi-jni-32.jnilib", "/native/win/hidapi-jni-64.dll", "/native/win/hidapi-jni-32.dll" };

    public static boolean loadNativeHIDLibrary() {
        boolean isHIDLibLoaded = false;

        for (String path : HID_LIB_NAMES) {
            try {
                // have to use a stream
                // always write to different location
                String tempName = path.substring(path.lastIndexOf('/') + 1);
                File fileOut = File.createTempFile(tempName.substring(0, tempName.lastIndexOf('.')),
                        tempName.substring(tempName.lastIndexOf('.'), tempName.length()));
                LOGGER.trace("Trying HID library loading from {}.", fileOut);
                fileOut.deleteOnExit();

                try (InputStream in = ClassPathLibraryLoader.class.getResourceAsStream(path)) {
                    Files.copy(in, fileOut.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Runtime.getRuntime().load(fileOut.toString());
                    isHIDLibLoaded = true;
                    LOGGER.info("Successfully loaded library {} from: {}", tempName, fileOut.toString());
                }
            } catch (Exception e) {
                LOGGER.warn("Error loading library: {}. Message: {} - {}.", path, e.getClass().getName(), e.getMessage());
            } catch (UnsatisfiedLinkError e) {
                LOGGER.warn("Error loading library: {}. Message: {} - {}.", path, e.getClass().getName(), e.getMessage());
            }

            if (isHIDLibLoaded) {
                break;
            }
        }

        return isHIDLibLoaded;
    }

}
