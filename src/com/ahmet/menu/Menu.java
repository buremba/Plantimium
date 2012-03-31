package com.ahmet.menu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Image.Scaling;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Menu implements ApplicationListener, InputProcessor {
	Stage ui;
	Texture uiTexture;
	BitmapFont font;

	@Override
	public void create () {
		font = new BitmapFont();

		uiTexture = new Texture(Gdx.files.internal("data/menu.png"));
		uiTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ui = new Stage(480, 320, false);

		final Image newgame = new Image(new TextureRegion(uiTexture, 0, 0, 180, 51), Scaling.none, Align.BOTTOM, "newgame") {
			public boolean touchDown (float x, float y, int pointer) {
				Gdx.app.log("a", "touchdown");
				return true;
			}
			public void touchUp(float x, float y, int pointer) {
				Gdx.app.log("a", "touchup");
			}
		};
		newgame.height = newgame.getPrefHeight();
		newgame.width = newgame.getPrefWidth();
		newgame.x = ui.width() - 220;
		newgame.y = 250;

		Image options = new Image(new TextureRegion(uiTexture, 0, 52, 180, 51), Scaling.none, Align.BOTTOM, "options") {
			public boolean touchDown (float x, float y, int pointer) {
				Gdx.app.log("a", "touchdown");
				return true;
			}
			public void touchUp(float x, float y, int pointer) {
				Gdx.app.log("a", "touchup");
			}
		};
		options.height = options.getPrefHeight();
		options.width = options.getPrefWidth();
		options.x = ui.width() - 220;
		options.y = newgame.y - newgame.getPrefHeight() - 10;


		Image help = new Image(new TextureRegion(uiTexture, 0, 104, 180, 51), Scaling.none, Align.BOTTOM, "help") {
			public boolean touchDown (float x, float y, int pointer) {
				Gdx.app.log("a", "touchdown");
				return true;
			}
			public void touchUp(float x, float y, int pointer) {
				Gdx.app.log("a", "touchup");
			}
		};
		help.height = help.getPrefHeight();
		help.width = help.getPrefWidth();
		help.x = ui.width() - 220;
		help.y = options.y - options.getPrefHeight() - 10;
		
		Image exit = new Image(new TextureRegion(uiTexture, 0, 156, 180, 51), Scaling.none, Align.BOTTOM, "exit") {
			public boolean touchDown (float x, float y, int pointer) {
				Gdx.app.log("a", "touchdown");
				return true;
			}
			public void touchUp(float x, float y, int pointer) {
				Gdx.app.log("a", "touchup");
			}
		};
		exit.height = exit.getPrefHeight();
		exit.width = exit.getPrefWidth();
		exit.x = ui.width() - 220;
		exit.y = help.y - help.getPrefHeight() - 10;

		ui.addActor(newgame);
		ui.addActor(options);
		ui.addActor(help);
		ui.addActor(exit);

		Label fps = new Label("fps: 0", new Label.LabelStyle(font, Color.WHITE), "fps");
		fps.x = 10;
		fps.y = 30;
		fps.color.set(0, 1, 0, 1);
		ui.addActor(fps);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		GL10 gl = Gdx.graphics.getGL10();
		gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


		((Label)ui.findActor("fps")).setText("fps: " + Gdx.graphics.getFramesPerSecond());
		ui.draw();
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		return ui.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		return ui.touchUp (x, y, pointer, button);
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean keyDown (int keycode) {
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
