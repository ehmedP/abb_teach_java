# Lesson Task — Library Management (OOP)

> **Qeyd:** Həll iki faylda yerləşir — [`Book.java`](./Book.java) (class) və
> [`Main.java`](./Main.java) (icra).

## How to run

```bash
java Main.java
```

## Console output

Proqramın işləyən nümunəsi:

![Console output](./images/img.png)

---

## Task

`Book` adlı bir class yarat. Bu class aşağıdakı xüsusiyyətlərə malik olsun.

### 🔹 Fields (özəlliklər)

| Field | Tip |
|-------|-----|
| `title` | `String` |
| `author` | `String` |
| `isbn` | `String` |
| `totalCopies` | `int` |
| `availableCopies` | `int` |

### 🔹 Access Modifiers

- `title`, `author`, `isbn` → **private**
- `totalCopies`, `availableCopies` → **protected**

### 🔹 Constructor

Kitabın başlığı, müəllifi və ISBN kodu verildikdə avtomatik olaraq
`totalCopies = 1` və `availableCopies = 1` olmalıdır.

### 🔹 Initializer block

Hər kitab yaradıldıqda `System.out.println("New book created!")` mesajı
göstərilsin (initializer block ilə).

### 🔹 Methods

- **`public void borrowBook()`** → Kitabı götürmək üçün. Əgər
  `availableCopies > 0` isə, `availableCopies--` və
  `System.out.println("Book borrowed successfully.");`
  Əks halda `System.out.println("Sorry, no copies available.");`

- **`public void returnBook()`** → Kitabı qaytarmaq üçün. Əgər
  `availableCopies < totalCopies` isə, `availableCopies++` və
  `System.out.println("Book returned successfully.");`
  Əks halda `System.out.println("All copies are already in the library.");`

- **`public void printInfo()`** → Kitab haqqında məlumatları çap etsin.

### 🔹 Static Method

- **`public static void libraryRules()`** →
  `System.out.println("Max 3 books can be borrowed per person.")`

### 🔹 Final Method

- **`public final void bookType()`** →
  `System.out.println("This is a regular book.")`

---

## Example

```java
public class Main {
    public static void main(String[] args) {
        Book b1 = new Book("Clean Code", "Robert C. Martin", "1234567890");

        b1.printInfo();
        b1.borrowBook();
        b1.returnBook();
        Book.libraryRules();
        b1.bookType();
    }
}
```

### Result

```
New book created!
Book Info:
Title: Clean Code
Author: Robert C. Martin
ISBN: 1234567890
Total Copies: 1
Available Copies: 1
Book borrowed successfully.
Book returned successfully.
Max 3 books can be borrowed per person.
This is a regular book.
```
