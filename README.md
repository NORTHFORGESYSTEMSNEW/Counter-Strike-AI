<div align="center">
  <img src="https://via.placeholder.com/150x150/0A0A0A/E53935?text=NF" alt="NorthForge Systems Logo" width="120" height="120">
  <h1>COUNTER-STRIKE AI (CS-AI)</h1>
  <h3>Taktiksel İletişim Savunma Motoru</h3>
</div>

---

**Geliştirici:** NorthForge Systems  
**Çekirdek Altyapı:** Google Gemini API Entegrasyonu (Gerçek Zamanlı, Dinamik Payload Üretimi)  
**Durum:** Aktif Operasyonel Kullanım (Sıfırıncı Gün / Zero-Day Mantığı)

---

## 1. YÖNETİCİ ÖZETİ VE SİSTEM GÖREVİ
Counter-Strike AI (CS-AI), dijital platformlardaki asimetrik bilgi harbi, dezenformasyon ve siber zorbalık unsurlarına karşı tasarlanmış otonom bir **Taktiksel İletişim Savunma Motoru'dur**. Sıradan şablon bazlı yanıtlayıcıların aksine CS-AI, toksik yorumları, mantıksız eleştirileri ve irrasyonel saldırıları gerçek zamanlı olarak ayrıştırır. Hedefin kullandığı bilişsel safsataları (logical fallacies) saptar ve soğukkanlı, analitik bir üstünlükle saldırganı argümansız bırakacak sentetik karşı-argümanlar (payload) derler.

## 2. TEMEL ÖZELLİKLER VE TEKNİK ÇERÇEVE
*   **Hedef ve Tehdit Analizi:** Operatör, hedefin dijital kimliğini ve saldırgan vektörünü (toksik mesajı) terminale girer. Sistem, saldırgandaki mantıksal bozuklukları (Örn. Ad Hominem, Yansıtma, Korkuluk Safsatası) anında izole eder.
*   **Gerçek Zamanlı AI Entegrasyonu:** İlk başlatmada güvenli bir `SecureStorage` (SharedPreferences) katmanına şifrelenen Gemini API Key aracılığıyla LLM ile asenkron iletişim kurulur. Sistem, ezberlenmiş metinler yerine hedef metnin spesifik bağlamına %100 uyumlu, keskin Türkçe yanıtlar derler.
*   **Taktiksel Dağıtım (Deploy):** Üretilen savunma metni, hedefe doğrudan enjekte edilmek veya şifreli/şifresiz ağlarda paylaşılmak üzere tek tuşla panoya donanımsal olarak kopyalanır.

## 3. TAKTİKSEL SAVUNMA MODLARI (PROTOKOLLER)

| Mod Kodu | Operasyon Adı | Saldırı Vektörü | Ana Hedef (Sonuç) |
| :--- | :--- | :--- | :--- |
| **01** | Yok Edici Mod *(Entelektüel İmha)* | Klinik ve acımasız mantık yürüterek argümandaki mantıksızlığı paramparça eder. | Saf kanıtlar ve mantıkla saldırganı izleyenlerin gözünde güvenilmez ve irrasyonel kılmak. |
| **02** | Psikolojik Baskı *(Çerçeveleme)* | Zorbalığı veya saldırıyı, kişinin kendi bastırılmış "onaylanma ihtiyacı" olarak yeniden yorumlar. | Saldırganı çaresiz, yalnız ve duygusal olarak oldukça dengesiz (uncalibrated) göstermek. |
| **03** | Siber-Tehdit *(OPSEC Meta-Analizi)* | Yüksek teknoloji jargonu kullanarak duygu patlamasını bir "sistem/güvenlik zafiyeti" olarak nitelendirir. | Toksik davranışı düşük seviye bir konfigürasyon hatası olarak görüp analitik olarak küçümsemek. |

## 4. KURULUM VE DERLEME (ANDROID MİMARİSİ İÇİN)

Sistemi operasyonel cihaza derlemek için standart Android JDK 17/SDK 34 araç seti gereklidir.

```bash
# Depoyu yerel ortama çekin ve derleme dizinine geçin
cd counter-strike-ai-core

# Temizlik ve Build-cache sıfırlama
./gradlew clean

# Operasyonel Debug paketini (APK) derleyin
./gradlew assembleDebug

# ADB ile hedef test cihazına yükleyin (İsteğe bağlı)
adb install app/build/outputs/apk/debug/app-debug.apk
```

> **Not:** API entegrasyonu için uygulama açılış terminalinde geçerli bir Gemini Anahtarı (API Key) girilmesi zorunludur.
(Google AI Studio'dan kodlama alanında yardım alınmıştır)
---

## 5. LİSANS VE KURUMSAL KOŞULLAR (GPL v3 UYARLAMASI)

Bu yazılım kesinlikle MIT veya benzeri esnek bir yapıyla **LİSANSLANMAMIŞTIR**. Projenin ticari entiteler tarafından kapatılması veya sömürülmesi engellenmiştir. 

**GNU General Public License v3 (GPL v3) Kurumsal Şartnamesi geçerlidir:**
*   **Ticari Kullanım Yasağı:** Bu kod, çekirdek altyapı veya sistemin oluşturduğu modellemeler hiçbir ticari şirketin kapalı kaynak yazılımında kullanılamaz.
*   **Açık Kaynak Zorunluluğu:** CS-AI kod bazını kullanan, değiştiren veya üzerine inşa edilen (fork) tüm türev (derivative) projeler, kaynak kodlarını açık bir şekilde **GPL v3** sınırları dahilinde topluluğa açmak zorundadır.
*   Bu sistem, "Copyleft" prensiplerine mutlak suretle sadıktır. Bilgi güvenliği ve asimetrik iletişim mimarisi tekelleştirilemez.
  
  Duyuru:Bu bir test sürümüdür. Sistemdeki mevcut eksiklikler ve hatalı fonksiyonlar sonraki güncellemelerle tamamen giderilecektir.  --


## 6. YASAL UYARI VE ASİMETRİK HARP VURGUSU (DISCLAIMER)

⚠️ **DİKKAT: HUKUKİ VE OPERASYONEL SORUMLULUK BİLDİRİMİ** ⚠️

Counter-Strike AI (CS-AI), dijital platformlardaki asimetrik dezenformasyona, siber zorbalığa ve organize linç girişimlerine karşı tasarlanmış otonom bir **"Meşru Müdafaa"** ve **"İletişim OPSEC'i"** aracıdır.

Sistem tarafından üretilen klinik ve psikolojik "payload" (yanıt) metinlerinin doğrudan hedefe yönlendirilmesi;
*   Karşı tarafta ağır psikolojik çöküntüye,
*   Hedefin dijital itibarının (clout) ani ve kalıcı kaybına,
*   Ve sosyal mühendislik eksenli beklenmedik yansımalara yol açabilir.

Bu yazılım (motor), yalnızca mantıksal analizi desteklemek amacıyla kodlanmıştır. Üretilen yanıtların saha operasyonlarında, sosyal medyada veya herhangi bir iletişim kanalında kullanılmasından doğacak manevi, sosyal, hukuki ve kurumsal sonuçlardan **TAMAMEN OPERATÖR (KULLANICI) SORUMLUDUR**. NorthForge Systems veya CS-AI projesine katkıda bulunan geliştiriciler, yazılımın kullanım sonuçları üzerine hiçbir hukuki sorumluluk kabul etmez.

**OPERASYONA BAŞLAMADAN ÖNCE DİJİTAL BARIŞI VE GERİLİMİ AZALTMAYI (DE-ESCALATION) TERCİH EDİNİZ.**
