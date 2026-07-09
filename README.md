# Smart Transport Management System

## Texnologiya

- **Java version:** JDK 24

## Təsvir

Bir şəhərdə müxtəlif nəqliyyat vasitələri (məsələn: avtobus, taksi, velosiped, scooter və s.) ilə hərəkəti idarə edən bir Transport Management System qurmaq lazımdır. Bu sistem:

- Müxtəlif nəqliyyat növlərini təmsil etməlidir.
- Hər bir nəqliyyat növünün öz xüsusiyyətləri və davranışı olmalıdır.
- Nəqliyyat növləri müxtəlif tariflərə, sürət limitlərinə, və tipik istifadə sahələrinə malik olmalıdır.
- İstifadəçi hansısa bir nəqliyyat növünü seçib onunla səyahət etmək istəyəndə sistem təxmini məsafəyə əsasən qiymət və vaxt hesablaya bilməlidir.
- Tariflər Enum vasitəsilə təyin olunacaq.
- Bütün nəqliyyat vasitələri ortaq bir interface və ya abstract class-dan törəməlidir.
- Polimorfizm vasitəsilə müxtəlif nəqliyyat növləri eyni metodlara fərqli şəkildə reaksiya verməlidirlər.
- Method Overloading və Overriding istifadə olunmalıdır.
- Encapsulation ilə obyektin daxili məlumatları qorunmalıdır.

## Texniki Tələblər

1. `enum TransportType { BUS, TAXI, BICYCLE, SCOOTER }`
2. `interface Transport`
3. `class Bus`, `class Taxi`, `class Bicycle`, `class Scooter` – hamısı Transport-dan implements etməlidir. Classlar daxilində `private final double ratePerKm` və
4. `private final double speed` olmalıdır və dəyərləri fərqli olmalıdır.
5. Hər sinif `calculateFare(double distance)` və `calculateTime(double distance)` və `String getTransportInfo()` metodlarını override etməlidir. Method daxilində `ratePerKm` və `speed` istifadə olunmalıdır.
6. Əlavə olaraq `calculateFare(double distance, int passengers)` kimi overload edilmiş method da olsun.
7. Bütün methodlar birbaşa Transport daxilində olmalıdır, lakin hər bir methodun daxilindəki məntiqi hər bir child class daxilində fərqli olmalıdır.
8. `TransportManager` sinfi – istifadəçidən transport növünü, məsafəni və sərnişin sayını qəbul edir, nəticəni çap edir.
9. `TransportManager` sinifi daxilində isə `static Transport getTransport(TransportType type)` methodu olmalıdır, switch vasitəsilə hər bir enuma qarşılıq gələn class qaytarmalıdır.

## Nümunə İstifadə

```java
public static void main(String[] args) {

    // Məsələn: istifadəçi TAXI seçir
    TransportType userChoice = TransportType.TAXI;
    double distance = 10.0;
    int passengers = 2;

    Transport transport = getTransport(userChoice);
    System.out.println("Transport Info: " + transport.getTransportInfo());
    System.out.println("Fare for " + passengers + " passenger(s): " +
            transport.calculateFare(distance, passengers));
    System.out.println("Estimated time: " +
            transport.calculateTime(distance) + " hours");
}
```

## Console Output

![Console Output](images/img.png)
