package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	public void create() {
            batch = new SpriteBatch();
            //Use LibGDX's default Arial font.
            font = new BitmapFont();
            this.setScreen(new MainScreen(this));
	}

	public void dispose() {
            batch.dispose();
            font.dispose();
	}
}
