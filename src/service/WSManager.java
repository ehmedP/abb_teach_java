package src.service;

import src.exception.CriticalSystemFailureException;
import src.exception.InvalidOrderException;
import src.exception.ProductOutOfStockException;
import src.exception.WarehouseConnectionException;
import src.model.Order;
import src.model.OrderResult;
import src.seed.SeedData;

import java.util.ArrayList;
import java.util.List;

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

        for (Order order : warehouseService.getOrders()) {

            try {

                OrderResult result = warehouseService.processOrder(order);
                results.add(result);

            } catch (CriticalSystemFailureException e) {

                System.out.println("Critical system failure: " + e.getMessage());
                System.out.println("Root cause: " + e.getCause().getMessage());

            } catch (InvalidOrderException | WarehouseConnectionException e) {

                System.out.println("Order processing failed: " + e.getMessage());

            } catch (ProductOutOfStockException e) {

                System.out.println("Order could not be completed due to insufficient stock: " + e.getMessage());

            } finally {

                System.out.printf("Order %s for customer %s has been processed.%n",
                        order.getId(),
                        order.getCustomerId()
                );

            }

        }

    }

}
