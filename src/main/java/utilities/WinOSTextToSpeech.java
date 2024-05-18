package utilities;

import java.io.IOException;

public class WinOSTextToSpeech {
    private static final String DEFAULT_VOICE_NAME = "Microsoft David Desktop"; // You can change this to any installed voice

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
        String command = String.format("Add-Type –AssemblyName System.speech; " +
                "$speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                "$speak.SelectVoice('%s'); " +
                "$speak.Speak('%s');", voiceName, text);

        String[] powerShellCommand = {"powershell.exe", "-Command", command};

        try {
            Runtime.getRuntime().exec(powerShellCommand);
        } catch (IOException e) {
            System.err.println("Error executing text-to-speech command: " + e.getMessage());
            // Optionally, rethrow the exception if needed
            // throw new RuntimeException("Text-to-speech failed", e);
        }
    }

    public static void main(String[] args) {
        // Test the text-to-speech functionality
        speak("Hello, this is a test of the text-to-speech functionality on Windows.");
    }
}
