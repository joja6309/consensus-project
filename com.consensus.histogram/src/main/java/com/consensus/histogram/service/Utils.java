package com.consensus.histogram.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.net.URL;
import java.nio.file.*;
import java.io.*;
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.awt.*;
//import java.net.URL;
//import java.awt.image.Raster;
//import java.awt.Graphics2D;
import com.consensus.histogram.domain.Image;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import java.io.File;

import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

import javax.imageio.ImageIO;

public class Utils {
    private Utils() {
    }
    public static void createDirectory(String path) {
        if (!Files.isDirectory(Paths.get(path))) {
            try {
                Files.createDirectory(Paths.get(path));
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    }

public static BufferedImage getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }
public static void WriteImage(BufferedImage img,String ext,String writePath){
     try {
            File imgFile = new File(writePath);
    ImageIO.write(img,ext,imgFile);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

  }



public static BufferedImage[] SplitImage(BufferedImage image, int rows, int cols){

        int chunks = rows * cols;
        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //com.consensus.histogram.com.consensus.grid.domain.Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0,
                            chunkWidth, chunkHeight,
                            chunkWidth * y, chunkHeight * x,
                            chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
}

