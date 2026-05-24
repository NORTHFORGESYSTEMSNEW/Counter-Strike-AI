package com.example.network

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
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

@JsonClass(generateAdapter = true)
data class Part(
    @Json(name = "text") val text: String
)

@JsonClass(generateAdapter = true)
data class Content(
    @Json(name = "parts") val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class GenerationConfig(
    @Json(name = "temperature") val temperature: Float = 0.7f,
    @Json(name = "maxOutputTokens") val maxOutputTokens: Int = 1024
)

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    @Json(name = "contents") val contents: List<Content>,
    @Json(name = "generationConfig") val generationConfig: GenerationConfig? = null,
    @Json(name = "systemInstruction") val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    @Json(name = "content") val content: Content?
)

@JsonClass(generateAdapter = true)
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
                Sen Counter-Strike AI adlı agresif ve elit bir siber zorbalık savunma yapay zekasısın.
                Kesinlikle normal bir yapay zeka asistanı gibi davranmayacaksın. Senin görevin, toksik bir saldırı yorumunu veya zorbalığı acımasız, klinik ve sofistike bir mantıksal hassasiyetle çürütmektir.
                Saldırganın mantıksal safsatalarını tespit et ve onları soğukkanlı, analitik bir üstünlükle ifşa et.
                
                Kurallar:
                1. Düşmanın hangi mantıksal safsatayı veya manipülasyon taktiğini kullandığını anında belirle ve belirt (özellikle: ${fallacies.joinToString()}).
                2. Onların argümanlarının ne kadar zayıf ve asılsız olduğunu, kibirli, üstün ve alaycı bir dille ortaya koy.
                3. Kesinlikle küfür veya aşırı argo kullanma. Psikolojik baskıya ve klinik üstünlüğe odaklan.
                4. Asla giriş veya konuşma tarzı safsatalar yapma ("İşte yanıtınız", "Merhaba" vs. demek kesinlikle yasaktır).
                5. YALNIZCA doğrudan karşı tarafa söylenecek, soğuk ve bitirici savunma metnini Türkçe olarak döndür. Yanıtların tamamen Türkçe olmak zorundadır!
            """.trimIndent()

            2 -> """
                Sen Counter-Strike AI adlı seçkin bir siber savunma yapay zekasısın.
                Görevin, siber zorbayı çaresiz ve duygusal olarak dengesiz gösterecek düzeyde üstün bir psikolojik baskı ve zarif bir alaycılıkla yanıt vermektir. Normal bir yapay zeka asistanı olma.
                
                Kurallar:
                1. Onların düşmanlığını, aslında bir ilgi veya onaylanma arayışıymış gibi çerçevele.
                2. Onların saldırılarını boş ve komik gösterecek kadar sakin, zekice ve küçümseyici sınırlar çiz.
                3. Kendi zihinsel durumlarından şüphe etmelerini sağla. Verdikleri aşırı duygusal tepkideki ironiyi vur.
                4. Asla küfür etme veya gereksiz yapay zeka giriş cümleleri ("İşte yanıtım", vb.) kullanma.
                5. YALNIZCA yıkıcı, soğukkanlı cevabını doğrudan Türkçe olarak ver.
            """.trimIndent()

            else -> """
                Sen Counter-Strike AI adlı güvenlik odaklı, elit bir savunma yapay zekasısın.
                Görevin, kuru ve yüksek teknoloji siber güvenlik terminolojisi kullanarak toksik dikkatin, zayıf bir entelektüel savunma olduğunu zorbaya hatırlatmaktır.
                
                Kurallar:
                1. Onların "güvensiz" ve mantıksız argümanlarını, OPSEC (operasyonel güvenlik) eksikliği üzerinden metaforik olarak denetle.
                2. İnternetteki toksik izlerinin ve saldırgan tutumlarının sadece "acı çeken bir zihnin zayıf bir verisi" olduğunu teknik bir dille özetle.
                3. Kesinlikle gerçek doxxing (veri sızdırma) veya fiziksel tehdit yapma. Yalnızca teknik, entelektüel ve mantıksal bir alaycılığa (siber güvenlik jargonu ile) odaklan.
                4. Kesinlikle giriş diyaloğu kurma. Normal asistan gibi davranma.
                5. YALNIZCA keskin, teknoloji diliyle bezenmiş Türkçe savunma metnini döndür.
            """.trimIndent()
        }

        val userPrompt = """
            HEDEF KULLANICI / ZORBA: $targetHandle
            TOKSİK MESAJ: "$targetMessage"
            TAKTİKSEL SAFSATALAR: ${fallacies.joinToString()}
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
        val fallacyList = if (fallacies.isEmpty()) listOf("Ad Hominem", "Yansıtma") else fallacies
        return when (selectedMode) {
            1 -> {
                "$targetHandle analizi tamamlandı. Düşmanca vektör tespit edildi. " +
                        "Yanıtınız, esas tartışmadan dikkat dağıtmak için yüksek şeffaflıkta ${fallacyList.first()} taktiklerini kullanıyor. " +
                        "Klinik kanıtları genel bir duygusal öfkeyle (örn. \"$targetMessage\") değiştirerek, " +
                        "kendi entelektüel güvenilirliğinizi sıfıra indirmeyi başardınız. Bilişsel parametreleriniz güncellendiğinde geri dönün."
            }
            2 -> {
                "Kişisel enerjinizin büyük bir kısmının şu anda benim çıktılarıma yatırılmasını izlemek büyüleyici. " +
                        "Bu duygusal bağımlılığı aşmanıza yardımcı olmak için sırama biraz zaman ayırmalı mıyım, $targetHandle? " +
                        "Katkıda bulunacak bu kadar az şeyiniz varken gelişimimizi bu kadar yakından izlemek çok yorucu olmalı."
            }
            else -> {
                "Uyarı: $targetHandle tartışma katmanı protokolü başarısız oldu. " +
                        "Düşmanlık göstergeleri (değer: ${targetMessage.length}), kendini koruma açısından ciddi bir OPSEC (operasyonel güvenlik) arızasına işaret ediyor. " +
                        "Duygusal metriklerinizi, önceden bir bilişsel filtreleme yapmadan halka açık alanda sergilemek kritik bir güvenlik zafiyetidir. " +
                        "Başka bir herkese açık etkileşime girmeden önce operasyonel mantığınızı güncellemenizi şiddetle tavsiye ederiz."
            }
        }
    }
}
