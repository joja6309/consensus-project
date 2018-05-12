package com.consensus.grid;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ConsensusGridGraph {



    public int height;

    public int width;

    public Set<String> vertices;

    public Set<String> edges;

    public List<String> nodeDegrees;

    @Override
    public String toString() {
        return "com.consensus.grid.ConsensusGridGraph{" +
                "height=" + height +
                ", width=" + width +
                ", vertices=" + vertices +
                ", edges=" + edges +
                ", nodeDegrees=" + nodeDegrees +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsensusGridGraph)) return false;
        ConsensusGridGraph that = (ConsensusGridGraph) o;
        return getHeight() == that.getHeight() &&
                getWidth() == that.getWidth() &&
                Objects.equals(getVertices(), that.getVertices()) &&
                Objects.equals(getEdges(), that.getEdges()) &&
                Objects.equals(nodeDegrees, that.nodeDegrees);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getHeight(), getWidth(), getVertices(), getEdges(), nodeDegrees);
    }

    public ConsensusGridGraph(int inputHeight, int inputWidth, Set<String> inputVertices, Set<String> inputEdges, List<String> nodeDeg){
        height = inputHeight;
        width = inputWidth;
        vertices = inputVertices;
        edges = inputEdges;
        nodeDegrees = nodeDeg;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Set<String> getVertices() {
        return vertices;
    }

    public void setVertices(Set<String> vertices) {
        this.vertices = vertices;
    }


    public Set<String> getEdges() {
        return edges;
    }

    public void setEdges(Set<String> edges) {
        this.edges = edges;
    }

}
//package com.consensus.generate.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


//@RestController

//public class GridGraphController {

//    private static final Logger logger = LoggerFactory.getLogger(GridGraphController.class);

//    @Autowired
//    private GridGraphGeneratorImpl gridGraphGenerator;
//
//    @RequestMapping(path = "/generate-directed", method = GET)
//    @CrossOrigin()
//    @GetMapping()
//    @ResponseBody
//    public ResponseEntity<String> generateDirectedGraph(
//            @RequestParam(name = "width") int width,
//            @RequestParam(name = "height") int height,
//            @RequestParam(name = "degree", defaultValue = "") String degree) {
//
//        if (Objects.isNull(width)) {
//            return ResponseEntity.badRequest().body("Missing width param");
//        } else if (Objects.isNull(height)) {
//            return ResponseEntity.badRequest().body("Missing height param");
//        }
//        String jsonString = "";
//        try {
//            List<Integer> degrees = new ArrayList<Integer>();
//            if (!degree.isEmpty()) {
//                degrees = Arrays.asList(degree.split(","))
//                        .stream()
//                        .map(element -> Integer.parseInt(element))
//                        .collect(Collectors.toList());
//
//            }
//
//
//            com.consensus.grid.ConsensusGridGraph graph = gridGraphGenerator.getDirectedGridGraph(width, height, degrees);
//            ObjectMapper mapper = new ObjectMapper();
//            jsonString = mapper.writeValueAsString(graph);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.ok(jsonString);
//    }


//    @RequestMapping(path = "/generate-undirected", method = GET)
//    @CrossOrigin()
//    @GetMapping()
//    @ResponseBody
//    public ResponseEntity<String> generateUndirectedGraph(
//            @RequestParam(name="width") int width,
//            @RequestParam(name="height") int height) {
//        if(Objects.isNull(width)){
//            return ResponseEntity.badRequest().body("Missing width param");
//        }
//        else if( Objects.isNull(height)){
//            return ResponseEntity.badRequest().body("Missing height param");
//        }
//        String jsonString = "";
//        try{
//            //com.consensus.grid.ConsensusGridGraph graph = gridGraphGenerator.getUndirectedGridGraph(width,height);
//            ObjectMapper mapper = new ObjectMapper();
//            //jsonString = mapper.writeValueAsString(graph);
//        }
////        catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
//        return ResponseEntity.ok(jsonString);
//    }



