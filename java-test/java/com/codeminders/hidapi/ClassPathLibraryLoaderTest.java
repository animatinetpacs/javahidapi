package com.codeminders.hidapi;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassPathLibraryLoaderTest {

    @Test
    public void testLoadNativeHIDLibrary() {
        assertTrue(ClassPathLibraryLoader.loadNativeHIDLibrary());
    }

}
