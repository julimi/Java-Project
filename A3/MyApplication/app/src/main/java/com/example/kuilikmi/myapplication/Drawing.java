package com.example.kuilikmi.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Drawing extends View{

    private Paint curPaint, rePaint;
    private String curColor = "#000000", lastColor = "#000000";
    private Canvas canvas;
    private Bitmap bitmap;
    private float drawSize, startX = 0, startY = 0;
    //array for store
    private ArrayList<Pair<ArrayList<Float>, Paint>> storage = new ArrayList<Pair<ArrayList<Float>, Paint>>();
    protected boolean erase = false, select = false, fill = false;
    protected int curTool = 1;

    public Drawing(Context c, AttributeSet a){
        super(c, a);
        drawSize = getResources().getInteger(R.integer.medium_size);
        curPaint = new Paint();
        curPaint.setColor(0);
        curPaint.setAntiAlias(true);
        curPaint.setStrokeWidth(drawSize);
        curPaint.setStyle(Paint.Style.STROKE);
        curPaint.setStrokeJoin(Paint.Join.ROUND);
        curPaint.setStrokeCap(Paint.Cap.ROUND);
        rePaint = new Paint(Paint.DITHER_FLAG);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, rePaint);

        for (Pair<ArrayList<Float>, Paint> i : storage) {
            if (i.first.get(0) == 1) {
                canvas.drawLine(i.first.get(1),i.first.get(2),i.first.get(3),i.first.get(4),i.second);
            } else if (i.first.get(0) == 2) {
                System.err.println("hehehehe");
                //i.second.setColor(Color.parseColor("#112233"));
                canvas.drawRect(i.first.get(1),i.first.get(2),i.first.get(3),i.first.get(4),i.second);
            } else if (i.first.get(0) == 3) {
                canvas.drawCircle(i.first.get(1)+(i.first.get(3)-i.first.get(1))/2,
                        i.first.get(2)+(i.first.get(4)-i.first.get(2))/2,
                        Math.max(Math.abs(i.first.get(4)-i.first.get(2))/2,Math.abs(i.first.get(3)-i.first.get(1))/2),
                        i.second);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //path.moveTo(touchX, touchY);
                startX = touchX;
                startY = touchY;
                if (erase || fill || select) {
                    for (Pair<ArrayList<Float>, Paint> i : storage) {

                        if (i.first.get(0) == 1) {
                            if (!fill) {
                                System.err.println("haha");
                                float x1 = i.first.get(1);
                                float y1 = i.first.get(2);
                                float x2 = i.first.get(3);
                                float y2 = i.first.get(4);
                                if (touchX >= Math.min(x1, x2) && touchX <= Math.max(x1, x2)) {
                                    if (touchY >= Math.min(y1, y2) && touchY <= Math.max(y1, y2)) {
                                        System.err.println("x1 " + x1);
                                        System.err.println("y1 " + y1);
                                        System.err.println("x2 " + x2);
                                        System.err.println("y2 " + y2);
                                        float temp = (float) Math.abs(Math.sqrt((double) ((touchX - x1) * (touchX - x1) + (touchY - y1) * (touchY - y1)))
                                                + Math.sqrt((double) ((x2 - touchX) * (x2 - touchX) + (y2 - touchY) * (y2 - touchY)))
                                                - Math.sqrt((double) ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))));
                                        System.err.println("haha11 " + temp);
                                        if (temp <= 5) {
                                            System.err.println("haha22");
                                            if (erase) {
                                                storage.remove(i);
                                            } else {
                                                System.err.println("hahahhahahahaha");
                                                i.second.setColor(Color.parseColor("#123456"));
                                                //invalidate();
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        } else if (i.first.get(0) == 2) {
                            //System.err.println("haha");
                            float x1 = i.first.get(1);
                            float y1 = i.first.get(2);
                            float x2 = i.first.get(3);
                            float y2 = i.first.get(4);
                            if (touchX >= Math.min(x1,x2) && touchX <= Math.max(x1,x2)) {
                                if (touchY >= Math.min(y1,y2) && touchY <= Math.max(y1,y2)) {
                                    if (erase) {
                                        storage.remove(i);
                                    } else if (fill) {
                                        i.second.setStyle(Paint.Style.FILL);
                                    } else {
                                        i.second.setColor(Color.parseColor("#123456"));
                                        //invalidate();
                                    }
                                    break;
                                }
                            }
                        } else if (i.first.get(0) == 3) {
                            float x1 = i.first.get(1);
                            float y1 = i.first.get(2);
                            float x2 = i.first.get(3);
                            float y2 = i.first.get(4);
                            float radius = Math.max(Math.abs(y2-y1)/2,Math.abs(x2-x1)/2);
                            float x0 = x1+(x2-x1)/2;
                            float y0 = y1+(y2-y1)/2;
                            if (touchX >= x0 - radius && touchX <= x0 + radius) {
                                if (touchY >= y0 - radius && touchY <= y0 + radius) {
                                    if (erase) {
                                        storage.remove(i);
                                    } else if (fill) {
                                        i.second.setStyle(Paint.Style.FILL);
                                    } else {
                                        i.second.setColor(Color.parseColor("#123456"));
                                        //invalidate();
                                    }
                                    break;
                                }
                            }
                        }

                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                lastColor = curColor;
                //canvas.drawLine(startX, startY, touchX, touchY, curPaint);
                break;
            case MotionEvent.ACTION_UP:

                if (curTool == 1) {

                        //canvas.drawLine(startX, startY, touchX, touchY, curPaint);
                        ArrayList<Float> na = new ArrayList<Float>();
                        na.add((float)1);
                        na.add(startX);
                        na.add(startY);
                        na.add(touchX);
                        na.add(touchY);
                        Paint newPaint = new Paint(curPaint);
                        storage.add(new Pair<ArrayList<Float>, Paint>(na,newPaint));

                }
                else if (curTool == 2) {
                    //canvas.drawRect(startX, startY, touchX, touchY, curPaint);
                    System.err.println("hohohoh");
                    ArrayList<Float> na = new ArrayList<Float>();
                    na.add((float)2);
                    na.add(startX);
                    na.add(startY);
                    na.add(touchX);
                    na.add(touchY);
                    Paint newPaint = new Paint(curPaint);
                    storage.add(new Pair<ArrayList<Float>, Paint>(na,newPaint));
                }
                else if (curTool == 3) {
                    //canvas.drawCircle(startX+(touchX-startX)/2, startY+(touchY-startY)/2, Math.max(Math.abs(touchY-startY)/2,Math.abs(touchX-startX)/2), curPaint);
                    ArrayList<Float> na = new ArrayList<Float>();
                    na.add((float)3);
                    na.add(startX);
                    na.add(startY);
                    na.add(touchX);
                    na.add(touchY);
                    Paint newPaint = new Paint(curPaint);
                    storage.add(new Pair<ArrayList<Float>, Paint>(na,newPaint));
                }
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;

    }


    public String getColor() {
        return curColor;
    }
    public void setColor(String color){
        invalidate();
        curColor = color;
        int newColor = Color.parseColor(color);
        curPaint.setColor(newColor);

    }

    public void setTool(int tool) {
        curTool = tool;
    }

    public void setdrawSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        drawSize=pixelAmount;
        curPaint.setStrokeWidth(drawSize);
    }

    //set erase true or false
    public void setErase(boolean b) {
        erase = b;
    }

    public void setSelect(boolean b) {
        select = b;
    }

    public void setFill(boolean b) {
        fill = b;
    }
    //start new drawing
    public void startNew(){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        storage.clear();
        invalidate();
    }

}
