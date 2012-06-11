package com.ahmet.polyshaping;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Bezier {
	public static int overalIndex(List arr,int index){
        if(index>=0 && index<arr.size())
        {
        	return index;
        }
        if(index<0)
        {
        	return arr.size()+index;
        }
        if(index>=arr.size())
        {
        	return index%arr.size();
        }
        return -1;
	}
	public static List<Vector2> smoothPolygon(List<Vector2> Polygon)
	{
		List<Vector2> smoothed=new ArrayList<Vector2>();
        for(int i=0; i<Polygon.size(); i++)
        {
        	Vector2 A=Polygon.get(overalIndex(Polygon,i-1));
        	Vector2 B=Polygon.get(overalIndex(Polygon,i));
        	Vector2 C=Polygon.get(overalIndex(Polygon,i+1));
        	Vector2 D=Polygon.get(overalIndex(Polygon,i+2));
            Vector2[] cs=getControls(B,C,A,D,0.9f);
            for(int k=0; k<100; k+=50)
            {
                float bx=bezierStep(B.x,cs[0].x,cs[1].x,C.x,k/100.0f);
                float by=bezierStep(B.y,cs[0].y,cs[1].y,C.y,k/100.0f);
                smoothed.add(new Vector2(bx,by));
            }
        }
		return smoothed;
	}
	public static float bezierStep(float A,float B,float C,float D,float t)
	{
		float s = 1 - t;
		float AB = A*s + B*t;
		float BC = B*s + C*t;
        float CD = C*s + D*t;
        float ABC = AB*s + CD*t;
        float BCD = BC*s + CD*t;
        return ABC*s + BCD*t;	
	}
	public static Vector2[] getControls(Vector2 A,Vector2 B,Vector2 C,Vector2 D,float smooth_value)
	{
		Vector2[] controls=new Vector2[2];
        float x1=A.x;
        float y1=A.y;
        float x2=B.x;
        float y2=B.y;
        
        float x0=C.x;
        float y0=C.y;
        
        float x3=D.x;
        float y3=D.y;
	    float xc1 = (x0 + x1) / 2.0f;
	    float yc1 = (y0 + y1) / 2.0f;
	    float xc2 = (x1 + x2) / 2.0f;
	    float yc2 = (y1 + y2) / 2.0f;
	    float xc3 = (x2 + x3) / 2.0f;
	    float yc3 = (y2 + y3) / 2.0f;

	    float len1 = (float) Math.sqrt((x1-x0) * (x1-x0) + (y1-y0) * (y1-y0));
	    float len2 = (float) Math.sqrt((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1));
	    float len3 = (float) Math.sqrt((x3-x2) * (x3-x2) + (y3-y2) * (y3-y2));

	    float k1 = len1 / (len1 + len2);
	    float k2 = len2 / (len2 + len3);

	    float xm1 = xc1 + (xc2 - xc1) * k1;
	    float ym1 = yc1 + (yc2 - yc1) * k1;

	    float xm2 = xc2 + (xc3 - xc2) * k2;
	    float ym2 = yc2 + (yc3 - yc2) * k2;

	    float ctrl1_x = xm1 + (xc2 - xm1) * smooth_value + x1 - xm1;
	    float ctrl1_y = ym1 + (yc2 - ym1) * smooth_value + y1 - ym1;

	    float ctrl2_x = xm2 + (xc2 - xm2) * smooth_value + x2 - xm2;
	    float ctrl2_y = ym2 + (yc2 - ym2) * smooth_value + y2 - ym2;	
	    controls[0]=new Vector2(ctrl1_x,ctrl1_y);
	    controls[1]=new Vector2(ctrl2_x,ctrl2_y);
		return controls;
	}
}
