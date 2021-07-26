package com.spayker.s2t.sphinx.keyboard;

import com.spayker.s2t.sphinx.recognition.SoundRecognizer;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private SoundRecognizer soundRecognizer;

    private boolean isPressed;

    private GlobalKeyListener() { }

    public GlobalKeyListener(SoundRecognizer soundRecognizer) {
        this.soundRecognizer = soundRecognizer;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!isPressed && e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl pressed");

            // Start recognition process pruning previously cached data.
            soundRecognizer.startSpeechRecognition();
            isPressed = true;
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (isPressed && e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl released");
            isPressed = false;
            soundRecognizer.setSpeechRecognizerThreadRunning(false);
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}

}
