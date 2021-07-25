package com.spayker.s2t.sphinx.keyboard;

import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private LiveSpeechRecognizer liveSpeechRecognizer;
    private SpeechResult result;

    private GlobalKeyListener() { }

    public GlobalKeyListener(LiveSpeechRecognizer liveSpeechRecognizer) {
        this.liveSpeechRecognizer = liveSpeechRecognizer;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl pressed");

            // Start recognition process pruning previously cached data.
            liveSpeechRecognizer.startRecognition(true);
            System.out.println("Start talking");
            result = liveSpeechRecognizer.getResult();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl released");

            liveSpeechRecognizer.stopRecognition();
            System.out.println("Stop talking");
            // Print utterance string without filler words.
            System.out.println(result.getHypothesis());

            // Get individual words and their times.
            //for (WordResult r : result.getWords()) {
            //    System.out.println(r);
            //}
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}

}
