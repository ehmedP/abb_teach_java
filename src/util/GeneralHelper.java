package src.util;

import src.model.abstracts.Employee;

import java.util.List;
import java.util.Map;

public class GeneralHelper {

    public static <T extends Employee> void printEmployees(List<? extends T> employees) {
        for (T employee : employees) {
            System.out.println(employee);
        }
    }

    public static void printSection(String title) {
        System.out.println("\n" + "========================================");
        System.out.println(title);
        System.out.println("========================================");
    }

    public static void printDepartmentMap(Map<String, List<Employee>> departmentMap) {

        for (Map.Entry<String, List<Employee>> entry : departmentMap.entrySet()) {

            System.out.print(entry.getKey() + ": ");

            List<Employee> emps = entry.getValue();

            for (int index = 0; index < emps.size(); index++) {
                System.out.print(emps.get(index).getName());

                if (index < emps.size() - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println();
        }
    }
}
