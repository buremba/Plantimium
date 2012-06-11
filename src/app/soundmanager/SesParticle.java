package app.soundmanager;

import app.shaping.Ellipse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SesParticle {
	Ellipse el;
	Vector2 v;
	SesParticle(Vector2 pos)
	{
		v=new Vector2(0,0);
		el=new Ellipse(pos,new Vector2(5,5),new Vector3(255,0,0),false);
	}
	void Draw(SpriteBatch batch)
	{
		el.Draw(batch);
		if(v.x>0 || v.y>0)
		{
			el.setPosition(new Vector2(el.getPosition().x+v.x,el.getPosition().y+v.y));
		}
		if(el.getRadius().x>100)
		{
			el.setRadius(new Vector2(5,5));
		}
		else
		{
			el.setRadius(new Vector2(el.getRadius().x+1,el.getRadius().x+1));
		}
		Boolean Collide=false;
		if(el.getPosition().x<0)
		{
			el.setPosition(new Vector2(0,el.getPosition().y));
			Collide=true;
		}
		if(el.getPosition().x>Gdx.graphics.getWidth())
		{
			el.setPosition(new Vector2(0,Gdx.graphics.getWidth()));
			Collide=true;
		}
		if(el.getPosition().y<0)
		{
			el.setPosition(new Vector2(el.getPosition().x,0));
			Collide=true;
		}
		if(el.getPosition().x>Gdx.graphics.getHeight())
		{
			el.setPosition(new Vector2(el.getPosition().x,Gdx.graphics.getHeight()));
			Collide=true;
		}
		if(Collide)
		{
			v.x*=-1;
			v.y*=-1;
		}
	}
	void gravitate(SesParticle b)
	{
		float cos=b.el.getPosition().x-el.getPosition().x;
		float sin=b.el.getPosition().y-el.getPosition().y;
		float hip=(float) Math.hypot(cos,sin);
		/*if(hip-el.getRadius().x>0)
		{
			v.x+=cos/(hip*hip);
			v.y+=sin/(hip*hip);
		}
		else
		{
			v.x-=cos/(hip*hip);
			v.y-=sin/(hip*hip);
		}*/
	}
}
