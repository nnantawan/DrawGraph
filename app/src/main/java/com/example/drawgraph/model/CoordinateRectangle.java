package com.example.drawgraph.model;

/**
 * Created by nantawan.u on 2/6/2560.
 */
public class CoordinateRectangle {

    private int coordinateStart;
    private int coordinateEnd;
    private int rectangleColor;

    public CoordinateRectangle(int coordinateStart, int coordinateEnd, int rectangleColor) {
        this.coordinateStart = coordinateStart;
        this.coordinateEnd = coordinateEnd;
        this.rectangleColor = rectangleColor;
    }

    public int getCoordinateStart() {
        return coordinateStart;
    }

    public void setCoordinateStart(int coordinateStart) {
        this.coordinateStart = coordinateStart;
    }

    public int getCoordinateEnd() {
        return coordinateEnd;
    }

    public void setCoordinateEnd(int coordinateEnd) {
        this.coordinateEnd = coordinateEnd;
    }

    public int getRectangleColor() {
        return rectangleColor;
    }

    public void setRectangleColor(int rectangleColor) {
        this.rectangleColor = rectangleColor;
    }
}
