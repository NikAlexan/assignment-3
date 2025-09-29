package com.nikitavassilenko.assignment2.cli;

import com.nikitavassilenko.assignment2.algorithms.SelectionSort;
import com.nikitavassilenko.assignment2.metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int size = 1000; // по умолчанию
        String inputType = "random"; // random, sorted, reversed, nearly
        String outputFile = "benchmark-results.csv";

        if (args.length >= 1) {
            size = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            inputType = args[1];
        }
        if (args.length >= 3) {
            outputFile = args[2];
        }

        int[] arr = generateArray(size, inputType);

        PerformanceTracker tracker = new PerformanceTracker();

        // замер памяти до
        long beforeMemory = getUsedMemory();

        SelectionSort.sort(arr, tracker);

        // замер памяти после
        long afterMemory = getUsedMemory();
        long memoryUsed = afterMemory - beforeMemory;

        // вывод на экран
        System.out.println("Input: " + inputType + " | n = " + size);
        System.out.println("Время (мс): " + tracker.getElapsedTimeMillis());
        System.out.println("Сравнений: " + tracker.getComparisons());
        System.out.println("Обменов: " + tracker.getSwaps());
        System.out.println("Доступов к массиву: " + tracker.getArrayAccesses());
        System.out.println("Память (байт): " + memoryUsed);

        // запись в CSV
        try (FileWriter writer = new FileWriter(outputFile, true)) {
            writer.write(size + "," + inputType + "," +
                    tracker.getComparisons() + "," +
                    tracker.getSwaps() + "," +
                    tracker.getArrayAccesses() + "," +
                    tracker.getElapsedTimeMillis() + "," +
                    memoryUsed + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка записи CSV: " + e.getMessage());
        }
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
                // перемешиваем 10% элементов
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

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // вызов GC для точности
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
