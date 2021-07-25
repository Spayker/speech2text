package com.spayker.s2t.sphinx.recognition;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.IOException;

public class SoundRecognizer {

    private final LiveSpeechRecognizer recognizer;

    public SoundRecognizer() throws IOException {

        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        configuration.setGrammarPath("src/main/resources/sphinx");
        configuration.setGrammarName("sgrammar");
        configuration.setUseGrammar(true);
        recognizer = new LiveSpeechRecognizer(configuration);
    }

    public LiveSpeechRecognizer getRecognizer() {
        return recognizer;
    }
}
