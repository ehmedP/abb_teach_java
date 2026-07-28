package src.service;

import src.model.abstracts.Employee;
import src.model.concret.Developer;
import src.model.concret.Intern;
import src.model.concret.Manager;
import src.seed.CompanySeeder;
import src.seed.SkillSeeder;
import src.util.GeneralHelper;
import src.util.Pair;

import java.util.*;

public class EmployeeEMS {

    public void execute() {

        Company<Employee> company = new Company<>();
        CompanySeeder.seed(company);

        GeneralHelper.printSection("ALL EMPLOYEES ADDED");

        for (Employee e : company.getAll()) {
            System.out.println(e);
        }

        GeneralHelper.printSection("SORTED BY SALARY (Comparable — Collections.sort)");

        List<Employee> bySalary = new ArrayList<>(company.getEmployees());
        Collections.sort(bySalary);

        for (int index = 0; index < bySalary.size(); index++) {
            System.out.println((index + 1) + ". " + bySalary.get(index).getName() + " — $" + bySalary.get(index).getSalary());
        }

        GeneralHelper.printSection("SORTED BY NAME (Comparator)");

        List<Employee> byName = new ArrayList<>(company.getEmployees());
        Comparator<Employee> byNameComp = Comparator.comparing(Employee::getName);
        Collections.sort(byName, byNameComp);

        for (Employee e : byName) {
            System.out.println(e.getName() + " — $" + e.getSalary());
        }

        GeneralHelper.printSection("SORTED BY START DATE (Comparator)");

        List<Employee> byDate = new ArrayList<>(company.getEmployees());
        Comparator<Employee> byDateComp = Comparator.comparing(Employee::getStartDate);
        byDate.sort(byDateComp);

        for (Employee e : byDate) {
            System.out.println(e.getName() + " — " + e.getStartDate());
        }

        GeneralHelper.printSection("DEPARTMENT GROUPING — HashMap (unordered)");

        Map<String, List<Employee>> groupedByDeptHash = new HashMap<>(company.groupByDepartment());

        for (String department : groupedByDeptHash.keySet()) {
            System.out.print(department + ": ");

            List<Employee> emps = groupedByDeptHash.get(department);

            for (int index = 0; index < emps.size(); index++) {
                System.out.print(emps.get(index).getName());
                if (index < emps.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        GeneralHelper.printSection("DEPARTMENT GROUPING — TreeMap (sorted by key)");

        Map<String, List<Employee>> groupedByDeptTree = new TreeMap<>(company.groupByDepartment());

        for (String dept : groupedByDeptTree.keySet()) {
            System.out.print(dept + ": ");

            List<Employee> emps = groupedByDeptTree.get(dept);

            for (int index = 0; index < emps.size(); index++) {
                System.out.print(emps.get(index).getName());

                if (index < emps.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        GeneralHelper.printSection("UNIQUE SKILLS — TreeSet (sorted alphabetically)");

        Set<String> skills = new TreeSet<>();
        SkillSeeder.seed(skills);

        System.out.println(String.join(", ", skills));

        GeneralHelper.printSection("GENERIC WILDCARD METHOD — printEmployees with List<Developer>");
        GeneralHelper.printEmployees(company.getDevelopers());

        GeneralHelper.printSection("GENERIC WILDCARD METHOD — printEmployees with List<Manager>");
        GeneralHelper.printEmployees(company.getManagers());

        GeneralHelper.printSection("GENERIC WILDCARD METHOD — printEmployees with List<Intern>");
        GeneralHelper.printEmployees(company.getInterns());

        List<Employee> sortedBySalary = new ArrayList<>(company.getEmployees());
        Collections.sort(sortedBySalary);

        GeneralHelper.printSection("Collections.max — Highest salary");
        Employee max = Collections.max(sortedBySalary);

        System.out.println(max.getName() + " — $" + max.getSalary());

        GeneralHelper.printSection("Collections.min — Lowest salary");
        Employee min = Collections.min(sortedBySalary);
        System.out.println(min.getName() + " — $" + min.getSalary());

        GeneralHelper.printSection("Collections.shuffle — Random order");
        List<Employee> shuffleList = new ArrayList<>(sortedBySalary);

        Collections.shuffle(shuffleList);

        for (Employee e : shuffleList) {
            System.out.println(e.getName() + " — $" + e.getSalary());
        }

        GeneralHelper.printSection("Collections.unmodifiableList — Read-only list");
        List<Employee> unmod = company.getAll();
        System.out.println("unmodifiableList size: " + unmod.size() + " employees (read-only)");

        GeneralHelper.printSection("Collections.unmodifiableMap — Read-only map");
        Map<String, List<Employee>> unmodMap = Collections.unmodifiableMap(groupedByDeptHash);

        System.out.println("unmodifiableMap size: " + unmodMap.size() + " departments (read-only)");

        GeneralHelper.printSection("BONUS: PriorityQueue — Top 3 highest paid employees");
        PriorityQueue<Employee> pq = new PriorityQueue<>(
                Comparator.comparingDouble(Employee::getSalary).reversed()
        );

        pq.addAll(company.getEmployees());

        for (int index = 0; index < 3 && !pq.isEmpty(); index++) {
            Employee e = pq.poll();
            System.out.println((index + 1) + ". " + e.getName() + " — $" + e.getSalary());
        }

        GeneralHelper.printSection("BONUS: Pair<K,V> — Manager department-employee pairs");
        List<Pair<String, String>> pairs = new ArrayList<>();

        for (Manager m : company.getManagers()) {
            pairs.add(new Pair<>(m.getDepartment(), m.getName()));
        }

        for (Pair<String, String> p : pairs) {
            System.out.println("Department: " + p.getKey() + " | Manager: " + p.getValue());
        }
    }
}
