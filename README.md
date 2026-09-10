# PostgreSQL — Kompleks Praktik Tapşırıq

## Vahid ssenari üzrə: Normalizasiya (1NF – 5NF) və bütün JOIN növləri

**Fənn:** Verilənlər Bazaları / SQL
**Ümumi bal:** 100 (+10 bonus)

**Tələbənin adı, soyadı:** ______________________  **Qrup:** ____________
**Təhvil tarixi:** ____ / ____ / 20____  **Qiymət:** ____________

---

### Tapşırığın məntiqi

Bu iş bir-birindən ayrı iki mövzu deyil. Sizə pis dizayn edilmiş bir cədvəl verilir. Hissə A-da onu 1NF-dən 5NF-ə qədər normallaşdırırsınız — nəticədə 10-dan çox əlaqəli cədvəl alınır. Hissə B-də isə məhz o cədvəllərdən məlumatı geri yığmaq üçün bütün JOIN növlərini yazırsınız. Hissə C hər iki mövzunu birbaşa əlaqələndirir. Yəni: normallaşdırma JOIN-a ehtiyacı yaradır, JOIN isə normallaşdırmanın qiymətidir.

### Təhvil qaydaları

1. Bütün cavablar PostgreSQL 14+ sintaksisinə uyğun olmalıdır. Oracle/MySQL sintaksisi (`(+)`, `NVL`, `LIMIT 1,10`) qəbul edilmir.
2. Cavablar tək bir `soyad_ad_tapsiriq.sql` faylında verilir; hər tapşırıq belə ayrılır:

   ```sql
   -- ===== Tapşırıq B4 =====
   -- İzah: ...
   SELECT ...;
   ```

3. Bütün sorğular real olaraq icra olunmalı, nəticələrin ekran görüntüsü ayrıca PDF-də təqdim edilməlidir.
4. İzah tələb olunan bəndlərdə cavab SQL şərhi (`--`) kimi yazılır. İzahsız düzgün sorğu maksimum balın 50%-ni alır.
5. Hər `CREATE TABLE`-də PRIMARY KEY, uyğun FOREIGN KEY, NOT NULL və məntiqli CHECK/UNIQUE olmalıdır.
6. İki tələbədə eyni cavablar → hər ikisi 0 bal.

---

## ÜMUMİ SSENARİ (hər iki hissə üçün eynidir)

"AkademiyaOnline" kurs mərkəzi bütün fəaliyyətini bir Excel cədvəlində saxlayır. Cədvəl olduğu kimi PostgreSQL-ə köçürülüb və `kurs_qeydiyyat` adlanır. Sütun sayı çox olduğu üçün nümunə məlumatlar aşağıda üç hissəyə bölünüb — hər üç cədvəl eyni 5 sətrə aiddir (sətirlər `telebe_id` + `kurs_kod` üzrə uyğunlaşdırılır).

### Hissə 1 — tələbə məlumatları

| telebe_id | telebe_ad | dogum_tarixi | telefonlar |
|---|---|---|---|
| T-01 | Aysel Məmmədova | 2001-04-12 | 055-111-22-33, 070-111-22-33 |
| T-01 | Aysel Məmmədova | 2001-04-12 | 055-111-22-33, 070-111-22-33 |
| T-02 | Elvin Hüseynov | 1999-11-03 | 051-777-88-99 |
| T-03 | Nigar Səfərova | 2003-02-20 | 070-222-33-44, 055-222-33-44 |
| T-05 | Leyla Orucova | 2002-09-30 | (boş) |

### Hissə 2 — kurs məlumatları

| telebe_id | kurs_kod | kurs_ad | kurs_saat | qiymet | fenn_kod | fenn_ad |
|---|---|---|---|---|---|---|
| T-01 | SQL-101 | SQL Əsasları | 40 | 350 | F-SQL | Verilənlər Bazaları |
| T-01 | PYT-201 | Python Başlanğıc | 60 | 500 | F-PYT | Proqramlaşdırma |
| T-02 | SQL-101 | SQL Əsasları | 40 | 350 | F-SQL | Verilənlər Bazaları |
| T-03 | DSC-301 | Data Science | 80 | 700 | F-DSC | Data Science |
| T-05 | SQL-101 | SQL Əsasları | 40 | 350 | F-SQL | Verilənlər Bazaları |

### Hissə 3 — müəllim, otaq və qeydiyyat məlumatları

| telebe_id | kurs_kod | muellim_id | muellim_ad | muellim_email | muellim_dilleri | otaq_no | otaq_tutum | filial_kod | filial_unvan | seher | qeyd_tarixi | odenis | imtahan_bali |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| T-01 | SQL-101 | M-05 | Rəşad Quliyev | reshad@akademiya.az | Azərbaycan, İngilis, Rus | A-201 | 25 | F-01 | Bakı, Nizami küç. 12 | Bakı | 2025-02-10 | 350 | 92 |
| T-01 | PYT-201 | M-07 | Nigar Əliyeva | nigar@akademiya.az | Azərbaycan, İngilis | A-305 | 30 | F-01 | Bakı, Nizami küç. 12 | Bakı | 2025-02-12 | 500 | 78 |
| T-02 | SQL-101 | M-05 | Rəşad Quliyev | reshad@akademiya.az | Azərbaycan, İngilis, Rus | A-201 | 25 | F-01 | Bakı, Nizami küç. 12 | Bakı | 2025-02-15 | 350 | 65 |
| T-03 | DSC-301 | M-09 | Tural Abbasov | tural@akademiya.az | Azərbaycan | B-410 | 18 | F-02 | Gəncə, Atatürk pr. 5 | Gəncə | 2025-03-01 | 700 | 88 |
| T-05 | SQL-101 | M-05 | Rəşad Quliyev | reshad@akademiya.az | Azərbaycan, İngilis, Rus | A-201 | 25 | F-01 | Bakı, Nizami küç. 12 | Bakı | 2025-02-18 | 350 | (boş) |

---

### Biznes qaydaları — bütün funksional asılılıqlar bunlardan çıxarılmalıdır:

1. Bir tələbənin bir neçə telefon nömrəsi ola bilər; heç nömrəsi olmayan tələbə də var.
2. Kursun adı, saatı, qiyməti və aid olduğu fənn yalnız kurs koduna görə müəyyəndir; fənnin adı yalnız fənn koduna görə.
3. Müəllimin adı və e-poçtu yalnız müəllim koduna görə müəyyəndir. Hər müəllim yalnız bir fənn tədris edir, lakin bir fənni bir neçə müəllim tədris edə bilər.
4. Bir müəllim bir neçə dil bilir; bildiyi dil ilə tədris etdiyi fənn arasında heç bir əlaqə yoxdur.
5. Otaq kodu otağın tutumunu və hansı filialda olduğunu müəyyən edir; filial kodu ünvanı və şəhəri müəyyən edir.
6. Bir tələbə eyni kursa yalnız bir dəfə yazıla bilər; bu qeydiyyat üçün müəllim, otaq, tarix, ödəniş və (varsa) imtahan balı birmənalıdır. Qeydiyyatı olub hələ imtahan verməmiş tələbə ola bilər.
7. Müəllimlərin öz aralarında mentorluq əlaqəsi var: hər müəllimin ən çoxu bir mentoru ola bilər və mentor da müəllimdir.

### Əlavə məlumatlar

Yuxarıdakı 5 nümunə sətir cədvəlin hamısı deyil. Bazanı qurarkən aşağıdakı faktları da daxil edin — Hissə B-dəki bir çox tapşırığın nəticəsi məhz bunlardan asılıdır.

- **Tələbələr:** T-04 Kamran İsmayılov (2000-07-08) — telefonu yoxdur və heç bir kursa yazılmayıb. T-05 Leyla Orucova — telefonu yoxdur və SQL-101 üzrə hələ imtahan verməyib.
- **Əlavə qeydiyyatlar:** T-02 → PYT-201 (müəllim M-07, otaq B-305, 2025-03-05, ödəniş 500, imtahan balı 54); T-03 → SQL-202 (müəllim M-11, otaq B-305, 2025-03-10, ödəniş 450, imtahan balı 71). T-02-nin SQL-101 üzrə balı 65-dir.
- **Əlavə kurslar:** SQL-202 "Ətraflı SQL", 50 saat, 450 (fənn F-SQL); PYT-305 "Django Web", 70 saat, 600 (fənn F-PYT) — heç bir tələbə yazılmayıb.
- **Əlavə fənn:** F-DIZ "Dizayn" — heç bir kursu yoxdur.
- **Əlavə müəllimlər:** M-11 Aygün Vəliyeva, aygun@akademiya.az, fənn F-SQL, dilləri: Azərbaycan, Rus. M-13 Samir Nəbiyev, samir@akademiya.az, fənn F-PYT — heç bir dərsi və dil qeydi yoxdur.
- **Mentorluq:** M-07-nin mentoru M-05, M-09-un mentoru M-05, M-11-in mentoru M-07. M-05 və M-13-ün mentoru yoxdur.
- **Otaqlar:** A-201 (tutum 25, F-01), A-305 (30, F-01), B-305 (22, F-02), B-410 (18, F-02).
- **Filiallar:** F-01 "Bakı, Nizami küç. 12"; F-02 "Gəncə, Atatürk pr. 5"; F-03 "Sumqayıt, Sülh küç. 3" — heç bir otağı yoxdur.
- **Qiymət şkalası (hərf qiymətləri):** A = 90–100, B = 80–89, C = 70–79, D = 60–69, F = 0–59.

---

## HİSSƏ A — Normalizasiya (1NF → 5NF)

### A1 — Birinci Normal Forma (1NF) *(6 bal)*

a. `kurs_qeydiyyat` cədvəlində 1NF-i pozan bütün sütunları göstərin (ən azı ikisi var) və nə üçün pozduğunu izah edin.
b. Cədvəli 1NF-ə gətirin. Alınan cədvəl(lər)i `CREATE TABLE` ilə yazın.
c. 1NF-dən sonra hələ də qalan INSERT, UPDATE və DELETE anomaliyalarının hər biri üçün bu ssenaridən konkret nümunə verin (məsələn: "DSC-301 kursuna yazılan tək tələbə silinsə, ...").
d. Müzakirə: PostgreSQL-də `telefonlar` `TEXT[]` və ya `JSONB` istifadə etsək, cədvəl 1NF-də sayılırmı? 3–5 cümlə ilə əsaslandırın və massivin praktikada nə vaxt məqbul olduğunu yazın.

### A2 — İkinci Normal Forma (2NF) *(6 bal)*

a. 1NF cədvəlinin namizəd açarını müəyyən edin və seçiminizi əsaslandırın.
b. Bütün qismən asılılıqları X → Y formasında siyahılayın (ən azı 6 ədəd olmalıdır).
c. Cədvəli 2NF-ə parçalayın; bütün cədvəllərin `CREATE TABLE` ifadələrini PK + FK ilə yazın.
d. Parçalanmanın itkisiz (lossless-join) olduğunu izah edin: hansı sütunlar üzərindən geri birləşdirmə mümkündür?

### A3 — Üçüncü Normal Forma (3NF) *(7 bal)*

a. 2NF-dən sonra qalan tranzitiv asılılıqları tapın. `otaq_no → filial_kod → filial_unvan, seher` zəncirini və `kurs_kod → fenn_kod → fenn_ad` zəncirini nəzərdən qaçırmayın.
b. Hamısını 3NF-ə gətirin, yekun `CREATE TABLE`-ləri yazın.
c. "F-01 filialının ünvanı dəyişdi" əməliyyatı 3NF-dən əvvəl və sonra neçə sətri UPDATE edir? Hər iki halı yazın.
d. 3NF-in rəsmi tərifini yazın və "hər qeyri-açar atribut açardan, bütün açardan və yalnız açardan asılıdır" ifadəsinin hansı hissəsi 1NF-ə, hansı 2NF-ə, hansı 3NF-ə aiddir — göstərin.

### A4 — Boyce-Codd Normal Forma (BCNF) *(7 bal)*

3NF-dən sonra alınan cədvəllərdən biri belə görünür (3-cü biznes qaydasını xatırlayın: hər müəllim yalnız bir fənn tədris edir):

```
ders(telebe_id, fenn_kod, muellim_id)
```

a. Bütün funksional asılılıqları və bütün namizəd açarları yazın.
b. Bu münasibətin 3NF-də olduğunu, lakin BCNF-də olmadığını sübut edin. Hansı asılılıq pozur və niyə?
c. BCNF-ə parçalayın, `CREATE TABLE` ilə yazın.
d. Parçalanmadan sonra hansı funksional asılılıq itir (dependency preservation)? Onu PostgreSQL-də necə qorumaq olar? Ən azı bir üsul (`UNIQUE`, kompozit FK, generated column, `TRIGGER`) təklif edib DDL/kodunu yazın.

### A5 — Dördüncü Normal Forma (4NF) *(7 bal)*

4-cü biznes qaydasına görə müəllimin tədris etdiyi fənn ilə bildiyi dillər bir-birindən asılı deyil. Bunu bir cədvəldə saxlamağa çalışsaq:

```
muellim_bacariq(muellim_id, tedris_fenn, bildiyi_dil)
```

a. M-05 (Rəşad Quliyev) SQL və Python fənlərini tədris etsəydi və 3 dil bilsəydi, bu cədvəldə onun üçün neçə sətir olardı? Bütün sətirləri yazın.
b. Buradakı çoxqiymətli asılılıqları X →→ Y formasında yazın.
c. Münasibət BCNF-də olsa da niyə hələ artıqlıq yaradır? "M-05 alman dilini də öyrəndi" hadisəsi neçə yeni sətir tələb edir? "M-05 daha bir fənn tədris etməyə başladı" hadisəsi neçə sətir?
d. 4NF-ə parçalayın, `CREATE TABLE` yazın və M-05 üçün ümumi sətir sayının necə dəyişdiyini göstərin (əvvəl → sonra).

### A6 — Beşinci Normal Forma (5NF / PJNF) *(5 bal)*

Akademiya tədris planını belə saxlayır:

```
tedris_plani(muellim_id, kurs_kod, filial_kod)
```

Biznes qaydası (dövri / birləşmə asılılığı): əgər müəllim M kurs K-nı tədris edirsə, və K kursu F filialında keçirilirsə, və M müəllimi F filialında işləyirsə — onda mütləq M müəllimi K kursunu F filialında tədris edir.

**Cari məlumat:**

| muellim_id | kurs_kod | filial_kod |
|---|---|---|
| M-05 | SQL-101 | F-01 |
| M-05 | SQL-202 | F-01 |
| M-11 | SQL-101 | F-01 |
| M-05 | SQL-101 | F-02 |

a. Bu cədvəldə heç bir funksional və çoxqiymətli asılılıq olmadığını qısaca əsaslandırın (yəni münasibət artıq 4NF-dədir).
b. Üç binar proyeksiyaya ayırın: `muellim_kurs`, `kurs_filial`, `muellim_filial`. Hər üçünün məzmununu cədvəl şəklində yazın.
c. Üçünü geri birləşdirin və nəticənin ilkin cədvəllə eyni olduğunu göstərin. Bunu PostgreSQL-də `NATURAL JOIN` (və ya `USING`) ilə yazın.
d. İndi yalnız iki proyeksiyanı (`muellim_kurs ⋈ kurs_filial`) birləşdirin. Hansı saxta sətir (spurious tuple) yaranır? Onu yazın, real həyatda nəyi səhv iddia etdiyini bir cümlə ilə izah edin və bunun niyə 5NF-i zəruri etdiyini yazın.

### A7 — Yekun sxem *(7 bal)*

a. A1–A6-nın nəticələrini birləşdirib tam normallaşdırılmış sxemi tək bir işlək `.sql` skripti kimi yazın: cədvəllər düzgün ardıcıllıqla (asılı olanlar sonra), bütün PK/FK/UNIQUE/CHECK/NOT NULL ilə. Müəllimlərin mentorluq əlaqəsini (7-ci biznes qaydası) öz-özünə istinad edən FK ilə əlavə etməyi unutmayın.
b. Sxemin ER diaqramını çəkin; əlaqələrin kardinallığını (1:1, 1:N, M:N) göstərin.
c. Bir cümlə ilə: normallaşdırma nə vaxt ziyanlıdır və denormalizasiya nə vaxt əsaslandırılır?

---

## HİSSƏ B — JOIN növləri (A hissəsinin sxemi üzərində)

**Başlamazdan əvvəl:** Bu hissə Hissə A-da özünüzün qurduğu sxem üzərində icra olunur. Əvvəlcə ssenaridəki bütün məlumatları (nümunə sətirlər + "Əlavə məlumatlar" siyahısı) öz cədvəllərinizə INSERT edin. Tapşırıqlarda cədvəllər ümumi adla göstərilir ("fənn cədvəli", "qeydiyyat cədvəli") — sorğuda öz sxeminizdəki adları yazın. Sxeminiz düzgün normallaşdırılıbsa, bütün tapşırıqlar icra olunmalıdır; icra olunmursa, əvvəlcə Hissə A-ya qayıdın.

**Verilənlərdə qəsdən qoyulmuş "boşluqlar" (OUTER JOIN tapşırıqlarının nəticəsi məhz bunlarla fərqlənir):**

- T-04 — heç bir kursa yazılmayıb və telefon nömrəsi yoxdur
- T-05 — qeydiyyatı var, amma telefonu və imtahan nəticəsi yoxdur
- PYT-305 — heç bir tələbə yazılmayan kurs • F-DIZ — heç bir kursu olmayan fənn
- M-13 — heç bir dərsi olmayan və heç dil qeydi olmayan müəllim • M-05, M-13 — mentoru olmayan müəllimlər
- F-03 — heç bir otağı və qeydiyyatı olmayan filial

### B1 — INNER JOIN *(4 bal)*

Hər qeydiyyat üçün tələbənin adı, kursun adı, fənnin adı və ödənişi çıxarın. Nəticəni tələbə adına görə sıralayın.
İzah edin: nəticədə neçə sətir var və T-04 niyə yoxdur?

### B3 — LEFT (OUTER) JOIN *(3 bal)*

> *(qeyd: mənbə sənəddə bu bənd "B2" başlığı ilə verilib)*

a. Bütün tələbələri telefon nömrələri ilə göstərin; nömrəsi olmayan üçün `'Nömrə yoxdur'` yazılsın (`COALESCE`).
b. Bütün qeydiyyatları imtahan balı ilə göstərin; imtahan verməmişlər üçün `'İmtahan verilməyib'` görünsün.
c. Tipik səhv: LEFT JOIN-u INNER JOIN-a çevirən səhv nədir? Şərti `ON` əvəzinə `WHERE`-ə yazın, hər iki sorğunun nəticəsini müqayisə edin və fərqi izah edin.

### B3 — RIGHT (OUTER) JOIN *(3 bal)*

RIGHT JOIN ilə bütün kursları və onlara yazılan tələbələrin sayını çıxarın. Tələbəsi olmayan kurs (PYT-305) da 0 ilə görünməlidir.
İzah edin: eyni nəticəni LEFT JOIN ilə necə alarsınız? Hər iki sorğunu yazın və praktikada RIGHT JOIN-un niyə nadir işlədildiyini izah edin.

### B4 — FULL OUTER JOIN *(4 bal)*

a. `fənn` və `kurs` cədvəllərini fənn kodu üzrə FULL OUTER JOIN edin. CASE ilə vəziyyət sütunu əlavə edin: `'Uyğun'`, `'Kursu olmayan fənn'`, `'Fənni olmayan kurs'`.
b. Yalnız uyğunsuz sətirləri qaytaran sorğunu yazın (FULL OUTER + `IS NULL`).
c. `müəllim` və `qeydiyyat` cədvəlləri arasında FULL OUTER JOIN edin. Nəticədə M-13 görünürmü? Niyə cari verilənlərdə "sağ tərəfdə tək qalan" sətir yoxdur? (İpucu: FOREIGN KEY.)
d. Eyni nəticəni LEFT JOIN ∪ RIGHT JOIN ilə alın. Burada UNION yerinə UNION ALL niyə işləməz?

### B5 — CROSS JOIN *(3 bal)*

a. `filial × qiymət şkalası` Dekart hasilini çıxarın. Sətir sayını sorğunu icra etməzdən əvvəl hesablayıb yazın, sonra yoxlayın.
b. CROSS JOIN ilə "fənn × qiymət hərfi" matrisini qurun və hər xanada həmin fənn üzrə həmin qiyməti alan tələbələrin sayını göstərin. Sıfır olan xanalar da görünməlidir. (İpucu: CROSS JOIN + LEFT JOIN + COUNT.)
c. Təsadüfi Dekart partlayışının əsas səbəbini bir cümlə ilə yazın.

### B6 — SELF JOIN *(3 bal)*

a. Hər müəllimin adını və mentorunun adını göstərin; mentoru olmayanlar üçün `'Mentoru yoxdur'`.
b. Mentoru ilə eyni fənni tədris edən müəllimləri tapın.
c. Eyni kursa yazılmış, lakin fərqli tələbələrdən ibarət cütlükləri çıxarın. Hər cütlük bir dəfə görünsün ((A,B) varsa (B,A) olmasın).
d. İzah edin: SELF JOIN-da alias niyə məcburidir? İki səviyyəli zəncir (müəllim → mentor → mentorun mentoru) üçün sorğunu yazın.

### B7 — NATURAL JOIN, USING və ON *(10 bal)*

a. `otaq` və `filial` cədvəllərini NATURAL JOIN ilə birləşdirin. Birləşmə hansı sütun(lar) üzrə gedir?
b. Eyni sorğunu `JOIN ... USING (filial_kodu)` ilə yazın. `SELECT *` ilə yoxlayın: USING nəticə sütunlarına necə təsir edir?
c. Eyni sorğunu `JOIN ... ON` ilə yazın və üç variantın nəticə sütunlarını müqayisə edin.
d. Təhlükə ssenarisi: hər iki cədvələ `ALTER TABLE ... ADD COLUMN created_at TIMESTAMP` əlavə edin (`otaq` və `filial` cədvəllərinə) və NATURAL JOIN-u yenidən icra edin. Nəticə necə dəyişdi və niyə? İstehsal kodunda NATURAL JOIN-un niyə tövsiyə edilmədiyini izah edin. (Yoxlamadan sonra sütunları `DROP COLUMN` edin.)

### B8 — Çoxcədvəlli JOIN zənciri *(4 bal)*

Ən azı 6 cədvəli birləşdirərək bir hesabat çıxarın: tələbənin adı, kursun adı, fənnin adı, müəllimin adı, otaq nömrəsi, otağın tutumu, filialın ünvanı, şəhər, ödəniş və imtahan balı.

a. Yalnız imtahan vermiş tələbələr üçün sorğunu yazın (tam INNER zənciri).
b. Sorğunu elə dəyişin ki, imtahan verməmiş qeydiyyatlar (T-05) da görünsün. Hansı bənddə hansı JOIN növünü dəyişdiniz?
c. İzah edin: zəncirdə LEFT JOIN-dan sonra gələn bir INNER JOIN nə üçün bütün nəticəni "daxili birləşməyə" çevirə bilər? Bunu qəsdən yaradıb göstərin.

### B9 — Bərabərlikdən fərqli (non-equi) JOIN *(3 bal)*

a. Hər imtahan nəticəsinin hərf qiymətini tapın: imtahan nəticələri cədvəli ilə qiymət şkalası cədvəlini `BETWEEN` şərti ilə birləşdirin (`=` istifadə etmədən).
b. Hər hərf üzrə nəticə sayını və orta balı çıxarın; heç kimin almadığı hərf də 0 ilə görünsün.
c. Özündən böyük bal alan hər tələbə ilə cütlük quran non-equi SELF JOIN yazın və hər nəticə üçün "ondan yuxarıda neçə nəticə var" sütununu hesablayın.

### B10 — ANTI JOIN *(3 bal)*

a. Heç bir kursa yazılmamış tələbələri `LEFT JOIN ... WHERE ... IS NULL` üsulu ilə tapın.
b. Eyni nəticəni `NOT EXISTS` ilə alın.
c. Eyni nəticəni `NOT IN` ilə almağa çalışın. Alt-sorğuya `UNION ALL SELECT NULL` əlavə edin — nəticə necə dəyişir? Bunu üç-qiymətli məntiq (TRUE / FALSE / UNKNOWN) baxımından izah edin.
d. Heç bir kursu olmayan fənni və heç bir otağı olmayan filialı tapın.

### B11 — SEMI JOIN *(4 bal)*

a. Ən azı bir kursa yazılmış tələbələri `EXISTS` ilə tapın; eyni nəticəni `IN` və `INNER JOIN + DISTINCT` ilə də alın.
b. Üç variantı müqayisə edin: INNER JOIN-da DISTINCT niyə lazımdır və hansı halda DISTINCT nəticəni səhv edə bilər (məsələn SUM ilə birlikdə)?
c. Qiyməti 400-dən yuxarı olan ən azı bir kursa yazılmış tələbələri `EXISTS` ilə tapın.

### B12 — LATERAL JOIN *(4 bal)*

a. `LATERAL` ilə hər kurs üzrə ən yüksək 2 bal alan tələbəni çıxarın. Nəticəsi olmayan kurslar da sətir kimi görünsün (`LEFT JOIN LATERAL ... ON true`).
b. Hər filial üzrə ən son qeydiyyatı (`qeydiyyat_tarixi` üzrə) `LATERAL` ilə tapın.
c. (a) bəndinin eyni nəticəsini `ROW_NUMBER() OVER (PARTITION BY ...)` ilə alın və iki yanaşmanı müqayisə edin. LATERAL olmadan alt-sorğu xarici cədvəlin sütununa niyə müraciət edə bilmir?

### B13 — JOIN + aqreqasiya *(4 bal)*

a. Hər filial üzrə: otaq sayı, unikal tələbə sayı, keçirilən kurs sayı və ümumi gəlir. F-03 da 0 ilə görünməlidir.
b. Yalnız ümumi gəliri 1000-dən çox olan filialları saxlayın (`HAVING`).
c. Tipik səhv: `tələbə` cədvəli ilə `qeydiyyat` cədvəlinin LEFT JOIN-undan sonra `COUNT(*)` ilə `COUNT(qeydiyyat.kurs_kod)` fərqli nəticə verir. Hər ikisini icra edin, T-04 üçün fərqi göstərin və səbəbini izah edin.

---

## HİSSƏ C — İki mövzunun birləşdiyi yer

### C1 — Normallaşdırılmış sxemdən ilkin cədvəli geri qurmaq

Yalnız JOIN-lardan istifadə edərək səhifə 1-dəki `kurs_qeydiyyat` cədvəlini olduğu kimi geri düzəldin — bütün sütunlarla, o cümlədən vergüllə birləşdirilmiş `telefonlar` və `muellim_dilleri` sütunları ilə (`STRING_AGG` istifadə edin).

- Neçə cədvəli birləşdirməli oldunuz?
- Telefonu olmayan tələbə (T-05) və imtahan verməmiş qeydiyyat itməməlidir — bunun üçün hansı JOIN növünü seçdiniz və niyə?
- Bir cümlə ilə: normallaşdırma nəyi ucuzlaşdırdı, nəyi bahalaşdırdı?

### C2 — 5NF cədvəllərinin birləşdirilməsi

a. A6-da aldığınız üç binar cədvəli birləşdirib tam tədris planını çıxarın; müəllimin adını və filialın ünvanını da əlavə edin (yəni 5 cədvəl).
b. Yalnız iki cədvəli birləşdirən variantı da yazın və hansı sətrin artıq gəldiyini göstərin. Bu sətir real həyatda nəyi səhv iddia edir?

### C3 — Anomaliyaların praktik nümayişi

Aşağıdakı üç əməliyyatı həm normallaşdırılmamış `kurs_qeydiyyat` cədvəlində (fikrən), həm də öz normallaşdırılmış sxeminizdə (real `UPDATE` / `DELETE` / `INSERT` ilə) yerinə yetirin və hər ikisində neçə sətrin təsirləndiyini müqayisə edin:

a. Müəllim M-05-in e-poçtu dəyişdi.
b. Yeni bir fənn ("Kibertəhlükəsizlik") əlavə olunur, hələ heç bir kursu və tələbəsi yoxdur. Normallaşdırılmamış cədvəldə bunu ümumiyyətlə yazmaq olurmu?
c. DSC-301 kursuna yazılan tək tələbənin (T-03) qeydiyyatı silinir. Normallaşdırılmamış cədvəldə hansı məlumat həmişəlik itir?

Nəticəni bir cümlə ilə ümumiləşdirin: bu üç hal hansı anomaliyalara (UPDATE / INSERT / DELETE) uyğundur?

*(C1–C3 üçün cəmi: 10 bal)*

---

## Bonus *(+10 bal)*

a. C1 sorğusu üçün `EXPLAIN (ANALYZE, BUFFERS)` icra edin; planlaşdırıcının seçdiyi birləşmə alqoritmlərini (Nested Loop, Hash Join, Merge Join) sadalayın və seçimin səbəbini izah edin.
b. `SET enable_hashjoin = off;` edib planı yenidən alın. Nə dəyişdi? (Sonda `on` qaytarın.)
c. `qeydiyyat` cədvəlinin `müəllim` sütunu üzərində indeks yaradın və planın dəyişib-dəyişmədiyini yoxlayın. Bu ölçüdə cədvəldə indeksin niyə istifadə olunmaya biləcəyini izah edin.
d. A4-də itən funksional asılılığı qorumaq üçün yazdığınız `TRIGGER`-i (və ya digər mexanizmi) real olaraq test edin: qaydanı pozan bir INSERT yazın və xətanın alındığını göstərin.

---

## Qiymətləndirmə meyarları

| Bölmə | Bal | Əsas meyar |
|---|---|---|
| A1 – A3 (1NF, 2NF, 3NF) | 19 | Asılılıqların düzgün tapılması, itkisiz parçalanma, düzgün DDL |
| A4 (BCNF) | 7 | Namizəd açarlar, 3NF/BCNF fərqinin sübutu, itən asılılığın qorunması |
| A5 (4NF) | 7 | MVD-nin yazılması, sətir sayının hesablanması |
| A6 (5NF) | 7 | Üçlü parçalanma və saxta sətrin göstərilməsi |
| A7 (Yekun sxem) | 5 | İşlək skript, məhdudiyyətlər, ER diaqram |
| B1 – B7 (əsas JOIN növləri) | 24 | Düzgün JOIN seçimi, NULL davranışının izahı |
| B8 – B11 (zəncir, anti/semi join) | 15 | Çoxcədvəlli zəncir, üç-qiymətli məntiq |
| B12 – B13 (LATERAL, aqreqasiya) | 6 | LATERAL, COUNT fərqinin izahı |
| C1 – C3 (birləşmiş hissə) | 10 | İki mövzunun əlaqələndirilməsi, anomaliyaların nümayişi |
| **Bonus** | **+10** | EXPLAIN ANALYZE, trigger testi |
| **CƏMİ** | **100 (+10)** | |

---

Uğurlar! Sual yaranarsa, təhvil tarixindən ən azı 2 gün əvvəl müraciət edin.
