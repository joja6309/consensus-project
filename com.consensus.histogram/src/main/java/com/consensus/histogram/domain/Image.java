package com.consensus.histogram.domain;

import java.io.*;
import java.awt.image.Raster;
import java.awt.image.BufferedImage;



public class Image
{

  public final BufferedImage image;
  public double[] redPixels;
  public double[] greenPixels;
  public double[] bluePixels;
  public String extension;
  public final String name;
  public final int height;
  public final int width;


  public Image(BufferedImage image, String imageName, String ext) throws IOException{

       this.image =  image;
       Raster raster = image.getRaster();
       this.height = image.getHeight();
       this.width = image.getWidth();
       double[] r = new double [image.getWidth()*image.getHeight()];
       this.redPixels = raster.getSamples(0, 0, image.getWidth(), image.getHeight(), 0, r);
       this.greenPixels = raster.getSamples(0, 0, image.getWidth(), image.getHeight(), 1, r);
       this.bluePixels = raster.getSamples(0, 0,image.getWidth(), image.getHeight(), 1, r);
       this.extension = ext;
       this.name = imageName;

  }
 }







  