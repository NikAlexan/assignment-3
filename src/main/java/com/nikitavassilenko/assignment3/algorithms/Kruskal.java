package com.nikitavassilenko.assignment3.algorithms;

import com.nikitavassilenko.assignment3.structures.Algorithm;
import com.nikitavassilenko.assignment3.structures.Input;
import com.nikitavassilenko.assignment3.structures.Output;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kruskal {
    public static Output.KruskalResult runKruskal(Input.InputGraph g) {
        Output.KruskalResult out = new Output.KruskalResult();
        Algorithm.Metrics metrics = new Algorithm.Metrics();

        // Индексация вершин по именам
        Map<String, Integer> idx = new HashMap<>();
        for (int i = 0; i < g.nodes.size(); i++) idx.put(g.nodes.get(i), i);

        // Список рёбер
        List<Algorithm.Edge> edges = new ArrayList<>();
        for (Input.InputEdge e : g.edges) {
            Integer u = idx.get(e.from);
            Integer v = idx.get(e.to);
            if (u == null || v == null) continue; // защита от некорректных ссылок
            edges.add(new Algorithm.Edge(u, v, e.weight, e.from, e.to));
        }

        // Сортируем рёбра по весу с подсчётом сравнений
        edges.sort((e1, e2) -> {
            metrics.comparisons++;
            return Integer.compare(e1.w, e2.w);
        });

        Algorithm.DSU dsu = new Algorithm.DSU(g.nodes.size(), metrics);

        long t0 = System.nanoTime();
        int taken = 0;
        int totalCost = 0;

        for (Algorithm.Edge e : edges) {
            if (dsu.union(e.u, e.v)) {
                out.mst_edges.add(new Output.OutputEdge(e.su, e.sv, e.w));
                totalCost += e.w;
                taken++;
                if (taken == g.nodes.size() - 1) break;
            }
        }
        long t1 = System.nanoTime();

        out.total_cost = totalCost;
        // время в мс с округлением до 2 знаков после запятой
        double ms = (t1 - t0) / 1_000_000.0;
        out.execution_time_ms = BigDecimal.valueOf(ms).setScale(2, RoundingMode.HALF_UP).doubleValue();
        out.operations_count = metrics.total();

        return out;
    }
}
