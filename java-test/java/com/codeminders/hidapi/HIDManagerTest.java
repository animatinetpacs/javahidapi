package com.codeminders.hidapi;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HIDManagerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HIDManagerTest.class);

    @Test
    public void testListDevices() {
        String property = System.getProperty("java.library.path");
        LOGGER.debug("Library path: {}", property);
        try {
            assertTrue(ClassPathLibraryLoader.loadNativeHIDLibrary());
            HIDManager manager = HIDManager.getInstance();
            HIDDeviceInfo[] devs = manager.listDevices();
            LOGGER.debug("Devices:\n\n");
            for (int i = 0; i < devs.length; i++) {
                LOGGER.debug("{}.\t{}",i, devs[i]);
                LOGGER.debug("---------------------------------------------\n");
            }
            manager.finalize();
            System.gc();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
            fail(e.getMessage());
        } 
    }

}
