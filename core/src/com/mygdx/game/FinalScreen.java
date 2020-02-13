/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author patri
 */
public class FinalScreen implements Screen{
    final MyGdxGame game;
        
    private SpriteBatch batch;
    private Texture texture,logo;
    private TextureRegion region;

    OrthographicCamera camera;


    public FinalScreen(final MyGdxGame game) {
            this.game = game;

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);

            batch = new SpriteBatch();   
            texture = new Texture(Gdx.files.internal("BG.png"));
            region = new TextureRegion(texture,0,0,800,480);

    }

  

    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0.1f, 0.3f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.draw(region,0,0);
            batch.end();

            camera.update();
            batch.setProjectionMatrix(camera.combined);

            game.batch.begin();
            game.font.draw(game.batch, "PULSA LA PANTALLA PARA VOLVER A EMPEZAR", 100, 200);
            //PONER EL SCORE
            game.batch.end();

            if (Gdx.input.isTouched()) {
                    game.setScreen(new GameScreenLevel1(game));
                    dispose();
            }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
