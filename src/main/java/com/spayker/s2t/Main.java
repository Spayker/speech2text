package com.spayker.s2t;

import com.spayker.s2t.keyboard.GlobalKeyListener;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, NativeHookException {

        /////////////////////////////////////// recognition init part
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);

        /////////////////////////////////////// global keyboard listener
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());

        /////////////////////////////////////// recognition part
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(false);
        System.out.println("Start talking");
        SpeechResult result = recognizer.getResult();
        // Pause recognition process. It can be resumed then with startRecognition(false).
        recognizer.stopRecognition();
        System.out.println("Stop talking");
        // Print utterance string without filler words.
        System.out.println(result.getHypothesis());

        // Get individual words and their times.
        for (WordResult r : result.getWords()) {
            System.out.println(r);
        }
    }



}
