package edu.neu.coe.info6205.threesum;

import java.util.Arrays;
import edu.neu.coe.info6205.util.Stopwatch;

public class PerformanceTest {


    public static long testThreeSumQuadratic(int[] array) {
        try (Stopwatch stopwatch = new Stopwatch()) {
            ThreeSumQuadratic threeSumQuadratic = new ThreeSumQuadratic(array);
            Triple[] result = threeSumQuadratic.getTriples();
            return stopwatch.lap();
        }
    }

    public static long testThreeSumQuadraticWithCalipers(int[] array) {
        try (Stopwatch stopwatch = new Stopwatch()) {
            ThreeSumQuadraticWithCalipers threeSumQuadraticWithCalipers = new ThreeSumQuadraticWithCalipers(array);
            Triple[] result = threeSumQuadraticWithCalipers.getTriples();
            return stopwatch.lap();
        }
    }

    public static long testThreeSumQuadrithmic(int[] array) {
        try (Stopwatch stopwatch = new Stopwatch()) {
            ThreeSumQuadrithmic threeSumQuadrithmic = new ThreeSumQuadrithmic(array);
            Triple[] result = threeSumQuadrithmic.getTriples();
            return stopwatch.lap();
        }
    }

    public static long testThreeSumCubic(int[] array) {
        try (Stopwatch stopwatch = new Stopwatch()) {
            ThreeSumCubic threeSumCubic = new ThreeSumCubic(array);
            Triple[] result = threeSumCubic.getTriples();
            return stopwatch.lap();
        }
    }

    public static void main(String[] args) {
        int[] sizes = {100, 200, 400, 800, 1600};
        for (int size : sizes) {
            int[] array = generateRandomArray(size);

            long timeCubic = testThreeSumCubic(array);
            long timeQuadrithmic = testThreeSumQuadrithmic(array);
            long timeQuadratic = testThreeSumQuadratic(array);
            long timeQuadraticWithCalipers = testThreeSumQuadraticWithCalipers(array);


            System.out.println("N = " + size + ": Cubic = " + timeCubic + "ms, Quadrithmic  = " + timeQuadrithmic  + "ms, Quadratic = " + timeQuadratic + "ms, QuadraticWithCalipers = " + timeQuadraticWithCalipers + "ms");
        }
    }

    private static int[] generateRandomArray(int size) {
        // 生成随机数组的逻辑
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 2000) - 1000; // 随机数范围
        }
        Arrays.sort(array);
        return array;
    }
}

