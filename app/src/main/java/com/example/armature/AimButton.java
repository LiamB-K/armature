package com.example.armature;

import android.graphics.Canvas;
import android.graphics.Paint;

public class AimButton {
    float x, y, radius, proportion = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean active = false;

    AimButton(float x, float y, float radius, int colour){
        this.x = x;
        this.y = y;
        this.radius = radius;
        paint.setColor(colour);
        paint.setStrokeWidth(5);
    }
    void handleTouchDown(float touchX, float touchY){
//        if(calculateDistance(touchX, touchY, x, y) <= radius){
//            active = true;
//        }
        active = calculateDistance(touchX, touchY, x, y) <= radius;
        if(active){
            proportion = calculateDistance(touchX, touchY, x, y) / (radius * 0.75f);
            if(proportion > 1){
                proportion = 1;
            }
            else if(proportion < 0){
                proportion = 0;
            }
        }
    }
    void handleTouchMove(float touchX, float touchY){
        if(active){
            if(calculateDistance(touchX, touchY, x, y) <= radius){
                proportion = calculateDistance(touchX, touchY, x, y) / (radius * 0.75f);
                if(proportion > 0.99f){
                    proportion = 0.99f;
                }
                else if(proportion < 0){
                    proportion = 0;
                }
            }
//            else{
//                float ratio = radius / calculateDistance()
//            }
        }
    }
    int getAngle(float touchX, float touchY){
        float x1 = 0;
        float y1 = -10;
        float x2 = touchX - x;
        float y2 = touchY - y;
        float magnitude1 = (float) (Math.sqrt((x1 * x1) + (y1 * y1)));
        float magnitude2 = (float) (Math.sqrt((x2 * x2) + (y2 * y2)));
        float dotProduct = x1 * x2 + y1 * y2;
        int angle = (int)Math.toDegrees(Math.acos(dotProduct/(magnitude1 * magnitude2)));
        if(x2 < 0){
            angle = 180 + (180 - (angle + 1));
        }
        return angle;
    }
    void handleTouchUp(){
        active = false;
        proportion = 0;
    }
    float getProportion(){
        return proportion;
    }
    float calculateDistance(float x1, float y1, float x2, float y2){
        return (float)(Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2))));
    }
    void draw(Canvas canvas){
        paint.setAlpha(255);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, radius, paint);
        paint.setAlpha(100);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, paint);
    }
}
