<div align="center">
  <img src="https://via.placeholder.com/150x150/0A0A0A/E53935?text=NF" alt="NorthForge Systems Logo" width="120" height="120">
  <h1>NORTHFORGE SYSTEMS</h1>
  <h3>Counter-Strike AI: Taktiksel İletişim Savunma Motoru</h3>
</div>

---

**Dağıtım Sürümü:** 4.0.2  
**Geliştirici:** NorthForge Systems - Ar-Ge Departmanı  
**Erişim Seviyesi:** Kurumsal / Sınırlı Erişim  
**Durum:** Aktif Operasyonel Kullanım

---

## 1. Yönetici Özeti
Counter-Strike AI, dijital iletişim kanallarında karşılaşılan sekronize, manipülatif ve saldırgan iletişim kalıplarını (siber zorbalık, provokasyon, irrasyonel söylemler) gerçek zamanlı analiz ederek yalıtan otonom bir savunma ve iletişim altyapısıdır. 

Uygulama, hedef profillerin ilettiği argümanlardaki bilişsel safsataları (logical fallacies) derinlemesine ayrıştırır ve iletişim ağını korumaya yönelik stratejik, klinik ve psikolojik karşı-argüman (payload) paketleri derler. Gelişmiş dil modellerini kullanarak asimetrik bilgi harbi ve dijital psikolojik savunma senaryoları için asistanlık sağlar.

## 2. Sistem Mimarisi ve Teknoloji Yığını
Proje, tamamen modern mobil geliştirme standartlarına uygun, ölçeklenebilir ve güvenli bir mimari üzerine inşa edilmiştir.

*   **Çekirdek Altyapı:** Android (Kotlin)
*   **Kullanıcı Arayüzü (UI) Katmanı:** Jetpack Compose (Karanlık Tema/Taktiksel Monitör tasarım dili, Material Design 3)
*   **Doğal Dil İşleme (NLP) Modülü:** Google Gemini 3.5 Flash Modeli (REST tabanlı etkileşim)
*   **Ağ Katmanı:** Retrofit & OkHttp (Asenkron, güvenli HTTP istekleri)
*   **Veri Modelleme ve Serileştirme:** Moshi
*   **Asenkron İşlemler:** Kotlin Coroutines & Flow

## 3. Taktiksel Operasyon Modları
Karşılaşılan tehdidin tipine, kullanılan safsata modeline ve hedeflenen sonuca göre sistem üç farklı reaksiyon protokolü işletir:

| Protokol Sınıfı | Kod Adı | Açıklama | Hedef Sonuç |
| :--- | :--- | :--- | :--- |
| **Model 01** | Yok Edici Mod (Entelektüel İmha) | İletilen argümanın mantıksal temelden yoksun olduğunu klinik bir dille analiz eder. Rasyonalite çerçevesini yıkar. | Karşı tarafı tamamen mantıksal çelişkileri ile baş başa bırakmak. |
| **Model 02** | Psikolojik Baskı (Çerçeveleme) | Saldırganın niyetini zararsız ve dikkate değmez bir "onaylanma ihtiyacı" olarak yeniden konumlandırır. | Saldırganın statüsünü düşürüp, eylemini anlamsızlaştırmak. |
| **Model 03** | Siber-Tehdit (OPSEC Meta-Analizi) | Mevcut saldırıyı düşük profilli bir veri akışı zafiyeti olarak değerlendirir. Güvenlik terminolojisiyle psikolojik üstünlük sağlar. | Zorbaya teknik jargon ile aşılmaz bir sınır çizmek. |

## 4. Güvenlik ve Kimlik Doğrulama Protokolleri
Sistemin analiz motoruna (LLM) erişimi için yetkilendirilmiş bir API anahtarına ihtiyacı bulunmaktadır. Veri güvenliği standartları gereği anahtar yönetimi iki aşamalı güvenlik protokolü ile sağlanır:

### Yöntem A: Sistem Seviyesi (Derleme Öncesi)
Uygulama derlenmeden önce proje kök dizinindeki `.env` dosyasına entegre edilir. Kapalı sistem dağıtımlarında önerilir.
```properties
GEMINI_API_KEY="NF-AIzaSy...PROD_KEY"
```

### Yöntem B: Çalışma Zamanı (Runtime) Enjeksiyonu
Sistem başlatıldığında API modülü eksikliği tespit edilirse, operatörden arayüz üzerinden (GUI) yetkilendirme anahtarı talep edilir. 
*Veri Güvenliği:* Girilen anahtar sunuculara iletilmez, yalnızca yerel cihaz üzerinde güvenli donanım önbelleğinde (`SharedPreferences` / `MODE_PRIVATE`) şifrelenmiş çerçevede tutulur.

## 5. Kurulum ve Dağıtım
Uygulamayı mevcut NorthForge Systems cihazlarına dağıtmak veya kaynak koddan derlemek için yetkili terminal üzerinden aşağıdaki prosedür izlenmelidir.

**Gereksinimler:**
*   Android Studio Ladybug veya üzeri
*   JDK 17
*   Android SDK 34

**Derleme Komutu:**
```bash
# Projeyi temizle ve bağımlılıkları getir
./gradlew clean

# Debug sürümünü derle
./gradlew assembleDebug

# Cihaza veya emülatöre kur
./gradlew installDebug
```

## 6. Operasyonel Kullanım Kılavuzu
1.  **Hedef Tanımlama:** Arayüz üzerinden hedefin dijital kimliği (kullanıcı adı vb.) girilir.
2.  **Veri Girişi:** Tespit edilen saldırgan ve toksik ileti sisteme beslenir.
3.  **Safsata Analizi:** Sistemin önerdiği veya analistin tespit ettiği mantıksal safsatalar (Örn: Ad Hominem, Yansıtma) işaretlenir.
4.  **Protokol Seçimi:** Uygun düşen operasyon modu (01, 02 veya 03) seçilir.
5.  **Ateşleme:** Karşı-argüman (payload) üretilir. Analist metni onayladıktan sonra panoya kopyalar veya şifreli ağlar üzerinden hedefe yönlendirir.

---

## Yasal Bildirim ve Lisanslama

### NORTHFORGE SYSTEMS PROPRIETARY LICENSE (NSPL) v2.0
**Telif Hakkı (C) 2026 NorthForge Systems. Tüm Hakları Saklıdır.**

Bu yazılım altyapısı, algoritmaları, kullanılan taktiksel prompt modelleri, kaynak kodları ve görsel varlıkları bütünüyle NorthForge Systems'in gizli fikri mülkiyetindedir. 

Bu yazılım ticari olmayan, açık kaynaklı veya genel kullanıma açık bir sistem DEĞİLDİR. Sadece yetkilendirilmiş NorthForge Systems personeli veya özel sözleşmelerle izin verilen birimler tarafından, NorthForge operasyonel donanımları üzerinde kullanılabilir.

1.  **Dağıtım Yasağı:** Bu kod veya barındırdığı yapılar, yazılı izin olmaksızın kopyalanamaz, paylaşılamaz, harici depolara aktarılamaz.
2.  **Tersine Mühendislik:** Kod üzerinde tersine mühendislik, kaynak kod derlemesini çözme (decompilation) veya alterasyon (modification) yapılamaz.
3.  **Sorumluluk Reddi:** Sistem tarafından oluşturulan sentetik karşı-argüman metinlerinin saha operasyonlarında kullanımı, kullanan personelin veya analistin tamamen kendi inisiyatifinde ve sorumluluğundadır. Uygulamanın yanlış kullanımı sonucu ortaya çıkabilecek hukuki, sosyal veya operasyonel zararlardan NorthForge Systems sorumlu tutulamaz.
4.  **Denetim:** İzin verilen cihazlar üzerindeki kullanım logları güvenlik amaçlı anonim olarak denetlenebilir. İhlali durumunda kurum hukuki süreç başlatma hakkını saklı tutar.

*İhlal Bildirimleri ve Erişim Yetkilendirmesi için: admin@northforge.systems*
