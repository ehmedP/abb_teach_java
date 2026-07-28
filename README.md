# İşçi İdarəetmə Sistemi (Employee Management System)

Generics və Java Collections Framework-dən istifadə edərək hazırlanmış işçi idarəetmə sistemi.

---

## Məqsəd

Bu tapşırıq aşağıdakı mövzuları praktiki şəkildə tətbiq etməyi hədəfləyir:

- Generic siniflər (generic classes) və generic metodlar
- Bounded type parameters (`<T extends Comparable<T>>`)
- Wildcard-lar (`? extends`, `? super`)
- `List`, `Map`, `Set` interfeysləri və onların implementasiyaları
- `Comparable` və `Comparator` interfeysləri
- `Collections` utility class-ının metodları (`sort`, `shuffle`, `binarySearch`, `unmodifiableList` və s.)

---

## Sinif Diaqramı

```
                  ┌──────────────────────┐
                  │   Employee<T>        │  ← abstrakt / əsas sinif
                  │   (Comparable)       │
                  └──────────┬───────────┘
                             │
            ┌────────────────┼────────────────┐
            ▼                ▼                ▼
     ┌───────────┐    ┌───────────┐    ┌───────────┐
     │ Developer │    │  Manager  │    │   Intern  │
     │           │    │           │    │           │
     │ - dil     │    │ - teamSize│    │ - uni     │
     │ - projSay │    │ - shobe   │    │ - muddet  │
     └───────────┘    └───────────┘    └───────────┘
```

---

## 1. Baza Generic Sinif — `Employee<T>`

`Employee<T>` abstrakt (və ya normal) sinif. Ümumi sahələr:

| Sahə    | Tip      |
| ------- | -------- |
| id      | `String` |
| ad      | `String` |
| iseBaslamaTarixi | `LocalDate` |
| maas    | `double` |

`Comparable<Employee>` interfeysini implement edir — default sıralama **maaşa görə**.

Əlavə sahə: `department` (`String`) — işçinin şöbəsi (qruplaşdırma üçün istifadə olunur).

Miras alan siniflər:
- **Developer** — proqramlaşdırma dili (`String`), layihə sayı (`int`)
- **Manager** — komanda ölçüsü (`int`), şöbə (`String`)
- **Intern** — universitet (`String`), təcrübə müddəti (`int`, ay ilə)

---

## 2. Generic Konteyner Sinifi — `Company<T extends Employee>`

```java
public class Company<T extends Employee> {
    private List<T> employees;
}
```

Metodlar:

| Metod                    | Açıqlama                                                |
| ------------------------ | ------------------------------------------------------- |
| `add(T employee)`        | İşçi əlavə edir                                         |
| `findById(String id)`    | İD-ə görə işçi tapır (`Optional<T>` qaytarır)           |
| `getAll()`               | Dəyişdirilə bilməyən siyahı qaytarır (`unmodifiableList`) |
| `groupByDepartment()`    | İşçiləri şöbələrə görə qruplaşdırır (`Map`)             |
| `getAllSkills()`         | Bütün işçilərin unikal bacarıqlarını qaytarır (`Set`)   |

---

## 3. Sıralama (Sorting)

### Default sıralama — `Comparable<Employee>`

```java
@Override
public int compareTo(Employee other) {
    return Double.compare(this.maas, other.maas);
}
```

### Əlavə Comparator-lar (ən azı 2)

1. **Ada görə sıralama**:
   ```java
   Comparator<Employee> byName = Comparator.comparing(Employee::getAd);
   ```

2. **İşə başlama tarixinə görə sıralama**:
   ```java
   Comparator<Employee> byDate = Comparator.comparing(Employee::getIseBaslamaTarixi);
   ```

`Collections.sort()` və ya `List.sort()` ilə tətbiq olunur.

---

## 4. Map istifadəsi — Departament üzrə qruplaşdırma

```java
Map<String, List<T>> departmentMap;
```

- **HashMap** — sıralanmamış (sürətli əlavə/axtarış)
- **TreeMap** — açar sırasına görə sıralanmış (əlifba sırası ilə şöbələr)

Eyni məlumat hər iki map-də saxlanılır və çap zamanı sıra fərqi göstərilir.

---

## 5. Set istifadəsi — Unikal bacarıqlar

```java
Set<String> skills = new HashSet<>();  // sıralanmamış
Set<String> sortedSkills = new TreeSet<>(); // əlifba sırası ilə
```

Bütün işçilərin bacarıqları toplanır, `TreeSet` avtomatik sıralama təmin edir.

---

## 6. Generic Metod

```java
public static <T extends Employee> void printEmployees(List<? extends T> employees) {
    for (T employee : employees) {
        System.out.println(employee);
    }
}
```

Bu metod həm `List<Developer>`, həm `List<Manager>`, həm də `List<Intern>` ilə işləyir (wildcard `? extends T` sayəsində).

---

## 7. Collections Utility Metodları

| Metod                               | İstifadə məqsədi                          |
| ----------------------------------- | ----------------------------------------- |
| `Collections.sort(list)`            | Siyahını sıralama                         |
| `Collections.max(list)`             | Maksimum elementi tapma (Comparable əsaslı) |
| `Collections.min(list)`             | Minimum elementi tapma                    |
| `Collections.shuffle(list)`         | Siyahını qarışdırma                       |
| `Collections.binarySearch(list, x)` | İkili axtarış (əvvəlcə sort edilməlidir)  |
| `Collections.unmodifiableList(list)`| Dəyişdirilə bilməyən siyahı qaytarır      |
| `Collections.unmodifiableMap(map)`  | Dəyişdirilə bilməyən map qaytarır         |

---

## 8. Bonus — Əlavə Xallar Üçün

### 8.1 PriorityQueue — Ən yüksək maaşlı işçilər

```java
PriorityQueue<Employee> pq = new PriorityQueue<>(
    Comparator.comparingDouble(Employee::getSalary).reversed()
);
pq.addAll(company.getEmployees());
```

`PriorityQueue` işçiləri maaşa görə tərs sıralayır və `poll()` metodu ilə ən yüksək maaşlı işçilər ardıcıl çıxarılır.

### 8.2 Generic Pair&lt;K, V&gt; sinfi

```java
public class Pair<K, V> {
    private final K key;
    private final V value;
    // getKey(), getValue(), toString(), equals(), hashCode()
}
```

`Map.Entry` əvəzinə istifadə olunur — məsələn, şöbə və işçi adı cütlüklərini saxlamaq üçün.

### 8.3 Stream API (Java 8+)

```java
// Filter, map, collect — müəyyən şöbədəki işçiləri tapmaq
List<String> names = company.getEmployees().stream()
    .filter(e -> "Engineering".equals(e.getDepartment()))
    .filter(e -> e.getSalary() > 3000)
    .map(Employee::getName)
    .collect(Collectors.toList());

// Average salary by department
Map<String, Double> avgSalary = company.getEmployees().stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
```

---

## Nümunə Çıxış

```
======= HashMap (sıralanmamış) =======
Mühəndislik: [Developer{ad=Ali}, Developer{ad=Elvin}]
Marketinq: [Manager{ad=Leyla}]

======= TreeMap (əlifba sırası ilə) =======
Marketinq: [Manager{ad=Leyla}]
Mühəndislik: [Developer{ad=Ali}, Developer{ad=Elvin}]

======= Unikal bacarıqlar (TreeSet) =======
Java, Python, SQL, TeamManagement

======= Sıralanmış siyahı (maaşa görə) =======
1. Intern{ad=Nihat, maas=500.0}
2. Developer{ad=Ali, maas=3000.0}
3. Manager{ad=Leyla, maas=5000.0}

======= Generic Wildcard Metod =======
[Developer, Manager, Intern] — hamısı eyni metodla çap olunur.
```
