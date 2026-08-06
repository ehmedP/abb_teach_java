import src.service.WSManager;

void main() {


    /**
     * Tələb: Bir sifarişin emalı zamanı 2-dən çox exception ardıcıl baş verərsə, sonuncusu tutulub
     * CriticalSystemFailureException kimi yenidən atılmalı (cause saxlanılmaqla).
     */

    /**
     * Tələb: processOrder() çağırılarkən həm ayrıca catch, həm də multi-catch (InvalidOrderException
     * | WarehouseConnectionException) nümunələri göstərilməli.
     */

    /**
     *
     * Bonus tapşırıqlar
     * 6. Optional&lt;Product&gt; istifadə edərək məhsul axtarışını null-safe edin (findProduct(String id):
     * Optional&lt;Product&gt;).
     * 7. Comparator.comparing().thenComparing() ilə tamamlanmış sifarişləri həm statusa, həm tarixə
     * görə sıralayın.
     * 8. Bütün log-u fayla (try-with-resources + FileWriter) yazan metod əlavə edin — bu da resurs
     * idarəetməsini gücləndirir.
     * 9. PriorityQueue-dan istifadə edərək &quot;stoku 5-dən az olan məhsullar&quot; siyahısını çap edən metod
     * yazın.
     * 10. Öz @FunctionalInterface-inizi yaradıb, checked exception atan bir lambda ilə sınayın.
     */

    WSManager.execute();

}
