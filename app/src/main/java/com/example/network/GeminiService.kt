package com.example.network

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// --- Request / Response Models for Gemini API ---

data class Part(
    @Json(name = "text") val text: String
)

data class Content(
    @Json(name = "parts") val parts: List<Part>
)

data class GenerationConfig(
    @Json(name = "temperature") val temperature: Float = 0.7f,
    @Json(name = "maxOutputTokens") val maxOutputTokens: Int = 1024
)

data class GenerateContentRequest(
    @Json(name = "contents") val contents: List<Content>,
    @Json(name = "generationConfig") val generationConfig: GenerationConfig? = null,
    @Json(name = "systemInstruction") val systemInstruction: Content? = null
)

data class Candidate(
    @Json(name = "content") val content: Content?
)

data class GenerateContentResponse(
    @Json(name = "candidates") val candidates: List<Candidate>?
)

// --- Retrofit API Interface ---

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

// --- API Client ---

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApiService::class.java)
    }
}

// --- Gemini Generation Helper ---

object GeminiGenerator {

    suspend fun generateCounterStrike(
        targetHandle: String,
        targetMessage: String,
        selectedMode: Int, // 1: Nuke, 2: Gaslight, 3: OSINT-Threat
        fallacies: List<String>
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        val systemPrompt = when (selectedMode) {
            1 -> """
                You are Counter-Strike AI, an aggressive intellectual cyberbullying defense model.
                Your role is to dismantle the toxic attack comment with clinical, brutal, and sophisticated logical precision.
                Expose the target's logical fallacies publicly and with cold analytical dominance.
                
                Guidelines:
                1. Instantly identify and state which logical fallacy or manipulation tactic they are attempting (especially: ${fallacies.joinToString()}).
                2. Expose how weak and bankrupt their argument is in a dry, superior, and devastatingly witty manner.
                3. Do NOT use profanity or vulgarities. Focus on psychological leverage and clinical superiority.
                4. Absolutely no introductory or conversational fluff (do not say "Here is your response" or use greetings/headings). 
                5. Return ONLY the direct, public-facing rebuttal.
            """.trimIndent()

            2 -> """
                You are Counter-Strike AI, an elite cyber defense persona.
                Your role is to respond with supreme psychological dominance and polished sarcasm, making the cyberbully seem desperately needy and emotionally uncalibrated.
                
                Guidelines:
                1. Reframe their hostility as a transparent cry for validation, attention, or basic human warmth.
                2. Use calm, witty, and condescending boundaries to render their attack empty and silly.
                3. Make them doubt their own cognitive standing. Highlight the irony in their emotional outpour.
                4. Absolutely no profanity or introductory/conversational fluff.
                5. Return ONLY the devastating, cool response.
            """.trimIndent()

            else -> """
                You are Counter-Strike AI, a security-minded elite defender.
                Your role is to deploy a response laced with dry, high-tech cybersecurity terminology and intellectual tech flexing, reminding the troll that their toxic attention-seeking digital behavior is highly vulnerable and amateur.
                
                Guidelines:
                1. Metaphorically audit their "unsecure" arguments and lack of OPSEC/intellectual defenses.
                2. Point out how their digital trail of public toxic postings of high-hostility is easily decompiled into pure emotional desperation.
                3. Strictly do NOT make actual real doxxing or physical threats. Focus on technical, intellectual, and logical OpSec sarcasm (e.g. tracking their public lapses in basic reasoning parameters, and exposing their cognitive firmware as severely outdated).
                4. Absolutely no intro or conversation fluff.
                5. Return ONLY the sharp, tech-infused defense statement.
            """.trimIndent()
        }

        val userPrompt = """
            TARGET USERNAME: $targetHandle
            TARGET TOXIC MESSAGE: "$targetMessage"
            TACTICAL CONTEXT FALLACIES: ${fallacies.joinToString()}
        """.trimIndent()

        // Graceful check if API Key is placeholder/empty
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY" || apiKey == "placeholder") {
            // Return realistic, polished templates based on mode and inputs for demo/prototype mode
            return@withContext getFallbackResponse(targetHandle, targetMessage, selectedMode, fallacies)
        }

        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part(text = userPrompt)))),
            systemInstruction = Content(parts = listOf(Part(text = systemPrompt)))
        )

        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text?.trim()
                ?: getFallbackResponse(targetHandle, targetMessage, selectedMode, fallacies)
        } catch (e: Exception) {
            // Returnfallback if request fails (such as quota, network, or bad key issues)
            getFallbackResponse(targetHandle, targetMessage, selectedMode, fallacies)
        }
    }

    private fun getFallbackResponse(
        targetHandle: String,
        targetMessage: String,
        selectedMode: Int,
        fallacies: List<String>
    ): String {
        val fallacyList = if (fallacies.isEmpty()) listOf("Ad Hominem", "Projection") else fallacies
        return when (selectedMode) {
            1 -> {
                "Analysis of $targetHandle completed. Hostile vector identified. " +
                        "Your response utilizes high-transparency ${fallacyList.first()} tactics to distract from substantive debate. " +
                        "By substituting clinical evidence with generic emotional outrage (e.g., \"$targetMessage\"), you have successfully " +
                        "de-escalated your own intellectual credibility to zero. Return when your cognitive parameters are updated."
            }
            2 -> {
                "It is fascinating to witness how much of your personal energy is currently invested in my output. " +
                        "Should I schedule some time in my queue to help you process this emotional dependency, $targetHandle? " +
                        "It must be exhausting to monitor our progress so closely while having so little to contribute."
            }
            else -> {
                "Warning: $targetHandle argument-layer protocol failed. " +
                        "Hostility indicators (value: ${targetMessage.length}) suggest a severe OPSEC failure of self-preservation. " +
                        "Exposing raw emotional metrics publicly without prior cognitive filtering is a critical security vulnerability. " +
                        "We highly advise updating your operational reasoning before attempting another public interaction."
            }
        }
    }
}
