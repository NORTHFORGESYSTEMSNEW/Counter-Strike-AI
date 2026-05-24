# NORTHFORGE SYSTEMS

## Counter-Strike AI: Taktiksel İletişim Savunma Motoru

**Dağıtım Sürümü:** 4.0.2  
**Geliştirici:** NorthForge Systems - Ar-Ge Departmanı  
**Erişim Seviyesi:** Kurumsal / Kısıtlı Kullanım

---

### Yönetici Özeti
Counter-Strike AI, dijital platformlarda karşılaşılan sekronize, manipülatif ve saldırgan iletişim kalıplarını gerçek zamanlı analiz ederek yalıtan otonom bir savunma altyapısıdır. Uygulama, hedef profillerin ilettiği argümanlardaki bilişsel safsataları (logical fallacies) ayrıştırır ve iletişim ağını korumaya yönelik stratejik, klinik ve psikolojik karşı-argüman (payload) paketleri derler.

### Sistem Mimarisi
- **Çekirdek Altyapı:** Android tabanlı Kotlin mimarisi
- **Arayüz Katmanı:** Jetpack Compose (Karanlık Tema/Taktiksel Monitör standartlarında)
- **Doğal Dil İşleme Katmanı:** Google Gemini (Retrofit HTTP İstemcisi üzerinden asenkron entegrasyon)
- **Veri Modelleme:** Moshi (JSON serileştirme standardı)

### Taktiksel Operasyon Modları
Karşılaşılan tehdidin tipine ve hedeflenen sonuca göre sistem üç farklı protokol işletir:
1. **Model 01 (Yok Edici Mod / Entelektüel İmha):** İletilen argümanın mantıksal temelden yoksun olduğunu klinik bir dille analiz eder. Karşı tarafın rasyonalitesini geçersiz kılar.
2. **Model 02 (Psikolojik Baskı / Çerçeveleme):** Saldırganın niyetini zararsız ve dikkate değmez bir onaylanma ihtiyacı olarak yeniden konumlandırır.
3. **Model 03 (Siber-Tehdit / OPSEC Meta-Analizi):** Mevcut saldırıyı düşük profilli bir veri akışı zafiyeti olarak değerlendirir. Siber güvenlik terminolojisiyle psikolojik üstünlük sağlar.

### Kimlik Doğrulama ve Güvenlik Protokolleri
Sistemin dil modelleme altyapısına erişimi için şifrelenmiş bir yetkilendirme anahtarına (API Key) ihtiyacı bulunmaktadır. Başlatma aşamasında iki yöntem tercih edilebilir:

1. **Çevresel Değişken (Derleme Süreci):** 
   Proje kök dizinindeki `.env` dosyasına entegre edilir.
   ```properties
   GEMINI_API_KEY="ANAHTAR_BURAYA_GIRILECEKTIR"
   ```
2. **Çalışma Zamanı (Runtime) Enjeksiyonu:**
   Sistem başlatıldığında API modülü eksikliği tespit edilirse kullanıcıdan istemci üzerinden (GUI) manuel anahtar girişi talep edilir. Anahtar, yerel cihaz üzerinde güvenli donanım önbelleğinde (SharedPreferences) şifrelenerek tutulur.

### Derleme ve Dağıtım
Uygulamayı mevcut NorthForge Systems derleme standartlarına göre cihaza aktarmak veya derlemek için yetkili terminal üzerinden aşağıdaki prosedür izlenmelidir:
```bash
./gradlew clean assembleDebug
```

### Kurumsal Yasal Bildirim
Bu yazılım altyapısı, algoritmaları ve görsel varlıkları NorthForge Systems'in fikri mülkiyetindedir. Sistem tarafından oluşturulan sentetik karşı-argüman metinlerinin saha operasyonlarında kullanımı, kullanan personelin inisiyatif ve sorumluluğundadır. İzinsiz kopyalanması, tersine mühendisliğe tabi tutulması veya üçüncü şahıslara yetkisiz dağıtılması hukuki işlem sebebidir.
