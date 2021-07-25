package com.spayker.s2t.picovoice;

import ai.picovoice.picovoice.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

public class Main {


    private static final String keywordPath = "src/main/resources/picovoice/jarvis_windows.ppn"; // wake
    private static final String contextPath = "src/main/resources/picovoice/smart_lighting_windows.rhn"; // context vocabulary

    private static final PicovoiceWakeWordCallback wakeWordCallback = () -> {
        System.out.println("Wake word detected!");
        // let user know wake word was detected
    };

    private static final PicovoiceInferenceCallback inferenceCallback = inference -> {
        if (inference.getIsUnderstood()) {
            final String intent = inference.getIntent();
            final Map<String, String> slots = inference.getSlots();
            // use intent and slots to trigger action
        }
    };

    public static void main(String[] args) throws PicovoiceException {

        Picovoice picovoice = new Picovoice.Builder()
                .setKeywordPath(keywordPath)
                .setWakeWordCallback(wakeWordCallback)
                .setContextPath(contextPath)
                .setInferenceCallback(inferenceCallback)
                .build();

        // get default audio capture device
        AudioFormat format = new AudioFormat(16000f, 16, 1, true, false);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine micDataLine;
        try {
            micDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            micDataLine.open(format);
        } catch (LineUnavailableException e) {
            System.err.println("Failed to get a valid audio capture device.");
            return;
        }

        // buffers for processing audio
        short[] picovoiceBuffer = new short[picovoice.getFrameLength()];
        ByteBuffer captureBuffer = ByteBuffer.allocate(picovoice.getFrameLength() * 2);
        captureBuffer.order(ByteOrder.LITTLE_ENDIAN);

        int numBytesRead;
        boolean recordingCancelled = true;
        micDataLine.start();

        while (recordingCancelled) {

            // read a buffer of audio
            numBytesRead = micDataLine.read(captureBuffer.array(), 0, captureBuffer.capacity());

            // don't pass to Picovoice if we don't have a full buffer
            if (numBytesRead != picovoice.getFrameLength() * 2) {
                continue;
            }

            // copy into 16-bit buffer
            captureBuffer.asShortBuffer().get(picovoiceBuffer);

            // process with picovoice
            picovoice.process(picovoiceBuffer);
        }


    }

}
