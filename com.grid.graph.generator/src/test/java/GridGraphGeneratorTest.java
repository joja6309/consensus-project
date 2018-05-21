//package java;

//import com.grid.graph.generator.ConsensusGridGraph;
//import com.grid.graph.generator.GridGraphGeneratorImpl;
import org.jgrapht.Graph;
import org.jgrapht.generate.HyperCubeGraphGenerator;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;


import com.grid.graph.generator.GridGraphGeneratorImpl;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class GridGraphGeneratorTest {

    @Test
    public void testGridGraphGenerator() {

        GridGraphGeneratorImpl graphGenImpl  = new GridGraphGeneratorImpl();
        graphGenImpl.getDirectedGridGraph(4,4, Arrays.asList(4,6,8));
//        assertFalse(Objects.isNull(graphGenImpl));
    }


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
