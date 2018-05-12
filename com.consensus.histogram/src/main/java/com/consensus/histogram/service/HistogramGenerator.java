package com.consensus.histogram.service;

import com.consensus.histogram.domain.Image;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HistogramGenerator {

//    public Image writeImage;
    public HistogramDataset dataset;

    private final int width;
    private final int height;

    private JFreeChart jFreeChart;

    public double[] redSeries;
    public double[] blueSeries;
    public double[] greenSeries;

    int BINS = 256;


    public HistogramGenerator(Image img){

        width = img.image.getWidth();
        height = img.image.getHeight();

        dataset = new HistogramDataset();

        redSeries = new double[width * height];
        blueSeries = new double[width * height];
        greenSeries = new double[width * height];

        generateHistogram(img.image);

    }
    private void generateHistogram(BufferedImage image){
        Raster raster = image.getRaster();

        redSeries = raster.getSamples(0, 0, width, height, 0, redSeries);
        blueSeries = raster.getSamples(0, 0, width, height, 1, blueSeries);
        greenSeries = raster.getSamples(0, 0,  width, height, 2, greenSeries);

        dataset.addSeries("Red", redSeries, BINS);
        dataset.addSeries("Green", greenSeries, BINS);
        dataset.addSeries("Blue", blueSeries, BINS);

        jFreeChart = ChartFactory.createHistogram("","","",dataset, PlotOrientation.VERTICAL, false, false, false);

    }

    public  void WriteHistogram(String writePath){
        try{
            OutputStream out = new FileOutputStream(writePath);
            createChartPanel();
            ChartUtilities.writeChartAsPNG(out,jFreeChart , 400,400);
        }
        catch(Exception exception){
        }
    }

    private void createChartPanel() {

        XYPlot plot = (XYPlot) jFreeChart.getPlot();
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        // translucent red, green & blue
        Paint[] paintArray = {
                new Color(0x80ff0000, true),
                new Color(0x8000ff00, true),
                new Color(0x800000ff, true)
        };
        plot.setDrawingSupplier(new DefaultDrawingSupplier(
                paintArray,
                DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

    }

}
