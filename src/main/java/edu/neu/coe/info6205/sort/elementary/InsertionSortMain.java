package edu.neu.coe.info6205.sort.elementary;
import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class InsertionSortMain {
    public static void main(String[] args) {
        // Define the number of tests and the factor by which the array size will increase
        int m = 5; // Number of tests for each n
        int factor = 2; // Factor for doubling the size of n

        for (int n = 100; n <= 100 * Math.pow(factor, 4); n *= factor) { // Test for at least five values of n
            benchmarkSort(n, m, "Random Array");
            benchmarkSort(n, m, "Ordered Array");
            benchmarkSort(n, m, "Partially-Ordered Array");
            benchmarkSort(n, m, "Reverse-Ordered Array");
            System.out.println();
        }
    }

    private static void benchmarkSort(int n, int m, String arrayType) {
        Integer[] array = new Integer[n];
        Random random = new Random();

        // Initialize array based on the type
        switch (arrayType) {
            case "Random Array":
                for (int i = 0; i < n; i++) array[i] = random.nextInt(n);
                break;
            case "Ordered Array":
                for (int i = 0; i < n; i++) array[i] = i;
                break;
            case "Partially-Ordered Array":
                for (int i = 0; i < n; i++) array[i] = (i % 2 == 0) ? i : random.nextInt(n);
                break;
            case "Reverse-Ordered Array":
                for (int i = 0; i < n; i++) array[i] = n - i;
                break;
        }

        // Create a benchmark to measure sorting time
        Benchmark_Timer<Integer[]> benchmark = new Benchmark_Timer<>(
                "Benchmarking for " + arrayType,
                null,
                arr -> new InsertionSort<Integer>().sort(arr),
                null
        );

        // Run the benchmark
        double time = benchmark.run(array, m);

        System.out.println("Array Type: " + arrayType + ", n: " + n + ", Time: " + time + " ms");
    }
}
