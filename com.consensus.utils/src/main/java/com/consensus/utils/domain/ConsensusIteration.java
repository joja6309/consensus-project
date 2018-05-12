package com.consensus.utils.domain;


import java.util.*;

public class ConsensusIteration {

    public String id;

    public String name;

    public Integer count;

    public String message;

    public List<String> hops;

    public double[] redSeries;
    public double[] blueSeries;
    public double[] greenSeries;



    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "ConsensusIteration{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", message='" + message + '\'' +
                ", hops=" + hops +
                ", redSeries=" + Arrays.toString(redSeries) +
                ", blueSeries=" + Arrays.toString(blueSeries) +
                ", greenSeries=" + Arrays.toString(greenSeries) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsensusIteration)) return false;
        ConsensusIteration that = (ConsensusIteration) o;
        return getCount() == that.getCount() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getHops(), that.getHops()) &&
                Arrays.equals(getRedSeries(), that.getRedSeries()) &&
                Arrays.equals(getBlueSeries(), that.getBlueSeries()) &&
                Arrays.equals(getGreenSeries(), that.getGreenSeries());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getId(), getName(), getCount(), getMessage(), getHops());
        result = 31 * result + Arrays.hashCode(getRedSeries());
        result = 31 * result + Arrays.hashCode(getBlueSeries());
        result = 31 * result + Arrays.hashCode(getGreenSeries());
        return result;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ConsensusIteration(){
        hops = new ArrayList<String>();
        setId();
    }




    public double[] getRedSeries() {
        return redSeries;
    }

    public void setRedSeries(double[] redSeries) {
        this.redSeries = redSeries;
    }

    public double[] getBlueSeries() {
        return blueSeries;
    }

    public void setBlueSeries(double[] blueSeries) {
        this.blueSeries = blueSeries;
    }

    public double[] getGreenSeries() {
        return greenSeries;
    }

    public void setGreenSeries(double[] greenSeries) {
        this.greenSeries = greenSeries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getHops() {
        return hops;
    }

    public void setHops(List<String> hops) {
        this.hops = hops;
    }

    public void setId(){
        this.id = UUID.randomUUID().toString();
    }



    /**
     * Created by aglenis on 12/21/16.
     */
    public static class ConsensusIterationGenerator {
        private int currId;

        public ConsensusIterationGenerator()
        {
            this.currId = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof  ConsensusIterationGenerator)) return false;
            ConsensusIterationGenerator that = ( ConsensusIterationGenerator) o;
            return currId == that.currId;
        }


    }


}
