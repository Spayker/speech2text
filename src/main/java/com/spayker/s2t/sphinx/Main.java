package com.spayker.s2t.sphinx;

import com.spayker.s2t.sphinx.keyboard.GlobalKeyListener;
import com.spayker.s2t.sphinx.recognition.SoundRecognizer;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws NativeHookException, IOException {
        // Clear previous logging configurations.
        LogManager.getLogManager().reset();

        Logger cmRootLogger = Logger.getLogger("default.config");
        cmRootLogger.setLevel(java.util.logging.Level.OFF);
        String conFile = System.getProperty("java.util.logging.config.file");
        if (conFile == null) {
            System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
        }

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(new SoundRecognizer()));
    }



}
