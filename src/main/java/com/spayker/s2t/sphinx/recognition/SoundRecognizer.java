package com.spayker.s2t.sphinx.recognition;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundRecognizer {

    private final LiveSpeechRecognizer recognizer;

    /**
     * Checks if the speech recognise is already running
     */
    private boolean speechRecognizerThreadRunning;

    private String speechRecognitionResult;

    /**
     * This executor service is used in order the playerState events to be executed in an order
     */
    private final ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

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

    public synchronized void startSpeechRecognition() {

        //Check lock
        if (speechRecognizerThreadRunning) {
            System.out.println("Speech Recognition Thread already running...");
        } else {
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {

                //locks
                setSpeechRecognizerThreadRunning(true);

                //Start Recognition
                recognizer.startRecognition(true);

                //Information
                System.out.println("You can start to speak...");

                try {
                    while (speechRecognizerThreadRunning) {
                        /*
                         * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
                         */
                        SpeechResult speechResult = recognizer.getResult();
                        //Check the result
                        if (speechResult == null) {
                            System.out.println("I can't understand what you said.");
                        } else {
                            //Get the hypothesis
                            speechRecognitionResult = speechResult.getHypothesis();

                            //You said?
                            System.out.println("You said: [" + speechRecognitionResult + "]\n");

                            //Call the appropriate method
                            makeDecision(speechRecognitionResult, speechResult.getWords());
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    speechRecognizerThreadRunning = false;
                }
                System.out.println("SpeechThread has exited...");
            });
        }
    }

    public void makeDecision(String speech, List<WordResult> speechWords) {
        System.out.println(speech);
        System.out.println(speechWords);
    }

    public synchronized void setSpeechRecognizerThreadRunning(boolean speechRecognizerThreadRunning) {
        this.speechRecognizerThreadRunning = speechRecognizerThreadRunning;
    }
}
