package com.nikitavassilenko.assignment3.bench;

import com.nikitavassilenko.assignment3.algorithms.Kruskal;
import com.nikitavassilenko.assignment3.structures.Input;
import com.nikitavassilenko.assignment3.structures.Output;
import org.openjdk.jmh.annotations.*;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class KruskalBenchmark {

    @State(Scope.Benchmark)
    public static class RealInputState {
        Input.InputGraph g1;
        Input.InputGraph g2;

        @Setup(Level.Trial)
        public void setup() throws Exception {
            try (InputStream is = KruskalBenchmark.class.getResourceAsStream("/input_example.json")) {
                ObjectMapper om = new ObjectMapper();
                Input.InputFile in = om.readValue(is, Input.InputFile.class);
                g1 = in.graphs.stream().filter(g -> g.id == 1).findFirst().orElseThrow();
                g2 = in.graphs.stream().filter(g -> g.id == 2).findFirst().orElseThrow();
            }
        }
    }

    @State(Scope.Thread)
    public static class SyntheticState {
        @Param({"50","100","200"})
        int n;
        Input.InputGraph g;

        @Setup(Level.Iteration)
        public void setup() {
            Input.InputGraph gg = new Input.InputGraph();
            gg.id = 0;
            gg.nodes = new ArrayList<>();
            for (int i = 0; i < n; i++) gg.nodes.add("V" + i);
            gg.edges = new ArrayList<>();
            Random rnd = new Random(42);
            // создаём связность: линейный остов
            for (int i = 1; i < n; i++) {
                Input.InputEdge e = new Input.InputEdge();
                e.from = "V" + (i - 1);
                e.to = "V" + i;
                e.weight = 1 + rnd.nextInt(1000);
                gg.edges.add(e);
            }
            // добавим 2n случайных рёбер
            for (int k = 0; k < 2 * n; k++) {
                int u = rnd.nextInt(n), v = rnd.nextInt(n);
                if (u == v) continue;
                Input.InputEdge e = new Input.InputEdge();
                e.from = "V" + u;
                e.to = "V" + v;
                e.weight = 1 + rnd.nextInt(1000);
                gg.edges.add(e);
            }
            g = gg;
        }
    }

    @Benchmark
    public int kruskal_real_graph1(RealInputState s) {
        Output.KruskalResult r = Kruskal.runKruskal(s.g1);
        return r.total_cost;
    }

    @Benchmark
    public int kruskal_real_graph2(RealInputState s) {
        Output.KruskalResult r = Kruskal.runKruskal(s.g2);
        return r.total_cost;
    }

    @Benchmark
    public int kruskal_synthetic(SyntheticState st) {
        Output.KruskalResult r = Kruskal.runKruskal(st.g);
        return r.total_cost;
    }
}
