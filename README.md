# Medistar 🏥 - Akıllı Hastane Randevu ve Önceliklendirme Sistemi

Medistar, hastaneler için geliştirilmiş, doktor, bölüm ve hasta yönetimini kolaylaştıran akıllı bir randevu ve önceliklendirme sisteminin arka plan (backend) servisidir. Bu proje, modern Java teknolojileri kullanılarak RESTful mimaride geliştirilmiştir.

## 🚀 Teknolojiler

Bu projede aşağıdaki teknolojiler ve araçlar kullanılmıştır:

*   **Java 21:** Ana programlama dili.
*   **Spring Boot:** Hızlı ve güvenli backend geliştirme altyapısı.
*   **Spring Data JPA / Hibernate:** Veritabanı işlemleri ve ORM.
*   **Microsoft SQL Server:** İlişkisel veritabanı yönetim sistemi.
*   **Lombok:** Boilerplate (tekrar eden) kodları (getter, setter, constructor vb.) azaltmak için.
*   **Maven:** Proje yönetimi ve bağımlılık inşası.

## 🛠️ Kurulum ve Çalıştırma

Projeyi yerel ortamınızda çalıştırmak için aşağıdaki adımları sırasıyla izleyin.

### 1. Ön Koşullar
*   Bilgisayarınızda **JDK 21** kurulu olmalıdır.
*   **MS SQL Server** yüklü ve çalışır durumda olmalıdır.
*   Kullandığınız IDE'de (Spring Tool Suite, IntelliJ IDEA, Eclipse vb.) **Lombok eklentisinin** kurulu ve aktif olduğundan emin olun.

### 2. Veritabanı Hazırlığı
SQL Server üzerinde projenin kullanacağı veritabanını oluşturun:

1. SQL Server Management Studio (SSMS) veya tercih ettiğiniz bir istemciyi açın.
2. Aşağıdaki bilgilerle giriş yapın:
   *   **Kullanıcı Adı:** `sa`
   *   **Şifre:** `12345`
3. `medistar` adında yeni bir veritabanı oluşturun:
   ```sql
   CREATE DATABASE medistar;
3. Proje Konfigürasyonu
Projenin veritabanına bağlanabilmesi için src/main/resources/application.properties (veya .yml) dosyanızdaki ayarların şu şekilde olduğundan emin olun:

Properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=medistar;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=12345
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver

# Hibernate Ayarları
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
(Not: ddl-auto=update ayarı sayesinde tablolar projeyi çalıştırdığınızda otomatik olarak oluşacaktır.)

4. Projeyi Ayağa Kaldırma
Projeyi bilgisayarınıza klonlayın veya indirin.

Tercih ettiğiniz IDE ile projeyi açın ve Maven bağımlılıklarının yüklenmesini bekleyin.

MedistarApplication.java (ana sınıf) dosyasını bularak uygulamayı Run as Java Application veya Run as Spring Boot App seçeneği ile başlatın.

Konsolda Started MedistarApplication yazısını gördüğünüzde sistem 8090 portunda başarıyla ayağa kalkmış demektir.

🔗 Temel API Uç Noktaları (Endpoints)
Sistem http://localhost:8090 adresi üzerinden hizmet vermektedir. Temel admin işlemleri şunlardır:

Kullanıcılar:

GET /api/admin/users - Tüm kullanıcıları listeler.

DELETE /api/admin/users/{id} - Belirtilen kullanıcıyı siler.

Doktorlar:

POST /api/admin/doctors - Yeni doktor ekler.

DELETE /api/admin/doctors/{id} - Belirtilen doktoru siler.

Bölümler:

POST /api/admin/departments - Yeni bölüm ekler.

DELETE /api/admin/departments/{id} - Belirtilen bölümü siler.

Randevular & İstatistikler:

GET /api/admin/appointments - Tüm randevuları listeler.

GET /api/admin/stats - Sistem istatistiklerini getirir.

⚠️ Önemli Notlar
IDE'nizde Lombok kurulu değilse, kodunuzda getter/setter bulunamadı hataları alabilirsiniz. Kurulum için Lombok resmi sitesini ziyaret edebilirsiniz.
