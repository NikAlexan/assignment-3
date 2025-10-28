package com.nikitavassilenko.assignment3.cli;

import com.nikitavassilenko.assignment3.structures.Input;
import com.nikitavassilenko.assignment3.structures.Output;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

import static com.nikitavassilenko.assignment3.algorithms.Kruskal.runKruskal;

public class Main {
    public static void main(String[] args) {
        String inPath = args.length >= 1 ? args[0] : "/home/nikalexan/IdeaProjects/nikitavassilenko.assignment3/docs/result/input_example.json";
        String outPath = args.length >= 2 ? args[1] : "/home/nikalexan/IdeaProjects/nikitavassilenko.assignment3/docs/result/kruskal_output.json";

        ObjectMapper om = new ObjectMapper();

        Input.InputFile in = om.readValue(new File(inPath), Input.InputFile.class);

        Output.OutputFile out = new Output.OutputFile();
        for (Input.InputGraph g : in.graphs) {
            Output.GraphResult gr = new Output.GraphResult();
            gr.graph_id = g.id;

            Output.InputStats stats = new Output.InputStats();
            stats.vertices = g.nodes != null ? g.nodes.size() : 0;
            stats.edges = g.edges != null ? g.edges.size() : 0;
            gr.input_stats = stats;

            gr.kruskal = runKruskal(g);
            out.results.add(gr);
        }

        om.writerWithDefaultPrettyPrinter().writeValue(new File(outPath), out);
        System.out.println("Written: " + outPath);
    }
}
