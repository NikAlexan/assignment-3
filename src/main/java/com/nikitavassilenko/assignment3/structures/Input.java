package com.nikitavassilenko.assignment3.structures;

import java.util.List;

public class Input {
    public static class InputFile {
        public List<InputGraph> graphs;
    }

    public static class InputGraph {
        public int id;
        public List<String> nodes;
        public List<InputEdge> edges;
    }

    public static class InputEdge {
        public String from;
        public String to;
        public int weight;
    }
}
