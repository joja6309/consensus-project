package com.consensus.histogram;/*
 * This Java source file was generated by the Gradle 'init' task.
 */
//import java.io.*;
import com.consensus.histogram.domain.Image;
import com.consensus.histogram.service.HistogramGenerator;
import com.consensus.histogram.service.Utils;

import java.io.IOException;
import java.util.*;
import java.awt.image.BufferedImage;


public class App {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
      
        
        String workingDir = System.getProperty("user.dir") + "/working";
        String imageName = "image0";
        String imageDir = workingDir + "/" + imageName;
        Utils.createDirectory(workingDir);
        Utils.createDirectory(imageDir);
        try {
            Image originalImage = new Image(Utils.getImage("http://i.imgur.com/kxXhIH1.jpg"),imageName,"jpg");
            ArrayList<Image> slices = new ArrayList<Image>();

            for(BufferedImage img:Utils.SplitImage(originalImage.image,8,8)){
                slices.add(new Image(img,"image" + slices.size(),"jpg"));
            }

            for(Image img: slices){
                Utils.WriteImage(img.image,
                        img.extension,
                        imageDir +"/"+img.name+"-"+ slices.size() +"." +img.extension);

                HistogramGenerator histGen = new HistogramGenerator(img);
                histGen.WriteHistogram(imageDir +"/"+img.name+"hist"+"-"+ slices.size() +"." +img.extension);

            }
        }
        catch(IOException exception) {
            System.out.println(exception);
        }


    }
}

