import com.nikitavassilenko.assignment3.algorithms.Kruskal;
import com.nikitavassilenko.assignment3.structures.Input;
import com.nikitavassilenko.assignment3.structures.Output;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

import tools.jackson.databind.ObjectMapper;

public class KruskalMSTTest {

    private Input.InputFile readInput() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/input_example.json")) {
            assertNotNull(is, "input_example.json not found on classpath");
            ObjectMapper om = new ObjectMapper();
            return om.readValue(is, Input.InputFile.class);
        }
    }

    @Test
    @DisplayName("Graph #1: total_cost == 16 и |MST| = N-1")
    void graph1_totalCost_and_size() throws Exception {
        Input.InputFile in = readInput();
        Input.InputGraph g1 = in.graphs.stream().filter(g -> g.id == 1).findFirst().orElseThrow();
        Output.KruskalResult res = Kruskal.runKruskal(g1);
        assertEquals(16, res.total_cost);
        assertEquals(g1.nodes.size() - 1, res.mst_edges.size());
    }

    @Test
    @DisplayName("Graph #2: total_cost == 6 и |MST| = N-1")
    void graph2_totalCost_and_size() throws Exception {
        Input.InputFile in = readInput();
        Input.InputGraph g2 = in.graphs.stream().filter(g -> g.id == 2).findFirst().orElseThrow();
        Output.KruskalResult res = Kruskal.runKruskal(g2);
        assertEquals(6, res.total_cost);
        assertEquals(g2.nodes.size() - 1, res.mst_edges.size());
    }
}
