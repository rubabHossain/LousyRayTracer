package com.raytracer.lib.canvas;

import com.raytracer.lib.primitives.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Canvas {

    private final List<List<Color>> pixelGrid;

    public Canvas(int height, int width) {
        this.pixelGrid = new ArrayList<>(height);
        Color black = new Color(0,0,0);
        for(int h = 0; h < height; h++) {
            List<Color> row = new ArrayList<>();
            IntStream.range(0, width).forEach( i -> row.add(black) ); // make a row of length w of Color
            this.pixelGrid.add(row);
        }
    }

    public int getHeight() {
        return this.pixelGrid.size();
    }

    public int getWidth() {
        return this.pixelGrid.size() > 0 ? this.pixelGrid.get(0).size() : 0;
    }

    public List<List<Color>> getPixelGrid() {
        return this.pixelGrid;
    }


    public Color getPixel(int row, int col) {
        return this.pixelGrid.get(row).get(col);
    }

    public void setPixel(int row, int col, Color c) {
        if (row < 0 || row >= getHeight()) return;
        if (col < 0 || col >= getWidth()) return;
        this.pixelGrid.get(row).set(col, c);
    }

}
