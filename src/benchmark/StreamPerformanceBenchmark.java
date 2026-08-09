package src.benchmark;

import src.record.Employee;
import src.seed.EmployeeSeed;
import src.seed.LargeEmployeeSeed;
import src.service.CompanyAnalyticsManager;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamPerformanceBenchmark {

    private static final int EMPLOYEE_COUNT = 500_000;

    private static final int WARMUP_ROUNDS = 2;
    private static final int MEASURED_ROUNDS = 3;

    private static final int SMALL_DATA_WARMUP_ROUNDS = 200;
    private static final int SMALL_DATA_MEASURED_ROUNDS = 500;

    private record Section(String name, Function<List<Employee>, String> task) {
    }

    private record Measurement(String section, double sequentialMs, double parallelMs) {

        double speedup() {
            return sequentialMs / parallelMs;
        }

        String verdict() {
            if (speedup() >= 1.15) {
                return "parallel beneficial";
            }

            if (speedup() <= 0.85) {
                return "parallel overhead";
            }

            return "no significant difference";
        }
    }

    public void execute() {

        System.out.println("stream() ve parallelStream() comparing | CPU core: " + Runtime.getRuntime().availableProcessors());
        System.out.println();

        this.benchmark(LargeEmployeeSeed.seed(EMPLOYEE_COUNT), EMPLOYEE_COUNT + " employee", WARMUP_ROUNDS, MEASURED_ROUNDS);

        System.out.println();

        this.benchmark(EmployeeSeed.seed(), "30 employee (small data)", SMALL_DATA_WARMUP_ROUNDS, SMALL_DATA_MEASURED_ROUNDS);

        System.out.println();
    }

    private void benchmark(List<Employee> employees, String title, int warmupRounds, int measuredRounds) {

        CompanyAnalyticsManager sequentialManager = new CompanyAnalyticsManager(false);
        CompanyAnalyticsManager parallelManager = new CompanyAnalyticsManager(true);

        List<Section> sequentialSections = sections(sequentialManager);
        List<Section> parallelSections = sections(parallelManager);

        List<Measurement> measurements = IntStream.range(0, sequentialSections.size())
                .mapToObj(index -> new Measurement(
                        sequentialSections.get(index).name(),
                        measure(sequentialSections.get(index).task(), employees, warmupRounds, measuredRounds),
                        measure(parallelSections.get(index).task(), employees, warmupRounds, measuredRounds)
                ))
                .toList();

        System.out.println(title + " | warmup: " + warmupRounds + " | measured: " + measuredRounds);
        System.out.println(report(measurements));
    }

    private List<Section> sections(CompanyAnalyticsManager manager) {
        return List.of(
                new Section("A - Umumi statistika", manager::generateGeneralReport),
                new Section("B - Departament analizi", manager::generateDepartmentAnalytics),
                new Section("C - Skill analizi", manager::generateSkillAnalytics),
                new Section("D - Staj analizi", manager::generateAgeExperienceReport),
                new Section("E - Rehberlik strukturu", manager::generateManagementStructureReport),
                new Section("F - teeing (min/max)", manager::generateMaxAndMinSalaryReport),
                new Section("G - Custom Collector", manager::generateGroupingEmployeesByDepartmentV2),
                new Section("TAM HESABAT", manager::generateReport)
        );
    }

    private double measure(Function<List<Employee>, String> task, List<Employee> employees,
                           int warmupRounds, int measuredRounds) {

        IntStream.range(0, warmupRounds).forEach(round -> task.apply(employees));

        return IntStream.range(0, measuredRounds)
                .mapToLong(round -> {
                    long start = System.nanoTime();
                    task.apply(employees);
                    return System.nanoTime() - start;
                })
                .min()
                .orElse(0L) / 1_000_000.0;
    }

    private String report(List<Measurement> measurements) {

        String header = "%-28s | %14s | %14s | %8s | %s".formatted(
                "Section", "stream() ms", "parallel() ms", "speedup", "result"
        );

        String rows = measurements.stream()
                .map(measurement -> "%-28s | %14.3f | %14.3f | %7.2fx | %s".formatted(
                        measurement.section(),
                        measurement.sequentialMs(),
                        measurement.parallelMs(),
                        measurement.speedup(),
                        measurement.verdict()
                ))
                .collect(Collectors.joining("\n"));

        return header + "\n" + "-".repeat(header.length()) + "\n" + rows;
    }
}
