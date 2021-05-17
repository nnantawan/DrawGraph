package com.example.drawgraph.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;


import com.example.drawgraph.model.CoordinateRectangle;
import com.example.drawgraph.model.LineGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nantawan.u on 1/6/2560.
 */
public class LineGraphLayout implements SurfaceHolder.Callback {


    private String[] mLegend;//คำอธิบายแกน X

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private List<LineGraph> mLineGraphList;//จำนวนกราฟเส้น
    private List<CoordinateRectangle> mRectangleList;//จำนวนสี่เหลี่ยม
    private int mMaxValue, mIncrement,mIncreaseSpanX;

    private Canvas canvas;
    private int canvasWidth,canvasHeight;
    private int paddingLineX = 100;//พิกัดแกน X เส้น แกน Y |
    private int paddingLineY = 50;//ระยะห่างจากขอบแกน Y
    private int bottomY;//พิกัด Y เส้นแกน X
    private int graphHeight;//ความสูงของกราฟ

    public LineGraphLayout(Context context) {

        mMaxValue = 200;//ค่าสูงสุดแกน Y
        mIncrement = 50;//จำนวนค่าที่เพิ่มตามแกน Y
        mIncreaseSpanX = 70;//ระยะห่างแต่ละจุดในแกน x
        mLineGraphList = new ArrayList<>();
        mRectangleList = new ArrayList<>();

        mSurfaceView = new SurfaceView(context);

        mSurfaceHolder = mSurfaceView.getHolder();

        // adding call back to this context means MainActivity
        mSurfaceHolder.addCallback(this);

        // to set surface type
        //mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }



    /**
     * วาดเส้นแกน X,Y
     */
    private void drawLayoutGraph() {
        canvas = mSurfaceHolder.lockCanvas(null);
        if (canvas != null){
            System.out.println("Width  : "+canvas.getWidth());
            System.out.println("Height : "+canvas.getHeight());

            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            bottomY = canvasHeight-paddingLineY;//พิกัดแกน Y เส้นแกน X ----
            //graphHeight = canvasHeight-(paddingLineY-20)-paddingLineY;
            graphHeight = canvasHeight-paddingLineY-(paddingLineY-20);

            //วาดพื้นหลัง = สีขาว
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRect(0,0,canvasWidth, canvasHeight, paint);

            //วาดรูปสี่เหลี่ยมก่อนวาดกราฟ
            drawRectangle();

            //วาดแกน x,y
            Paint paintXY = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintXY.setStyle(Paint.Style.STROKE);
            paintXY.setColor(Color.parseColor("#757575"));
            paintXY.setStrokeWidth(2);

            //วาดเส้นแกน Y |
            canvas.drawLine(paddingLineX, 0,paddingLineX,canvasHeight-(paddingLineY-10), paintXY);

            //วาดเส้นแกน X ---
            canvas.drawLine(paddingLineX-10,canvasHeight-paddingLineY,canvasWidth-paddingLineY,canvasHeight-paddingLineY, paintXY);

            //วาดเส้นประ - - -
            Paint paintX = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintX.setStyle(Paint.Style.STROKE);
            paintX.setColor(Color.parseColor("#757575"));
            paintX.setStrokeWidth(2);
            paintX.setPathEffect(new DashPathEffect(new float[]{5, 10}, 5));

            //ตัวหนังสือแกน Y
            Paint paintTxt = new Paint();
            paintTxt.setColor(Color.BLACK);
            paintTxt.setTextSize(24);

            int amountLine = mMaxValue/mIncrement;//จำนวนเส้นประ
            int lineHeight = graphHeight/amountLine;//ระยะห่างระหว่างเส้นประแต่ละเส้น
            int increase = lineHeight;//พิกัดแกน Y
            int legendX = mIncrement;//คำอธิบายแกน Y |
            int coorTextX;//พิกัดแกน X ตัวหนังสือ
            for (int i=0; i<= amountLine; i++){

                //วาดจุดแกน Y
                canvas.drawLine(paddingLineX-10, bottomY-increase,paddingLineX,bottomY-increase, paintXY);

                //เส้นประ
                canvas.drawLine(paddingLineX, bottomY-increase,canvasWidth-paddingLineY,bottomY-increase, paintX);

                //คำนวณพิกัดแกน x ของตัวหนังสือแกน Y
                coorTextX = (int) ((paddingLineX-20)- paintTxt.measureText(String.valueOf(legendX)));

                //ตัวหนังสือแกน Y
                canvas.drawText(String.valueOf(legendX), coorTextX, (bottomY+10)-increase, paintTxt);
                increase += lineHeight;//เพิ่มพิกัดแกน Y ของเส้นประ
                legendX += mIncrement;//เพิ่มจำนวนของตัวหนังสือ
            }

            //วาดกราฟเส้น
            drawLine();
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    /**
     * วาดกราฟเส้น
     */
    private void drawLine(){
        System.out.println("---- DrawLine ----");
        //paint เส้น
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        //paint จุด
        Paint paintCircle = new Paint();

        int increaseX;//พิกัดแกน X
        int coordinateY;//พิกัดแกน Y
        int coordinateNextY;//พิกัดแกน Y จุดถัดไป ใช้วาดเส้น
        float [] coordinateArr;//arr พิกัด Y ของแต่ละเส้น
        for (LineGraph lineGraph : mLineGraphList){//จำนวนกราฟ = กี่เส้น
            increaseX = mIncreaseSpanX;
            coordinateArr = lineGraph.getCoordinateArr();
            paintCircle.setColor(lineGraph.getColor());
            paint.setColor(lineGraph.getColor());

            //จำนวนพิกัด X = กี่วัน
            for (int i=0; i<lineGraph.getCoordinateArr().length; i++){
                //convert coordinate value to pixel
                coordinateY = (int) ((coordinateArr[i]/mMaxValue) * graphHeight);

                //plot graph
                canvas.drawCircle(paddingLineX+increaseX,bottomY-coordinateY,5,paintCircle);

                //ไม่ใช่จุดสุดท้ายวาดเส้นเชื่อม = เชื่อมจุด1ไป2,2ไป3
                if (i != (lineGraph.getCoordinateArr().length-1)){
                    //คำนวณพิกัดแกน Y จุดถัดไป
                    coordinateNextY = (int) ((coordinateArr[i+1]/mMaxValue) * graphHeight);

                    //วาดเส้นเชื่อม
                    canvas.drawLine(paddingLineX+increaseX, bottomY-coordinateY,
                            paddingLineX+increaseX+mIncreaseSpanX, bottomY-coordinateNextY, paint);
                }

                //เพิ่มพิกัดแกน X
                increaseX += mIncreaseSpanX;
            }
        }

        //paint คำอธิบาย = วันที่
        Paint paintTxt = new Paint();
        paintTxt.setColor(Color.BLACK);
        paintTxt.setTextSize(24);

        //เส้นจุดแกน X
        Paint paintXY = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintXY.setStyle(Paint.Style.STROKE);
        paintXY.setColor(Color.parseColor("#757575"));
        paintXY.setStrokeWidth(2);

        increaseX = mIncreaseSpanX;
        int legendCoorX;//พิกัด X
        for (int i=0; i<mLegend.length; i++){
            //คำนวนให้คำอธิบายอยู่ตรงกลางจุด
            legendCoorX = (int) ((paddingLineX+increaseX) - (paintTxt.measureText(mLegend[i])/2));

            //วาดเส้นแกน X
            canvas.drawLine(paddingLineX+increaseX, bottomY,paddingLineX+increaseX,bottomY+10,paintXY);

            //เขียนคำอธิบาย
            canvas.drawText(mLegend[i], legendCoorX, canvasHeight-10, paintTxt);
            increaseX += mIncreaseSpanX;//เพิ่มพิกัดแกน X
        }

    }

    /**
     * วาดสี่เหลี่ยม
     */
    private void drawRectangle(){
        System.out.println("---- DrawRectangle ----");

        Paint paintRect = new Paint();
        int coordinateY1,coordinateY2;
        double scale = (double) graphHeight/mMaxValue;
        for (CoordinateRectangle coordinate : mRectangleList){
            paintRect.setColor(coordinate.getRectangleColor());
            paintRect.setAlpha(175);

            //พิกัด Y จุด1
            coordinateY1 = (int) (coordinate.getCoordinateStart() * scale);

            //พิกัด Y จุด2
            coordinateY2 = (int) (coordinate.getCoordinateEnd() * scale);

            //วาดสี่เหลี่ยม
            canvas.drawRect(paddingLineX, bottomY-coordinateY1,
                    canvasWidth-paddingLineY, bottomY-coordinateY2, paintRect);

        }
    }

    /**
     * กราฟที่วาดเสร็จแล้ว
     * @return SurfaceView
     */
    public SurfaceView createGraph(){

        return mSurfaceView;
    }

    //กำหนดจำนวนที่เพิ่มตามแกน Y
    public void setIncrement(int mIncrement) {
        this.mIncrement = mIncrement;
        //drawLayoutGraph();
    }

    //กำหนดค่าสูงสุดของกราฟ
    public void setMaxValue(int mMaxValue) {
        this.mMaxValue = mMaxValue;
        //drawLayoutGraph();
    }

    public void setIncrementSpan(int incrementSpan){
        this.mIncreaseSpanX = incrementSpan;
        //drawLayoutGraph();
    }

    //กำหนดความกว้างกราฟ
    public void setLayoutGraphWidth(int width){
        System.out.println("layout width1 : "+width);
        width = width+paddingLineX+paddingLineY;

        System.out.println("layout width2 : "+width);

        System.out.println("+++ setLayoutGraphWidth +");
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                width, ViewGroup.LayoutParams.MATCH_PARENT);

        mSurfaceView.setLayoutParams(layoutParams);
    }

    //กำหนดกราฟเส้น
    public void drawLineGraph(List<LineGraph> lineGraphList, String[] legend){
        System.out.println("+++ drawLineGraph +");
        this.mLineGraphList = lineGraphList;
        this.mLegend = legend;
    }

    //กำหนดวาดรูปสี่เหลี่ยม
    public void drawRectangle(List<CoordinateRectangle> rectangleList){
        System.out.println("+++ set drawRectangle +");
        this.mRectangleList = rectangleList;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawLayoutGraph();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
