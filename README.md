# Multi-Branch Library Management System

## Məqsəd
Bir neçə filialı olan kitabxana şəbəkəsini modelləşdirin. Kitablar filiallar arasında transfer oluna bilər, üzvlər fərqli filiallardan kitab götürə bilər, sistem bildiriş, cərimə, rezervasiya və hesabat funksiyaları ilə işləyir.

---

## 1. Enum-lar

### MembershipType
- `REGULAR`
- `PREMIUM`
- `STUDENT`

### CopyStatus
- `AVAILABLE` – mövcuddur
- `BORROWED` – götürülüb
- `LOST` – itib
- `IN_TRANSIT` – transfer olunur

### NotificationType
- `DUE_SOON` – vaxtı yaxınlaşır
- `OVERDUE` – vaxtı keçib
- `RESERVATION_READY` – rezervasiya hazırdır
- `BLACKLIST_WARNING` – qara siyahı xəbərdarlığı

---

## 2. Interfeyslər

### `Finable`
```java
double calculateFine(int daysLate);
```

### `Notifiable`
```java
void receiveNotification(Notification n);
```

---

## 3. Abstract Class

### `LibraryUser`
- **Sahələr:** `id`, `name`, `email`
- **Abstract metod:** `double getDiscountRate()`
- `Member` bu class-ı extend edir

---

## 4. Əsas Class-lar

### `Book`
- `id`, `title`, `author`, `genre`, `isbn`

### `BookCopy`
- `copyId`, `book`, `branchId`, `status`

### `Member` extends `LibraryUser` implements `Finable`, `Notifiable`
- `membershipType`

### `Branch`
- `branchId`, `name`, `address`

### `Loan`
- `loanId`, `bookCopy`, `member`, `borrowDay`, `dueDay`, `returned` (boolean)

### `Reservation`
- `member`, `book`, `reservationDay`, `priorityScore`
- **Prioritet:** PREMIUM = 1, REGULAR = 2, STUDENT = 3 (kiçik rəqəm = yüksək prioritet)

### `Notification`
- `type`, `message`, `day`

### `FineRecord`
- `member`, `amount`, `reason`, `day`

### `Library` – bütün sistemi idarə edən əsas class

---

## 5. Data Strukturları

| Məqsəd | Struktur |
|--------|----------|
| Filiallar (ID → Branch) | `Map<Integer, Branch>` |
| Hər filialın kitab nüsxələri (bookId → copies) | `Map<Integer, List<BookCopy>>` (hər Branch daxilində) |
| Bütün sistemdəki unikal kitablar | `Set<Book>` |
| Aktiv götürmələr, üzvə görə | `Map<Member, List<Loan>>` |
| Bütün Loan-ların tam tarixçəsi | `List<Loan>` |
| Kitab üzrə rezervasiya növbəsi (prioritetli) | `Map<Integer, PriorityQueue<Reservation>>` |
| Populyarlıq sayğacı | `Map<Book, Integer>` |
| Cərimə tarixçəsi | `List<FineRecord>` |
| Üzvün bildiriş qutusu | `Map<Member, Queue<Notification>>` |
| Qara siyahı | `Set<Member>` |
| Filiallararası transfer tarixçəsi (son 5) | `Deque<String>` |
| Janr → kitablar (əlifba sırası ilə) | `TreeMap<String, Set<Book>>` |
| Ən aktiv üzvlər sayğacı | `Map<Member, Integer>` |

---

## 6. Əməliyyatlar / Logic

### `Library.borrowBook(Member m, Integer bookId, Integer branchId, int currentDay) → boolean`

1. **Qara siyahı yoxlaması:** Əgər `m` qara siyahıdadırsa → console-a "Member is blacklisted!" çap et, `false` qaytar
2. **Nüsxə axtarışı:** Filialda kitabın `AVAILABLE` statuslu nüsxəsini axtar
3. **Tapılmazsa:**
   - "Book not available, added to reservation queue" çap et
   - Üzvü rezervasiya PriorityQueue-suna əlavə et (priorityScore membership-ə görə)
   - `false` qaytar
4. **Tapılarsa:**
   - Nüsxənin statusu `BORROWED` et
   - `dueDay = currentDay + 14` (PREMIUM üçün +21)
   - Yeni `Loan` yarat, siyahılara əlavə et
   - Populyarlıq sayğacını artır
   - `true` qaytar

### `Library.returnBook(Member m, Integer loanId, int currentDay)`

1. **Loan tapılmasa:** "Loan not found" çap et, dayan
2. `returned = true` et
3. **Gecikmə yoxlaması:** Əgər `currentDay > dueDay`:
   - `daysLate = currentDay - dueDay`
   - Cərimə hesabla: STUDENT → 0.3/gün, REGULAR → 0.5/gün, PREMIUM → 0.2/gün
   - `FineRecord` yarat, siyahıya əlavə et
   - Əgər üzvün gecikmə sayı 3-dən çoxdursa → qara siyahıya əlavə et, `BLACKLIST_WARNING` bildirişi yarat
4. **Rezervasiya yoxlaması:** Kitab boşalanda:
   - Rezervasiya növbəsi boş deyilsə → PriorityQueue-dan `poll()` et, həmin üzvə avtomatik yeni `Loan` yarat, `RESERVATION_READY` bildirişi göndər
   - Əks halda nüsxənin statusunu `AVAILABLE` et

### `Library.transferBook(Integer bookId, Integer fromBranchId, Integer toBranchId) → boolean`

1. Mənbə filialda kitab yoxdursa → "Transfer failed: book not found in source branch" çap et, `false` qaytar
2. Varsa: statusu `IN_TRANSIT` et, hədəf filiala köçür, sonra `AVAILABLE` et
3. Deque-yə qeyd əlavə et ("Book X: BranchA -> BranchB"); Deque ölçüsü 5-i keçərsə, ən köhnəni sil

### `Library.searchBooks(String keyword) → List<Book>`

1. Bütün kitabları Iterator ilə gəz
2. `title`, `author` və ya `genre`-də keyword varsa (case-insensitive) siyahıya əlavə et
3. Nəticə `List<Book>` qaytar

### `Library.generateBranchReport(Integer branchId)`

- Çap et: cəmi kitab sayı, unikal janr sayı, aktiv loan sayı, gecikmiş loan sayı

### `Library.getTopActiveMembers(int topN)`

- `Map<Member, Integer>`-i gəz, ən yüksək loan sayına malik `topN` üzvü tap (manual sıralama: `List` + `Collections.sort()` + `Comparator`)

### `Library.processNotifications(int currentDay)`

- Hər üzvün `Queue<Notification>`-nu gəz, mesajları console-a çap et, sonra queue-nu boşalt (`poll()` loop)

---

## 7. Fayl Strukturu

```
MembershipType.java (→ src.enums/MembershipTypeEnum.java)
CopyStatus.java (→ src.enums/CopyStatusEnum.java)
NotificationType.java (→ src.enums/NotificationTypeEnum.java)
Finable.java (→ src.interfaces/Finable.java)
Notifiable.java (→ src.interfaces/Notifiable.java)
LibraryUser.java (→ src.model/abstracts/LibraryUser.java)
Book.java (→ src.model/concrets/Book.java)
BookCopy.java (→ src.model/concrets/BookCopy.java)
Member.java (→ src.model/concrets/Member.java)
Branch.java (→ src.model/concrets/Branch.java)
Loan.java (→ src.model/concrets/Loan.java)
Reservation.java (→ src.model/concrets/Reservation.java)
Notification.java (→ src.model/concrets/Notification.java)
FineRecord.java (→ src.model/concrets/FineRecord.java)
Library.java (→ src.service/Library.java)
Main.java
```

## Terminal Output

Example execution of the program:

![Terminal Output](images/img1.png)