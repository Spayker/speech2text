package com.spayker.s2t.sphinx;

import com.spayker.s2t.sphinx.recognition.SoundRecognizer;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static LiveSpeechRecognizer recognizer;

    /**
     * Checks if the resources Thread is already running
     */
    private static boolean resourcesThreadRunning;

    private static String speechRecognitionResult;

    /**
     * Checks if the speech recognise is already running
     */
    private static boolean speechRecognizerThreadRunning;

    private static boolean ignoreSpeechRecognitionResults;


    /**
     * This executor service is used in order the playerState events to be executed in an order
     */
    private static ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws NativeHookException, IOException {
        // Clear previous logging configurations.
        LogManager.getLogManager().reset();

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        recognizer = new SoundRecognizer().getRecognizer();

        startResourcesThread();
        startSpeechRecognition();
        //GlobalScreen.registerNativeHook();
        //GlobalScreen.addNativeKeyListener(new GlobalKeyListener(recognizer.getRecognizer()));
    }

    public static synchronized void startSpeechRecognition() {

        //Check lock
        if (speechRecognizerThreadRunning) {
            System.out.println("Speech Recognition Thread already running...");
        } else {
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {

                //locks
                speechRecognizerThreadRunning = true;
                ignoreSpeechRecognitionResults = false;

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

                        //Check if we ignore the speech recognition results
                        if (!ignoreSpeechRecognitionResults) {

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
                        } else {
                            System.out.println("Ingoring Speech Recognition Results...");
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

    /**
     * Starting a Thread that checks if the resources needed to the SpeechRecognition library are available
     */
    public static void startResourcesThread() {

        //Check lock
        if (resourcesThreadRunning) {
            System.out.println("Resources Thread already running...");
        } else {
            //Submit to ExecutorService
            eventsExecutorService.submit(() -> {
                try {

                    //Lock
                    resourcesThreadRunning = true;

                    // Detect if the microphone is available
                    while (true) {

                        //Is the Microphone Available
                        if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
                            System.out.println("Microphone is not available.");

                        // Sleep some period
                        Thread.sleep(350);
                    }

                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                    resourcesThreadRunning = false;
                }
            });
        }
    }

    public static void makeDecision(String speech, List<WordResult> speechWords) {

        System.out.println(speech);
        System.out.println(speechWords);

    }

}
