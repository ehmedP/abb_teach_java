package src.seed;

import src.model.Customer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class CustomerSeeder {

    public static final String ELVIN = "Elvin Məmmədov";
    public static final String NIGAR = "Nigar Hüseynova";
    public static final String RASHAD = "Rəşad Quliyev";
    public static final String AYSEL = "Aysel Əliyeva";
    public static final String KAMRAN = "Kamran Səfərov";

    private CustomerSeeder() {
    }

    public static Map<Integer, Customer> seed() {

        List<Customer> seeded = List.of(
                new Customer(ELVIN),
                new Customer(NIGAR),
                new Customer(RASHAD),
                new Customer(AYSEL),
                new Customer(KAMRAN)
        );

        Map<Integer, Customer> customers = new LinkedHashMap<>();

        for (Customer customer : seeded) {
            customers.put(customer.getId(), customer);
        }

        return customers;
    }

    public static Integer idOf(Map<Integer, Customer> customers, String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().equals(name))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Seed müştərisi tapılmadı: " + name));
    }
}
