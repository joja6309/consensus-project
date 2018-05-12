package com.consensus.histogram.domain;

import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;


public class Histogram {

    private static final int BINS = 256;
    private HistogramDataset dataset;
    private XYBarRenderer renderer;

}