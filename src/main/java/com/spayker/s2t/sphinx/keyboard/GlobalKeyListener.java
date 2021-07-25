package com.spayker.s2t.sphinx.keyboard;

import com.spayker.s2t.sphinx.recognition.SoundRecognizer;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private SoundRecognizer soundRecognizer;
    private SpeechResult result;

    private GlobalKeyListener() { }

    public GlobalKeyListener(SoundRecognizer soundRecognizer) {
        this.soundRecognizer = soundRecognizer;
        soundRecognizer.startResourcesThread();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl pressed");

            // Start recognition process pruning previously cached data.
            soundRecognizer.startSpeechRecognition();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl released");

        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}

}
