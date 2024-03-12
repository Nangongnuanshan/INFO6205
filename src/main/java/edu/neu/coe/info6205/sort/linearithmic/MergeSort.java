package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.Config;

import java.util.Arrays;

/**
 * Class MergeSort.
 *
 * @param <X> the underlying comparable type.
 */
public class MergeSort<X extends Comparable<X>> extends SortWithHelper<X> {

    public static final String DESCRIPTION = "MergeSort";

    /**
     * Constructor for MergeSort
     * <p>
     * NOTE this is used only by unit tests, using its own instrumented helper.
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public MergeSort(Helper<X> helper) {
        super(helper);
        insertionSort = new InsertionSort<>(helper);
    }

    /**
     * Constructor for MergeSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public MergeSort(int N, Config config) {
        super(DESCRIPTION + ":" + getConfigString(config), N, config);
        insertionSort = new InsertionSort<>(getHelper());
    }

    @Override
    public X[] sort(X[] xs, boolean makeCopy) {
        getHelper().init(xs.length);
        X[] result = makeCopy ? Arrays.copyOf(xs, xs.length) : xs;
        sort(result, 0, result.length);
        return result;
    }

    @Override
    public void sort(X[] a, int from, int to) {
        // CONSIDER don't copy but just allocate according to the xs/aux interchange optimization
        X[] aux = Arrays.copyOf(a, a.length);
        sort(a, aux, from, to);
    }

    private void sort(X[] a, X[] aux, int from, int to) {
        final Helper<X> helper = getHelper();
        Config config = helper.getConfig();
        boolean insurance = config.getBoolean(MERGESORT, INSURANCE);
        boolean noCopy = config.getBoolean(MERGESORT, NOCOPY);
        if (to <= from + helper.cutoff()) {
            insertionSort.sort(a, from, to);
            return;
        }

        if (insurance && isSorted(a, from, to, helper)) return;

        // TO BE IMPLEMENTED  : implement merge sort with insurance and no-copy optimizations
        int mid = from + (to - from) / 2;
        if (noCopy) {
            // Sort aux based on a's current state
            sort(aux, a, from, mid);
            sort(aux, a, mid, to);
            // Merge without copying: auxiliary roles of a and aux are switched
            merge(aux, a, from, mid, to);
            //if (!insurance) System.arraycopy(aux, from, a, from, to - from);

        } else {
            // Normal merge sort: sort halves of a, merge into aux, then copy back to a
            sort(a, aux, from, mid);
            sort(a, aux, mid, to);
            merge(a, aux, from, mid, to);
            // Copy back if not using the no-copy optimization
            System.arraycopy(aux, from, a, from, to - from);
        }
//throw new RuntimeException("implementation missing");
    }

    // CONSIDER combine with MergeSortBasic perhaps.
    private void merge(X[] sorted, X[] result, int from, int mid, int to) {
        final Helper<X> helper = getHelper();
        int i = from;
        int j = mid;
        for (int k = from; k < to; k++)
            if (i >= mid) helper.copy(sorted, j++, result, k);
            else if (j >= to) helper.copy(sorted, i++, result, k);
            else if (helper.less(sorted[j], sorted[i])) {
                helper.incrementFixes(mid - i);
                helper.copy(sorted, j++, result, k);
            } else helper.copy(sorted, i++, result, k);
    }

    public static final String MERGESORT = "mergesort";
    public static final String NOCOPY = "nocopy";
    public static final String INSURANCE = "insurance";

    private static String getConfigString(Config config) {
        StringBuilder stringBuilder = new StringBuilder();
        if (config.getBoolean(MERGESORT, INSURANCE)) stringBuilder.append(" with insurance comparison");
        if (config.getBoolean(MERGESORT, NOCOPY)) stringBuilder.append(" with no copy");
        return stringBuilder.toString();
    }

    private boolean isSorted(X[] a, int from, int to, Helper<X> helper) {
        for (int i = from + 1; i < to; i++) {
            if (helper.less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private final InsertionSort<X> insertionSort;
}
