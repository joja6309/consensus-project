import org.jgrapht.Graph;
import org.jgrapht.generate.HyperCubeGraphGenerator;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JgraphTests {

    @Test
    public void hyperCubeGenerator(){
        Graph<Object, DefaultEdge> hyperCubeGraph =
                new SimpleGraph<Object, DefaultEdge>(
                        DefaultEdge.class);
        HyperCubeGraphGenerator<Object, DefaultEdge> hyperCubeGenerator =
                new HyperCubeGraphGenerator<Object, DefaultEdge>(
                        4);
        hyperCubeGenerator.generateGraph(
                hyperCubeGraph,
                new ClassBasedVertexFactory<Object>(Object.class),
                null);

        // Hypercube of 4 dimensions should have 2^4=16 vertices and
        // 4*2^(4-1)=32 total edges
        assertEquals(16, hyperCubeGraph.vertexSet().size());
        assertEquals(32, hyperCubeGraph.edgeSet().size());
    }
}
