

Merhaba! Bu proje, spor bahisleri için geliştirdiğim bir Android uygulaması. Uygulamada canlı maç oranlarını görebilir, bahis kuponları oluşturabilir ve yönetebilirsiniz.

## Neler Var?

- Giriş yapıp kayıt olabilirsiniz
- Canlı maç oranlarını görebilirsiniz
- Farklı spor dalları için bahis seçeneklerini inceleyebilirsiniz
- Bahis sepetinizi yönetebilirsiniz
- Karanlık/Aydınlık tema seçeneği var
- Maç arama özelliği ile istediğiniz maçı hızlıca bulabilirsiniz

## Nasıl Yapıldı?

Uygulamayı geliştirirken modern Android teknolojilerini kullandım:
- Kotlin programlama dili
- MVVM mimarisi
- Jetpack Navigation Component
- Hilt ile dependency injection
- Retrofit & OkHttp ile API istekleri
- Firebase ile kimlik doğrulama ve analitik
- DataStore ile veri saklama
- Coroutines & Flow ile asenkron işlemler
- ViewBinding ile view yönetimi
- Glide ile resim yükleme

## Nasıl Çalıştırırım?

1. Projeyi bilgisayarınıza indirin
2. Android Studio'da açın
3. `gradle.properties` dosyasına API anahtarlarınızı ekleyin:
```properties
ODDS_API_KEY=your_api_key_here
ODDS_API_BASE_URL=your_base_url_here
```
4. Çalıştırın ve kullanmaya başlayın.

## API Kullanımı

Maç oranları için The Odds API'yi kullanıyorum. API anahtarınızı `gradle.properties` dosyasında tanımlamanız yeterli olacaktır.

