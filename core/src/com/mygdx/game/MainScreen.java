package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class MainScreen implements Screen {
    
    final MyGdxGame game;
        
    private Texture texture,logo;
    private TextureRegion region;

    OrthographicCamera camera;


    public MainScreen(final MyGdxGame game) {
            this.game = game;

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);

            
            texture = new Texture(Gdx.files.internal("BG.png"));
            region = new TextureRegion(texture,0,0,800,480);

            logo = new Texture(Gdx.files.internal("super_logo.png"));
    }

  

    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0.1f, 0.3f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.begin();
            game.batch.draw(region,0,0);
            game.batch.end();

            camera.update();
            game.batch.setProjectionMatrix(camera.combined);

            game.batch.begin();
            game.font.draw(game.batch, "PULSA LA PANTALLA PARA EMPEZAR", 100, 200);
            game.batch.draw(logo, 100, 280);
            game.batch.end();

            if (Gdx.input.isTouched()) {
                    game.setScreen(new GameScreen(game));
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
