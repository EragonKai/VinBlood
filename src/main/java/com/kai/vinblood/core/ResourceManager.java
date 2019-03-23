package com.kai.vinblood.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    ALL CREDIT FOR 0x27 SPRITES:

    - link to the itch.io page: 0x72.itch.io
    - his web page: 0x72.pl
    - his legal name: Robert Norenberg
 */

public class ResourceManager {
    private ResourceManager() {}

    private static Map<String, BufferedImage> images = new HashMap<>();

    static {
        File dir1 = new File("src/main/resources/images");
        File[] listOfFiles1 = dir1.listFiles();
        for (File f: listOfFiles1) {
            if (f.isFile()) {
                load(f.getName().replaceAll("\\.png", ""));
            }
            if (f.isDirectory()) {
                File dir = new File(f.getPath());
                File[] listOfFiles = dir.listFiles();
                for (File f1 : listOfFiles) {
                    if (f1.isFile()) {
                        load(f1.getName().replaceAll("\\.png", ""), "/images/" + f.getName() + "/" + f1.getName());
                    }
                }
            }
        }
    }

    private static void load(String name) {
        load(name, "/images/" + name + ".png");
    }

    private static void load(String name, String path) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(ResourceManager.class.getResourceAsStream(path));
        } catch (IOException ex) { ex.printStackTrace(); }

        images.put(name, bi);
    }

    public static BufferedImage getSlice(BufferedImage begin, int x, int y, int width, int height) {
        return (begin.getSubimage(x, y, width, height));
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }

    public static BufferedImage getImage(String name, int x, int y, int width, int height) {
        return getSlice(getImage(name), x, y, width, height);
    }

    public static BufferedImage getImage(String name, int width, int height) {
        return toBufferedImage(getImage(name).getScaledInstance(width, height, Image.SCALE_FAST));
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;

    }

    public static BufferedImage mirrorImage(BufferedImage simg) {
        int width = simg.getWidth();
        int height = simg.getHeight();
        //BufferedImage for mirror image
        BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //create mirror image pixel by pixel
        for(int y = 0; y < height; y++){
            for(int lx = 0, rx = width-1; lx < width; lx++, rx--){
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = simg.getRGB(lx, y);
                //set mirror image pixel value - both left and right
                mimg.setRGB(rx, y, p);
            }
        }
        return mimg;
    }
}
