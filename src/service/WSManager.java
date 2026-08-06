package src.service;

import src.seed.SeedData;

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

    }

}
