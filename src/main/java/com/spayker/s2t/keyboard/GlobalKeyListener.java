package com.spayker.s2t.keyboard;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private boolean isCtrlDown;

    private GlobalKeyListener() { }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl pressed");
            isCtrlDown = true;
        }

        if (e.getKeyCode() == NativeKeyEvent.VC_V && isCtrlDown) {
            System.out.println("User hit Ctrl+V");
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R) {
            System.out.println("Ctrl released");
            isCtrlDown = false;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}

}
