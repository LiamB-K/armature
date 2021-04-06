package com.example.armature;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Arm {
    PointF base, end, joint = new PointF();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final float length1, length2;
    Arm(PointF base, PointF end, float length1, float length2){
        this.base = base;
        this.end = end;
        this.length1 = length1;
        this.length2 = length2;
        paint.setStrokeWidth(5);
    }
    void setBase(float x, float y){
        base.set(x, y);
    }
    void setEnd(float x, float y){
        end.set(x, y);
    }
    void setJoint(boolean left){
        float distance = (float) (Math.sqrt(((base.x - end.x) * (base.x - end.x)) + ((base.y - end.y) * (base.y - end.y))));
        if(distance <= length1 + length2){
//            canvas.drawText("check", 10, 160, paint);
            float a1 = distance + length1 + length2;
            float a2 = distance + length1 - length2;
            float a3 = distance - length1 + length2;
            float a4 = -distance + length1 + length2;

            float area = (float) (Math.sqrt(a1 * a2 * a3 * a4) / 4);

            float val1 = (base.x + end.x) * 0.5f + (end.x - base.x) * (length1 * length1 - length2 * length2) / (2 * distance * distance);
            float val2 = 2 * (base.y - end.y) * area / (distance * distance);

            float x1 = val1 + val2;
            float x2 = val1 - val2;

            val1 = (base.y + end.y) * 0.5f + (end.y - base.y) * (length1 * length1 - length2 * length2) / (2 * distance * distance);
            val2 = 2 * (base.x - end.x) * area / (distance * distance);

            float y1 = val1 - val2;
            float y2 = val1 + val2;

//            canvas.drawCircle(x1, y1, 5, paint);
//            canvas.drawLine(circle1.x, circle1.y, x1, y1, paint);
//            canvas.drawLine(x1, y1, circle2.x, circle2.y, paint);
//            canvas.drawLine(circle1.x, circle1.y, x2, y2, paint);
//            canvas.drawLine(x2, y2, circle2.x, circle2.y, paint);
//            canvas.drawLine(circle1.x, circle1.y, circle2.x, circle2.y, paint);
//            canvas.drawCircle(x2, y2, 20, paint);
            if(left){
                joint.set(x2, y2);
            }
            else{
                joint.set(x1, y1);
            }

        }
        else{
            joint.set(base.x, base.y);
        }
    }
    void draw(Canvas canvas){
        setJoint(true);
        canvas.drawCircle(base.x, base.y, 20, paint);
        canvas.drawLine(base.x, base.y, joint.x, joint.y, paint);
        canvas.drawLine(joint.x, joint.y, end.x, end.y, paint);
    }
}
