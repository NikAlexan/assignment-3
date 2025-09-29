package com.nikitavassilenko.assignment2.cli;

import com.nikitavassilenko.assignment2.algorithms.SelectionSort;
import com.nikitavassilenko.assignment2.metrics.PerformanceTracker;

import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int size = 1000; // по умолчанию
        String inputType = "random"; // random, sorted, reversed, nearly

        if (args.length >= 1) {
            size = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            inputType = args[1];
        }

        int[] arr = generateArray(size, inputType);

        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSort.sort(arr, tracker);

        System.out.println("Input: " + inputType + " | n = " + size);
        System.out.println("Время (мс): " + tracker.getElapsedTimeMillis());
        System.out.println("Сравнений: " + tracker.getComparisons());
        System.out.println("Обменов: " + tracker.getSwaps());
        System.out.println("Доступов к массиву: " + tracker.getArrayAccesses());
    }

    private static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random();

        switch (type.toLowerCase()) {
            case "sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "nearly":
                for (int i = 0; i < size; i++) arr[i] = i;
                // немного перемешаем
                for (int i = 0; i < size / 10; i++) {
                    int a = rand.nextInt(size);
                    int b = rand.nextInt(size);
                    int tmp = arr[a];
                    arr[a] = arr[b];
                    arr[b] = tmp;
                }
                break;
            default: // random
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(size * 10);
        }
        return arr;
    }
}
