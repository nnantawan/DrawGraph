package com.example.drawgraph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.drawgraph.Utils.LineGraphLayout;
import com.example.drawgraph.model.CoordinateRectangle;
import com.example.drawgraph.model.LineGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGraphView = (ViewGroup) findViewById(R.id.graph_view);

        int dayAmount = 5;
        float [] coorX1 = new float[dayAmount];
        float [] coorX2 = new float[dayAmount];
        String [] legend = new String[dayAmount];
        /*coorX1[0] = 120;
        coorX1[1] = 110;
        coorX1[2] = 150;
        coorX1[3] = 130;
        coorX1[4] = 110;
        coorX1[5] = 140;

        coorX2[0] = 50;
        coorX2[1] = 80;
        coorX2[2] = 65;
        coorX2[3] = 75;
        coorX2[4] = 90;
        coorX2[5] = 85;

        legend[0] = "1";
        legend[1] = "2";
        legend[2] = "3";
        legend[3] = "4";
        legend[4] = "5";
        legend[5] = "6";*/
        Random random = new Random();
        for (int i=0; i<dayAmount; i++){
            coorX1[i] = random.nextInt(400)+100;
            coorX2[i] = random.nextInt(400)+100;
            legend[i] = String.valueOf(i);
        }

        List<LineGraph> graphList = new ArrayList<>();
        graphList.add(new LineGraph(coorX1, Color.parseColor("#015659")));
        graphList.add(new LineGraph(coorX2, Color.parseColor("#ff5722")));
        setLayoutLineGraph(dayAmount, graphList, legend);
    }

    /**
     * set layout for graph view
     * @param dayAmount
     * @param graphList
     * @param legend
     */
    private void setLayoutLineGraph(int dayAmount, List<LineGraph> graphList, String [] legend) {

        //กำหนดค่าพื้นฐานกราฟ
        int increment = 100;
        System.out.println("Increment : "+increment);
        LineGraphLayout graphLayout = new LineGraphLayout(this);
        graphLayout.setIncrement(100);
        graphLayout.setMaxValue(500);
        graphLayout.setIncrementSpan(increment);
        graphLayout.setLayoutGraphWidth((dayAmount+1)*increment);

        //วาดสี่เหลี่ยม
        List<CoordinateRectangle> rectangleList = new ArrayList<>();
        rectangleList.add(new CoordinateRectangle(200,350, ContextCompat.getColor(this, R.color.colorPrimary)));
        rectangleList.add(new CoordinateRectangle(70,150, ContextCompat.getColor(this, R.color.colorAccent)));
        graphLayout.drawRectangle(rectangleList);

        graphLayout.drawLineGraph(graphList, legend);

        if (mGraphView.getChildCount()>0){
            mGraphView.removeAllViews();
        }
        mGraphView.addView(graphLayout.createGraph());
    }
}