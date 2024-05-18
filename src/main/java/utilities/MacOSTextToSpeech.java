package utilities;

import java.io.IOException;

public class MacOSTextToSpeech {
    private static final String DEFAULT_VOICE_NAME = "Ava";
    private static final String SAY_COMMAND = "/usr/bin/say";

    /**
     * Speaks the given text using the default voice.
     *
     * @param text the text to be spoken
     */
    public static void speak(String text) {
        speakWithVoice(DEFAULT_VOICE_NAME, text);
    }

    /**
     * Speaks the given text using the specified voice.
     *
     * @param voiceName the name of the voice to be used
     * @param text      the text to be spoken
     */
    public static void speakWithVoice(String voiceName, String text) {
        String[] command = {SAY_COMMAND, "-v", voiceName, text};
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            System.err.println("Error executing text-to-speech command: " + e.getMessage());
            // Optionally, rethrow the exception if needed
            // throw new RuntimeException("Text-to-speech failed", e);
        }
    }
}
