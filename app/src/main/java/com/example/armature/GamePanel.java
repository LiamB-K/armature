package com.example.armature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    DecimalFormat df = new DecimalFormat("#.##");
    private MainThread thread;
    public Paint paint = new Paint();
    AimButton control = new AimButton(1000, 500, 150, Color.rgb(0, 120, 255));
    float touchX = 0, touchY = 0;
    float proportion = 0;
    Arm arm = new Arm(new PointF(640, 360), new PointF(640, 360), 75, 100);
    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                control.handleTouchDown(event.getX(), event.getY());
                touchX = event.getX();
                touchY = event.getY();
                proportion = control.getProportion();
                arm.setEnd((float) (Math.sin(Math.toRadians(control.getAngle(event.getX(), event.getY()))) * (proportion * (arm.length1 + arm.length2))) + arm.base.x, -(float)(Math.cos(Math.toRadians(control.getAngle(event.getX(), event.getY())))) * (proportion * (arm.length1 + arm.length2)) + arm.base.y);
                break;
            case MotionEvent.ACTION_MOVE:
                control.handleTouchMove(event.getX(), event.getY());
                touchX = event.getX();
                touchY = event.getY();
                proportion = control.getProportion();
                arm.setEnd((float) (Math.sin(Math.toRadians(control.getAngle(event.getX(), event.getY()))) * (proportion * (arm.length1 + arm.length2))) + arm.base.x, -(float)(Math.cos(Math.toRadians(control.getAngle(event.getX(), event.getY())))) * (proportion * (arm.length1 + arm.length2)) + arm.base.y);
                break;
            case MotionEvent.ACTION_UP:
                control.handleTouchUp();
                break;
        }
        return true;
    }
    @Override
    public void draw(Canvas canvas){
        paint.setTextSize(40);
        paint .setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        super.draw(canvas);
        canvas.drawColor(Color.rgb(255, 255, 255));
        control.draw(canvas);arm.draw(canvas);
        canvas.drawText("FPS: " + df.format(MainThread.getFPS()), 10, 40, paint);
        canvas.drawText("angle: " + control.getAngle(touchX, touchY), 10, 80, paint);
        canvas.drawText("proportion: " + proportion, 10, 120, paint);

    }
    private static int round(float floatNum){
        int intNum = (int)(floatNum);
        float decimal = floatNum - intNum;
        if(decimal >= 0.5){
            intNum++;
        }
        return intNum;
    }
    private static int round(double floatNum){
        int intNum = (int)(floatNum);
        float decimal = (float) (floatNum - intNum);
        if(decimal >= 0.5){
            intNum++;
        }
        return intNum;
    }
}

