package com.grid.graph.generator;

import java.util.Arrays;
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
        return "ConsensusGridGraph{" +
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
                Objects.equals(getNodeDegrees(), that.getNodeDegrees());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getHeight(), getWidth(), getVertices(), getEdges(), getNodeDegrees());

        return result;
    }

    public List<String> getNodeDegrees() {
        return nodeDegrees;
    }

    public void setNodeDegrees(List<String> nodeDegrees) {
        this.nodeDegrees = nodeDegrees;
    }

    public ConsensusGridGraph(int inputHeight, int inputWidth, Set<String> inputVertices, Set<String> inputEdges, List<String> nodeDeg,double[][] weightMatrix){
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




