# Şirkət Analitika Hesabatı

Java tapşırığı — Stream API (`java.util.stream`) ilə tam analitika hesabatı generatoru

| | |
| --- | --- |
| **Mövzu** | Java Stream API (`java.util.stream`) |
| **Səviyyə** | Orta – İrəli |
| **Format** | Bir metodda tam hesabat generatoru |
| **Əsas şərt** | Klassik `for`/`while` loop qadağandır (yalnız data generasiyası istisnadır) |
| **Bölmə sayı** | 7 əsas bölmə (A–G) + performans testi |

---

## 1. Ssenari

Sən bir şirkətin İT sistemində analitika modulu yazan proqramçısan. Sənə işçilər haqqında məlumat verilir və rəhbərlik səndən tam bir analitika hesabatı istəyir.

**Əsas şərt:** bütün hesablamalar Stream API ilə edilməlidir — heç bir yerdə klassik `for`/`while` loop işlədilə bilməz (yalnız data yaratmaq üçün istisnadır).

---

## 2. Hazırlıq — Data modeli

Aşağıdakı record sinfini yarat:

```java
record Employee(
        String name,
        String department,
        double salary,
        int age,
        List<String> skills,
        LocalDate hireDate,
        String managerName // null ola bilər (rəhbər üçün)
) {}
```

Ən azı **25 işçilik** `List<Employee>` yarat:

- 4–5 departament: `Engineering`, `Sales`, `HR`, `Marketing`, `Finance`
- Maaşlar: 3000–18000 aralığında
- Yaşlar: 22–60
- Hər işçiyə 2–5 skill (`Java`, `Python`, `SQL`, `AWS`, `React`, `Docker`, `Excel`, `Sales`, `Negotiation` və s.)
- `hireDate`: 2014–2024 aralığında
- Bəzilərinin `managerName`-i null olsun (rəhbərlər), bəzilərinin dolu olsun

---

## 3. Əsas tapşırıq — Tək metodda tam hesabat

Aşağıdakı imzaya malik bir metod yaz:

```java
String generateReport(List<Employee> employees)
```

Bu metod aşağıdakı **bütün bölmələri** ehtiva edən formatlı bir `String` qaytarmalıdır. Hər bölmə **ayrı bir stream zənciri** ilə hesablanmalı, nəticələr sonda `StringBuilder` və ya `String.join` / `Collectors.joining` ilə birləşdirilməlidir.

### Tapşırıq A: Ümumi statistika

- Ümumi işçi sayı, ümumi maaş xərci, orta maaş (`DoubleSummaryStatistics` istifadə et)
- Ən yaşlı və ən gənc işçini (adı, yaşı) tap — `Optional` ilə düzgün idarə et

### Tapşırıq B: Departament analizi

- Hər departament üzrə: işçi sayı, orta maaş, ən yüksək maaşlı işçinin adı
  (`groupingBy` + downstream collector-lərin kombinasiyası, `Map<String, DepartmentSummary>` formasında — özün `DepartmentSummary` record-u yaz)
- Maaş xərci ən yüksək olan departamenti tap

### Tapşırıq C: Skill analizi

- Bütün unikal skill-lər və hər birinə malik neçə işçi olduğu (`flatMap` + `groupingBy(counting())`)
- Ən populyar 3 skill (sıralanmış)
- **Əlavə çətinlik:** heç bir ortaq skill-i olmayan iki departamentin adlarını tap — iki dəyər arası kəsişməni stream ilə yoxla

### Tapşırıq D: Yaş/staj analizi

- İşçiləri iş stajına görə (`hireDate`-dən bu günə) 3 qrupa böl: `"0-2 il"`, `"3-5 il"`, `"5+ il"`
  (`Collectors.groupingBy` + xüsusi classifier funksiya)
- Hər qrupun orta maaşını tap

### Tapşırıq E: Rəhbərlik strukturu

- Neçə nəfərin rəhbər olduğunu (yəni kimlərin adı başqalarının `managerName`-ində keçir) tap
- Hər rəhbərin neçə tabeliyində işçi olduğunu hesabla (`Map<String, Long>`)

### Tapşırıq F: Tək keçidli optimallaşdırma

- `Collectors.teeing` istifadə edərək **tək stream keçidində** həm minimum, həm maksimum maaşı tap və hesabata əlavə et

### Tapşırıq G: Custom Collector

- Bütün işçi adlarını departament üzrə qruplaşdırıb, `"Engineering: Ali, Vəli, Aygün"` formatında sətirlərə çevirən **öz custom Collector**-ini yaz
  (`Collector.of` ilə, hazır `Collectors.joining` istifadə etməyəcəksən — sıfırdan `supplier` / `accumulator` / `combiner` / `finisher` yazacaqsan)

---

## 4. Performans testi

Eyni hesabatı **500,000 elementlik** süni generasiya olunmuş `List<Employee>` üzərində işlət:

- Bir dəfə `stream()` ilə, bir dəfə `parallelStream()` ilə çalışdır, hər ikisinin vaxtını ölç
- Hansı bölmələrdə paralel stream-in fayda verdiyini qeyd et
- Hansı bölmələrdə (məsələn kiçik map-lərdə) overhead yaratdığını qeyd et

---

## Performans testi — nəticələr

Ölçmə: `src/benchmark/StreamPerformanceBenchmark.java`, data generatoru: `src/seed/LargeEmployeeSeed.java` (fixed seed `42`, yəni hər işlədişdə eyni data).
Metodika: hər bölmə üçün 2 warmup keçidi (JIT isinsin), sonra 3 ölçülən keçid, **minimum** vaxt götürülür (GC/OS noise vaxtı yalnız artıra bilər).

**500,000 employee** (16 core):

| Bölmə | `stream()` ms | `parallelStream()` ms | speedup |
| --- | ---: | ---: | ---: |
| A — Ümumi statistika | 25.21 | 18.68 | 1.35x |
| B — Departament analizi | 56.86 | 83.21 | **0.68x** |
| C — Skill analizi | 76.10 | 15.25 | 4.99x |
| D — Staj analizi | 42.56 | 5.54 | 7.68x |
| E — Rəhbərlik strukturu | 25.45 | 10.56 | 2.41x |
| F — `teeing` (min/max) | 12.80 | 2.40 | 5.33x |
| G — Custom Collector | 48.80 | 37.64 | 1.30x |
| **Tam hesabat** | **283.54** | **137.14** | **2.07x** |

**30 employee** (eyni kod, kiçik data — 200 warmup / 500 ölçülən keçid):

| Bölmə | `stream()` ms | `parallelStream()` ms | speedup |
| --- | ---: | ---: | ---: |
| A | 0.003 | 0.023 | 0.14x |
| B | 0.026 | 0.033 | 0.81x |
| C | 0.021 | 0.040 | 0.53x |
| D | 0.003 | 0.010 | 0.32x |
| E | 0.004 | 0.007 | 0.58x |
| F | 0.004 | 0.008 | 0.50x |
| G | 0.002 | 0.012 | 0.15x |
| **Tam hesabat** | **0.031** | **0.152** | **0.20x** |

### Paralel harada fayda verir

**A, C, D, E, F** — bu bölmələr 500k elementi bir dəfə gəzib **kiçik nəticə** qaytarır (`summaryStatistics`, `counting`, `averagingDouble`, `teeing`). İş bütün core-lar arasında bölünür, birləşdiriləcək nəticə isə balacadır — ona görə 2.4x–7.7x qazanc.

### Paralel harada overhead yaradır

**B — Departament analizi (0.68x)** — `groupingBy` hər departament üçün ayrıca `ArrayList` yığır, sonra `collectingAndThen` içində həmin siyahı üzərindən **3 dəfə əlavə keçid** (avg, max, sum) edilir və o keçidlər onsuz da sequential-dır. Paraleldə hər thread öz map-ini qurur, sonra 5 kiçik map merge olunur — `ArrayList` kopyalaması + merge xərci qazancdan bahalı çıxır.

**G — Custom Collector (1.30x, dəyişkən)** — vaxtın böyük hissəsi stream-də yox, 500k adın `String.join` ilə birləşdirilməsində gedir; bu iş `finisher`-də tək thread-də olur, ona görə paralel yalnız işin kiçik hissəsini sürətləndirir.

**Kiçik data — bütün bölmələr** — 30 elementdə paralel **hər yerdə** daha yavaşdır (0.14x–0.81x): `ForkJoinPool`-un task bölüşdürmə xərci hesablamanın özündən çoxdur.

> Qeyd: G bölməsinin `parallelStream()` ilə işləməsi üçün custom collector-in `combiner()`-i düzgün implement olunmalıdır — `null` qaytarsa `NullPointerException` atır.

---

## Qeyd

Diqqət mərkəzi Stream API-nin təkib olunmuş (composed) istifadəsindədir: `groupingBy` + downstream collector-lər, `flatMap`, `teeing`, `collectingAndThen`, `Optional` ilə təhlükəsiz idarəetmə və sıfırdan yazılmış `Collector`. Klassik dövrlər yalnız test datasının generasiyasında icazəlidir.

## Terminal Output

Example execution of the program:

![Terminal Output](images/img.png)