
//public class SpeechRecognition {
//    private static String speechKey = System.getenv("Azure key");
//    private static String speechRegion = System.getenv("Azure region");
//
//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
//        speechConfig.setSpeechRecognitionLanguage("en-US");
//        recognizeFromMicrophone(speechConfig);
//    }
//
//    public static void recognizeFromMicrophone(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
//        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
//        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
//
//        System.out.println("Speak into your microphone.");
//        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
//        SpeechRecognitionResult speechRecognitionResult = task.get();
//
//        if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
//            System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
//        }
//        else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
//            System.out.println("NOMATCH: Speech could not be recognized.");
//        }
//        else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
//            CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
//            System.out.println("CANCELED: Reason=" + cancellation.getReason());
//
//            if (cancellation.getReason() == CancellationReason.Error) {
//                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
//                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
//                System.out.println("CANCELED: Did you set the speech resource key and region values?");
//            }
//        }
//
//        System.exit(0);
//    }
//}
//
// ... [other imports]
package main;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class SpeechRecognition {
    private static String speechKey = System.getenv("Azure key");
    private static String speechRegion = System.getenv("Azure region");

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setSpeechRecognitionLanguage("en-US");
        recognizeFromMicrophone(speechConfig);
    }

    public static void recognizeFromMicrophone(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig);
        try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig)) {

            System.out.println("Speak into your microphone. Say 'exit' to quit.");

            while (true) {
                Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
                SpeechRecognitionResult speechRecognitionResult = task.get();

                String recognizedText = null;
                if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
                    recognizedText = speechRecognitionResult.getText().toLowerCase().trim();
                    System.out.println("You said: " + recognizedText);
                    recognizedText = recognizedText.replaceAll("[.,?!]", "").trim();
                    System.out.println("Recognized text length: " + recognizedText.length());
//                    for (char c : recognizedText.toCharArray()) {
//                        System.out.println("Char: [" + c + "]");
//                    }
                    if (recognizedText.equals("hello")) {
                        System.out.println("Recognized text matches 'hello'");
                    } else if (recognizedText.equals("how are you")) {
                        System.out.println("Recognized text matches 'how are you'");
                    } else {
                        System.out.println("No direct match found.");
                    }
                    if (recognizedText.equals("exit")) {
                        System.out.println("Goodbye!");
                        break;
                    }

                    String response = getBotResponse(recognizedText);
                    System.out.println("Bot: " + response);
                } else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
                    System.out.println("NOMATCH: Speech could not be recognized.");
                } else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
                    // ... [handle cancellations like before]
                }
                String response = getBotResponse(recognizedText);
//                System.out.println("Bot: " + response);

                // Convert the bot's response text into speech
                synthesizer.SpeakTextAsync(response);
//                break;
            }
        }
    }

    private static String getBotResponse(String input) {
        switch (input) {
            case "hello":
                return "Hi there!";
            case "how are you":
                return "I'm fine";//just a program, so I don't have feelings, but thanks for asking!";
            default:
                return "I'm not sure how to respond to that.";
        }
    }
}

