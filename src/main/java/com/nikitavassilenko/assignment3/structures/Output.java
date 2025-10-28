package com.nikitavassilenko.assignment3.structures;

import java.util.ArrayList;
import java.util.List;

public class Output {
    public static class OutputFile {
        public List<GraphResult> results = new ArrayList<>();
    }

    public static class GraphResult {
        public int graph_id;
        public InputStats input_stats;
        public KruskalResult kruskal;
    }

    public static class InputStats {
        public int vertices;
        public int edges;
    }

    public static class KruskalResult {
        public List<OutputEdge> mst_edges = new ArrayList<>();
        public int total_cost;
        public long operations_count;
        public double execution_time_ms;
    }

    public static class OutputEdge {
        public String from;
        public String to;
        public int weight;

        public OutputEdge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}
