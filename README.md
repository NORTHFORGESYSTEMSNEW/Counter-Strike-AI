# Counter-Strike AI

A modern, highly-stylized Jetpack Compose Android application acting as an "Aggressive Cyberbullying Defense Engine." It dismantles toxic arguments with logical precision by utilizing the Gemini API to formulate intelligent counter-arguments against online trolls, toxic comments, or logical fallacies.

## Features
- 🛡️ **Cyber Defense UI**: A cyberpunk, terminal-style interface built purely via Jetpack Compose.
- 🤖 **Gemini AI Engine**: Utilizes Gemini 3.5 Flash through Retrofit to dismantle arguments logically.
- 🎯 **Tactical Modes**: 
  - **Nuke Mode**: Pure intellectual and logical breakdown.
  - **Gaslight Mode**: Psychological dominance by framing the attacker as emotionally needy.
  - **OSINT-Threat Mode**: Tech-flex focusing on their OPSEC and emotional vulnerabilities.
- 🔗 **Share/Copy Payloads**: Easily copy generated responses to the clipboard or share them seamlessly via the Android share sheet.

## Project Structure
- Built with **Kotlin** and **Jetpack Compose**.
- Network requests are handled by **Retrofit** and **Moshi**.
- Utilizes the **Secrets Gradle Plugin** for API Key integration.

## How to Set Up (API Key Configuration)
This application uses the Gemini Pro API to dynamically generate responses. To build and run the app with real AI responses, follow these steps:

1. Obtain a Gemini API Key from Google AI Studio.
2. Open the **Secrets panel** in Google AI Studio on the right, or create a `.env` file in the root directory (alongside `build.gradle.kts`).
3. Add your key as follows:
   ```properties
   GEMINI_API_KEY="AIzaSyYourGeneratedGeminiKeyHere"
   ```
4. Build and deploy to your emulator or device! (If a valid key is not found, the app gracefully falls back to highly polished offline template responses).

## Building
Run the following Gradle command to compile an APK:
```bash
./gradlew assembleDebug
```
