# VoiceAssistant

## Overview

This voice assistant application is designed to recognize user speech and respond with synthesized voice messages. Built using Microsoft's Cognitive Services Speech SDK, it showcases the seamless integration of speech-to-text and text-to-speech capabilities.

## Features

1. **Speech Recognition**: Converts user's spoken words into text.
2. **Intelligent Responses**: Processes the recognized text and generates relevant responses.
3. **Speech Synthesis**: Converts the generated text response back into human-like speech.

## Setup & Installation

### Prerequisites

- Ensure you have Java installed (JDK 8 or later recommended).
- An Azure account with Cognitive Services enabled.

### Installation

1. Clone the repository: `git clone <repository-url>`.
2. Navigate to the project directory.
3. Set up your Azure key and region as environment variables:
   ```
   export Azure key=YOUR_AZURE_KEY
   export Azure region=YOUR_AZURE_REGION
   ```
4. Build and run the project.

## Usage

1. Run the application.
2. Speak into the microphone after the prompt.
3. The application will recognize your speech, process it, and respond audibly.

Some example commands include:
- "hello"
- "how are you"
- "good night"
