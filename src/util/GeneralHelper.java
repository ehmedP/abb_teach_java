package src.util;

import src.model.abstracts.Employee;

import java.util.List;

public class GeneralHelper {

    public static <T extends Employee> void printEmployees(List<? extends T> employees) {
        for (T employee : employees) {
            System.out.println(employee);
        }
    }

}
