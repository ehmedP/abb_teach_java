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

    WSManager.execute();

}
