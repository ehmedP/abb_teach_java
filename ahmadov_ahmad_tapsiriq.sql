-- =========================================================
-- AkademiyaOnline - normallasdirilmis sxem (5NF) + datalar
-- PostgreSQL 14+
-- Ahmadov Ahmad
-- =========================================================

-- ===== Kohne cedvelleri silinmesi (asili olanlar evvel) =====
DROP TABLE IF EXISTS telebe_muellim;
DROP TABLE IF EXISTS muellim_fenn;
DROP TABLE IF EXISTS muellim_filial;
DROP TABLE IF EXISTS kurs_filial;
DROP TABLE IF EXISTS muellim_kurs;
DROP TABLE IF EXISTS qeydiyyat;
DROP TABLE IF EXISTS telebe_telefon;
DROP TABLE IF EXISTS muellim_dil;
DROP TABLE IF EXISTS telebe;
DROP TABLE IF EXISTS muellim;
DROP TABLE IF EXISTS kurs;
DROP TABLE IF EXISTS fenn;
DROP TABLE IF EXISTS otaq;
DROP TABLE IF EXISTS filial;
DROP TABLE IF EXISTS qiymet_shkalasi;
DROP TABLE IF EXISTS kurs_qeydiyyat;


-- =========================================================
-- 1) FILIAL
-- filial_kod -> filial_unvan, seher
-- =========================================================
CREATE TABLE filial
(
    filial_kod   VARCHAR(10) PRIMARY KEY,
    filial_unvan VARCHAR(100) NOT NULL,
    seher        VARCHAR(50)  NOT NULL,
    CONSTRAINT uq_filial_unvan UNIQUE (filial_unvan)
);

-- =========================================================
-- 2) OTAQ
-- otaq_no -> otaq_tutum, filial_kod
-- =========================================================
CREATE TABLE otaq
(
    otaq_no    VARCHAR(10) PRIMARY KEY,
    otaq_tutum INT         NOT NULL,
    filial_kod VARCHAR(10) NOT NULL,
    CONSTRAINT fk_otaq_filial FOREIGN KEY (filial_kod) REFERENCES filial (filial_kod),
    CONSTRAINT ch_otaq_tutum CHECK (otaq_tutum > 0)
);

-- =========================================================
-- 3) FENN
-- fenn_kod -> fenn_ad
-- =========================================================
CREATE TABLE fenn
(
    fenn_kod VARCHAR(10) PRIMARY KEY,
    fenn_ad  VARCHAR(60) NOT NULL,
    CONSTRAINT uq_fenn_ad UNIQUE (fenn_ad)
);

-- =========================================================
-- 4) KURS
-- kurs_kod -> kurs_ad, kurs_saat, qiymet, fenn_kod
-- =========================================================
CREATE TABLE kurs
(
    kurs_kod  VARCHAR(10) PRIMARY KEY,
    kurs_ad   VARCHAR(60)   NOT NULL,
    kurs_saat INT           NOT NULL,
    qiymet    NUMERIC(8, 2) NOT NULL,
    fenn_kod  VARCHAR(10)   NOT NULL,
    CONSTRAINT fk_kurs_fenn FOREIGN KEY (fenn_kod) REFERENCES fenn (fenn_kod),
    CONSTRAINT ch_kurs_saat CHECK (kurs_saat > 0),
    CONSTRAINT ch_kurs_qiymet CHECK (qiymet >= 0)
);

-- =========================================================
-- 5) MUELLIM
-- muellim_id -> muellim_ad, muellim_email, fenn_kod, mentor_id
-- mentor_id -> ozune istinad eden FK (7-ci biznes qaydasi)
-- =========================================================
CREATE TABLE muellim
(
    muellim_id    VARCHAR(10) PRIMARY KEY,
    muellim_ad    VARCHAR(60) NOT NULL,
    muellim_email VARCHAR(80) NOT NULL,
    fenn_kod      VARCHAR(10) NOT NULL,
    mentor_id     VARCHAR(10),
    CONSTRAINT uq_muellim_email UNIQUE (muellim_email),
    CONSTRAINT fk_muellim_fenn FOREIGN KEY (fenn_kod) REFERENCES fenn (fenn_kod),
    CONSTRAINT fk_muellim_mentor FOREIGN KEY (mentor_id) REFERENCES muellim (muellim_id),
    CONSTRAINT ch_muellim_mentor_ozu CHECK (mentor_id <> muellim_id)
);

-- =========================================================
-- 6) MUELLIM_DIL  (4NF - dil ile tedris etdiyi fenn asili deyil)
-- =========================================================
CREATE TABLE muellim_dil
(
    muellim_id VARCHAR(10) NOT NULL,
    dil        VARCHAR(30) NOT NULL,
    CONSTRAINT pk_muellim_dil PRIMARY KEY (muellim_id, dil),
    CONSTRAINT fk_muellim_dil_muellim FOREIGN KEY (muellim_id) REFERENCES muellim (muellim_id)
);

-- =========================================================
-- 7) TELEBE
-- telebe_id -> telebe_ad, dogum_tarixi
-- =========================================================
CREATE TABLE telebe
(
    telebe_id    VARCHAR(10) PRIMARY KEY,
    telebe_ad    VARCHAR(60) NOT NULL,
    dogum_tarixi DATE        NOT NULL,
    CONSTRAINT ch_telebe_dogum CHECK (dogum_tarixi < CURRENT_DATE)
);

-- =========================================================
-- 8) TELEBE_TELEFON  (1NF - cox qiymetli sutun ayrildi)
-- =========================================================
CREATE TABLE telebe_telefon
(
    telebe_id VARCHAR(10) NOT NULL,
    telefon   VARCHAR(20) NOT NULL,
    CONSTRAINT pk_telebe_telefon PRIMARY KEY (telebe_id, telefon),
    CONSTRAINT fk_telefon_telebe FOREIGN KEY (telebe_id) REFERENCES telebe (telebe_id)
);

-- =========================================================
-- 9) QEYDIYYAT
-- (telebe_id, kurs_kod) -> muellim_id, otaq_no, qeyd_tarixi, odenis, imtahan_bali
-- imtahan_bali NULL ola biler (hele imtahan vermeyen telebe)
-- =========================================================
CREATE TABLE qeydiyyat
(
    telebe_id    VARCHAR(10)   NOT NULL,
    kurs_kod     VARCHAR(10)   NOT NULL,
    muellim_id   VARCHAR(10)   NOT NULL,
    otaq_no      VARCHAR(10)   NOT NULL,
    qeyd_tarixi  DATE          NOT NULL,
    odenis       NUMERIC(8, 2) NOT NULL,
    imtahan_bali INT,
    CONSTRAINT pk_qeydiyyat PRIMARY KEY (telebe_id, kurs_kod),
    CONSTRAINT fk_qeyd_telebe FOREIGN KEY (telebe_id) REFERENCES telebe (telebe_id),
    CONSTRAINT fk_qeyd_kurs FOREIGN KEY (kurs_kod) REFERENCES kurs (kurs_kod),
    CONSTRAINT fk_qeyd_muellim FOREIGN KEY (muellim_id) REFERENCES muellim (muellim_id),
    CONSTRAINT fk_qeyd_otaq FOREIGN KEY (otaq_no) REFERENCES otaq (otaq_no),
    CONSTRAINT ch_qeyd_odenis CHECK (odenis >= 0),
    CONSTRAINT ch_qeyd_bal CHECK (imtahan_bali BETWEEN 0 AND 100)
);

-- =========================================================
-- 10) QIYMET_SHKALASI  (herf qiymetleri)
-- =========================================================
CREATE TABLE qiymet_shkalasi
(
    herf    CHAR(1) PRIMARY KEY,
    min_bal INT NOT NULL,
    max_bal INT NOT NULL,
    CONSTRAINT ch_shkala_interval CHECK (min_bal <= max_bal),
    CONSTRAINT ch_shkala_diapazon CHECK (min_bal >= 0 AND max_bal <= 100)
);

-- =========================================================
-- 11) 5NF - tedris_plani-nin uc binar proyeksiyasi
-- =========================================================
CREATE TABLE muellim_kurs
(
    muellim_id VARCHAR(10) NOT NULL,
    kurs_kod   VARCHAR(10) NOT NULL,
    CONSTRAINT pk_muellim_kurs PRIMARY KEY (muellim_id, kurs_kod),
    CONSTRAINT fk_mk_muellim FOREIGN KEY (muellim_id) REFERENCES muellim (muellim_id),
    CONSTRAINT fk_mk_kurs FOREIGN KEY (kurs_kod) REFERENCES kurs (kurs_kod)
);

CREATE TABLE kurs_filial
(
    kurs_kod   VARCHAR(10) NOT NULL,
    filial_kod VARCHAR(10) NOT NULL,
    CONSTRAINT pk_kurs_filial PRIMARY KEY (kurs_kod, filial_kod),
    CONSTRAINT fk_kf_kurs FOREIGN KEY (kurs_kod) REFERENCES kurs (kurs_kod),
    CONSTRAINT fk_kf_filial FOREIGN KEY (filial_kod) REFERENCES filial (filial_kod)
);

CREATE TABLE muellim_filial
(
    muellim_id VARCHAR(10) NOT NULL,
    filial_kod VARCHAR(10) NOT NULL,
    CONSTRAINT pk_muellim_filial PRIMARY KEY (muellim_id, filial_kod),
    CONSTRAINT fk_mf_muellim FOREIGN KEY (muellim_id) REFERENCES muellim (muellim_id),
    CONSTRAINT fk_mf_filial FOREIGN KEY (filial_kod) REFERENCES filial (filial_kod)
);


-- =========================================================
-- DATALARIN DOLDURULMASI
-- =========================================================

-- ----- Filiallar (F-03-un hec bir otagi yoxdur) -----
INSERT INTO filial (filial_kod, filial_unvan, seher)
VALUES ('F-01', 'Bakı, Nizami küç. 12', 'Bakı'),
       ('F-02', 'Gəncə, Atatürk pr. 5', 'Gəncə'),
       ('F-03', 'Sumqayıt, Sülh küç. 3', 'Sumqayıt');

-- ----- Otaqlar -----
INSERT INTO otaq (otaq_no, otaq_tutum, filial_kod)
VALUES ('A-201', 25, 'F-01'),
       ('A-305', 30, 'F-01'),
       ('B-305', 22, 'F-02'),
       ('B-410', 18, 'F-02');

-- ----- Fennler (F-DIZ-in hec bir kursu yoxdur) -----
INSERT INTO fenn (fenn_kod, fenn_ad)
VALUES ('F-SQL', 'Verilənlər Bazaları'),
       ('F-PYT', 'Proqramlaşdırma'),
       ('F-DSC', 'Data Science'),
       ('F-DIZ', 'Dizayn');

-- ----- Kurslar (PYT-305-e hec bir telebe yazilmayib) -----
INSERT INTO kurs (kurs_kod, kurs_ad, kurs_saat, qiymet, fenn_kod)
VALUES ('SQL-101', 'SQL Əsasları', 40, 350, 'F-SQL'),
       ('SQL-202', 'Ətraflı SQL', 50, 450, 'F-SQL'),
       ('PYT-201', 'Python Başlanğıc', 60, 500, 'F-PYT'),
       ('PYT-305', 'Django Web', 70, 600, 'F-PYT'),
       ('DSC-301', 'Data Science', 80, 700, 'F-DSC');

-- ----- Muellimler -----
-- Evvel mentoru olmayanlari yazırıq, sonra mentoru olanları (self FK ucun)
INSERT INTO muellim (muellim_id, muellim_ad, muellim_email, fenn_kod, mentor_id)
VALUES ('M-05', 'Rəşad Quliyev', 'reshad@akademiya.az', 'F-SQL', NULL),
       ('M-13', 'Samir Nəbiyev', 'samir@akademiya.az', 'F-PYT', NULL);

INSERT INTO muellim (muellim_id, muellim_ad, muellim_email, fenn_kod, mentor_id)
VALUES ('M-07', 'Nigar Əliyeva', 'nigar@akademiya.az', 'F-PYT', 'M-05'),
       ('M-09', 'Tural Abbasov', 'tural@akademiya.az', 'F-DSC', 'M-05');

INSERT INTO muellim (muellim_id, muellim_ad, muellim_email, fenn_kod, mentor_id)
VALUES ('M-11', 'Aygün Vəliyeva', 'aygun@akademiya.az', 'F-SQL', 'M-07');

-- ----- Muellim dilleri (M-13-un dil qeydi yoxdur) -----
INSERT INTO muellim_dil (muellim_id, dil)
VALUES ('M-05', 'Azərbaycan'),
       ('M-05', 'İngilis'),
       ('M-05', 'Rus'),
       ('M-07', 'Azərbaycan'),
       ('M-07', 'İngilis'),
       ('M-09', 'Azərbaycan'),
       ('M-11', 'Azərbaycan'),
       ('M-11', 'Rus');

-- ----- Telebeler -----
INSERT INTO telebe (telebe_id, telebe_ad, dogum_tarixi)
VALUES ('T-01', 'Aysel Məmmədova', DATE '2001-04-12'),
       ('T-02', 'Elvin Hüseynov', DATE '1999-11-03'),
       ('T-03', 'Nigar Səfərova', DATE '2003-02-20'),
       ('T-04', 'Kamran İsmayılov', DATE '2000-07-08'),
       ('T-05', 'Leyla Orucova', DATE '2002-09-30');

-- ----- Telefonlar (T-04 ve T-05-in telefonu yoxdur) -----
INSERT INTO telebe_telefon (telebe_id, telefon)
VALUES ('T-01', '055-111-22-33'),
       ('T-01', '070-111-22-33'),
       ('T-02', '051-777-88-99'),
       ('T-03', '070-222-33-44'),
       ('T-03', '055-222-33-44');

-- ----- Qeydiyyatlar (T-04 hec bir kursa yazilmayib) -----
-- T-05-in imtahan bali NULL-dir (hele imtahan vermeyib)
INSERT INTO qeydiyyat (telebe_id, kurs_kod, muellim_id, otaq_no, qeyd_tarixi, odenis, imtahan_bali)
VALUES ('T-01', 'SQL-101', 'M-05', 'A-201', DATE '2025-02-10', 350, 92),
       ('T-01', 'PYT-201', 'M-07', 'A-305', DATE '2025-02-12', 500, 78),
       ('T-02', 'SQL-101', 'M-05', 'A-201', DATE '2025-02-15', 350, 65),
       ('T-02', 'PYT-201', 'M-07', 'B-305', DATE '2025-03-05', 500, 54),
       ('T-03', 'DSC-301', 'M-09', 'B-410', DATE '2025-03-01', 700, 88),
       ('T-03', 'SQL-202', 'M-11', 'B-305', DATE '2025-03-10', 450, 71),
       ('T-05', 'SQL-101', 'M-05', 'A-201', DATE '2025-02-18', 350, NULL);

-- ----- Qiymet skalasi -----
INSERT INTO qiymet_shkalasi (herf, min_bal, max_bal)
VALUES ('A', 90, 100),
       ('B', 80, 89),
       ('C', 70, 79),
       ('D', 60, 69),
       ('F', 0, 59);

-- ----- 5NF cedvelleri (A6-daki tedris plani) -----
INSERT INTO muellim_kurs (muellim_id, kurs_kod)
VALUES ('M-05', 'SQL-101'),
       ('M-05', 'SQL-202'),
       ('M-11', 'SQL-101');

INSERT INTO kurs_filial (kurs_kod, filial_kod)
VALUES ('SQL-101', 'F-01'),
       ('SQL-202', 'F-01'),
       ('SQL-101', 'F-02');

INSERT INTO muellim_filial (muellim_id, filial_kod)
VALUES ('M-05', 'F-01'),
       ('M-11', 'F-01'),
       ('M-05', 'F-02');


-- #########################################################
-- #                                                       #
-- #   HİSSƏ A — NORMALİZASİYA (1NF -> 5NF)                #
-- #                                                       #
-- #########################################################


-- ===== Tapşırıq A1 — Birinci Normal Forma (1NF) ===== (6 bal)
-- a) kurs_qeydiyyat cədvəlində 1NF-i pozan bütün sütunları göstərin (ən azı ikisi var) və nə üçün pozduğunu izah edin.
-- İzah:
-- 1) telefonlar — bir xanada birdən çox dəyər saxlanılır (T-01: '055-111-22-33, 070-111-22-33'). Dəyər atomik deyil, ona görə 1NF pozulur.
-- 2) muellim_dilleri — eyni problem (M-05: 'Azərbaycan, İngilis, Rus'). Belə sütunda axtarış (WHERE dil = 'Rus'), sayma və dəyişiklik yalnız mətn üzərində LIKE/SPLIT ilə mümkün olur, bu isə səhvə açıqdır.
-- 3) Cədvəldə PRIMARY KEY yoxdur və eyni sətir təkrarlanır (T-01 iki dəfə yazılıb). Münasibətdə eyni sətirin təkrarlanması da 1NF pozuntusu sayılır.
--
-- Nəticə: atomik olmayan sütunlar məlumatın təkrarlanmasına və INSERT / UPDATE / DELETE anomaliyalarına səbəb olur.

-- b) Cədvəli 1NF-ə gətirin. Alınan cədvəl(lər)i CREATE TABLE ilə yazın.

CREATE TABLE kurs_qeydiyyat
(
    telebe_id     VARCHAR(20)   NOT NULL,
    telebe_ad     VARCHAR(100)  NOT NULL,
    dogum_tarixi  DATE          NOT NULL,
    kurs_kod      VARCHAR(20)   NOT NULL,
    kurs_ad       VARCHAR(100)  NOT NULL,
    kurs_saat     INT           NOT NULL CHECK (kurs_saat > 0),
    qiymet        NUMERIC(8, 2) NOT NULL CHECK (qiymet >= 0),
    fenn_kod      VARCHAR(20)   NOT NULL,
    fenn_ad       VARCHAR(100)  NOT NULL,
    muellim_id    VARCHAR(20)   NOT NULL,
    muellim_ad    VARCHAR(100)  NOT NULL,
    muellim_email VARCHAR(100)  NOT NULL,
    otaq_no       VARCHAR(20)   NOT NULL,
    otaq_tutum    INT CHECK (otaq_tutum > 0),
    filial_kod    VARCHAR(20)   NOT NULL,
    filial_unvan  VARCHAR(100)  NOT NULL,
    seher         VARCHAR(50),
    qeyd_tarixi   DATE          NOT NULL,
    odenis        NUMERIC(8, 2) NOT NULL CHECK (odenis >= 0),
    imtahan_bali  INT CHECK (imtahan_bali BETWEEN 0 AND 100),
    CONSTRAINT pk_kurs_qeydiyyat PRIMARY KEY (telebe_id, kurs_kod)
);

-- telebe_telefon ve muellim_dil cedvellerini yuxarida, DDL bolmesinde (6 ve 8 nomreli cedveller) artiq yaratmisam - bu iki cedvel 1NF-den 5NF-e
-- qeder deyismeden qalir, ona gore burada tekrar yaratmiram.

-- c) 1NF-dən sonra hələ də qalan INSERT, UPDATE və DELETE anomaliyalarının hər biri üçün bu ssenaridən konkret nümunə verin (məsələn: "DSC-301 kursuna yazılan tək tələbə silinsə, ...").
-- İzah:

-- INSERT anomaliyası: yeni fənn ("F-DIZ" kimi) əlavə etmək istəsək, kurs_qeydiyyat
-- cədvəlində fenn_kod/fenn_ad yalnız bir kursla (kurs_kod ilə) birlikdə mövcud ola bilər.
-- Hələ heç bir kursu olmayan fənni ümumiyyətlə yazmaq mümkün deyil, çünki kurs_kod NOT NULL-dur.
--
-- UPDATE anomaliyası: müəllim M-05-in e-poçtu dəyişəndə bu, onun tədris etdiyi
-- bütün qeydiyyat sətirlərində (T-01/SQL-101, T-02/SQL-101, T-05/SQL-101) ayrı-ayrılıqda
-- yenilənməlidir. Bir sətir unudulsa, eyni müəllimin iki fərqli e-poçtu olduğu görünər
-- (məlumat uyğunsuzluğu).
--
-- DELETE anomaliyası: DSC-301 kursuna yazılan tək tələbənin (T-03) qeydiyyatı silinsə,
-- kurs_ad, kurs_saat, qiymet və fenn_kod/fenn_ad kimi kurs məlumatları da onunla birlikdə
-- həmişəlik itir, çünki bu məlumatlar başqa heç bir sətirdə saxlanmır.

-- d) Müzakirə: PostgreSQL-də telefonlar TEXT[] və ya JSONB istifadə etsək, cədvəl 1NF-də sayılırmı? 3-5 cümlə ilə əsaslandırın və massivin praktikada nə vaxt məqbul olduğunu yazın.
-- İzah: Sayılır, amma hər vəziyyətdə yaxşı yanaşma deyil. Əgər telefon nömrələri ayrıca istifadə olunmayacaqsa və sadəcə bir neçə mətn dəyəri kimi saxlanılıb göstəriləcəksə,
-- `TEXT[]` və ya `JSONB` istifadə etmək olar. Amma bu dəyərlərlə ayrıca axtarış, şərt və ya əlaqə qurulacaqsa, onları ayrıca cədvəldə saxlamaq daha düzgün olar.


-- ===== Tapşırıq A2 — İkinci Normal Forma (2NF) ===== (6 bal)
-- a) 1NF cədvəlinin namizəd açarını müəyyən et və seçimi əsaslandır.
-- İzah: (telebe_id, kurs_kod) kompozit açardır. Nə telebe_id tək başına, nə də kurs_kod tək başına sətri unikal etmir — bir tələbə bir neçə kursa yazıla bilər,
-- bir kursa da bir neçə tələbə yazıla bilər. Yalnız bu ikisi birlikdə hər qeydiyyatı unikal müəyyən edir, ona görə namizəd açar budur.

-- b) Bütün qismən asılılıqları X -> Y formasında siyahıla (ən azı 6 ədəd).
-- İzah:

-- telebe_id -> telebe_ad
-- telebe_id -> dogum_tarixi
-- kurs_kod -> kurs_ad
-- kurs_kod -> kurs_saat
-- kurs_kod -> qiymet
-- kurs_kod -> fenn_kod
-- kurs_kod -> fenn_ad
-- (muellim_id, otaq_no, qeyd_tarixi, odenis, imtahan_bali isə tam açara -
-- (telebe_id, kurs_kod) ikisinə birdən - asılıdır;

-- c) Cədvəli 2NF-ə parçala; bütün CREATE TABLE-ləri PK + FK ilə yaz.

-- CREATE TABLE telebe (
--     telebe_id    VARCHAR(10) PRIMARY KEY,
--     telebe_ad    VARCHAR(60) NOT NULL,
--     dogum_tarixi DATE NOT NULL
-- );

-- CREATE TABLE kurs_2nf (
--     kurs_kod  VARCHAR(10) PRIMARY KEY,
--     kurs_ad   VARCHAR(60) NOT NULL,
--     kurs_saat INT NOT NULL,
--     qiymet    NUMERIC(8,2) NOT NULL,
--     fenn_kod  VARCHAR(10) NOT NULL,
--     fenn_ad   VARCHAR(60) NOT NULL
-- );

-- CREATE TABLE qeydiyyat_2nf (
--     telebe_id     VARCHAR(10) NOT NULL,
--     kurs_kod      VARCHAR(10) NOT NULL,
--     muellim_id    VARCHAR(10) NOT NULL,
--     muellim_ad    VARCHAR(60) NOT NULL,
--     muellim_email VARCHAR(80) NOT NULL,
--     otaq_no       VARCHAR(10) NOT NULL,
--     otaq_tutum    INT,
--     filial_kod    VARCHAR(10) NOT NULL,
--     filial_unvan  VARCHAR(100) NOT NULL,
--     seher         VARCHAR(50),
--     qeyd_tarixi   DATE NOT NULL,
--     odenis        NUMERIC(8,2) NOT NULL,
--     imtahan_bali  INT,
--     PRIMARY KEY (telebe_id, kurs_kod),
--     FOREIGN KEY (telebe_id) REFERENCES telebe(telebe_id),
--     FOREIGN KEY (kurs_kod) REFERENCES kurs_2nf(kurs_kod)
-- );

-- (telebe_telefon və muellim_dil 1NF-dən dəyişmədən qalır.)

-- d) Parçalanmanın itkisiz (lossless-join) olduğunu izah et: hansı sütunlar üzərindən geri birləşdirmə mümkündür?
-- İzah:

-- Parçalanma itkisizdir, çünki hər iki ortaq sütun digər tərəfdə PRIMARY KEY-dir:
-- telebe_id qeydiyyat_2nf-də FK, telebe-də isə PK-dır; kurs_kod qeydiyyat_2nf-də FK, kurs_2nf-də isə PK-dır. Bu şərt ödəndiyi üçün geri JOIN saxta sətir yaratmır.
-- qeydiyyat_2nf JOIN telebe USING (telebe_id) JOIN kurs_2nf USING (kurs_kod) sorğusu dəqiq A1-in kurs_qeydiyyat cədvəlini bərpa edir.

-- ===== Tapşırıq A3 — Üçüncü Normal Forma (3NF) ===== (7 bal)
-- a) 2NF-dən sonra qalan tranzitiv asılılıqları tap.
--    (otaq_no -> filial_kod -> filial_unvan, seher və kurs_kod -> fenn_kod -> fenn_ad zəncirlərini unutma)

-- İzah:
-- kurs_kod -> fenn_kod -> fenn_ad
-- (telebe_id, kurs_kod) -> muellim_id -> muellim_ad
-- (telebe_id, kurs_kod) -> muellim_id -> muellim_email
-- (telebe_id, kurs_kod) -> otaq_no -> otaq_tutum
-- (telebe_id, kurs_kod) -> otaq_no -> filial_kod -> filial_unvan
-- (telebe_id, kurs_kod) -> otaq_no -> filial_kod -> seher

-- b) Hamısını 3NF-ə gətir, yekun CREATE TABLE-ləri yaz.
-- İzah: A3-ün nəticəsi elə yuxarıdakı DDL bölməsindəki cədvəllərdir - fenn, kurs,
-- muellim, otaq, filial, telebe, telebe_telefon, qeydiyyat, muellim_dil. Hər tranzitiv
-- zəncir (kurs_kod->fenn_kod->fenn_ad , otaq_no->filial_kod->filial_unvan/seher ,
-- muellim_id->muellim_ad/email) artıq ayrı cədvələ çıxarılıb, qeydiyyat-da yalnız muellim_id və otaq_no FK kimi qalıb.

-- c) "F-01 filialının ünvanı dəyişdi" əməliyyatı 3NF-dən əvvəl və sonra neçə sətri UPDATE edir? Hər iki halı yaz.
-- İzah:
-- 3NF-dən əvvəl: F-01-ə aid bütün qeydiyyat sətirləri yenilənməli olur, çünki filial_unvan hər sətirdə təkrarlanır.
-- Real, dolu cədvəldə (milyon/milyard qeydiyyat olduqda) bu, eyni sayda sətri UPDATE etmək deməkdir.
-- 3NF-dən sonra: yalnız filial cədvəlindəki 1 sətir (F-01) yenilənir, qeydiyyat cədvəlinə heç toxunulmur.

-- d) 3NF-in rəsmi tərifi + "açardan, bütün açardan, yalnız açardan" ifadəsinin hansı hissəsi 1NF / 2NF / 3NF-ə aiddir.
-- İzah:
-- 3NF-in rəsmi tərifi: cədvəl 2NF-dədir və heç bir qeyri-açar sütun başqa bir qeyri-açar sütundan tranzitiv asılı deyil (yəni X->Y->Z zənciri yoxdur).
-- "açardan" -> 1NF (hər sütun açara görə müəyyənləşir),
-- "bütün açardan" -> 2NF (kompozit açarın hər iki hissəsinə ehtiyac var, qismən asılılıq yoxdur),
-- "yalnız açardan" -> 3NF (başqa qeyri-açar sütundan asılılıq yoxdur, tranzitiv yoxdur).


-- ===== Tapşırıq A4 — Boyce-Codd Normal Forma (BCNF) ===== (7 bal)
-- Verilən münasibət: ders(telebe_id, fenn_kod, muellim_id)
--
-- a) Bütün funksional asılılıqları və bütün namizəd açarları yaz.
-- İzah:
-- FD-lər: muellim_id -> fenn_kod (biznes qaydası 3: hər müəllim bir fənn tədris edir);
-- (telebe_id, fenn_kod) -> muellim_id (bir tələbə bir fənni bir müəllimdən öyrənir).
-- Namizəd açarlar: (telebe_id, fenn_kod) və (telebe_id, muellim_id) - hər ikisi qalan
-- sütunu müəyyənləşdirir, tək sütunların heç biri (telebe_id, fenn_kod, muellim_id)
-- tək başına kifayət etmir.

-- b) Bu münasibətin 3NF-də olduğunu, lakin BCNF-də olmadığını sübut et. Hansı asılılıq pozur və niyə?
-- İzah:
-- muellim_id -> fenn_kod asılılığı 2NF/3NF-i pozmur, çünki fenn_kod özü açar sütunudur (prime atribut - (telebe_id, fenn_kod) namizəd açarının hissəsidir),
-- 2NF/3NF isə yalnız qeyri-açar sütunların qismən/tranzitiv asılılığını qadağan edir.
-- BCNF isə Y-in açar olub-olmamasına baxmır - hər X->Y üçün X superkey olmalıdır.
-- muellim_id nə PK, nə namizəd açardır, ona görə muellim_id -> fenn_kod BCNF-i pozur.

-- c) BCNF-ə parçala, CREATE TABLE ilə yaz.
-- İzah: pozan asılılığı (muellim_id -> fenn_kod) ayrıca cədvələ çıxarmaq lazımdır.

-- CREATE TABLE muellim_fenn (
--     muellim_id VARCHAR(10) PRIMARY KEY,
--     fenn_kod   VARCHAR(10) NOT NULL,
--     FOREIGN KEY (fenn_kod) REFERENCES fenn(fenn_kod)
-- );

-- CREATE TABLE telebe_muellim (
--     telebe_id  VARCHAR(10) NOT NULL,
--     muellim_id VARCHAR(10) NOT NULL,
--     PRIMARY KEY (telebe_id, muellim_id),
--     FOREIGN KEY (telebe_id) REFERENCES telebe(telebe_id),
--     FOREIGN KEY (muellim_id) REFERENCES muellim_fenn(muellim_id)
-- );

-- d) Parçalanmadan sonra hansı funksional asılılıq itir? Onu PostgreSQL-də necə qorumaq olar (UNIQUE / kompozit FK / generated column / TRIGGER) — ən azı bir üsul + DDL yaz.
-- İzah:
-- İtən FD: (telebe_id, fenn_kod) -> muellim_id (artıq JOIN-suz yoxlamaq olmur).
-- Qorumaq üçün kompozit FK + UNIQUE istifade edile biler.

-- CREATE TABLE muellim_fenn (
--     muellim_id VARCHAR(10) PRIMARY KEY,
--     fenn_kod   VARCHAR(10) NOT NULL,
--     UNIQUE (muellim_id, fenn_kod)
-- );

-- CREATE TABLE telebe_muellim (
--     telebe_id  VARCHAR(10) NOT NULL,
--     muellim_id VARCHAR(10) NOT NULL,
--     fenn_kod   VARCHAR(10) NOT NULL,
--     PRIMARY KEY (telebe_id, muellim_id),
--     FOREIGN KEY (muellim_id, fenn_kod) REFERENCES muellim_fenn(muellim_id, fenn_kod),
--     UNIQUE (telebe_id, fenn_kod)
-- );


-- ===== Tapşırıq A5 — Dördüncü Normal Forma (4NF) ===== (7 bal)
-- Verilən münasibət: muellim_bacariq(muellim_id, tedris_fenn, bildiyi_dil)
--
-- a) M-05 iki fənn tədris etsəydi və 3 dil bilsəydi neçə sətir olardı? Bütün sətirləri yaz.
-- İzah: 6 sətir (2 fənn x 3 dil - fənn və dil arasında əlaqə olmadığı üçün bütün

-- kombinasiyalar yazılmalıdır):
-- M-05, F-SQL, Azərbaycan
-- M-05, F-SQL, İngilis
-- M-05, F-SQL, Rus
-- M-05, F-PYT, Azərbaycan
-- M-05, F-PYT, İngilis
-- M-05, F-PYT, Rus

-- b) Çoxqiymətli asılılıqları X ->-> Y formasında yaz.
-- İzah:
-- muellim_id ->-> tedris_fenn (müəllimin tədris etdiyi fənlər onun bildiyi dillərdən asılı deyil)
-- muellim_id ->-> bildiyi_dil (müəllimin bildiyi dillər onun tədris etdiyi fənlərdən asılı deyil)

-- c) BCNF-də olsa da niyə artıqlıq yaradır? "M-05 alman dilini öyrəndi" neçə yeni sətir? "Yeni fənn" neçə sətir?
-- İzah:
-- BCNF yalnız funksional asılılıqlara baxır, burada isə FD yox, MVD var - ona görə
-- BCNF-i pozan heç nə yoxdur, amma cədvəldə hələ də artıqlıq qalır: fenn və dil
-- əlaqəsiz olduğu üçün, hər yeni dil öyrənəndə onu bütün fənnlər üçün, hər yeni
-- fənn öyrənəndə onu bütün dillər üçün təkrar yazmalı oluruq.
-- Alman dili öyrənsə: 2 yeni sətir (M-05-in 2 fənni var, hər biri ilə cütləşir).
-- Yeni fənn (F-DSC) öyrətsə: 3 yeni sətir (M-05-in 3 dili var, hər biri ilə cütləşir).

-- d) 4NF-ə parçala, CREATE TABLE yaz və M-05 üçün sətir sayının necə dəyişdiyini göstər (əvvəl -> sonra).
-- İzah: iki müstəqil çoxqiymətli faktı ayrı cədvəllərə bölürük.

-- CREATE TABLE muellim_fenn (
--     muellim_id VARCHAR(10) NOT NULL,
--     tedris_fenn VARCHAR(10) NOT NULL,
--     PRIMARY KEY (muellim_id, tedris_fenn)
-- );

-- CREATE TABLE muellim_dil (
--     muellim_id VARCHAR(10) NOT NULL,
--     bildiyi_dil VARCHAR(30) NOT NULL,
--     PRIMARY KEY (muellim_id, bildiyi_dil)
-- );

-- M-05 üçün sətir sayı: əvvəl 6 (2 fənn x 3 dil) -> sonra 5 (muellim_fenn-də 2 + muellim_dil-də 3).
-- Burda əsil fərq məlumatın çoxluqundadı. Dəyər sayı artdıqca (məs. 5 fənn, 5 dil) əvvəlki 25 sətir olardı, sonrakı isə cəmi 10 - fərq multiplikativdən additivə düşür.

-- ===== Tapşırıq A6 — Beşinci Normal Forma (5NF / PJNF) ===== (5 bal)
-- Verilən münasibət: tedris_plani(muellim_id, kurs_kod, filial_kod)
--
-- a) Cədvəldə heç bir FD və MVD olmadığını qısaca əsaslandır (yəni 4NF-dədir).
-- İzah:

-- FD yoxdur: muellim_id kurs_kod-u müəyyənləşdirmir (M-05 iki fərqli kurs tədris edir),
-- kurs_kod filial_kod-u müəyyənləşdirmir (SQL-101 iki fərqli filialda keçirilir), və
-- muellim_id filial_kod-u müəyyənləşdirmir (M-05 iki fərqli filialda işləyir).

-- MVD də yoxdur: əgər muellim_id ->-> kurs_kod olsaydı, M-05-in bütün kurs/filial
-- kombinasiyaları cədvəldə olmalıydı (4 sətir), amma yalnız 3-ü var (SQL-202/F-02 yoxdur) -
-- deməli MVD-yə uyğun gəlmir. Heç bir FD/MVD olmadığı üçün cədvəl artıq BCNF/4NF-dədir.

-- b) Üç binar proyeksiyaya ayır: muellim_kurs, kurs_filial, muellim_filial. Hər üçünün məzmununu yaz.
-- İzah:

-- muellim_kurs (muellim_id, kurs_kod):
-- M-05, SQL-101
-- M-05, SQL-202
-- M-11, SQL-101
--
-- kurs_filial (kurs_kod, filial_kod):
-- SQL-101, F-01
-- SQL-202, F-01
-- SQL-101, F-02
--
-- muellim_filial (muellim_id, filial_kod):
-- M-05, F-01
-- M-11, F-01
-- M-05, F-02

-- c) Üçünü geri birləşdir və nəticənin ilkin cədvəllə eyni olduğunu göstər (NATURAL JOIN və ya USING ilə).

SELECT *
FROM muellim_kurs
         NATURAL JOIN kurs_filial
         NATURAL JOIN muellim_filial;

/**
    M-05,F-02,SQL-101
    M-05,F-01,SQL-101
    M-05,F-01,SQL-202
    M-11,F-01,SQL-101
 */

-- d) Yalnız iki proyeksiyanı (muellim_kurs |><| kurs_filial) birləşdir.
--    Hansı saxta sətir (spurious tuple) yaranır? Real həyatda nəyi səhv iddia edir və bu niyə 5NF-i zəruri edir?

SELECT *
FROM muellim_kurs
         NATURAL JOIN kurs_filial;

-- Nəticə: 5 sətir - saxta sətir (M-11, SQL-101, F-02):

/**
    SQL-101,M-05,F-02
    SQL-101,M-05,F-01
    SQL-202,M-05,F-01
    SQL-101,M-11,F-02
    SQL-101,M-11,F-01
 */

-- M-11 F-02-də işləmədiyi üçün bu, "M-11 SQL-101-i F-02-də tədris edir" kimi yanlış fakt iddia edir.
-- Yalnız iki cədvəlin birləşməsi üçüncü faktı (kim harda işləyir) yoxlamadığı üçün belə saxta
-- kombinasiyalar yaranır - buna görə 5NF üç proyeksiyanın hamısını tələb edir.

-- ===== Tapşırıq A7 — Yekun sxem ===== (7 bal)
-- a) A1-A6-nın nəticələrini birləşdirib tam normallaşdırılmış sxemi tək işlək skript kimi yaz (yuxarıdakı DDL bölməsinə bax).
-- İzah: Bax yuxarıdakı DDL bölməsi (filial, otaq, fenn, kurs, muellim, muellim_dil,
-- telebe, telebe_telefon, qeydiyyat, qiymet_shkalasi, muellim_kurs, kurs_filial,
-- muellim_filial) - bütün PK/FK/UNIQUE/CHECK/NOT NULL-larla, düzgün ardıcıllıqla
-- (asılı cədvəllər sonra). Müəllimlərin mentorluq əlaqəsi (7-ci biznes qaydası)
-- muellim.mentor_id-nin öz-özünə istinad edən FK-si ilə təmin olunub.

-- b) Sxemin ER diaqramı + kardinallıqlar (1:1, 1:N, M:N).
-- İzah:
-- ER diaqram (mətn formasında, oxlar aşağıdakı kardinallıqları göstərir):
--
-- filial (1) --- (N) otaq
-- fenn (1) --- (N) kurs
-- fenn (1) --- (N) muellim
-- muellim (1) --- (N) muellim (mentor, oz-ozune istinad)
-- telebe (1) --- (N) telebe_telefon
-- muellim (1) --- (N) muellim_dil
-- telebe (1) --- (N) qeydiyyat --- (N) kurs   [qeydiyyat = telebe/kurs arasinda M:N koreni]
-- muellim (1) --- (N) qeydiyyat
-- otaq (1) --- (N) qeydiyyat
-- muellim (M) --- (N) kurs    [muellim_kurs ara cedveli ile]
-- kurs (M) --- (N) filial     [kurs_filial ara cedveli ile]
-- muellim (M) --- (N) filial  [muellim_filial ara cedveli ile]
--
-- Kardinallıqlar: filial-otaq, fenn-kurs, fenn-muellim, muellim-mentor,
-- telebe-telefon, muellim-dil, telebe/kurs/muellim/otaq-qeydiyyat hamisi 1:N-dir.
-- muellim-kurs, kurs-filial, muellim-filial ve telebe-kurs (qeydiyyat uzerinden)
-- ise M:N-dir. Bu sxemde hec bir 1:1 elaqe yoxdur.

-- c) Bir cümlə ilə: normallaşdırma nə vaxt ziyanlıdır, denormalizasiya nə vaxt əsaslandırılır?
-- İzah:
-- Normallaşdırma oxuma zamanı çox sayda JOIN tələb edib performansı aşağı saldıqda
-- ziyanlıdır; denormalizasiya isə oxuma sürəti yazma zamanı yaranan az miqdarda
-- artıqlıqdan daha vacib olduqda (məs. hesabat/analitika cədvəllərində) əsaslandırılır.


-- #########################################################
-- #                                                       #
-- #   HİSSƏ B — JOIN NÖVLƏRİ                              #
-- #                                                       #
-- #########################################################


-- ===== Tapşırıq B1 — INNER JOIN ===== (4 bal)
-- Hər qeydiyyat üçün: tələbənin adı, kursun adı, fənnin adı, ödəniş.
-- Nəticəni tələbə adına görə sırala.
SELECT t.telebe_ad, k.kurs_ad, f.fenn_ad, q.odenis
FROM telebe t
         JOIN qeydiyyat q ON t.telebe_id = q.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
         JOIN fenn f ON k.fenn_kod = f.fenn_kod
ORDER BY t.telebe_ad;

-- İzah: nəticədə neçə sətir var və T-04 niyə yoxdur?
-- İzah: 7 sətir (qeydiyyat sayı qədər). T-04 heç bir kursa yazılmadığı üçün
-- qeydiyyat cədvəlində uyğun sətri yoxdur, ona görə INNER JOIN-da görünmür.


-- ===== Tapşırıq B2 — LEFT (OUTER) JOIN ===== (3 bal)
-- (qeyd: mənbə sənəddə bu bənd bəzən "B3" kimi də göstərilib)
--
-- a) Bütün tələbələri telefon nömrələri ilə göstər;
--    nömrəsi olmayan üçün 'Nömrə yoxdur' (COALESCE).
SELECT t.telebe_ad, COALESCE(tt.telefon, 'Nömrə yoxdur') AS telefon
FROM telebe t
         LEFT JOIN telebe_telefon tt ON t.telebe_id = tt.telebe_id
ORDER BY t.telebe_ad;

-- b) Bütün qeydiyyatları imtahan balı ilə göstər;
--    imtahan verməmişlər üçün 'İmtahan verilməyib'.
SELECT t.telebe_ad, k.kurs_ad, COALESCE(q.imtahan_bali::TEXT, 'İmtahan verilməyib') AS imtahan_bali
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
ORDER BY t.telebe_ad;

-- c) Tipik səhv: LEFT JOIN-u INNER JOIN-a çevirən səhv nədir?
--    Şərti ON əvəzinə WHERE-ə yaz, hər iki sorğunu müqayisə et.
-- Düzgün (LEFT JOIN saxlanılır):
SELECT t.telebe_ad, tt.telefon
FROM telebe t
         LEFT JOIN telebe_telefon tt ON t.telebe_id = tt.telebe_id;

-- Səhv (şərt WHERE-ə keçirilib, LEFT JOIN faktiki INNER JOIN-a çevrilir):
SELECT t.telebe_ad, tt.telefon
FROM telebe t
         LEFT JOIN telebe_telefon tt ON t.telebe_id = tt.telebe_id
WHERE tt.telefon IS NOT NULL;

-- İzah: birinci sorğu 7 sətir qaytarır (T-04/T-05 NULL telefonla daxildir), ikinci
-- sorğu 5 sətir qaytarır (T-04/T-05 tamamilə itir), çünki WHERE şərti sağ tərəfin
-- NULL olduğu sətirləri süzür - bu, LEFT JOIN-u faktiki INNER JOIN-a çevirir.


-- ===== Tapşırıq B3 — RIGHT (OUTER) JOIN ===== (3 bal)
-- RIGHT JOIN ilə bütün kursları və onlara yazılan tələbələrin sayını çıxar.
-- Tələbəsi olmayan kurs (PYT-305) da 0 ilə görünməlidir.
SELECT k.kurs_ad, COUNT(q.telebe_id) AS telebe_sayi
FROM qeydiyyat q
         RIGHT JOIN kurs k ON q.kurs_kod = k.kurs_kod
GROUP BY k.kurs_ad
ORDER BY k.kurs_ad;

-- Eyni nəticəni LEFT JOIN ilə al:
SELECT k.kurs_ad, COUNT(q.telebe_id) AS telebe_sayi
FROM kurs k
         LEFT JOIN qeydiyyat q ON k.kurs_kod = q.kurs_kod
GROUP BY k.kurs_ad
ORDER BY k.kurs_ad;

-- İzah: praktikada RIGHT JOIN niyə nadir işlədilir?
-- İzah: RIGHT JOIN-u LEFT JOIN-a çevirmək üçün sadəcə iki cədvəlin yerini dəyişmək
-- kifayətdir, nəticə eynidir. Əksər inkişafçılar "əsas" cədvəli həmişə FROM-da solda
-- yazmağa öyrəşdiyi üçün LEFT JOIN daha oxunaqlı sayılır, RIGHT JOIN-a ehtiyac qalmır.


-- ===== Tapşırıq B4 — FULL OUTER JOIN ===== (4 bal)
-- a) fenn və kurs cədvəllərini fenn_kod üzrə FULL OUTER JOIN et.
--    CASE ilə vəziyyət sütunu: 'Uyğun' / 'Kursu olmayan fənn' / 'Fənni olmayan kurs'.
SELECT f.fenn_kod, f.fenn_ad, k.kurs_kod, k.kurs_ad,
       CASE
           WHEN f.fenn_kod IS NOT NULL AND k.kurs_kod IS NOT NULL THEN 'Uyğun'
           WHEN k.kurs_kod IS NULL THEN 'Kursu olmayan fənn'
           ELSE 'Fənni olmayan kurs'
           END AS veziyyet
FROM fenn f
         FULL OUTER JOIN kurs k ON f.fenn_kod = k.fenn_kod;

-- b) Yalnız uyğunsuz sətirləri qaytaran sorğu (FULL OUTER + IS NULL).
SELECT f.fenn_kod, f.fenn_ad, k.kurs_kod, k.kurs_ad
FROM fenn f
         FULL OUTER JOIN kurs k ON f.fenn_kod = k.fenn_kod
WHERE f.fenn_kod IS NULL
   OR k.kurs_kod IS NULL;

-- c) muellim və qeydiyyat cədvəlləri arasında FULL OUTER JOIN.
--    M-13 görünürmü? Niyə "sağ tərəfdə tək qalan" sətir yoxdur? (İpucu: FOREIGN KEY)
SELECT m.muellim_id, m.muellim_ad, q.telebe_id, q.kurs_kod
FROM muellim m
         FULL OUTER JOIN qeydiyyat q ON m.muellim_id = q.muellim_id;

-- İzah: M-13 görünür (NULL qeydiyyat sütunları ilə), çünki heç bir dərsi yoxdur.
-- Sağ tərəfdə tək qalan sətir yoxdur, çünki qeydiyyat.muellim_id FOREIGN KEY ilə
-- muellim-ə bağlıdır - hər qeydiyyat sətrinin mütləq mövcud bir müəllimi olmalıdır,
-- ona görə uyğunsuz (orphan) sağ sətir mümkün deyil.

-- d) Eyni nəticəni LEFT JOIN UNION RIGHT JOIN ilə al.
--    Burada UNION yerinə UNION ALL niyə işləməz?
SELECT m.muellim_id, m.muellim_ad, q.telebe_id, q.kurs_kod
FROM muellim m
         LEFT JOIN qeydiyyat q ON m.muellim_id = q.muellim_id
UNION
SELECT m.muellim_id, m.muellim_ad, q.telebe_id, q.kurs_kod
FROM muellim m
         RIGHT JOIN qeydiyyat q ON m.muellim_id = q.muellim_id;

-- İzah: UNION ALL işləməzdi, çünki hər iki tərəfdə uyğun gələn (əsl JOIN olunan)
-- sətirlər HƏR İKİ sorğuda eyni şəkildə görünür - UNION ALL bunları TƏKRAR yazardı.
-- UNION isə təkrarları avtomatik silir, düzgün nəticəni verir.


-- ===== Tapşırıq B5 — CROSS JOIN ===== (3 bal)
-- a) filial x qiymet_shkalasi Dekart hasili.
--    Sətir sayını sorğudan ƏVVƏL hesabla, sonra yoxla.
-- Gözlənilən sətir sayı: 3 (filial) x 5 (qiymet_shkalasi) = 15
SELECT f.filial_kod, qs.herf
FROM filial f
         CROSS JOIN qiymet_shkalasi qs
ORDER BY f.filial_kod, qs.herf;

-- b) CROSS JOIN ilə "fənn x qiymət hərfi" matrisi; hər xanada həmin fənn üzrə
--    həmin qiyməti alan tələbələrin sayı. Sıfır xanalar da görünsün.
--    (İpucu: CROSS JOIN + LEFT JOIN + COUNT)
SELECT f.fenn_ad, qs.herf, COUNT(q.telebe_id) AS telebe_sayi
FROM fenn f
         CROSS JOIN qiymet_shkalasi qs
         LEFT JOIN kurs k ON k.fenn_kod = f.fenn_kod
         LEFT JOIN qeydiyyat q
                   ON q.kurs_kod = k.kurs_kod AND q.imtahan_bali BETWEEN qs.min_bal AND qs.max_bal
GROUP BY f.fenn_ad, qs.herf
ORDER BY f.fenn_ad, qs.herf;

-- c) Təsadüfi Dekart partlayışının əsas səbəbi bir cümlə ilə.
-- İzah: iki cədvəl arasında JOIN şərti (ON) unudulur və ya səhv yazılır, nəticədə
-- CROSS JOIN kimi davranan qeyri-adi böyük nəticə yaranır.


-- ===== Tapşırıq B6 — SELF JOIN ===== (3 bal)
-- a) Hər müəllimin adı və mentorunun adı; mentoru olmayanlar üçün 'Mentoru yoxdur'.
SELECT m.muellim_ad, COALESCE(mentor.muellim_ad, 'Mentoru yoxdur') AS mentor_ad
FROM muellim m
         LEFT JOIN muellim mentor ON m.mentor_id = mentor.muellim_id
ORDER BY m.muellim_ad;

-- b) Mentoru ilə eyni fənni tədris edən müəllimləri tap.
SELECT m.muellim_ad, mentor.muellim_ad AS mentor_ad, m.fenn_kod
FROM muellim m
         JOIN muellim mentor ON m.mentor_id = mentor.muellim_id
WHERE m.fenn_kod = mentor.fenn_kod;
-- Nəticə: 0 sətir - bizim datada heç bir müəllim mentoru ilə eyni fənni tədris etmir.

-- c) Eyni kursa yazılmış, fərqli tələbələrdən ibarət cütlüklər.
--    Hər cütlük bir dəfə ((A,B) varsa (B,A) olmasın).
SELECT q1.telebe_id AS telebe_1, q2.telebe_id AS telebe_2, q1.kurs_kod
FROM qeydiyyat q1
         JOIN qeydiyyat q2 ON q1.kurs_kod = q2.kurs_kod AND q1.telebe_id < q2.telebe_id
ORDER BY q1.kurs_kod;

-- d) İzah: SELF JOIN-da alias niyə məcburidir?
-- İzah: eyni cədvəl FROM-da iki dəfə göründüyü üçün, alias olmasa Postgres hansı
-- sütunun hansı "nüsxəyə" aid olduğunu ayırd edə bilmir və "ambiguous" xətası verir.

-- İki səviyyəli zəncir (müəllim -> mentor -> mentorun mentoru):
SELECT m.muellim_ad,
       mentor.muellim_ad          AS mentor_ad,
       mentor_of_mentor.muellim_ad AS mentorun_mentoru
FROM muellim m
         LEFT JOIN muellim mentor ON m.mentor_id = mentor.muellim_id
         LEFT JOIN muellim mentor_of_mentor ON mentor.mentor_id = mentor_of_mentor.muellim_id
ORDER BY m.muellim_ad;


-- ===== Tapşırıq B7 — NATURAL JOIN, USING və ON ===== (10 bal)
-- a) otaq və filial cədvəllərini NATURAL JOIN ilə birləşdir.
--    Birləşmə hansı sütun(lar) üzrə gedir?
SELECT *
FROM otaq
         NATURAL JOIN filial;
-- İzah: filial_kod hər iki cədvəldə eyni adla mövcuddur, NATURAL JOIN avtomatik
-- olaraq bu ortaq sütun üzərindən birləşir.

-- b) Eyni sorğunu JOIN ... USING (filial_kod) ilə yaz.
--    SELECT * ilə yoxla: USING nəticə sütunlarına necə təsir edir?
SELECT *
FROM otaq
         JOIN filial USING (filial_kod);
-- İzah: USING da NATURAL JOIN kimi filial_kod sütununu nəticədə YALNIZ BİR DƏFƏ
-- göstərir, amma fərqli olaraq birləşmə sütununu özün əl ilə seçirsən (bütün ortaq
-- adlı sütunlar avtomatik seçilmir).

-- c) Eyni sorğunu JOIN ... ON ilə yaz və üç variantın sütunlarını müqayisə et.
SELECT *
FROM otaq o
         JOIN filial f ON o.filial_kod = f.filial_kod;
-- İzah: ON versiyasında filial_kod sütunu İKİ DƏFƏ görünür (həm otaq-dan, həm
-- filial-dan), çünki ON heç bir sütunu birləşdirmir/gizlətmir - NATURAL/USING isə
-- ortaq sütunu tək dəfə göstərir.

-- d) Təhlükə ssenarisi: hər iki cədvələ created_at TIMESTAMP əlavə et
--    və NATURAL JOIN-u yenidən icra et. Sonda DROP COLUMN et.
ALTER TABLE otaq
    ADD COLUMN created_at TIMESTAMP;
ALTER TABLE filial
    ADD COLUMN created_at TIMESTAMP;

SELECT *
FROM otaq
         NATURAL JOIN filial;
-- Nəticə: 0 sətir! İndi NATURAL JOIN filial_kod İLƏ YANAŞI created_at sütunu
-- üzərindən də birləşməyə çalışır - hər iki tərəfdə created_at NULL olduğu üçün
-- (NULL = NULL yalan sayılır) heç bir sətir uyğun gəlmir.

ALTER TABLE otaq
    DROP COLUMN created_at;
ALTER TABLE filial
    DROP COLUMN created_at;

-- İzah: nəticə necə dəyişdi, NATURAL JOIN istehsalda niyə tövsiyə edilmir?
-- İzah: cədvələ yeni, hətta əlaqəsiz bir sütun (created_at) əlavə olunanda,
-- NATURAL JOIN-un birləşmə məntiqi səssizcə dəyişdi - inkişafçı bunu görmür,
-- gözlənilməz (bəzən boş) nəticələr yaranır. Ona görə istehsalda ON/USING üstünlük təşkil edir.


-- ===== Tapşırıq B8 — Çoxcədvəlli JOIN zənciri ===== (4 bal)
-- Ən azı 6 cədvəl: tələbə adı, kurs adı, fənn adı, müəllim adı, otaq nömrəsi,
-- otaq tutumu, filial ünvanı, şəhər, ödəniş, imtahan balı.
--
-- a) Yalnız imtahan vermiş tələbələr üçün (tam INNER zənciri).
SELECT t.telebe_ad,
       k.kurs_ad,
       f.fenn_ad,
       m.muellim_ad,
       o.otaq_no,
       o.otaq_tutum,
       fl.filial_unvan,
       fl.seher,
       q.odenis,
       q.imtahan_bali
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
         JOIN fenn f ON k.fenn_kod = f.fenn_kod
         JOIN muellim m ON q.muellim_id = m.muellim_id
         JOIN otaq o ON q.otaq_no = o.otaq_no
         JOIN filial fl ON o.filial_kod = fl.filial_kod
WHERE q.imtahan_bali IS NOT NULL
ORDER BY t.telebe_ad;

-- b) İmtahan verməmiş qeydiyyatlar (T-05) da görünsün.
--    Hansı bənddə hansı JOIN növünü dəyişdin?
SELECT t.telebe_ad,
       k.kurs_ad,
       f.fenn_ad,
       m.muellim_ad,
       o.otaq_no,
       o.otaq_tutum,
       fl.filial_unvan,
       fl.seher,
       q.odenis,
       q.imtahan_bali
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
         JOIN fenn f ON k.fenn_kod = f.fenn_kod
         JOIN muellim m ON q.muellim_id = m.muellim_id
         JOIN otaq o ON q.otaq_no = o.otaq_no
         JOIN filial fl ON o.filial_kod = fl.filial_kod
ORDER BY t.telebe_ad;

-- İzah: heç bir JOIN növünü dəyişmədim - sadəcə WHERE q.imtahan_bali IS NOT NULL
-- şərtini sildim. T-05-in bütün FK-ləri (telebe, kurs, muellim, otaq, filial)
-- mövcud olduğu üçün INNER zəncir onu onsuz da tuturdu, yalnız WHERE gizlədirdi.

-- c) Zəncirdə LEFT JOIN-dan sonra gələn INNER JOIN nə üçün bütün nəticəni
--    "daxili birləşməyə" çevirə bilər? Qəsdən yarat və göstər.
SELECT t.telebe_ad, k.kurs_ad
FROM telebe t
         LEFT JOIN qeydiyyat q ON t.telebe_id = q.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
ORDER BY t.telebe_ad;

-- İzah: T-04 yenə görünmür, çünki onun q.kurs_kod-u NULL-dur, INNER JOIN isə
-- NULL-u heç bir kurs_kod ilə uyğunlaşdırmır - bu, LEFT JOIN-un "hamısını saxla"
-- effektini sıradan çıxarır, bütün zəncir faktiki INNER JOIN kimi davranır.


-- ===== Tapşırıq B9 — Non-equi JOIN ===== (3 bal)
-- a) Hər imtahan nəticəsinin hərf qiymətini tap:
--    qeydiyyat ilə qiymet_shkalasi-nı BETWEEN şərti ilə birləşdir ("=" işlətmə).
SELECT t.telebe_ad, q.kurs_kod, q.imtahan_bali, qs.herf
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN qiymet_shkalasi qs ON q.imtahan_bali BETWEEN qs.min_bal AND qs.max_bal
ORDER BY t.telebe_ad;
-- Nəticə: 6 sətir (T-05 imtahan verməyib, BETWEEN NULL ilə heç bir hərflə uyğunlaşmır).

-- b) Hər hərf üzrə nəticə sayı və orta bal; heç kimin almadığı hərf də 0 ilə.
SELECT qs.herf, COUNT(q.imtahan_bali) AS neticeler_sayi, AVG(q.imtahan_bali) AS orta_bal
FROM qiymet_shkalasi qs
         LEFT JOIN qeydiyyat q ON q.imtahan_bali BETWEEN qs.min_bal AND qs.max_bal
GROUP BY qs.herf
ORDER BY qs.herf;

-- c) Özündən böyük bal alan hər tələbə ilə cütlük quran non-equi SELF JOIN;
--    hər nəticə üçün "ondan yuxarıda neçə nəticə var" sütunu.
SELECT q1.telebe_id, q1.kurs_kod, q1.imtahan_bali, COUNT(q2.imtahan_bali) AS ondan_yuxari_sayi
FROM qeydiyyat q1
         LEFT JOIN qeydiyyat q2 ON q2.imtahan_bali > q1.imtahan_bali
WHERE q1.imtahan_bali IS NOT NULL
GROUP BY q1.telebe_id, q1.kurs_kod, q1.imtahan_bali
ORDER BY q1.imtahan_bali DESC;


-- ===== Tapşırıq B10 — ANTI JOIN ===== (3 bal)
-- a) Heç bir kursa yazılmamış tələbələr — LEFT JOIN ... WHERE ... IS NULL.
SELECT t.telebe_ad
FROM telebe t
         LEFT JOIN qeydiyyat q ON t.telebe_id = q.telebe_id
WHERE q.telebe_id IS NULL;

-- b) Eyni nəticə — NOT EXISTS.
SELECT t.telebe_ad
FROM telebe t
WHERE NOT EXISTS (SELECT 1 FROM qeydiyyat q WHERE q.telebe_id = t.telebe_id);

-- c) Eyni nəticə — NOT IN. Alt-sorğuya UNION ALL SELECT NULL əlavə et.
--    Nəticə necə dəyişir? Üç-qiymətli məntiq (TRUE/FALSE/UNKNOWN) ilə izah et.
SELECT t.telebe_ad
FROM telebe t
WHERE t.telebe_id NOT IN (SELECT q.telebe_id FROM qeydiyyat q);
-- Nəticə: T-04 (1 sətir) - normal işləyir, çünki qeydiyyat.telebe_id-də NULL yoxdur.

SELECT t.telebe_ad
FROM telebe t
WHERE t.telebe_id NOT IN (SELECT q.telebe_id FROM qeydiyyat q UNION ALL SELECT NULL);
-- Nəticə: 0 sətir!

-- İzah: "x NOT IN (a, b, NULL)" daxildə "x <> a AND x <> b AND x <> NULL" kimi
-- açılır. x <> NULL nəticəsi həmişə UNKNOWN-dur (nə TRUE, nə FALSE). AND zənciri
-- bir dəfə UNKNOWN olsa bütün ifadə UNKNOWN olur, WHERE isə yalnız TRUE sətirləri
-- saxlayır - UNKNOWN sətirlər atılır, ona görə heç bir tələbə qaytarılmır.

-- d) Heç bir kursu olmayan fənni və heç bir otağı olmayan filialı tap.
SELECT f.fenn_ad
FROM fenn f
WHERE NOT EXISTS (SELECT 1 FROM kurs k WHERE k.fenn_kod = f.fenn_kod);

SELECT fl.filial_unvan
FROM filial fl
WHERE NOT EXISTS (SELECT 1 FROM otaq o WHERE o.filial_kod = fl.filial_kod);


-- ===== Tapşırıq B11 — SEMI JOIN ===== (4 bal)
-- a) Ən azı bir kursa yazılmış tələbələr — EXISTS ilə;
--    eyni nəticəni IN və INNER JOIN + DISTINCT ilə də al.
SELECT t.telebe_ad
FROM telebe t
WHERE EXISTS (SELECT 1 FROM qeydiyyat q WHERE q.telebe_id = t.telebe_id);

SELECT t.telebe_ad
FROM telebe t
WHERE t.telebe_id IN (SELECT q.telebe_id FROM qeydiyyat q);

SELECT DISTINCT t.telebe_ad
FROM telebe t
         JOIN qeydiyyat q ON t.telebe_id = q.telebe_id;

-- b) Üç variantı müqayisə et: INNER JOIN-da DISTINCT niyə lazımdır və
--    hansı halda DISTINCT nəticəni səhv edə bilər (məsələn SUM ilə)?
-- İzah: INNER JOIN + DISTINCT-də DISTINCT lazımdır, çünki bir tələbə bir neçə
-- kursa yazılmışsa (T-01, T-02 kimi), JOIN onu bir neçə dəfə təkrarlayır - DISTINCT
-- təkrarları silir. Amma DISTINCT aqreqat funksiyalarla (SUM kimi) birlikdə səhv
-- nəticə verə bilər: hər tələbənin ümumi ödənişini SUM(odenis) ilə hesablasaq,
-- DISTINCT tələbənin fərqli kurslara görə ayrı-ayrı ödədiyi məbləğləri "eyni sətir"
-- kimi silə bilər, real cəm səhv çıxar. EXISTS/IN heç bir sətri təkrarlamadığı üçün
-- bu problemi yaratmır.

-- c) Qiyməti 400-dən yuxarı olan ən azı bir kursa yazılmış tələbələr — EXISTS.
SELECT t.telebe_ad
FROM telebe t
WHERE EXISTS (SELECT 1
              FROM qeydiyyat q
                       JOIN kurs k ON q.kurs_kod = k.kurs_kod
              WHERE q.telebe_id = t.telebe_id
                AND k.qiymet > 400);


-- ===== Tapşırıq B12 — LATERAL JOIN ===== (4 bal)
-- a) LATERAL ilə hər kurs üzrə ən yüksək 2 bal alan tələbə.
--    Nəticəsi olmayan kurslar da görünsün (LEFT JOIN LATERAL ... ON true).
SELECT k.kurs_ad, top2.telebe_id, top2.imtahan_bali
FROM kurs k
         LEFT JOIN LATERAL (
    SELECT q.telebe_id, q.imtahan_bali
    FROM qeydiyyat q
    WHERE q.kurs_kod = k.kurs_kod
      AND q.imtahan_bali IS NOT NULL
    ORDER BY q.imtahan_bali DESC
    LIMIT 2
    ) top2 ON TRUE
ORDER BY k.kurs_ad;

-- b) Hər filial üzrə ən son qeydiyyat (qeyd_tarixi üzrə) — LATERAL ilə.
SELECT fl.filial_kod, son.telebe_id, son.kurs_kod, son.qeyd_tarixi
FROM filial fl
         LEFT JOIN LATERAL (
    SELECT q.telebe_id, q.kurs_kod, q.qeyd_tarixi
    FROM qeydiyyat q
             JOIN otaq o ON q.otaq_no = o.otaq_no
    WHERE o.filial_kod = fl.filial_kod
    ORDER BY q.qeyd_tarixi DESC
    LIMIT 1
    ) son ON TRUE
ORDER BY fl.filial_kod;

-- c) (a) bəndinin eyni nəticəsi — ROW_NUMBER() OVER (PARTITION BY ...) ilə.
SELECT kurs_ad, telebe_id, imtahan_bali
FROM (SELECT k.kurs_kod,
             k.kurs_ad,
             q.telebe_id,
             q.imtahan_bali,
             ROW_NUMBER() OVER (PARTITION BY k.kurs_kod ORDER BY q.imtahan_bali DESC) AS sira
      FROM kurs k
               LEFT JOIN qeydiyyat q ON q.kurs_kod = k.kurs_kod AND q.imtahan_bali IS NOT NULL) sub
WHERE sira <= 2
ORDER BY kurs_ad;

-- İzah: iki yanaşmanın müqayisəsi; LATERAL olmadan alt-sorğu xarici cədvəlin
--       sütununa niyə müraciət edə bilmir?
-- İzah: hər iki yanaşma eyni nəticəni verir. LATERAL hər xarici sətir üçün alt-
-- sorğunu ayrıca icra edir (mürəkkəb, "hər qrup üçün N nəticə" tipli sorğularda
-- rahatdır), ROW_NUMBER isə bütün datanı bir dəfəyə pəncərə funksiyası ilə emal
-- edir (adətən daha performanslıdır). Adi (LATERAL olmayan) alt-sorğu FROM-dan
-- əvvəl, xarici sətirdən asılı olmadan bir dəfə planlaşdırılır, ona görə xarici
-- cədvəlin sütununa (k.kurs_kod kimi) müraciət edə bilmir - LATERAL isə "hər xarici
-- sətir üçün yenidən qiymətləndirilə bilən" alt-sorğuya icazə verir.


-- ===== Tapşırıq B13 — JOIN + aqreqasiya ===== (4 bal)
-- a) Hər filial üzrə: otaq sayı, unikal tələbə sayı, keçirilən kurs sayı,
--    ümumi gəlir. F-03 da 0 ilə görünməlidir.
SELECT fl.filial_kod,
       COUNT(DISTINCT o.otaq_no)      AS otaq_sayi,
       COUNT(DISTINCT q.telebe_id)    AS telebe_sayi,
       COUNT(DISTINCT q.kurs_kod)     AS kurs_sayi,
       COALESCE(SUM(q.odenis), 0)     AS umumi_gelir
FROM filial fl
         LEFT JOIN otaq o ON o.filial_kod = fl.filial_kod
         LEFT JOIN qeydiyyat q ON q.otaq_no = o.otaq_no
GROUP BY fl.filial_kod
ORDER BY fl.filial_kod;

-- b) Yalnız ümumi gəliri 1000-dən çox olan filiallar (HAVING).
SELECT fl.filial_kod, COALESCE(SUM(q.odenis), 0) AS umumi_gelir
FROM filial fl
         LEFT JOIN otaq o ON o.filial_kod = fl.filial_kod
         LEFT JOIN qeydiyyat q ON q.otaq_no = o.otaq_no
GROUP BY fl.filial_kod
HAVING COALESCE(SUM(q.odenis), 0) > 1000
ORDER BY fl.filial_kod;

-- c) Tipik səhv: telebe LEFT JOIN qeydiyyat-dan sonra COUNT(*) ilə
--    COUNT(qeydiyyat.kurs_kod) fərqli nəticə verir. Hər ikisini icra et,
--    T-04 üçün fərqi göstər.
SELECT t.telebe_ad, COUNT(*) AS say_ulduz, COUNT(q.kurs_kod) AS say_sutun
FROM telebe t
         LEFT JOIN qeydiyyat q ON t.telebe_id = q.telebe_id
GROUP BY t.telebe_ad
ORDER BY t.telebe_ad;

-- İzah: T-04 üçün COUNT(*)=1 (LEFT JOIN nəticəsində uyğun qeydiyyat olmasa da
-- 1 sətir yaranır), COUNT(q.kurs_kod)=0 (q.kurs_kod NULL-dur, COUNT(sütun) NULL
-- dəyərləri saymır). Fərq: COUNT(*) sətirləri sayır, COUNT(sütun) yalnız həmin
-- sütunda NULL OLMAYAN dəyərləri sayır.


-- #########################################################
-- #                                                       #
-- #   HİSSƏ C — İKİ MÖVZUNUN BİRLƏŞDİYİ YER               #
-- #                                                       #
-- #########################################################


-- ===== Tapşırıq C1 — İlkin cədvəli geri qurmaq =====
-- Yalnız JOIN-lardan istifadə edərək kurs_qeydiyyat cədvəlini olduğu kimi
-- geri düzəlt — bütün sütunlarla, o cümlədən vergüllə birləşdirilmiş
-- telefonlar və muellim_dilleri sütunları ilə (STRING_AGG).
SELECT t.telebe_id,
       t.telebe_ad,
       t.dogum_tarixi,
       tel.telefonlar,
       q.kurs_kod,
       k.kurs_ad,
       k.kurs_saat,
       k.qiymet,
       k.fenn_kod,
       f.fenn_ad,
       m.muellim_id,
       m.muellim_ad,
       m.muellim_email,
       dil.muellim_dilleri,
       o.otaq_no,
       o.otaq_tutum,
       fl.filial_kod,
       fl.filial_unvan,
       fl.seher,
       q.qeyd_tarixi,
       q.odenis,
       q.imtahan_bali
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
         JOIN fenn f ON k.fenn_kod = f.fenn_kod
         JOIN muellim m ON q.muellim_id = m.muellim_id
         JOIN otaq o ON q.otaq_no = o.otaq_no
         JOIN filial fl ON o.filial_kod = fl.filial_kod
         LEFT JOIN (SELECT telebe_id, STRING_AGG(telefon, ', ') AS telefonlar
                    FROM telebe_telefon
                    GROUP BY telebe_id) tel ON tel.telebe_id = t.telebe_id
         LEFT JOIN (SELECT muellim_id, STRING_AGG(dil, ', ') AS muellim_dilleri
                    FROM muellim_dil
                    GROUP BY muellim_id) dil ON dil.muellim_id = m.muellim_id
ORDER BY t.telebe_id, q.kurs_kod;

-- Neçə cədvəli birləşdirməli oldun?
-- İzah: 9 (qeydiyyat, telebe, kurs, fenn, muellim, otaq, filial - 7 əsas cədvəl -
-- üstəgəl telebe_telefon və muellim_dil-in STRING_AGG ilə aqreqasiya edilmiş alt-sorğuları).

-- T-05 (telefonsuz) və imtahan verməmiş qeydiyyat itməməlidir —
-- hansı JOIN növünü seçdin və niyə?
-- İzah: telebe/kurs/fenn/muellim/otaq/filial arasında INNER JOIN kifayətdir, çünki
-- hamısı FK ilə təmin olunub və heç biri boş ola bilməz. telebe_telefon və
-- muellim_dil ilə isə LEFT JOIN seçdim, çünki T-05-in telefonu yoxdur - INNER
-- JOIN olsaydı bu sətir tamamən itərdi. imtahan_bali qeydiyyat-ın öz sütunu
-- olduğu üçün NULL kimi avtomatik görünür, ayrıca JOIN tələb etmir.

-- Bir cümlə ilə: normallaşdırma nəyi ucuzlaşdırdı, nəyi bahalaşdırdı?
-- İzah: Normallaşdırma yazma əməliyyatlarını və məlumat düzgünlüyünü ucuzlaşdırdı,
-- amma oxuma zamanı bu qədər çox JOIN etməyi tələb edərək sorğu mürəkkəbliyini bahalaşdırdı.


-- ===== Tapşırıq C2 — 5NF cədvəllərinin birləşdirilməsi =====
-- a) A6-dakı üç binar cədvəli birləşdirib tam tədris planını çıxar;
--    müəllimin adını və filialın ünvanını da əlavə et (5 cədvəl).
SELECT mk.muellim_id, m.muellim_ad, mk.kurs_kod, kf.filial_kod, fl.filial_unvan
FROM muellim_kurs mk
         JOIN kurs_filial kf ON mk.kurs_kod = kf.kurs_kod
         JOIN muellim_filial mf ON mk.muellim_id = mf.muellim_id AND kf.filial_kod = mf.filial_kod
         JOIN muellim m ON mk.muellim_id = m.muellim_id
         JOIN filial fl ON kf.filial_kod = fl.filial_kod
ORDER BY mk.muellim_id, mk.kurs_kod;

-- b) Yalnız iki cədvəli birləşdirən variant; hansı sətir artıq gəlir?
SELECT mk.muellim_id, mk.kurs_kod, kf.filial_kod
FROM muellim_kurs mk
         JOIN kurs_filial kf ON mk.kurs_kod = kf.kurs_kod;
-- Nəticə: 5 sətir - əlavə gələn (M-11, SQL-101, F-02) A6-d-də göstərdiyimiz eyni saxta sətirdir.

-- İzah: bu sətir real həyatda nəyi səhv iddia edir?
-- İzah: "M-11 SQL-101-i F-02 filialında tədris edir" - halbuki M-11 F-02-də
-- ümumiyyətlə işləmir (muellim_filial cədvəlinə görə). Üçüncü proyeksiya
-- (muellim_filial) yoxlanmadığı üçün bu yanlış kombinasiya süzülməyib qalır.


-- ===== Tapşırıq C3 — Anomaliyaların praktik nümayişi =====
-- Hər əməliyyatı normallaşdırılmamış cədvəldə (fikrən) və öz sxemində
-- (real UPDATE / DELETE / INSERT ilə) yerinə yetir, təsirlənən sətir
-- sayını müqayisə et.
--
-- a) Müəllim M-05-in e-poçtu dəyişdi.
UPDATE muellim
SET muellim_email = 'reshad.quliyev@akademiya.az'
WHERE muellim_id = 'M-05';
-- Nəticə: 1 sətir təsirlənir (yalnız muellim cədvəlində).

-- İzah: Normallaşdırılmamış kurs_qeydiyyat-da isə M-05-in olduğu bütün sətirlər
-- (3 sətir: T-01/SQL-101, T-02/SQL-101, T-05/SQL-101) ayrı-ayrılıqda yenilənməli olardı.

-- b) Yeni fənn ("Kibertəhlükəsizlik") əlavə olunur — hələ kursu və tələbəsi yoxdur.
--    Normallaşdırılmamış cədvəldə bunu ümumiyyətlə yazmaq olurmu?
INSERT INTO fenn (fenn_kod, fenn_ad)
VALUES ('F-SEC', 'Kibertəhlükəsizlik');
-- Nəticə: 1 sətir əlavə olunur, heç bir problem yoxdur (fenn kurs-dan asılı deyil).

-- İzah: Normallaşdırılmamış kurs_qeydiyyat-da bunu yazmaq mümkün deyil, çünki
-- kurs_kod (və digər kurs sütunları) NOT NULL-dur - kursu olmayan fənni sətir
-- kimi ifadə etmək olmur (INSERT anomaliyası).

-- c) DSC-301 kursuna yazılan tək tələbənin (T-03) qeydiyyatı silinir.
--    Normallaşdırılmamış cədvəldə hansı məlumat həmişəlik itir?
DELETE
FROM qeydiyyat
WHERE telebe_id = 'T-03'
  AND kurs_kod = 'DSC-301';
-- Nəticə: 1 sətir silinir, kurs (DSC-301) və fənn (F-DSC) məlumatı öz
-- cədvəllərində (kurs, fenn) toxunulmadan qalır.

-- İzah: Normallaşdırılmamış kurs_qeydiyyat-da bu sətrin silinməsi kurs_ad,
-- kurs_saat, qiymet, fenn_kod, fenn_ad kimi məlumatları da özü ilə həmişəlik
-- apararaq itirər (DELETE anomaliyası).

-- Nəticə: bu üç hal hansı anomaliyalara (UPDATE / INSERT / DELETE) uyğundur?
-- İzah: a) UPDATE anomaliyası, b) INSERT anomaliyası, c) DELETE anomaliyası.


-- #########################################################
-- #                                                       #
-- #   BONUS (+10 bal)                                     #
-- #                                                       #
-- #########################################################


-- ===== Bonus a — EXPLAIN (ANALYZE, BUFFERS) =====
-- C1 sorğusu üçün icra et; birləşmə alqoritmlərini (Nested Loop, Hash Join, Merge Join) sadala və seçimin səbəbini izah et.
EXPLAIN (ANALYZE, BUFFERS)
SELECT t.telebe_id,
       t.telebe_ad,
       tel.telefonlar,
       q.kurs_kod,
       k.kurs_ad,
       m.muellim_ad,
       dil.muellim_dilleri,
       fl.filial_unvan,
       q.odenis,
       q.imtahan_bali
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
         JOIN muellim m ON q.muellim_id = m.muellim_id
         JOIN otaq o ON q.otaq_no = o.otaq_no
         JOIN filial fl ON o.filial_kod = fl.filial_kod
         LEFT JOIN (SELECT telebe_id, STRING_AGG(telefon, ', ') AS telefonlar
                    FROM telebe_telefon
                    GROUP BY telebe_id) tel ON tel.telebe_id = t.telebe_id
         LEFT JOIN (SELECT muellim_id, STRING_AGG(dil, ', ') AS muellim_dilleri
                    FROM muellim_dil
                    GROUP BY muellim_id) dil ON dil.muellim_id = m.muellim_id;

-- İzah: cədvəllər çox kiçik olduğu üçün planlaşdırıcı adətən Hash Join və ya
-- Nested Loop seçir (Merge Join isə böyük, əvvəlcədən sıralanmış cədvəllərdə
-- üstünlük təşkil edir). Nəticəni (screenshot) ayrıca PDF-də göstər.


-- ===== Bonus b — enable_hashjoin = off =====
-- Planı yenidən al, nəyin dəyişdiyini yaz, sonda 'on' qaytar.
SET enable_hashjoin = off;

EXPLAIN (ANALYZE, BUFFERS)
SELECT t.telebe_id,
       t.telebe_ad,
       tel.telefonlar,
       q.kurs_kod,
       k.kurs_ad,
       m.muellim_ad,
       dil.muellim_dilleri,
       fl.filial_unvan,
       q.odenis,
       q.imtahan_bali
FROM qeydiyyat q
         JOIN telebe t ON q.telebe_id = t.telebe_id
         JOIN kurs k ON q.kurs_kod = k.kurs_kod
         JOIN muellim m ON q.muellim_id = m.muellim_id
         JOIN otaq o ON q.otaq_no = o.otaq_no
         JOIN filial fl ON o.filial_kod = fl.filial_kod
         LEFT JOIN (SELECT telebe_id, STRING_AGG(telefon, ', ') AS telefonlar
                    FROM telebe_telefon
                    GROUP BY telebe_id) tel ON tel.telebe_id = t.telebe_id
         LEFT JOIN (SELECT muellim_id, STRING_AGG(dil, ', ') AS muellim_dilleri
                    FROM muellim_dil
                    GROUP BY muellim_id) dil ON dil.muellim_id = m.muellim_id;

SET enable_hashjoin = on;

-- İzah: Hash Join söndürüldükdə planlaşdırıcı Merge Join-a keçdi (hər iki tərəfi
-- ortaq sütun üzrə sort edib sıra ilə müqayisə etdi). Bizim cədvəllər çox kiçik
-- olduğu üçün icra vaxtında hiss ediləcək fərq az olur, amma EXPLAIN planında
-- seçilən alqoritm və planlaşdırılan xərc (cost) dəyişir.


-- ===== Bonus c — İndeks =====
-- qeydiyyat cədvəlinin muellim_id sütunu üzərində indeks yarat,
-- planın dəyişib-dəyişmədiyini yoxla.
CREATE INDEX idx_qeydiyyat_muellim ON qeydiyyat (muellim_id);

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM qeydiyyat
WHERE muellim_id = 'M-05';

-- İzah: bu ölçüdə cədvəldə indeks niyə istifadə olunmaya bilər?
-- İzah: qeydiyyat cədvəlində cəmi 7 sətir olduğu üçün planlaşdırıcı böyük
-- ehtimalla Seq Scan seçəcək - indeksi oxumaq (əlavə I/O mərhələsi) kiçik
-- cədvəldə bütün cədvəli birbaşa oxumaqdan daha baha başa gəlir.


-- ===== Bonus d — TRIGGER testi =====
-- A4-də itən funksional asılılığı qorumaq üçün yazdığın mexanizmi test et:
-- qaydanı pozan bir INSERT yaz və xətanın alındığını göstər.
-- A4-dəki mexanizmi (kompozit FK + UNIQUE) real yaradıb test edirik:
CREATE TABLE muellim_fenn
(
    muellim_id VARCHAR(10) PRIMARY KEY,
    fenn_kod   VARCHAR(10) NOT NULL REFERENCES fenn (fenn_kod),
    UNIQUE (muellim_id, fenn_kod)
);

CREATE TABLE telebe_muellim
(
    telebe_id  VARCHAR(10) NOT NULL REFERENCES telebe (telebe_id),
    muellim_id VARCHAR(10) NOT NULL,
    fenn_kod   VARCHAR(10) NOT NULL,
    PRIMARY KEY (telebe_id, muellim_id),
    FOREIGN KEY (muellim_id, fenn_kod) REFERENCES muellim_fenn (muellim_id, fenn_kod),
    UNIQUE (telebe_id, fenn_kod)
);

INSERT INTO muellim_fenn (muellim_id, fenn_kod)
VALUES ('M-05', 'F-SQL'),
       ('M-11', 'F-SQL');

INSERT INTO telebe_muellim (telebe_id, muellim_id, fenn_kod)
VALUES ('T-01', 'M-05', 'F-SQL');

-- Qaydanı pozan INSERT: T-01 eyni fənni (F-SQL) ikinci bir müəllimdən (M-11) öyrənmək istəyir.
INSERT INTO telebe_muellim (telebe_id, muellim_id, fenn_kod)
VALUES ('T-01', 'M-11', 'F-SQL');
-- Gözlənilən nəticə: XƏTA - "duplicate key value violates unique constraint"
-- (UNIQUE (telebe_id, fenn_kod) pozulur), çünki T-01 artıq F-SQL-i M-05-dən
-- öyrənir, ikinci müəllimdən eyni fənni öyrənə bilməz.

-- İzah: test nəticəsini (xəta mesajının screenshot-unu) ayrıca PDF-də göstər.
