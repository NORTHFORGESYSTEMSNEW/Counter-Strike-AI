package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.GeminiGenerator
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Color tokens matching the "Sophisticated Dark" HTML theme
val BgDark = Color(0xFF0A0A0A)
val CardDark = Color(0xFF141414)
val CardDarkLighter = Color(0xFF1A1A1A)
val RedAccent = Color(0xFFDC2626)
val RedGlow = Color(0xFFEF4444)
val IndigoAccent = Color(0xFF818CF8)
val EmeraldAccent = Color(0xFF34D399)
val SlatedGray = Color(0xFF64748B)
val LightSlate = Color(0xFFE2E8F0)

data class HostilePreset(
    val username: String,
    val message: String,
    val hostility: String,
    val likelyFallacies: List<String>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme(dynamicColor = false, darkTheme = true) {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CounterStrikeDashboard(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BgDark)
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterStrikeDashboard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    // Screen state
    var targetUsername by remember { mutableStateOf("") }
    var targetMessage by remember {
        mutableStateOf("")
    }

    // List of fallacies current active
    var activeFallacies by remember {
        mutableStateOf(emptySet<String>())
    }

    var selectedMode by remember { mutableIntStateOf(1) } // 1: Nuke, 2: Gaslight, 3: OSINT-Threat
    var isLoading by remember { mutableStateOf(false) }
    var currentLoadingStatus by remember { mutableStateOf("") }
    var generatedRebuttal by remember { mutableStateOf("") }
    var hasGeneratedOnce by remember { mutableStateOf(false) }

    // Glowing animation for red security indicator
    val infiniteTransition = rememberInfiniteTransition(label = "Glow")
    val indicatorAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowValue"
    )

    // Custom ScrollState
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp)
    ) {
        // 1. Status Bar & Top Diagnostic Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SİSTEM SAATİ: 2026-05-24",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = SlatedGray.copy(alpha = 0.8f)
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(color = RedGlow.copy(alpha = indicatorAlpha), shape = CircleShape)
                        .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                )
                Text(
                    text = "AKTİF GÜVENLİK MOTORU",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = RedGlow
                    )
                )
            }
        }

        // 2. Main Title Line
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(RedAccent, Color.Transparent),
                            radius = 20f
                        )
                    )
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "COUNTER-STRIKE ",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "AI",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = RedAccent
                        )
                    )
                }
                Text(
                    text = "AGRESİF SAVUNMA MOTORU v4.0.2",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = SlatedGray
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Input Fields for Target Profile
        Text(
            text = "HEDEF PROFİL VERİTABANI",
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = SlatedGray
            ),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardDark),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "HEDEF NİYET MONİTÖRÜ",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = RedAccent,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Hedef Gösterilmesi Bekleniyor",
                            fontSize = 12.sp,
                            color = LightSlate,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(RedAccent.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                            .border(1.dp, RedAccent.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "TEHDİT SEVİYESİ: ${if (activeFallacies.size >= 3) "KRİTİK" else "YÜKSEK"}",
                            fontSize = 9.sp,
                            color = RedGlow,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Username Input Field
                OutlinedTextField(
                    value = targetUsername,
                    onValueChange = { targetUsername = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("target_username_input"),
                    label = { Text("Hedef Dijital Kimlik", color = SlatedGray) },
                    textStyle = TextStyle(color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 14.sp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RedAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        cursorColor = RedAccent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Attack Message Input Field
                OutlinedTextField(
                    value = targetMessage,
                    onValueChange = { targetMessage = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                        .testTag("target_message_input"),
                    label = { Text("Tespit Edilen Toksik Yorum", color = SlatedGray) },
                    textStyle = TextStyle(color = LightSlate, fontSize = 13.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RedAccent,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        cursorColor = RedAccent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Simulated original quote visual
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .drawBehind {
                            drawLine(
                                color = RedAccent,
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 8f
                            )
                        }
                        .padding(start = 14.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                ) {
                    Text(
                        text = "\"$targetMessage\"",
                        style = TextStyle(
                            color = SlatedGray,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Tactical Context Chips (Fallacies / Speech patterns selector)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TAKTİKSEL BİLİŞSEL SAFSATALAR",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = SlatedGray
                )
            )
            Text(
                text = "${activeFallacies.size} AKTİF",
                style = TextStyle(
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = IndigoAccent
                )
            )
        }

        val allFallacies = listOf(
            "Kişiye Saldırı", "Gaslighting", "Hedef Saptırma", "Yansıtma",
            "Üslup Bekçiliği", "Korkuluk Safsatası", "Duygusal Savunma", "Yanlış Eşdeğerlik"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            allFallacies.forEach { fallacy ->
                val isSelected = activeFallacies.contains(fallacy)
                Box(
                    modifier = Modifier
                        .testTag("fallacy_chip_$fallacy")
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) IndigoAccent.copy(alpha = 0.15f) else CardDark)
                        .border(
                            1.dp,
                            if (isSelected) IndigoAccent else Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(14.dp)
                        )
                        .clickable {
                            activeFallacies = if (isSelected) {
                                activeFallacies - fallacy
                            } else {
                                activeFallacies + fallacy
                            }
                        }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(IndigoAccent, CircleShape)
                            )
                        }
                        Text(
                            text = fallacy,
                            color = if (isSelected) Color.White else SlatedGray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 6. Select Strike Mode List
        Text(
            text = "KARŞI SALDIRI PROTOKOLÜ SEÇİN",
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = SlatedGray
            ),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Mode 1 Indicator Card
            StrikeModeSelectorCard(
                modeIndex = 1,
                title = "01. YOK EDİCİ MOD",
                subText = "Entelektüel İmha",
                tagline = "Argümanları tamamen çürütmek için tasarlanmış yıkıcı mantıksal analiz.",
                accentColor = RedGlow,
                isSelected = selectedMode == 1,
                onClick = { selectedMode = 1 },
                modifier = Modifier.testTag("mode_nuke_selector")
            )

            // Mode 2 Indicator Card
            StrikeModeSelectorCard(
                modeIndex = 2,
                title = "02. PSİKOLOJİK BASKI",
                subText = "Psikolojik Üstünlük",
                tagline = "Saldırganı paniğe sokup kendi sözlerinden şüphe ettiren kibar çerçeveleme.",
                accentColor = IndigoAccent,
                isSelected = selectedMode == 2,
                onClick = { selectedMode = 2 },
                modifier = Modifier.testTag("mode_gaslight_selector")
            )

            // Mode 3 Indicator Card
            StrikeModeSelectorCard(
                modeIndex = 3,
                title = "03. SİBER-TEHDİT",
                subText = "Teknolojik Üstünlük",
                tagline = "Onlara güvenlik ve mantıklarının ne kadar zayıf olduğunu gösteren meta yorum.",
                accentColor = EmeraldAccent,
                isSelected = selectedMode == 3,
                onClick = { selectedMode = 3 },
                modifier = Modifier.testTag("mode_osint_selector")
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 7. Loading Status Simulator Display
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, RedAccent.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = RedAccent,
                        strokeWidth = 2.dp
                    )
                    Text(
                        text = currentLoadingStatus,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = RedGlow
                        )
                    )
                }
            }
        }

        // 8. Execute Button Action
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 6.dp)
        ) {
            Button(
                onClick = {
                    if (targetMessage.isBlank()) {
                        Toast.makeText(context, "Lütfen önce hedef bir yorum girin.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    coroutineScope.launch {
                        isLoading = true
                        generatedRebuttal = ""
                        // Interactive sequence simulation to mimic highly complex diagnostic cybersecurity engines
                        val loadingStages = listOf(
                            "[CYBER DEEPMIND: SAVUNMA PROTOKOLLERİ BAŞLATILIYOR...]",
                            "[KONUŞMA MODELLERİ VE NİYET ANALİZ EDİLİYOR...]",
                            "[BİLİŞSEL ZAAFLAR EŞLEŞTİRİLİYOR VE KARŞI-ARGÜMAN MANTIĞI ÇEKİLİYOR...]",
                            "[TERMİNAL DAĞITIMI İÇİN YANIT YÜKÜ DERLENİYOR...]"
                        )
                        for (stage in loadingStages) {
                            currentLoadingStatus = stage
                            delay(520)
                        }

                        // Generate payload using Gemini
                        generatedRebuttal = GeminiGenerator.generateCounterStrike(
                            targetHandle = targetUsername,
                            targetMessage = targetMessage,
                            selectedMode = selectedMode,
                            fallacies = activeFallacies.toList()
                        )

                        isLoading = false
                        hasGeneratedOnce = true
                        toastCounterStrikeFeedback(context, selectedMode)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("execute_strike_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedAccent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Threat Active Alert",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = if (isLoading) "MOTORLAR İŞLİYOR..." else "TAKTİKSEL KARŞI SALDIRIYI BAŞLAT",
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            letterSpacing = 1.5.sp
                        )
                    )
                }
            }
        }

        // 9. Output Rebuttal Terminal
        AnimatedVisibility(
            visible = hasGeneratedOnce && generatedRebuttal.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(400))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "UYGULANAN SAVUNMA SALDIRISI",
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            color = SlatedGray
                        )
                    )
                    val modeLabel = when (selectedMode) {
                        1 -> "[YOK EDİCİ MOD AKTİF]"
                        2 -> "[PSİKOLOJİK BASKI AKTİF]"
                        else -> "[SİBER TEHDİT AKTİF]"
                    }
                    val modeColor = when (selectedMode) {
                        1 -> RedGlow
                        2 -> IndigoAccent
                        else -> EmeraldAccent
                    }
                    Text(
                        text = modeLabel,
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = modeColor
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // The Output Terminal Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("output_terminal_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF030303)),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        // Hacker Terminal-like details
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(modifier = Modifier.size(7.dp).background(RedAccent, CircleShape))
                                Box(modifier = Modifier.size(7.dp).background(IndigoAccent, CircleShape))
                                Box(modifier = Modifier.size(7.dp).background(EmeraldAccent, CircleShape))
                            }
                            Text(
                                text = "TERMİNAL_ÇIKTISI // GÜVENLİ_HAT_1",
                                color = SlatedGray,
                                fontSize = 8.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        // Response message
                        Text(
                            text = generatedRebuttal,
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp,
                                color = Color.White,
                                lineHeight = 20.sp
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))

                        Spacer(modifier = Modifier.height(12.dp))

                        // Terminal Action Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(generatedRebuttal))
                                    Toast.makeText(context, "Taktiksel saldırı metni kopyalandı!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .testTag("copy_response_button")
                                    .height(38.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White.copy(alpha = 0.06f),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Panoya Kopyala",
                                        modifier = Modifier.size(14.dp),
                                        tint = EmeraldAccent
                                    )
                                    Text("PANOYA KOPYALA", fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                }
                            }

                            IconButton(
                                onClick = {
                                    val shareIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, generatedRebuttal)
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Metni Paylaş"))
                                },
                                modifier = Modifier
                                    .testTag("share_response_button")
                                    .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(10.dp))
                                    .size(38.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Saldırı Yanıtını Paylaş",
                                    tint = LightSlate,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 10. Defense Guidance / Educational Tip
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardDarkLighter),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Yapay Zeka Rehberliği",
                                tint = IndigoAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "TAKTİKSEL ANALİZ DANIŞMANLIĞI (YAPAY ZEKA)",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = LightSlate,
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        val defenseInsight = when (selectedMode) {
                            1 -> "Entelektüel imha temiz ve halka açıktır. ${activeFallacies.firstOrNull() ?: "Mantıksal safsatalarını"} ifşa etmek, izleyenleri duygusal bir dramadan ziyade onları saf kanıtlarla yargılamaya zorladığı için kişisel meşruiyetini elinden alır."
                            2 -> "Zekice sınırlar koymak, zorbalığın duygusal gücünü kırar. Düşmanlıklarını şeffaf bir 'onaylanma ihtiyacı' olarak göstererek tüm itibarını alt üst ediyoruz."
                            else -> "Siber güvenlik odaklı metinler, üst düzey teknik altyapılardan yararlanan psikolojik bir kalkandır. Toksik mesajlarını bir 'OPSEC güvenlik zafiyeti' olarak sunmak, sizi bu tür zorbalıkları küçük bir donanım hatası olarak gören seçkin bir siber analist konumuna getirir."
                        }
                        Text(
                            text = defenseInsight,
                            fontSize = 11.sp,
                            color = SlatedGray,
                            lineHeight = 17.sp
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))
                
                // Opsec tip educational section
                Text(
                    text = "🔒 ÇEVRİMİÇİ SINIR GÜVENLİĞİ NOTU:\nUnutmayın, siber zorbalar duygusal etkileşimden beslenir. Çevrimiçi yorumların karakterinizi tanımlamasına asla izin vermeyin. Counter-Strike AI adlı bu motor, siber güvenlik farkındalığı ve yapay zeka destekli mantıksal sınır savunması sağlamak için tasarlanmıştır. OPSEC'inizi güçlü kılın ve dijital huzurunuzu güvence altına alın.",
                    fontSize = 10.sp,
                    color = SlatedGray.copy(alpha = 0.8f),
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

private fun toastCounterStrikeFeedback(context: Context, mode: Int) {
    val strikeText = when (mode) {
        1 -> "Yok Edici Yanıt Derlendi"
        2 -> "Psikolojik Baskı Ayarlandı"
        else -> "Siber Tehdit Jargonu Uygulandı"
    }
    Toast.makeText(context, "Taktiksel Counter-Strike: $strikeText", Toast.LENGTH_SHORT).show()
}

@Composable
fun StrikeModeSelectorCard(
    modeIndex: Int,
    title: String,
    subText: String,
    tagline: String,
    accentColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CardDarkLighter else CardDark
        ),
        border = BorderStroke(
            1.dp,
            if (isSelected) accentColor.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.05f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(accentColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = title,
                        color = accentColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Text(
                    text = subText,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = tagline,
                    color = SlatedGray,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Active option ring
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        1.dp,
                        if (isSelected) accentColor else Color.White.copy(alpha = 0.15f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(accentColor, CircleShape)
                    )
                }
            }
        }
    }
}
