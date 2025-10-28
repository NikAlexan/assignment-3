package com.nikitavassilenko.assignment3.structures;

public class Algorithm {
    public static class Edge {
        public int u;
        public int v;
        public int w;
        public String su;
        public String sv; // исходные имена вершин

        public Edge(int u, int v, int w, String su, String sv) {
            this.u = u; this.v = v; this.w = w; this.su = su; this.sv = sv;
        }
    }

    public static class DSU {
        int[] parent, rank;
        Metrics metrics;

        public DSU(int n, Metrics metrics) {
            parent = new int[n];
            rank = new int[n];
            this.metrics = metrics;
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            metrics.findCalls++;
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public boolean union(int a, int b) {
            metrics.unionCalls++;
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }

    public static class Metrics {
        public long comparisons = 0;
        long findCalls = 0;
        long unionCalls = 0;

        public long total() {
            return comparisons + findCalls + unionCalls;
        }
    }
}
