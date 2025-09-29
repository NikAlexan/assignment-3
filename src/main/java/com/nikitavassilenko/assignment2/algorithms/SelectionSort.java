package com.nikitavassilenko.assignment2.algorithms;

import com.nikitavassilenko.assignment2.metrics.PerformanceTracker;

public class SelectionSort {

    /**
     * Сортировка выбором с ранним завершением.
     *
     * @param arr     массив для сортировки
     * @param tracker сборщик метрик
     */
    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null || arr.length <= 1) return;

        tracker.startTimer();

        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            swapped = false;

            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses(2); // arr[j] и arr[minIndex]

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Меняем местами, если нашли минимум
            if (minIndex != i) {
                swap(arr, i, minIndex, tracker);
                swapped = true;
            }

            // Раннее завершение: если ни одной перестановки
            if (!swapped) break;
        }

        tracker.stopTimer();
    }

    private static void swap(int[] arr, int i, int j, PerformanceTracker tracker) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        tracker.incrementSwaps();
        tracker.incrementArrayAccesses(4); // чтение + запись
    }
}
