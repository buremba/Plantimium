package com.ahmet.b2d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends InputAdapter implements ApplicationListener,InputProcessor {
	private SpriteBatch batch;
	
	float step=0;
	Thread thread;
	boolean stop = false;
	
	float stepinc = 0.1f;
	float discarpan=100;
	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		if (thread == null) {
			final AudioDevice device = Gdx.app.getAudio().newAudioDevice(2000, false);
			thread = new Thread(new Runnable() {
				@Override
				public void run () {
					float samples[] = new float[100];

					while (!stop) {
						for (int i = 0; i < samples.length; i += 2) {
							samples[i] = 0.5f * (float)Math.sin(step)*discarpan;
							//samples[i + 1] = 2 * samples[i];
							step+=stepinc;
						}

						device.writeSamples(samples, 0, samples.length);
					}

					device.dispose();
				}
			});
			thread.start();
		}
		
	}
	@Override
	public boolean touchMoved (int x, int y) {
		discarpan=x*100;
		stepinc=x/200.0f;
		return false;
	}
	@Override
	public void render () {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	@Override
	public void dispose () {
		//world.dispose();
		stop = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void pause() {}
	@Override
	public void resize(int arg0, int arg1) {}
	@Override
	public void resume() {}
}
