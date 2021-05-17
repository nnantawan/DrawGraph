package com.example.drawgraph.model;

/**
 * Created by nantawan.u on 1/6/2560.
 */
public class LineGraph {
    private float[] coordinateArr;
    private int color;

    public LineGraph(float[] coordinateArr, int color) {
        this.coordinateArr = coordinateArr;
        this.color = color;
    }

    public float[] getCoordinateArr() {
        return coordinateArr;
    }

    public void setCoordinateArr(float[] coordinateArr) {
        this.coordinateArr = coordinateArr;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
