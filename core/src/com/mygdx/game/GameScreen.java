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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patri
 */
public class GameScreen implements Screen{
    MyGdxGame game;
    
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Ninja ninja;
    ZombieMale zombie;
    Coin coin;
    public List<Coin> coins;
    
    public Random rand;
    
    int score;
    int cont;
    int lon=-1;

    GameScreen(MyGdxGame game) {
        this.game=game;
    }

    public void show() {
        map = new TmxMapLoader().load("level1ninja.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);

        ninja = new Ninja();
        ninja.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        ninja.setPosition(20, 10);
        stage.addActor(ninja);
        
        zombie = new ZombieMale();
        zombie.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        zombie.setPosition(0, 10);
        stage.addActor(zombie);
        
//        coin = new Coin();
//        //coin.layer = (TiledMapTileLayer) map.getLayers().get("walls");
//        coin.setPosition(30, 10);
//        stage.addActor(coin);
	this.coins = new ArrayList<Coin>();
        this.loadCoin(0, 0);
        
        score = 100;
        
        
    }

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
        
        //MUERTE
        if(ninja.getY()<=0){
            game.setScreen(new FinalScreen(game/*, this.score*/));
            dispose();
        }
        
        
        camera.update();
        //game.batch.setProjectionMatrix(camera.combined);
        
        
        
        this.overlapsCoin();
        
        
        
        
//        int len = coins.size();
        
//        if (rand.nextFloat() > 0.2f) {
//            Coin coin = new Coin( rand.nextFloat(), 20 + Coin.COIN_HEIGHT + rand.nextFloat() * 3);
//            coins.add(coin);
//        }
        
//        for (int i = 0; i < len; i++) {
//                Coin coin = coins.get(i);
//                //TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
////                batch.draw(coin.coin, coin.position.x - 0.5f, coin.position.y - 0.5f, 1, 1);                
//        }

        renderer.setView(camera);
        renderer.render();

        this.zombie.movimiento_zombie(delta, ninja.getX());
        stage.act(delta);
        stage.draw();
        
        game.batch.begin();
        game.font.draw(game.batch, "Score: " + score, 100, 480);//SI NO FUNCIONA, PONER UN ATRIBUTO EN EL MAPA E IR LLAMANDOLO PARA SUMAR O RESTAR PUNTOS
        //SI QUIRO MONEDAS ALEATORIAS AQUI (CREO)
        game.batch.end();
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false,30* width / height, 20);
    }

    public void resume() {
    }
    
    private void loadCoin(float startX, float startY) {
        TiledMapTileLayer monedas = (TiledMapTileLayer) map.getLayers().get("monedas1");
        
        float endX = startX + monedas.getWidth();
        float endY = startY + monedas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (monedas.getCell(x, y) != null) {
                    if (monedas.getProperties().get("visible", Boolean.class) == true) {
                        monedas.setCell(x, y, null);
                        this.spawMoneda(x,y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    public void spawMoneda (float x, float y){
        Coin coin2 = new Coin();
        coin2.setPosition(x, y);
        stage.addActor(coin2);
        coins.add(coin2);
    }
    
    public void overlapsCoin(){
        int i = 0;
        for (Coin coin : coins) {
            if(coin.getX()-1.5f < ninja.getX() &&coin.getX()+1.5f > ninja.getX() &&coin.getY()-1.5f < ninja.getY() && coin.getY()+1.5f > ninja.getY()){
                //dropSound.play();
                score+=10;
                coin.remove();
                coin.setY(100);
            }
            i++;
        }      
    }
    
    //HACER COLISISONES CON EL ZOMBIE Y EL NINJA CON UN SCORE
}