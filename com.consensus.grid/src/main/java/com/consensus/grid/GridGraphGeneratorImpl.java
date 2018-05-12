package com.consensus.grid;
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
    private  List<String> listEdges = new ArrayList<>();
    public GridGraphGenerator<String, String> generator;
    public Map<String, String> resultMap;

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

    class StringEdgeFactory
            implements EdgeFactory<String, String> {
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

private List<String> getNeighborNodes(Collection<String> collection) {

        return collection.stream()
                .map(edge -> edge.split("-")[0])
                .collect(Collectors.toList());
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


    private void setConsensusVertices(Collection<String> nodes,Graph<String, String> graph)  {

        HashMap<String,Set<String>> uniqueNeighborMap = new HashMap<>();
        for(String node: nodes){

            List<String> neighbors =
                    getNeighborNodes(graph.edgesOf(node));

            List<String> secondHopNeighborNodes = new ArrayList<String>();

            for(String neigh: neighbors){
                secondHopNeighborNodes
                        .addAll(getNeighborNodes(graph
                                .edgesOf(neigh)));
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
//            nodeDegrees.add(graph.degreeOf(key));
        }


    }




    private List<String> getNodesofDegree(int n, Graph<String,String> graph){
        return graph.vertexSet()
                .stream()
                .filter(vertex -> graph.degreeOf(vertex) == n)
                .collect(Collectors.toList());

    }
//    public Set<String> sortEdgeSet(Set<String> edges,Graph graph){
//        for()
//        return edges.stream()
//                .map(vertex -> graph.degreeOf(Integer.valueOf(vertex.split("-")[0])))
//                .sorted()
//                .collect(Collectors.toSet());
//    }

    public ConsensusGridGraph getDirectedGridGraph(int rows, int columns,List<Integer> degrees){

        generator = new GridGraphGenerator<>(rows, columns);

        resultMap = new HashMap<>();

        Graph<String, String> directedGridGraph =  new DefaultDirectedGraph<String, String>(new StringEdgeFactory());


        generator.generateGraph( directedGridGraph, new StringVertexFactory(), resultMap);

        removeDuplicateNodes(directedGridGraph);

        setConsensusVertices(directedGridGraph.vertexSet(),directedGridGraph);

        List<String> nodeDegrees  = directedGridGraph.vertexSet()
                .stream()
                .map(v -> v.toString() + "-"+ String.valueOf(directedGridGraph.degreeOf(v)))
                .collect(Collectors.toList());

        return new ConsensusGridGraph(rows,columns,directedGridGraph.vertexSet(),directedGridGraph.edgeSet(),nodeDegrees);


    }

    @Override
    public String toString() {
        return "GridGraphGeneratorImpl{" +
                "generator=" + generator +
                ", resultMap=" + resultMap +
                ", vertexFactory=" + vertexFactory +
                '}';
    }

    public GridGraphGenerator<String, String> getGenerator() {
        return generator;
    }

    public void setGenerator(GridGraphGenerator<String, String> generator) {
        this.generator = generator;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }

    public VertexFactory<Object> getVertexFactory() {
        return vertexFactory;
    }

    public void setVertexFactory(VertexFactory<Object> vertexFactory) {
        this.vertexFactory = vertexFactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridGraphGeneratorImpl)) return false;
        GridGraphGeneratorImpl that = (GridGraphGeneratorImpl) o;
        return Objects.equals(getGenerator(), that.getGenerator()) &&
                Objects.equals(getResultMap(), that.getResultMap()) &&
                Objects.equals(getVertexFactory(), that.getVertexFactory());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getGenerator(), getResultMap(), getVertexFactory());
    }
}
