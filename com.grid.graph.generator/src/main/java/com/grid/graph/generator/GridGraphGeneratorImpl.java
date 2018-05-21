package com.grid.graph.generator;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GridGraphGenerator;
import org.jgrapht.graph.DefaultDirectedGraph;


import static java.util.stream.Collectors.toSet;
import java.util.*;
import java.util.stream.Collectors;


public class GridGraphGeneratorImpl {

    public GridGraphGeneratorImpl(){};

    public GridGraphGenerator<String, String> generator;
    public Graph<String, String> directedGridGraph;
    public Map<String, String> resultMap;
    public List<String> graphDegrees;



    private VertexFactory<Object> vertexFactory = new VertexFactory<Object>() {
        private int i;

        @Override
        public Object createVertex() {
            return ++i;
        }
    };

    public static <T> List<T> setToList(Set<T> set){
        return set.stream().collect(Collectors.toList());
    }

    class StringVertexFactory
            implements VertexFactory<String> {
        int index = 1;

        @Override
        public String createVertex() {
            return String.valueOf(index++);
        }

    }
    class StringEdgeFactory implements EdgeFactory<String, String> {
        @Override
        public String createEdge(String sourceVertex, String targetVertex) {
            return sourceVertex + '-' + targetVertex;
        }
    }

private <T> Set<T> findDuplicates(Collection<T> collection) {
    Set<T> uniques = new HashSet<>();
    return collection.stream()
            .filter(e -> !uniques.add(e))
            .collect(toSet());
}
private Set<String> getNeighborNodes(Collection<String> collection) {

        return collection.stream()
                .map(edge -> edge.split("-")[0])
                .collect(Collectors.toSet());
    }
    private void removeDuplicateNodes(Graph<String, String> graph){
        for(String vertex: graph.vertexSet()){
            for(String edge:graph.edgesOf(vertex)){
                String[] split = edge.split("-");
                if(split[0].equals(split[1])){
                    graph.removeEdge(split[0],split[1]);
                }

            }
        }

    }
    private int neighborMax(String vertex,Graph<String, String> graph){
       return getNeighborNodes(graph.edgesOf(vertex))
                                    .stream()
                                    .map(graph::degreeOf)
                                    .max(Integer::max).get();



    }

    private void setConsensusVertices(Collection<String> nodes,Graph<String, String> graph)  {

        HashMap<String,Set<String>> uniqueNeighborMap = new HashMap<>();
        for(String node: nodes){

            Set<String> neighbors =
                    getNeighborNodes(graph.edgesOf(node));

            List<String> secondHopNeighborNodes = new ArrayList<String>();

            for(String neigh: neighbors){
                secondHopNeighborNodes
                        .addAll(getNeighborNodes(graph.edgesOf(neigh)));
            }
            Set<String> uniqueNeighbors = findDuplicates(secondHopNeighborNodes);
            if(uniqueNeighbors.contains(node)){
                uniqueNeighbors.remove(node);
            }
            uniqueNeighborMap.put(node,uniqueNeighbors);
        }
        for(String key: uniqueNeighborMap.keySet()){

            uniqueNeighborMap.get(key)
                    .forEach(secondNeigh ->
                            graph.addEdge(key,secondNeigh));
        }
    }
    private List<String> getNodesofDegree(int n, Graph<String,String> graph){
        return graph.vertexSet()
                .stream()
                .filter(vertex -> graph.degreeOf(vertex) == n)
                .collect(Collectors.toList());

    }

    public void generateDirectedGridGraph(int rows, int columns,List<Integer> degrees){

        generator = new GridGraphGenerator<>(rows, columns);
        resultMap = new HashMap<>();

        directedGridGraph =  new DefaultDirectedGraph<String, String>(new StringEdgeFactory());
        generator.generateGraph( directedGridGraph, new StringVertexFactory(), resultMap);

        removeDuplicateNodes(directedGridGraph);
        setConsensusVertices(directedGridGraph.vertexSet(),directedGridGraph);

        graphDegrees  = directedGridGraph.vertexSet()
                .stream()
                .map(v -> v.toString() + "-"+ String.valueOf(directedGridGraph.degreeOf(v)))
                .collect(Collectors.toList());



    }


}
