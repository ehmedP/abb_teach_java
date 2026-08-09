package src.support;

import src.record.Employee;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Texniki olaraq oz collectorumdur bu (mence)
 */
public class DepartmentEmployeeCollector implements Collector<Employee, Map<String, List<String>>, String> {


    /**
     * A function that creates and returns a new mutable result container.
     *
     * @return a function which returns a new, mutable result container
     */
    @Override
    public Supplier<Map<String, List<String>>> supplier() {
        return TreeMap::new;
    }

    /**
     * A function that folds a value into a mutable result container.
     *
     * @return a function which folds a value into a mutable result container
     */
    @Override
    public BiConsumer<Map<String, List<String>>, Employee> accumulator() {
        return (map, employee) -> {
            map.computeIfAbsent(employee.department(), k -> new ArrayList<>()).add(employee.name());
        };
    }

    /**
     * A function that accepts two partial results and merges them.  The
     * combiner function may fold state from one argument into the other and
     * return that, or may return a new result container.
     *
     * @return a function which combines two partial results into a combined
     * result
     */
    @Override
    public BinaryOperator<Map<String, List<String>>> combiner() {
        return (left, right) -> {
            right.forEach((department, names) ->
                    left.merge(department, names, (leftNames, rightNames) -> {
                        leftNames.addAll(rightNames);
                        return leftNames;
                    })
            );

            return left;
        };
    }

    /**
     * Perform the final transformation from the intermediate accumulation type
     * {@code A} to the final result type {@code R}.
     *
     * <p>If the characteristic {@code IDENTITY_FINISH} is
     * set, this function may be presumed to be an identity transform with an
     * unchecked cast from {@code A} to {@code R}.
     *
     * @return a function which transforms the intermediate result to the final
     * result
     */
    @Override
    public Function<Map<String, List<String>>, String> finisher() {
        return map -> map.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + String.join(", ", entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns a {@code Set} of {@code Collector.Characteristics} indicating
     * the characteristics of this Collector.  This set should be immutable.
     *
     * @return an immutable set of collector characteristics
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
