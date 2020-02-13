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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author patri
 */
public class GameScreenLevel2 implements Screen{
    MyGdxGame game;
    int score;
    
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    
    Ninja ninja;
    
    
    
    GameScreenLevel2(MyGdxGame game, int score, Ninja ninja) {
        this.game = game;
        this.score = score;
        this.ninja = ninja;
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("level2-2ninja.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);
        
        ninja.layer = (TiledMapTileLayer) map.getLayers().get("tierras");
        ninja.setPosition(4, 400);
        stage.addActor(ninja);
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.x = ninja.getX();
        if(ninja.getX()<=20){
            camera.position.x=20;
        }
        if(ninja.getX()>=189.6){
            camera.position.x=189.6f;
        }
        if(ninja.getX()<=0){
            ninja.setX(0);
        }
        if(ninja.getX()>=209){
            ninja.setX(209);
        }
        
//        //MUERTE
//        if(ninja.getY()<=0){
//            this.muerte();
//        }
//        
//        if(score <0){
//            this.muerte();
//        }
        
        camera.update();
        
//        this.overlapsCoin();
//        this.overlapsMunieco();
//        this.overlapsZombieMale();
//        this.overlapsZombieFemale();
        
        
        System.out.println("Ninja X = " + ninja.getX());
//        System.out.println("Munieco x = "+ munieco.getX());
        System.out.println("Ninja y = " + ninja.getY());
//        System.out.println("Munieco y = "+ munieco.getY());
        
        
        renderer.setView(camera);
        renderer.render();

//        this.zombie.movimiento_zombie(delta, ninja.getX());
//        this.zombie_fem.movimiento_zombie(delta, ninja.getX());
        
        stage.act(delta);
        stage.draw();
        
        game.batch.begin();
        game.font.draw(game.batch, "Score: " + score, 100, 480);//SI NO FUNCIONA, PONER UN ATRIBUTO EN EL MAPA E IR LLAMANDOLO PARA SUMAR O RESTAR PUNTOS
        //SI QUIRO MONEDAS ALEATORIAS AQUI (CREO)
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false,30* width / height, 20);
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
