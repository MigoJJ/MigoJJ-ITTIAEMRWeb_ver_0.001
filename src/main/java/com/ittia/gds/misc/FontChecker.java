package com.ittia.gds.misc;

import java.awt.GraphicsEnvironment;

public class FontChecker {
    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        boolean consolasFound = false;
        
        System.out.println("Available Fonts:");
        for (String fontName : fontNames) {
            System.out.println(fontName);
            if (fontName.equals("Consolas")) {
                consolasFound = true;
            }
        }
        
        if (consolasFound) {
            System.out.println("\nConsolas font found by Java!");
        } else {
            System.out.println("\nConsolas font NOT found by Java. This is likely the problem.");
        }
    }
}