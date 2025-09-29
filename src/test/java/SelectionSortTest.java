import com.nikitavassilenko.assignment2.algorithms.SelectionSort;
import com.nikitavassilenko.assignment2.metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SelectionSortTest {
    @Test
    void testEmptyArray() {
        int[] arr = {};
        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testDuplicates() {
        int[] arr = {5, 3, 5, 2, 2};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(expected, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = arr.clone();

        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(expected, arr);
    }

    @Test
    void testReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        SelectionSort.sort(arr, new PerformanceTracker());
        assertArrayEquals(expected, arr);
    }
}
