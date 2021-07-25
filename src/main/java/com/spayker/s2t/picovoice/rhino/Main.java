package com.spayker.s2t.picovoice.rhino;

import ai.picovoice.rhino.Rhino;
import ai.picovoice.rhino.RhinoException;
import ai.picovoice.rhino.RhinoInference;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws RhinoException {

        // Create an instance of Rhino
        Rhino handle = new Rhino.Builder().setContextPath("/PATH/TO/YOUR/CONTEXT_MODEL").build();

        // Process audio
        while (true) {
            boolean isFinalized = handle.process(null);
            if(isFinalized) {
                RhinoInference inference = handle.getInference();
                if(inference.getIsUnderstood()) {
                    String intent = inference.getIntent();
                    Map<String, String> slots = inference.getSlots();
                    // Insert inference logic here
                }
            }
        }


    }

}
