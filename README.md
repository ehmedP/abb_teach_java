# SQL PRAKTİKA · TAPŞIRIQ VƏRƏQİ

## Onlayn Mağaza Bazası

Bu tapşırıqda sıfırdan `magaza` adlı sxem quracaq, onu məlumatla dolduracaq və üzərində sorğular yazacaqsınız.

**Mövzular:** DDL, DML, DQL, WHERE, ORDER BY, TCL.

**Cədvəllər:** `kateqoriyalar` · `mehsullar` · `musteriler` · `sifarisler`

---

## Bölmə 1 · DDL

> Cədvəl yaratmaq, dəyişmək, silmək

1. Verilənlər bazasında `magaza` adlı sxem yarat.
2. `kateqoriyalar` cədvəlini yarat: `id` (PK), `ad` (VARCHAR 50, NOT NULL, UNIQUE), `tesvir` (VARCHAR 200).
3. `mehsullar` cədvəlini yarat: `id` (PK), `ad` (VARCHAR 100, NOT NULL), `qiymet` (DECIMAL 10,2 — 0-dan böyük olmalıdır), `stok` (INTEGER — mənfi ola bilməz), `kateqoriya_id` (xarici açar). CHECK constraint istifadə et.
4. `musteriler` cədvəlini yarat: `id` (PK), `ad` və `soyad` (NOT NULL), `email` (UNIQUE), `sehir` (VARCHAR 50), `qeydiyyat_tarixi` (DATE, default — bugünkü tarix). DEFAULT CURRENT_DATE.
5. `sifarisler` cədvəlini yarat: `id` (PK), `musteri_id` və `mehsul_id` (NOT NULL, xarici açar), `say` (ən azı 1), `tarix` (DATE), `status` (VARCHAR 20).
6. `musteriler` cədvəlinə `telefon` adlı VARCHAR(20) sütunu əlavə et. ALTER TABLE ... ADD COLUMN.
7. `mehsullar` cədvəlində `stok` sütununun adını `stok_sayi` olaraq dəyiş. RENAME COLUMN.
8. `mehsullar(kateqoriya_id)` üzərində `idx_mehsul_kateqoriya` adlı index yarat.

---

## Bölmə 2 · DML

> INSERT · UPDATE · DELETE

9. 4 kateqoriya əlavə et: Elektronika, Geyim, Kitab, İdman.
10. Ən azı 10 məhsul əlavə et — hər kateqoriyadan minimum 2 ədəd. Qiymətlər müxtəlif olsun, ən azı 2 məhsulun stoku 0 olsun.
11. 6 müştəri əlavə et. Ən azı 2-si Bakıdan, 1-i Gəncədən olsun. Bir müştərinin `telefon` sütunu NULL qalsın.
12. 12 sifariş əlavə et. `status` dəyərləri `'gozleyir'`, `'gonderildi'`, `'catdirildi'`, `'legv edildi'` arasından olsun.
13. Bütün Elektronika məhsullarının qiymətini 10% artır.
14. Telefonu qeyd olunmamış müştərilərə `'+994500000000'` dəyərini yaz. **Diqqət:** `= NULL` yox, `IS NULL`.
15. Statusu `'legv edildi'` olan bütün sifarişləri sil.

---

## Bölmə 3 · DQL — WHERE şərtləri

> Hər tapşırıqda göstərilən operatordan istifadə et

16. Qiyməti 100 manatdan baha olan məhsulları göstər. `(>)`
17. Stoku sıfır olan məhsulları tap. `(=)`
18. Bakıda yaşamayan müştəriləri göstər. `(<>` və ya `!=)`
19. Qiyməti 50 ilə 200 manat arasında olan məhsulları tap. `(BETWEEN)`
20. Statusu `'gozleyir'` və ya `'gonderildi'` olan sifarişləri göstər. `(IN)`
21. Adı “A” hərfi ilə başlayan müştəriləri tap. `(LIKE 'A%')`
22. Emailində `gmail` sözü keçən müştəriləri göstər. `(LIKE '%...%')`
23. Telefon nömrəsi olmayan müştəriləri tap. Nəticə niyə belədir? Şərh kimi izah et. `(IS NULL)`
24. Qiyməti 100-dən baha **və** stoku 5-dən çox olan məhsulları göstər. `(AND)`

---

## Bölmə 4 · DQL — ORDER BY

> Nəticələri sıralamaq

25. Bütün məhsulları qiymətə görə ucuzdan bahaya düz. `(ASC)`
26. Müştəriləri qeydiyyat tarixinə görə ən yeniləri əvvəldə olmaqla düz. `(DESC)`
27. Məhsulları əvvəlcə `kateqoriya_id`-ə görə artan, sonra qiymətə görə azalan sırala.
28. Ən bahalı 3 məhsulu göstər. `(ORDER BY ... LIMIT)`

---

## Bölmə 5 · TCL

> Tranzaksiyaların idarə olunması

29. BEGIN ilə tranzaksiya aç, yeni bir məhsul əlavə et, SELECT ilə yoxla, sonra ROLLBACK et. Məhsul cədvəldə qaldımı? Yoxla və cavabı şərh kimi yaz.
30. BEGIN ilə tranzaksiya aç, bir müştərinin şəhərini dəyiş, COMMIT et və nəticəni yoxla.
31. Tranzaksiya içində 2 sifariş əlavə et. Birincidən sonra `SAVEPOINT sp1` qoy, ikincini əlavə et, sonra `ROLLBACK TO sp1` et və COMMIT ver. Neçə sifariş qaldı?
32. Tranzaksiya aç və qəsdən səhv sorğu yaz (məsələn, olmayan sütun adı). Sonra düzgün bir sorğu yaz — işləyirmi? Aldığın xəta kodunu şərh kimi qeyd et və vəziyyətdən necə çıxdığını yaz. `(25P02)`

---

## Qaydalar

- Hər sorğunun üstündə `-- Tapşırıq N` şəklində şərh olsun.
- Mətn dəyərləri tək dırnaq içində yazılır: `'Bakı'`. Ədədlər dırnaqsızdır: `150`.
- Cədvəl adlarını sxemlə birlikdə yaz: `magaza.mehsullar`.
- Hər tapşırığın nəticəsini SELECT ilə yoxla.
- Bütün işi bir `.sql` faylında ardıcıllıqla təhvil ver.
