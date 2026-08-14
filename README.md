# Java Concurrency Tapşırığı — Paylanmış Sifariş Emal Sistemi (Distributed Order Processing Engine)

## 1. Ssenari

Bir e-ticarət platforması üçün sifarişləri paralel emal edən, vaxt məhdudiyyəti olan, ləğv edilə bilən və monitorinqi aparılan bir backend simulyasiya sistemi qurmalısan.

## 2. Sistemin Tələb Etdiyi Komponentlər

### 2.1 Sifariş Generatoru (Producer)

- Ayrıca bir Thread (klassik `new Thread()` ilə, `ExecutorService` yox) hər 200–500ms arasında random vaxtla yeni sifariş yaradıb `BlockingQueue<Order>`-ə əlavə etsin.
- Hər `Order` obyekti: id, məhsul adı, miqdar, "emal müddəti" (simulyasiya üçün random 500ms–3000ms) daşısın.
- Bu thread "Producer" adlanmalı və 50 sifariş yaratdıqdan sonra özü dayanmalıdır.

### 2.2 Emal Hovuzu (ExecutorService Worker Pool)

- `ExecutorService` (fixed thread pool, 5 worker) `BlockingQueue`-dan sifarişləri götürüb emal etsin.
- Hər worker sifarişi "emal edərkən" (`Thread.sleep` ilə simulyasiya) `Thread.currentThread().isInterrupted()`-i mütəmadi yoxlamalıdır ki, dayandırılanda dərhal çıxa bilsin.
- Hər worker öz nəticəsini `Future<OrderResult>` kimi qaytarsın (`OrderResult`: uğurlu/uğursuz/yarımçıq statusu daşısın).

### 2.3 Ödəniş Doğrulama Alt-sistemi (Nested Concurrency)

- Hər sifariş emal olunarkən, worker daxilində əlavə olaraq ikinci bir kiçik `ExecutorService` (2 thread-lik) çağırılsın — bu, "ödəniş yoxlanışı" və "stok yoxlanışı"nı paralel icra etsin.
- İkisi də bitənə qədər worker gözləməlidir (`Future.get()` və ya `CountDownLatch` ilə — hansını daha uyğun bilirsənsə seç və izah et).
- Əgər bunlardan biri 1 saniyədən çox çəkərsə, timeout tətbiq olunmalı və sifariş "REJECTED" olaraq işarələnməlidir.

### 2.4 Monitor Thread (join() məcburi istifadəsi)

- Ayrıca klassik Thread — hər 1 saniyədə bir dashboard kimi console-a çap etsin:
  - Neçə sifariş növbədədir (queue ölçüsü)
  - Neçəsi tamamlanıb, neçəsi rədd olunub (`ConcurrentHashMap`-dən sayaraq)
  - Aktiv worker sayı
- Bütün sifarişlər bitdikdə, əsas thread bu Monitor Thread-i `interrupt()` etsin və `join()` ilə onun tam bitməsini gözləsin ki, console-da "yarımçıq sətir" qalmasın.

### 2.5 Timeout və Ləğvetmə Məntiqi

- Bütün sistem üçün ümumi 20 saniyəlik limit qoyulsun.
- Əgər 20 saniyə ərzində bütün sifarişlər bitməzsə:
  - Producer dayandırılmalı
  - `ExecutorService.shutdownNow()` çağırılıb qalan worker-lər interrupt olunmalı
  - Yarımçıq qalan sifarişlər "TIMEOUT_CANCELLED" statusu ilə qeyd olunmalı

### 2.6 Yekun Hesabat

- Proqram bitəndə: cəmi neçə sifariş yaradıldı, neçəsi uğurlu, neçəsi rədd, neçəsi timeout oldu — bunları table formatında console-a çap et.
- `ConcurrentHashMap<String, OrderResult>` istifadə edərək race condition olmadan nəticələri saxla (sayğaclar da bu map-dən hesablansın, ayrıca Atomic dəyişən istifadə olunmasın).

## 3. Texniki Tələblər (Checklist)

- [ ] BlockingQueue (Producer-Consumer pattern)
- [ ] ExecutorService (ana worker pool) + nested ExecutorService (ödəniş/stok)
- [ ] Manual Thread (Producer və Monitor üçün)
- [ ] join() — Monitor thread üçün məcburi
- [ ] interrupt() — həm worker-lərdə, həm Producer-də, düzgün emal olunmalı
- [ ] Future və Future.get(timeout)
- [ ] ConcurrentHashMap — thread-safe nəticə saxlanışı və sayğaclar üçün
- [ ] shutdown(), shutdownNow(), awaitTermination() — düzgün ardıcıllıqla
- [ ] Heç bir thread "hang" qalmamalı, heç bir resurs sızması olmamalı

## Terminal Output

Example execution of the program:

![Terminal Output](images/img.png)
