package src.service;

import src.exception.CriticalSystemFailureException;
import src.functional.OrderItemFormatter;
import src.exception.InvalidOrderException;
import src.exception.ProductOutOfStockException;
import src.exception.WarehouseConnectionException;
import src.model.Order;
import src.model.OrderResult;
import src.seed.SeedData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WSManager {

    public static void execute() {

        SeedData seedData = SeedData.create();

        WarehouseService warehouseService = new WarehouseService(
                seedData.getProducts(),
                seedData.getOrders(),
                seedData.getOrderHistory(),
                seedData.getLogs(),
                seedData.getLowStockProducts(),
                seedData.getFailedOrdersByCustomer()
        );

        List<OrderResult> results = new ArrayList<>();

        header("SİFARİŞLƏRİN EMALI");

        for (Order order : warehouseService.getOrders()) {

            System.out.printf("%n#%d  %s  %s%n",
                    order.getId(),
                    seedData.customerName(order.getCustomerId()),
                    describeItems(seedData, order));

            try {

                results.add(warehouseService.processOrder(order));
                System.out.println("Uğurlu");

            } catch (CriticalSystemFailureException e) {

                System.out.println("KRİTİK XƏTA : " + e.getMessage());
                System.out.println("Əsl səbəb   : " + e.getCause().getMessage());

            } catch (InvalidOrderException | WarehouseConnectionException e) {

                System.out.println("Xəta [" + e.getErrorCode() + "] : " + e.getMessage());

            } catch (ProductOutOfStockException e) {

                System.out.println("Stok xətası [" + e.getErrorCode() + "] : " + e.getMessage());

            } finally {

                System.out.println("Status: " + order.getStatus());
            }
        }

        summary(seedData, warehouseService, results);
    }

    private static void summary(SeedData seedData, WarehouseService warehouseService, List<OrderResult> results) {

        header("NƏTİCƏ");

        System.out.println("Uğurlu sifariş : " + results.size() + " / " + warehouseService.getOrders().size());

        System.out.println("\nMüştərilər üzrə uğursuz sifariş sayı:");

        warehouseService.getFailedProductsByCustomer().forEach((customerId, count) ->
                System.out.println("    " + seedData.customerName(customerId) + " : " + count));

        System.out.println("\nStoku 5-dən az olan məhsullar:");

        warehouseService.printLowStockProducts(5);

        System.out.println("\nLog qeydlərinin sayı : " + warehouseService.getLogs().size());
    }

    private static void header(String title) {
        System.out.println("\n===== " + title + " =====");
    }

    private static String describeItems(SeedData seedData, Order order) {

        if (order.getItems().isEmpty()) {
            return "{boş}";
        }

        OrderItemFormatter formatter = OrderItemFormatter.withProductNames(seedData.getProducts());

        return order.getItems().entrySet().stream()
                .map(formatter::formatItem)
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
