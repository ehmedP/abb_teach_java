# SQL Funksiyaları — Praktik Tapşırıq

**Skalyar · Aqreqat · Pəncərə funksiyaları — 40 sorğu yazma tapşırığı**

**Mühit:** PostgreSQL · **Tapşırıq sayı:** 40 · **Maksimal bal:** 100 · **Tövsiyə olunan vaxt:** 120 dəqiqə

Ad, Soyad: `______________________________`  Qrup: `____________`  Tarix: `____________`

## Qaydalar

Hər tapşırığın cavabı işlək bir SQL sorğusu olmalıdır. **JOIN istifadə etmək qadağandır** — bütün suallar tək `satislar` cədvəli üzərində həll olunur. Sütunlara mənalı ləqəb (`AS`) verilməlidir. Sintaktik xəta olan sorğu 0 bal alır. Cavabları tək `.sql` faylında, hər sorğunun üstündə `-- 1-ci tapşırıq` şəklində şərhlə təhvil verin.

## Hazırlıq — cədvəli yaradın

Aşağıdakı skripti olduğu kimi icra edin:

```sql
DROP TABLE IF EXISTS satislar;

CREATE TABLE satislar
(
    satis_id      INT PRIMARY KEY,
    mehsul        VARCHAR(50),
    kateqoriya    VARCHAR(30),
    seher         VARCHAR(30),
    satici        VARCHAR(50),
    miqdar        INT,
    qiymet        NUMERIC(10, 2),
    endirim_faiz  NUMERIC(5, 2),
    tarix         DATE
);

INSERT INTO satislar (satis_id, mehsul, kateqoriya, seher, satici, miqdar, qiymet, endirim_faiz, tarix)
VALUES (1, ' noutbuk ', 'Texnika', 'Bakı', 'aysel memmedova', 2, 1250.00, 10.00, DATE '2024-01-15'),
       (2, 'MONITOR', 'Texnika', 'Bakı', 'aysel memmedova', 3, 320.50, NULL, DATE '2024-01-28'),
       (3, 'klaviatura', 'Aksesuar', 'Gəncə', 'RAUF ELIYEV', 5, 45.90, 0.00, DATE '2024-02-05'),
       (4, 'Printer', 'Texnika', 'Sumqayıt', 'nigar huseynova', 1, 480.00, 5.00, DATE '2024-02-17'),
       (5, 'telefon ', 'Texnika', 'Bakı', 'Elvin Qasimov', 4, 899.99, 15.00, DATE '2024-03-03'),
       (6, 'tablet', 'Texnika', 'Gəncə', 'RAUF ELIYEV', 2, 640.00, NULL, DATE '2024-03-11'),
       (7, 'kamera', 'Texnika', 'Bakı', 'aysel memmedova', 1, 1250.00, NULL, DATE '2024-03-22'),
       (8, ' kabel', 'Aksesuar', 'Sumqayıt', 'nigar huseynova', 10, 12.50, 0.00, DATE '2024-04-02'),
       (9, 'MONITOR', 'Texnika', 'Şəki', 'Elvin Qasimov', 2, 320.50, NULL, DATE '2024-04-14'),
       (10, 'noutbuk', 'Texnika', 'Gəncə', 'RAUF ELIYEV', 1, 1799.00, 20.00, DATE '2024-04-25'),
       (11, 'klaviatura', 'Aksesuar', 'Bakı', 'Elvin Qasimov', 6, 45.90, NULL, DATE '2024-05-06'),
       (12, 'Telefon', 'Texnika', 'Sumqayıt', 'nigar huseynova', 3, 899.99, NULL, DATE '2024-05-19'),
       (13, 'kabel', 'Aksesuar', 'Bakı', 'aysel memmedova', 8, 12.50, 0.00, DATE '2024-06-01'),
       (14, 'printer', 'Texnika', NULL, 'Elvin Qasimov', 2, 480.00, 10.00, DATE '2024-06-12'),
       (15, 'kamera', 'Texnika', 'Gəncə', 'RAUF ELIYEV', 1, 1150.00, NULL, DATE '2024-06-23'),
       (16, 'tablet ', 'Texnika', 'Bakı', 'nigar huseynova', 4, 640.00, 25.00, DATE '2024-07-04'),
       (17, 'MONITOR ', 'Texnika', 'Sumqayıt', 'aysel memmedova', 5, 299.00, NULL, DATE '2024-07-15'),
       (18, 'qulaqliq', 'Aksesuar', 'Bakı', 'Elvin Qasimov', 7, 89.90, 5.00, DATE '2024-07-27');
```

## Cədvəlin məzmunu

| satis_id | mehsul | kateqoriya | seher | satici | miqdar | qiymet | endirim_faiz | tarix |
|---|---|---|---|---|---|---|---|---|
| 1 | `" noutbuk "` | Texnika | Bakı | aysel memmedova | 2 | 1250.00 | 10.00 | 2024-01-15 |
| 2 | `"MONITOR"` | Texnika | Bakı | aysel memmedova | 3 | 320.50 | NULL | 2024-01-28 |
| 3 | `"klaviatura"` | Aksesuar | Gəncə | RAUF ELIYEV | 5 | 45.90 | 0.00 | 2024-02-05 |
| 4 | `"Printer"` | Texnika | Sumqayıt | nigar huseynova | 1 | 480.00 | 5.00 | 2024-02-17 |
| 5 | `"telefon "` | Texnika | Bakı | Elvin Qasimov | 4 | 899.99 | 15.00 | 2024-03-03 |
| 6 | `"tablet"` | Texnika | Gəncə | RAUF ELIYEV | 2 | 640.00 | NULL | 2024-03-11 |
| 7 | `"kamera"` | Texnika | Bakı | aysel memmedova | 1 | 1250.00 | NULL | 2024-03-22 |
| 8 | `" kabel"` | Aksesuar | Sumqayıt | nigar huseynova | 10 | 12.50 | 0.00 | 2024-04-02 |
| 9 | `"MONITOR"` | Texnika | Şəki | Elvin Qasimov | 2 | 320.50 | NULL | 2024-04-14 |
| 10 | `"noutbuk"` | Texnika | Gəncə | RAUF ELIYEV | 1 | 1799.00 | 20.00 | 2024-04-25 |
| 11 | `"klaviatura"` | Aksesuar | Bakı | Elvin Qasimov | 6 | 45.90 | NULL | 2024-05-06 |
| 12 | `"Telefon"` | Texnika | Sumqayıt | nigar huseynova | 3 | 899.99 | NULL | 2024-05-19 |
| 13 | `"kabel"` | Aksesuar | Bakı | aysel memmedova | 8 | 12.50 | 0.00 | 2024-06-01 |
| 14 | `"printer"` | Texnika | NULL | Elvin Qasimov | 2 | 480.00 | 10.00 | 2024-06-12 |
| 15 | `"kamera"` | Texnika | Gəncə | RAUF ELIYEV | 1 | 1150.00 | NULL | 2024-06-23 |
| 16 | `"tablet "` | Texnika | Bakı | nigar huseynova | 4 | 640.00 | 25.00 | 2024-07-04 |
| 17 | `"MONITOR "` | Texnika | Sumqayıt | aysel memmedova | 5 | 299.00 | NULL | 2024-07-15 |
| 18 | `"qulaqliq"` | Aksesuar | Bakı | Elvin Qasimov | 7 | 89.90 | 5.00 | 2024-07-27 |

**Məlumatın üç xüsusiyyəti:**

1. `mehsul` sütunu səliqəsizdir — artıq boşluqlar və qarışıq hərf registri var.
2. `endirim_faiz`-də həm NULL, həm 0 var — bunlar eyni şey deyil.
3. Bir satışda (`satis_id = 14`) `seher` NULL-dur.

---

## A. Mətn (string) funksiyaları

`UPPER` · `LOWER` · `TRIM` · `LENGTH` · `LEFT` · `SUBSTRING` · `POSITION` · `CONCAT`

**1.** Hər satış üçün satis_id, məhsul adının boşluqsuz və böyük hərflərlə yazılışı, həmin adın hərf sayı və ilk 3 hərfi göstərilən sorğunu yazın. Nəticə satis_id üzrə sıralansın. *(2 bal)*
> **İpucu:** TRIM olmadan LENGTH səhv nəticə verəcək.

**2.** Cədvəldəki təkrarsız məhsul adlarını çıxarın. Səliqəsizlik təmizlənməlidir — `' noutbuk '` ilə `'noutbuk'` eyni sayılsın. Əlifba sırası ilə. *(2 bal)*
> **İpucu:** DISTINCT + TRIM + UPPER.

**3.** Hər satış üçün `MƏHSUL / Şəhər` formatında etiket sütunu düzəldin. Şəhər NULL olduqda etiketdə `NAMELUM` yazılsın. *(2 bal)*
> **İpucu:** CONCAT və ya `||`, üstəgəl COALESCE.

**4.** Satıcıların təkrarsız siyahısını çıxarın və `satici` sütununu ad və soyad olmaqla iki sütuna bölün. *(2 bal)*
> **İpucu:** `POSITION(' ' IN satici)` boşluğun yerini verir; sonra LEFT və SUBSTRING.

**5.** Hər satış üçün anbar kodu yaradın: məhsulun ilk 3 hərfi (böyük) - satışın ayı - 3 rəqəmli `satis_id`. Nümunə: `NOU-01-001`. *(2 bal)*
> **İpucu:** LEFT + UPPER + `TO_CHAR(tarix,'MM')` + LPAD.

## B. Ədədi (numeric) funksiyalar

`ROUND` · `CEILING` · `FLOOR` · `ABS` · `MOD` · `SQRT` · `POWER`

**6.** Hər satış üçün ümumi məbləği (`qiymet * miqdar`), onun 18% ƏDV-ni (2 rəqəmə yuvarlaqlaşdırılmış), qiymətin yuxarı və aşağı yuvarlaqlaşdırılmış variantını hesablayın. Ümumi məbləğə görə azalan sıra. *(2 bal)*
> **İpucu:** ROUND, CEILING, FLOOR.

**7.** Hər satış üçün: `miqdar`-ın 2-yə bölünməsindən qalıq, `miqdar`-ın 5-dən fərqinin modulu və qiymətin kvadrat kökü (2 rəqəm). *(2 bal)*
> **İpucu:** MOD (və ya `%`), ABS, SQRT.

**8.** Ödəniləcək məbləği hesablayın: `qiymet * miqdar` üzərinə `endirim_faiz` tətbiq olunsun. Endirim NULL olarsa 0 kimi qəbul edilsin. Azalan sıra. *(2 bal)*
> **İpucu:** COALESCE + 100.0-a bölmə (100 yox!).

**9.** Məbləği bütün satışların orta məbləğindən böyük olan satışları tapın. Orta məbləğ sorğunun içində hesablanmalıdır (əl ilə rəqəm yazmaq olmaz). *(2 bal)*
> **İpucu:** Alt-sorğu: `WHERE ... > (SELECT AVG(...) FROM satislar)`.

## C. Tarix və zaman funksiyaları

`EXTRACT` · `TO_CHAR` · `DATE_TRUNC` · `INTERVAL` · `AGE`

**10.** Hər satışın tarixindən il, ay və gün hissələrini ayrı sütunlarda çıxarın. *(2 bal)*
> **İpucu:** EXTRACT.

**11.** Yalnız iyun və iyul aylarındakı satışlar üçün: satışın üzərindən neçə gün keçdiyi və tarixə 30 gün əlavə edilmiş zəmanət sonu tarixi. *(2 bal)*
> **İpucu:** `CURRENT_DATE - tarix`, `tarix + INTERVAL '30 days'`.

**12.** Aylıq hesabat: hər ay üçün (format `AA-İİİİ`, məsələn `03-2024`) satış sayı və ümumi dövriyyə. Xronoloji sıra. *(2 bal)*
> **İpucu:** TO_CHAR göstərmək üçün, DATE_TRUNC sıralamaq üçün.

**13.** Satışların həftənin günü üzrə paylanmasını çıxarın: gün nömrəsi, satış sayı və dövriyyə. *(2 bal)*
> **İpucu:** `EXTRACT(DOW FROM tarix)` — 0 bazar günüdür.

**14.** Bir sətirdə göstərin: ilk və son satış tarixi, aralarındakı gün fərqi, ümumi satış sayı və bir aya düşən orta dövriyyə. *(2 bal)*
> **İpucu:** MIN/MAX + `COUNT(DISTINCT DATE_TRUNC('month', tarix))`.

## D. Çevirmə və NULL funksiyaları

`CAST` · `COALESCE` · `NULLIF` · `COUNT(sutun)` fərqi

**15.** Bir sorğuda göstərin: `endirim_faiz`-in xam qiyməti, NULL-un 0-a çevrilmiş variantı, 0 olduqda NULL qaytaran variantı və şəhərin NULL-suz variantı. *(2 bal)*
> **İpucu:** COALESCE + NULLIF birlikdə.

**16.** Hər satış üçün: qiymətin mətn tipinə çevrilmiş variantı, `satis_id-MƏHSUL` kodu (məs. `1-NOUTBUK`) və satışdan 2024-12-31-ə qədər neçə gün qaldığı. *(2 bal)*
> **İpucu:** CAST (və ya `::`), CONCAT, tarixlərin fərqi.

**17.** Hər satıcı üçün: ümumi satış sayı, endirimi qeyd olunmuş (NULL olmayan) satışların sayı, həqiqətən endirim tətbiq edilmiş (0-dan böyük) satışların sayı və sonuncunun faizi. Faizə görə azalan sıra. *(2 bal)*
> **İpucu:** `COUNT(*)` / `COUNT(sutun)` / `COUNT(*) FILTER (WHERE ...)` üçünün fərqi.

## E. Şərti ifadələr — CASE

`CASE WHEN` · `SUM(CASE ...)` · CASE ORDER BY-da

**18.** Qiymətə görə kateqoriya təyin edin: 1000 və yuxarı → `Bahali`, 300–999 → `Orta`, qalanı → `Ucuz`. Qiymətə görə azalan sıra. *(2 bal)*
> **İpucu:** Şərtlər yuxarıdan aşağı yoxlanır.

**19.** Endirim statusu sütunu yazın: NULL və ya 0 → `Endirim yoxdur`, 15 və yuxarı → `Boyuk endirim`, qalanı → `Kicik endirim`. *(2 bal)*
> **İpucu:** NULL şərtini birinci yoxlayın.

**20.** Tək sorğu, tək sətir nəticə: ümumi satış sayı, Texnika satışlarının sayı, Aksesuar satışlarının sayı. WHERE istifadə etmək olmaz. *(2 bal)*
> **İpucu:** `SUM(CASE WHEN ... THEN 1 ELSE 0 END)`.

## F. Aqreqat funksiyalar, GROUP BY, HAVING

`COUNT` · `SUM` · `AVG` · `MIN` · `MAX` · `STRING_AGG` · `HAVING`

**21.** Ümumi statistika (bir sətir): satış sayı, şəhəri boş olmayan sətirlərin sayı, fərqli satıcı sayı, ümumi dövriyyə, orta qiymət, ən ucuz və ən bahalı qiymət. *(2 bal)*
> **İpucu:** `COUNT(*)` ilə `COUNT(seher)` fərqi burada görünməlidir.

**22.** Şəhər üzrə satış sayı və dövriyyə. Şəhəri NULL olan satış `Namelum` adı altında görünsün. Dövriyyəyə görə azalan sıra. *(2 bal)*
> **İpucu:** GROUP BY-da da COALESCE lazımdır.

**23.** Satıcı üzrə hesabat: yalnız qiyməti 50-dən böyük satışlar nəzərə alınsın, qruplaşdırmadan sonra isə yalnız dövriyyəsi 5000-dən çox olan satıcılar qalsın. *(2 bal)*
> **İpucu:** Biri WHERE, digəri HAVING olmalıdır — yerlərini dəyişməyin.

**24.** Eyni məhsul müxtəlif qiymətlərə satılıb. Hər məhsul üçün (təmizlənmiş ad) satış sayı, ən ucuz, ən bahalı qiymət və aralarındakı fərq. Yalnız birdən çox dəfə satılan məhsullar. Fərqə görə azalan sıra. *(2 bal)*
> **İpucu:** `GROUP BY UPPER(TRIM(mehsul))` + `HAVING COUNT(*) > 1`.

**25.** Hər şəhər üçün orada satılan məhsulların vergüllə ayrılmış siyahısını bir sətirdə çıxarın (təkrarsız, böyük hərflərlə). *(2 bal)*
> **İpucu:** `STRING_AGG(DISTINCT ..., ', ')`.

## G. Pəncərə (window) funksiyaları

`ROW_NUMBER` · `RANK` · `DENSE_RANK` · `NTILE` · `LAG` · `LEAD` · `SUM() OVER`

**26.** Bütün satışları qiymətə görə azalan sıralayın və üç sütun əlavə edin: `ROW_NUMBER()`, `RANK()`, `DENSE_RANK()`. *(2 bal)*
> **İpucu:** Bərabər qiymətlərə (1250 və 899.99) diqqət edin.

**27.** Hər şəhərin daxilində satışları məbləğə görə sıralayın (NULL şəhər `Namelum` qrupuna düşsün). *(2 bal)*
> **İpucu:** PARTITION BY.

**28.** Hər satışın ümumi dövriyyədə neçə faiz pay tutduğunu hesablayın. Ümumi cəm `SUM(...) OVER ()` ilə alınmalıdır — GROUP BY istifadə etmək olmaz, nəticədə 18 sətir qalmalıdır. *(2 bal)*
> **İpucu:** Pəncərə funksiyası sətirləri birləşdirmir.

**29.** Tarix sırası ilə: hər satışın məbləği, əvvəlki və sonrakı satışın məbləği, aralarındakı fərq və yığılan (running) cəm. *(2 bal)*
> **İpucu:** LAG, LEAD, `SUM() OVER (ORDER BY tarix)`.

**30.** Satışları məbləğə görə 4 bərabər qrupa (çeyrəyə) bölün və hər satışın hansı çeyrəyə düşdüyünü göstərin. *(2 bal)*
> **İpucu:** `NTILE(4)`.

## H. Çətin və qarışıq tapşırıqlar

Bir neçə funksiya tipini birlikdə tələb edir · alt-sorğu lazımdır

**31.** İkinci ən bahalı satışı tapın. LIMIT və ya OFFSET istifadə etmək qadağandır. *(4 bal)*
> **İpucu:** Pəncərə funksiyasını alt-sorğuda hesablayıb WHERE ilə süzün. Bərabər qiymətlər var — hansı funksiya düzgündür?

**32.** Hər satıcının ən böyük məbləğli satışını tapın — satıcı başına yalnız 1 sətir. Məbləğə görə azalan sıra. *(4 bal)*
> **İpucu:** `PARTITION BY satici` + alt-sorğu + `WHERE r = 1`.

**33.** Tarix sırası ilə baxdıqda ardıcıl iki satış arasında ən böyük düşüş hansı tarixdə baş verib? Tarixi, məbləği, əvvəlki məbləği və fərqi göstərin (yalnız 1 sətir). *(4 bal)*
> **İpucu:** LAG + alt-sorğu + `ORDER BY ferq ASC`.

**34.** Aydan-aya artım faizi: hər ay üçün dövriyyə, əvvəlki ayın dövriyyəsi və artım faizi (1 rəqəm). İlk ayda faiz NULL olmalıdır. *(4 bal)*
> **İpucu:** Əvvəlcə aylıq cəmi hesablayın (CTE və ya alt-sorğu), sonra onun üzərində LAG işlədin. Aqreqat və pəncərə funksiyası eyni pillədə işləmir.

**35.** Hər ayın ilk satışını tapın: ay, satis_id, tarix və məhsul adı (7 sətir). *(4 bal)*
> **İpucu:** `ROW_NUMBER() OVER (PARTITION BY ay ORDER BY tarix)`.

**36.** Satışları məbləğə görə azalan sıralayın və ümumi dövriyyənin 50%-ni doldurmaq üçün kifayət edən ən böyük satışları tapın (Pareto təhlili). Hər sətirdə yığılan cəm və onun faizi də görünsün. *(4 bal)*
> **İpucu:** İki pəncərə funksiyası: yığılan cəm və ümumi cəm. Şərt: sətirdən əvvəlki yığılan cəm hələ 50%-i keçməmiş olsun.

**37.** Hər şəhərin ümumi dövriyyədəki faiz payını hesablayın. Şərt: sorğuda həm GROUP BY, həm də pəncərə funksiyası eyni anda işlədilməlidir (alt-sorğu olmadan). *(4 bal)*
> **İpucu:** Aqreqatın üzərinə pəncərə: `SUM(SUM(...)) OVER ()`.

**38.** Hər satıcının satışları arasında ən uzun fasilə (gün ilə) hansı olub? Ən uzun 3 fasiləni göstərin: satıcı, əvvəlki tarix, sonrakı tarix, fasilə. *(4 bal)*
> **İpucu:** `PARTITION BY satici` + `LAG(tarix)` + tarix fərqi.

**39.** Qiyməti öz şəhərinin orta qiymətindən yüksək olan satışları tapın. Nəticədə şəhər, satis_id, qiymət və həmin şəhərin orta qiyməti göstərilsin. *(4 bal)*
> **İpucu:** `AVG(...) OVER (PARTITION BY seher)` alt-sorğuda hesablanmalıdır — pəncərə funksiyasını birbaşa WHERE-də yazmaq olmaz.

**40.** Yekun hesabat. Satıcılar üzrə bir sorğuda: adı böyük hərflərlə, satış sayı, endirim çıxıldıqdan sonrakı xalis dövriyyə (2 rəqəm), orta qiymət (1 rəqəm) və status — brut dövriyyə 6000+ → `Ulduz`, 5000+ → `Yaxsi`, qalanı → `Zeif`. Yalnız 3 və daha çox satışı olan satıcılar, xalis dövriyyəyə görə azalan sıra. *(4 bal)*
> **İpucu:** Mətn + ədədi + NULL + CASE + aqreqat + HAVING — hamısı bir sorğuda.

---

## Qiymətləndirmə

| Bölmə | Tapşırıqlar | Bal |
|---|---|---|
| A. Mətn (string) funksiyaları | 1–5 | 10 |
| B. Ədədi (numeric) funksiyalar | 6–9 | 8 |
| C. Tarix və zaman funksiyaları | 10–14 | 10 |
| D. Çevirmə və NULL funksiyaları | 15–17 | 6 |
| E. Şərti ifadələr — CASE | 18–20 | 6 |
| F. Aqreqat funksiyalar, GROUP BY, HAVING | 21–25 | 10 |
| G. Pəncərə (window) funksiyaları | 26–30 | 10 |
| H. Çətin və qarışıq tapşırıqlar | 31–40 | 40 |
| **Cəmi** | **1–40** | **100** |

| Bal | Qiymət |
|---|---|
| 90–100 | Əla |
| 75–89 | Yaxşı |
| 60–74 | Kafi |
| 60-dan aşağı | Təkrar |
