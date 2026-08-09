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

## Qeyd

Diqqət mərkəzi Stream API-nin təkib olunmuş (composed) istifadəsindədir: `groupingBy` + downstream collector-lər, `flatMap`, `teeing`, `collectingAndThen`, `Optional` ilə təhlükəsiz idarəetmə və sıfırdan yazılmış `Collector`. Klassik dövrlər yalnız test datasının generasiyasında icazəlidir.
