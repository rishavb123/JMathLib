package tests.math.graphtheory;

import io.bhagat.math.graphtheory.Graph;
import tests.TestUtils;

public class TestGraphs {

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>("A");
        graph.getHead().connect("B");
        graph.getHead().getConnection(0).connect("C");

        TestUtils.check("Contains C", graph.contains("C"), true);
        TestUtils.check("Contains D", graph.contains("D"), false);
        TestUtils.check("Is Tree", graph.isTree(), true);

        graph.getHead().getConnection(0).getConnection(0).connect(graph.getHead());

        TestUtils.check("Contains C", graph.contains("C"), true);
        TestUtils.check("Contains D", graph.contains("D"), false);
        TestUtils.check("Is Tree", graph.isTree(), false);
    }

}
