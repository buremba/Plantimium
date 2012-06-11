package app.soundmanager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import app.shaping.Ellipse;

public class Ses implements Runnable {
	final AudioDevice device;
	public ArrayList<SesParticle> sesler;
	float samples[]=new float[1024];
	Boolean stop=false;
	float stepinc = 0.1f,step=0;
	Ses()
	{
		this.sesler=new ArrayList<SesParticle>();
		this.device=Gdx.app.getAudio().newAudioDevice(8000, false);
	}

	@Override
	public void run() {
		while (!stop) {
			float powCollision=0;
			int nCollision=0;
			for(int k=0; k<sesler.size(); k++)
			{
				Ellipse a=sesler.get(k).el;
				for(int m=0; m<sesler.size(); m++)
				{				
					if(k!=m)
					{						
						Ellipse b=sesler.get(m).el;
						sesler.get(k).gravitate(sesler.get(m));
						float cos=a.getPosition().x-b.getPosition().x;
						float sin=a.getPosition().y-b.getPosition().y;
						float hip=(float) Math.hypot(cos, sin);
						if(Math.abs(hip-(a.getRadius().x+b.getRadius().x))>0)
						{
							powCollision+=Math.abs(hip-(a.getRadius().x+b.getRadius().x));
							nCollision++;
						}
					}
				}
			}
			for (int i = 0; i < samples.length; i ++) {
				samples[i] =  (float)Math.sin(step)*powCollision;
				//samples[i + 1] = 2 * samples[i];
				step+=i%(nCollision+1)<((nCollision+1)/2)?stepinc:-stepinc;
			}

			device.writeSamples(samples, 0, samples.length);
		}
		device.dispose();
	}
}
