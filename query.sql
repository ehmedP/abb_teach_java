drop table if exists satislar;

create table satislar
(
    satis_id     int primary key,
    mehsul       varchar(50),
    kateqoriya   varchar(30),
    seher        varchar(30),
    satici       varchar(50),
    miqdar       int,
    qiymet       numeric(10, 2),
    endirim_faiz numeric(5, 2),
    tarix        date
);

insert into satislar (satis_id, mehsul, kateqoriya, seher, satici, miqdar, qiymet, endirim_faiz, tarix)
values (1, ' noutbuk ', 'Texnika', 'Bakı', 'aysel memmedova', 2, 1250.00, 10.00, date '2024-01-15'),
       (2, 'MONITOR', 'Texnika', 'Bakı', 'aysel memmedova', 3, 320.50, null, date '2024-01-28'),
       (3, 'klaviatura', 'Aksesuar', 'Gəncə', 'RAUF ELIYEV', 5, 45.90, 0.00, date '2024-02-05'),
       (4, 'Printer', 'Texnika', 'Sumqayıt', 'nigar huseynova', 1, 480.00, 5.00, date '2024-02-17'),
       (5, 'telefon ', 'Texnika', 'Bakı', 'Elvin Qasimov', 4, 899.99, 15.00, date '2024-03-03'),
       (6, 'tablet', 'Texnika', 'Gəncə', 'RAUF ELIYEV', 2, 640.00, null, date '2024-03-11'),
       (7, 'kamera', 'Texnika', 'Bakı', 'aysel memmedova', 1, 1250.00, null, date '2024-03-22'),
       (8, ' kabel', 'Aksesuar', 'Sumqayıt', 'nigar huseynova', 10, 12.50, 0.00, date '2024-04-02'),
       (9, 'MONITOR', 'Texnika', 'Şəki', 'Elvin Qasimov', 2, 320.50, null, date '2024-04-14'),
       (10, 'noutbuk', 'Texnika', 'Gəncə', 'RAUF ELIYEV', 1, 1799.00, 20.00, date '2024-04-25'),
       (11, 'klaviatura', 'Aksesuar', 'Bakı', 'Elvin Qasimov', 6, 45.90, null, date '2024-05-06'),
       (12, 'Telefon', 'Texnika', 'Sumqayıt', 'nigar huseynova', 3, 899.99, null, date '2024-05-19'),
       (13, 'kabel', 'Aksesuar', 'Bakı', 'aysel memmedova', 8, 12.50, 0.00, date '2024-06-01'),
       (14, 'printer', 'Texnika', null, 'Elvin Qasimov', 2, 480.00, 10.00, date '2024-06-12'),
       (15, 'kamera', 'Texnika', 'Gəncə', 'RAUF ELIYEV', 1, 1150.00, null, date '2024-06-23'),
       (16, 'tablet ', 'Texnika', 'Bakı', 'nigar huseynova', 4, 640.00, 25.00, date '2024-07-04'),
       (17, 'MONITOR ', 'Texnika', 'Sumqayıt', 'aysel memmedova', 5, 299.00, null, date '2024-07-15'),
       (18, 'qulaqliq', 'Aksesuar', 'Bakı', 'Elvin Qasimov', 7, 89.90, 5.00, date '2024-07-27');


-- task 1: Hər satış üçün satis_id, məhsul adının boşluqsuz və böyük hərflərlə yazılışı, həmin adın hərf sayı və ilk 3 hərfi göstərilən sorğunu yazın. Nəticə satis_id üzrə sıralansın.

select s.satis_id,
       upper(trim(s.mehsul)),
       length(trim(s.mehsul))         as herf_sayi,
       upper(left(trim(s.mehsul), 3)) as ilk_3_herf
from satislar s
order by s.satis_id;

-- task 2: Cədvəldəki təkrarsız məhsul adlarını çıxarın. Səliqəsizlik təmizlənməlidir — ' noutbuk ' ilə 'noutbuk' eyni sayılsın. Əlifba sırası ilə.

select distinct upper(trim(s.mehsul)) as mehsul_adi
from satislar s
order by mehsul_adi;

-- task 3: Hər satış üçün MƏHSUL / Şəhər formatında etiket sütunu düzəldin. Şəhər NULL olduqda etiketdə NAMELUM yazılsın.

select *, concat(upper(trim(s.mehsul)), ' / ', coalesce(upper(trim(s.seher)), 'NAMELUM')) as etiket
from satislar s;

-- task 4: Satıcıların təkrarsız siyahısını çıxarın və `satici` sütununu ad və soyad olmaqla iki sütuna bölün.

select left(upper(trim(s.satici)), position(' ' in upper(trim(s.satici))) - 1)   as satici_adi,
       substr(upper(trim(s.satici)), position(' ' in upper(trim(s.satici))) + 1) as satici_soyadi
from satislar s
group by s.satici;

-- task 5: Hər satış üçün anbar kodu yaradın: məhsulun ilk 3 hərfi (böyük) - satışın ayı - 3 rəqəmli `satis_id`. Nümunə: `NOU-01-001`.

select *,
       concat(left(upper(trim(s.mehsul)), 3), '-', s.miqdar, '-', lpad(text(s.satis_id), 3, '0')) as code
from satislar s;

-- task 6: Hər satış üçün ümumi məbləği (`qiymet * miqdar`), onun 18% ƏDV-ni (2 rəqəmə yuvarlaqlaşdırılmış), qiymətin yuxarı və aşağı yuvarlaqlaşdırılmış variantını hesablayın. Ümumi məbləğə görə azalan sıra.

select *,
       qiymet * miqdar                      as umumi_mebleg,
       round(qiymet * miqdar * 18 / 100, 2) as edv,
       ceiling(qiymet)                      as yuxari_qiymet,
       floor(qiymet)                        as asagi_qiymet
from satislar
order by umumi_mebleg desc;

-- task 7: Hər satış üçün: `miqdar`-ın 2-yə bölünməsindən qalıq, `miqdar`-ın 5-dən fərqinin modulu və qiymətin kvadrat kökü (2 rəqəm).

select *,
       miqdar % 2             as qaliq,
       abs(miqdar - 5)        as modul_ferq,
       round(sqrt(qiymet), 2) as kvadrat_kok
from satislar;

-- task 8: Ödəniləcək məbləği hesablayın: `qiymet * miqdar` üzərinə `endirim_faiz` tətbiq olunsun. Endirim NULL olarsa 0 kimi qəbul edilsin. Azalan sıra.

select *,
       coalesce(qiymet * miqdar, 0) *
       (1 - coalesce(endirim_faiz, 0) / 100.0) as odenilecek_mebleg
from satislar
order by odenilecek_mebleg desc;

-- task 9: Məbləği bütün satışların orta məbləğindən böyük olan satışları tapın. Orta məbləğ sorğunun içində hesablanmalıdır (əl ilə rəqəm yazmaq olmaz).

select *
from satislar
where qiymet * miqdar > (select avg(qiymet * miqdar) from satislar);

-- task 10: Hər satışın tarixindən il, ay və gün hissələrini ayrı sütunlarda çıxarın.

select *,
       extract(year from tarix),
       extract(month from tarix),
       extract(day from tarix)
from satislar;

-- task 11: Yalnız iyun və iyul aylarındakı satışlar üçün: satışın üzərindən neçə gün keçdiyi və tarixə 30 gün əlavə edilmiş zəmanət sonu tarixi.

select *,
       current_date - tarix,
       tarix + interval '30 days' as thirty_days_later
from satislar
where extract(month from tarix) = 7;

-- task 12: Aylıq hesabat: hər ay üçün (format `AA-İİİİ`, məsələn `03-2024`) satış sayı və ümumi dövriyyə. Xronoloji sıra.

select to_char(date_trunc('month', tarix), 'MM-YYYY') as ay,
       count(*)                                       as satis_sayi,
       sum(qiymet * miqdar)                           as umumi_dovriyye
from satislar
group by date_trunc('month', tarix)
order by date_trunc('month', tarix);

-- task 13: Satışların həftənin günü üzrə paylanmasını çıxarın: gün nömrəsi, satış sayı və dövriyyə.

select extract(isodow from tarix) as hefte,
       count(*)                   as satis_sayi,
       sum(qiymet * miqdar)       as umumi_dovriyye
from satislar
group by extract(isodow from tarix)
order by extract(isodow from tarix);

-- task 14: Bir sətirdə göstərin: ilk və son satış tarixi, aralarındakı gün fərqi, ümumi satış sayı və bir aya düşən orta dövriyyə.

select min(tarix)                                                   as ilk_satis_tarixi,
       max(tarix)                                                   as son_satis_tarixi,
       max(tarix)::date - min(tarix)::date                          as gun_ferqi,
       count(*)                                                     as satis_sayi,
       round(
               sum(qiymet * miqdar)
                   / count(distinct date_trunc('month', tarix)), 2) as aya_dusen_orta_dovriyye
from satislar;

-- task 15: Bir sorğuda göstərin: `endirim_faiz`-in xam qiyməti, null-un 0-a çevrilmiş variantı, 0 olduqda null qaytaran variantı və şəhərin null-suz variantı.

select *,
       endirim_faiz               as xam_deyer,
       coalesce(endirim_faiz, 0)  as null_0,
       nullif(endirim_faiz, 0)    as sifir_null,
       coalesce(seher, 'NAMELUM') as seher
from satislar;

-- task 16: Hər satış üçün: qiymətin mətn tipinə çevrilmiş variantı, `satis_id-məhsul` kodu (məs. `1-noutbuk`) və satışdan 2024-12-31-ə qədər neçə gün qaldığı.

select *,
       qiymet::varchar                                 as qiymet_str,
       satis_id::varchar || '-' || upper(trim(mehsul)) as satis_kod,
       abs(tarix::date - '2024-12-31'::date)           as gun_ferqi
from satislar;

-- task 17: Hər satıcı üçün: ümumi satış sayı, endirimi qeyd olunmuş (null olmayan) satışların sayı, həqiqətən endirim tətbiq edilmiş (0-dan böyük) satışların sayı və sonuncunun faizi. Faizə görə azalan sıra.

select upper(trim(satici))                                                   as satici,
       count(*)                                                              as umumi_satis,
       count(*) filter (where endirim_faiz is not null)                      as qeyd_olunmus_endirim,
       count(*) filter (where endirim_faiz > 0)                              as tetbiq_edilmis_endirim,
       round(count(*) filter (where endirim_faiz > 0) * 100.0 / count(*), 2) as endirim_faizi
from satislar
group by upper(trim(satici))
order by endirim_faizi desc;

-- task 18: Qiymətə görə kateqoriya təyin edin: 1000 və yuxarı → `bahali`, 300–999 → `orta`, qalanı → `ucuz`. Qiymətə görə azalan sıra.

select *,
       case
           when qiymet >= 1000 then 'bahali'
           when qiymet between 300 and 999 then 'orta'
           else 'ucuz'
           end as qiymet_kateqoriya
from satislar
order by qiymet desc;

-- task 19: Endirim statusu sütunu yazın: null və ya 0 → `endirim yoxdur`, 15 və yuxarı → `boyuk endirim`, qalanı → `kicik endirim`.

select *,
       case
           when endirim_faiz is null or endirim_faiz = 0 then 'endirim yoxdur'
           when endirim_faiz >= 15 then 'boyuk endirim'
           else 'kicik endirim'
           end as endirim_kateqoriya
from satislar;

-- task 20: Tək sorğu, tək sətir nəticə: ümumi satış sayı, Texnika satışlarının sayı, Aksesuar satışlarının sayı. WHERE istifadə etmək olmaz.

select count(*),
       count(case when kateqoriya = 'Texnika' then 1 end)  as texnika_sayi,
       count(case when kateqoriya = 'Aksesuar' then 1 end) as aksesuar_sayi
from satislar;

-- task 21: Ümumi statistika (bir sətir): satış sayı, şəhəri boş olmayan sətirlərin sayı, fərqli satıcı sayı, ümumi dövriyyə, orta qiymət, ən ucuz və ən bahalı qiymət.

select count(*)                       as satis_sayi,
       count(seher)                   as seher_sayi,
       count(distinct satici)         as ferqli_satici_sayi,
       round(sum(qiymet * miqdar), 2) as umumi_dovriyye,
       round(avg(qiymet), 2)          as orta_qiymet,
       round(min(qiymet), 2)          as en_ucuz,
       round(max(qiymet), 2)          as en_bahali
from satislar;

-- task 22: Şəhər üzrə satış sayı və dövriyyə. Şəhəri null olan satış `namelum` adı altında görünsün. Dövriyyəyə görə azalan sıra.

select coalesce(upper(trim(seher)), 'namelum') as seher,
       count(*)                                as satis_sayi,
       round(sum(qiymet * miqdar), 2)          as umumi_dovriyye
from satislar
group by upper(trim(seher));

-- task 23: Satıcı üzrə hesabat: yalnız qiyməti 50-dən böyük satışlar nəzərə alınsın, qruplaşdırmadan sonra isə yalnız dövriyyəsi 5000-dən çox olan satıcılar qalsın.

select upper(trim(satici))            as satici,
       count(*)                       as satis_sayi,
       round(sum(qiymet * miqdar), 2) as umumi_dovriyye
from satislar
where qiymet > 50
group by upper(trim(satici))
having sum(qiymet * miqdar) > 5000
order by satici;

-- task 24: Eyni məhsul müxtəlif qiymətlərə satılıb. Hər məhsul üçün (təmizlənmiş ad) satış sayı, ən ucuz, ən bahalı qiymət və aralarındakı fərq. Yalnız birdən çox dəfə satılan məhsullar. Fərqə görə azalan sıra.

select upper(trim(mehsul))                      as mehsul,
       count(*)                                 as satis_sayi,
       round(min(qiymet), 2)                    as en_az_qiymet,
       round(max(qiymet), 2)                    as en_cox_qiymet,
       round(abs(max(qiymet) - min(qiymet)), 2) as qiymet_ferqi
from satislar
group by upper(trim(mehsul))
having count(*) > 1
order by qiymet_ferqi desc;

-- task 25: Hər şəhər üçün orada satılan məhsulların vergüllə ayrılmış siyahısını bir sətirdə çıxarın (təkrarsız, böyük hərflərlə).

select coalesce(upper(trim(seher)), 'namelum')                                     as seher,
       string_agg(distinct upper(trim(mehsul)), ', ' order by upper(trim(mehsul))) as mehsullar
from satislar
group by seher;

-- task 26: Bütün satışları qiymətə görə azalan sıralayın və üç sütun əlavə edin: `row_number()`, `rank()`, `dense_rank()`.

select *,
       row_number() over (order by qiymet desc) as row_,
       rank() over (order by qiymet desc)       as rank_,
       dense_rank() over (order by qiymet desc) as dense_rank_
from satislar
order by qiymet desc;

-- task 27: Hər şəhərin daxilində satışları məbləğə görə sıralayın (null şəhər `namelum` qrupuna düşsün).

select coalesce(upper(trim(seher)), 'namelum')                                                       as seher,
       qiymet,
       row_number() over (partition by coalesce(upper(trim(seher)), 'namelum') order by qiymet desc) as rn
from satislar
order by seher, qiymet desc;


-- task 28: Hər satışın ümumi dövriyyədə neçə faiz pay tutduğunu hesablayın. Ümumi cəm `sum(...) over ()` ilə alınmalıdır — group by istifadə etmək olmaz, nəticədə 18 sətir qalmalıdır.

select *,
       round((qiymet * miqdar) / sum(qiymet * miqdar) over () * 100, 2) as dovriyye_faizi
from satislar;

-- task 29: Tarix sırası ilə: hər satışın məbləği, əvvəlki və sonrakı satışın məbləği, aralarındakı fərq və yığılan (running) cəm.

select *,
       qiymet * miqdar                                                           as mebleg,
       lag(qiymet * miqdar) over (order by tarix)                                as evvelki_mebleg,
       lead(qiymet * miqdar) over (order by tarix)                               as sonraki_mebleg,
       round((qiymet * miqdar) - lag(qiymet * miqdar) over (order by tarix), 2)  as mebleg_ferqi_evvelki,
       round((qiymet * miqdar) - lead(qiymet * miqdar) over (order by tarix), 2) as mebleg_ferqi_sonraki,
       sum(qiymet * miqdar) over (order by tarix)                                as running_cem
from satislar
order by tarix;

-- task 30: Satışları məbləğə görə 4 bərabər qrupa (çeyrəyə) bölün və hər satışın hansı çeyrəyə düşdüyünü göstərin.

select *,
       ntile(4) over (order by qiymet * miqdar desc) as quartile
from satislar;

-- task 31: İkinci ən bahalı satışı tapın. LIMIT və ya OFFSET istifadə etmək qadağandır.

select *
from (select *,
             round(qiymet * miqdar, 2)                         as mebleg,
             dense_rank() over (order by qiymet * miqdar desc) as rank_
      from satislar) t
where t.rank_ = 2
order by t.mebleg desc;

-- task 32: Hər satıcının ən böyük məbləğli satışını tapın — satıcı başına yalnız 1 sətir. Məbləğə görə azalan sıra.

select *
from (select *,
             round(qiymet * miqdar, 2)                                                                         as mebleg,
             rank() over (partition by coalesce(upper(trim(satici)), 'namelum') order by qiymet * miqdar desc) as rank_
      from satislar) t
where rank_ = 1
order by mebleg desc;

-- task 33: Tarix sırası ilə baxdıqda ardıcıl iki satış arasında ən böyük düşüş hansı tarixdə baş verib? Tarixi, məbləği, əvvəlki məbləği və fərqi göstərin (yalnız 1 sətir).

select t.tarix,
       t.mebleg,
       t.evvelki_mebleg,
       t.mebleg_ferqi_evvelki
from (select *,
             round(qiymet * miqdar, 2)                                                as mebleg,
             lag(qiymet * miqdar) over (order by tarix)                               as evvelki_mebleg,
             round((qiymet * miqdar) - lag(qiymet * miqdar) over (order by tarix), 2) as mebleg_ferqi_evvelki
      from satislar) t
where t.mebleg_ferqi_evvelki < 0
order by t.mebleg_ferqi_evvelki
limit 1;

-- task 34: Aydan-aya artım faizi: hər ay üçün dövriyyə, əvvəlki ayın dövriyyəsi və artım faizi (1 rəqəm). İlk ayda faiz NULL olmalıdır.

select t.ay,
       t.umumi_dovriyye,
       lag(t.umumi_dovriyye) over () as evvelki_dovriyye,
       round((t.umumi_dovriyye - lag(t.umumi_dovriyye) over ()) / lag(t.umumi_dovriyye) over () * 100, 2)::varchar ||
       '%'                           as artim_faizi
from (select to_char(date_trunc('month', tarix), 'MM-YYYY') as ay,
             round(sum(qiymet * miqdar), 2)                 as umumi_dovriyye
      from satislar
      group by ay
      order by ay) t;

-- task 35: Hər ayın ilk satışını tapın: ay, satis_id, tarix və məhsul adı (7 sətir).

select t.ay, t.satis_id, t.tarix, t.mehsul
from (select to_char(date_trunc('month', tarix), 'MM-YYYY') as ay,
             row_number() over (
                 partition by to_char(date_trunc('month', tarix), 'MM-YYYY')
                 order by tarix
                 )                                          as ay_sira,
             satis_id,
             tarix,
             coalesce(initcap(trim(mehsul)), 'NAMELUM')     as mehsul
      from satislar
      order by tarix) t
where t.ay_sira = 1;

-- task 36: Satışları məbləğə görə azalan sıralayın və ümumi dövriyyənin 50%-ni doldurmaq üçün kifayət edən ən böyük satışları tapın (Pareto təhlili). Hər sətirdə yığılan cəm və onun faizi də görünsün.

select t.*, round(t.yigilan_cem / t.umumi_cem * 100, 2) as yigilan_faiz
from (select *,
             round(qiymet * miqdar, 2)                                 as mebleg,
             sum(qiymet * miqdar) over (order by qiymet * miqdar desc) as yigilan_cem,
             sum(qiymet * miqdar) over ()                              as umumi_cem
      from satislar) t
where (t.yigilan_cem - t.mebleg) / t.umumi_cem < 0.5
order by t.mebleg desc;

-- task 37: Hər şəhərin ümumi dövriyyədəki faiz payını hesablayın. Şərt: sorğuda həm GROUP BY, həm də pəncərə funksiyası eyni anda işlədilməlidir (alt-sorğu olmadan).

select coalesce(initcap(trim(seher)), 'NAMELUM')                                                as seher,
       sum(qiymet * miqdar)                                                                     as seher_dovriyye,
       round(sum(qiymet * miqdar) / sum(sum(qiymet * miqdar)) over () * 100, 2)::varchar || '%' as seher_faizi
from satislar
group by coalesce(initcap(trim(seher)), 'NAMELUM');

-- task 38: Hər satıcının satışları arasında ən uzun fasilə (gün ilə) hansı olub? Ən uzun 3 fasiləni göstərin: satıcı, əvvəlki tarix, sonrakı tarix, fasilə.

select *
from (select initcap(trim(satici))                                        as satici,
             lag(tarix) over (partition by satici order by tarix)         as evvelki_tarix,
             tarix                                                        as sonraki_tarix,
             tarix - lag(tarix) over (partition by satici order by tarix) as fasilə
      from satislar) t
where evvelki_tarix is not null
order by fasilə desc
limit 3;

-- task 39: Qiyməti öz şəhərinin orta qiymətindən yüksək olan satışları tapın. Nəticədə şəhər, satis_id, qiymət və həmin şəhərin orta qiyməti göstərilsin.

select *, t.seher_orta_qiymet
from (select *,
             avg(qiymet) over (partition by coalesce(initcap(trim(seher)), 'NAMELUM')) as seher_orta_qiymet
      from satislar) t
where t.qiymet > t.seher_orta_qiymet;

-- task 40: Yekun hesabat. Satıcılar üzrə bir sorğuda: adı böyük hərflərlə, satış sayı, endirim çıxıldıqdan sonrakı xalis dövriyyə (2 rəqəm), orta qiymət (1 rəqəm) və status — brut dövriyyə 6000+ → `Ulduz`, 5000+ → `Yaxsi`, qalanı → `Zeif`. Yalnız 3 və daha çox satışı olan satıcılar, xalis dövriyyəyə görə azalan sıra.

select coalesce(upper(trim(satici)), 'NAMELUM')                                 as satici_name,
       count(*)                                                                 as satis_sayi,
       round(sum(qiymet * miqdar * (1 - coalesce(endirim_faiz, 0) / 100.0)), 2) as xalis_dovriyye,
       round(avg(qiymet), 1)                                                    as orta_qiymet,
       case
           when sum(qiymet * miqdar) >= 6000 then 'Ulduz'
           when sum(qiymet * miqdar) >= 5000 then 'Yaxsi'
           else 'Zeif'
           end                                                                  as status
from satislar
group by satici_name;