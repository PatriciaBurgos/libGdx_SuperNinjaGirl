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
import java.util.ArrayList;
import java.util.List;

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
    ZombieMale zombie;
    Coin coin;
    public List<Coin> coins;
    Munieco_Pasa_Level munieco;
    Munieco_Menos_60 mun_resta;
    Munieco_Mas_60 mun_sum;
    
    
    GameScreenLevel2(MyGdxGame game, int score, Ninja ninja) {
        this.game = game;
        this.score += score;
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
        ninja.setPosition(0, 100);
        stage.addActor(ninja);
        
        this.zombie = new ZombieMale();
        zombie.layer = (TiledMapTileLayer) map.getLayers().get("tierras");
        zombie.setPosition(43, 10);
        stage.addActor(zombie);
        
        this.mun_resta = new Munieco_Menos_60();
        mun_resta.layer = (TiledMapTileLayer) map.getLayers().get("tierras");
        this.loadMunieco3(0, 0);
        
        this.mun_sum = new Munieco_Mas_60();
        this.loadMunieco2(0, 0);
        
        this.coins = new ArrayList<Coin>();
        this.loadCoin(0, 0);
        
        munieco = new Munieco_Pasa_Level();
        this.loadMunieco(0,0);
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
        
        //MUERTE
        if(ninja.getY()<=0){
            this.muerte();
        }
        
        if(score <0){
            this.muerte();
        }
        
        camera.update();
        
        this.overlapsCoin();
        this.overlapsMunieco();
        this.overlapsZombieMale();
        this.overlapsMuniecoResta();
        this.overlapsMuniecoSuma();
        
        System.out.println("Ninja X = " + ninja.getX());
//        System.out.println("Munieco_Pasa_Level x = "+ munieco.getX());
        System.out.println("Ninja y = " + ninja.getY());
//        System.out.println("Munieco_Pasa_Level y = "+ munieco.getY());
        
        
        renderer.setView(camera);
        renderer.render();

        this.zombie.movimiento_zombie2(delta, ninja.getX());
        this.mun_resta.movimiento_munieco3(delta, ninja.getX());
        this.mun_sum.movimiento_munieco2(delta, ninja.getX());
        
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

    
    public void overlapsZombieMale(){        
        if(zombie.getX()-1f < ninja.getX() &&zombie.getX()+1f > ninja.getX() &&zombie.getY()-1f < ninja.getY() && zombie.getY()+1f > ninja.getY()){
            //dropSound.play();
            score-=25;
            if(zombie.getX()>ninja.getX()){
                ninja.xVelocity -= 50;
            }else{
                ninja.xVelocity += 50;
            }            
        }       
    }
    
    public void overlapsMuniecoResta(){
        if(this.mun_resta.getX()-1f < ninja.getX() &&mun_resta.getX()+1f > ninja.getX() &&mun_resta.getY()-1f < ninja.getY() && mun_resta.getY()+1f > ninja.getY()){
            //dropSound.play();
            score-=60;
            
            if(mun_resta.getX()>ninja.getX()){
                ninja.xVelocity -= 100;
            }else{
                ninja.xVelocity += 100;
            }  
            
//            mun_resta.remove();
//            mun_resta.setY(100);
        } 
    }
    
    public void overlapsMuniecoSuma(){
        if(this.mun_sum.getX()-1f < ninja.getX() &&mun_sum.getX()+1f > ninja.getX() &&mun_sum.getY()-1f < ninja.getY() && mun_sum.getY()+1f > ninja.getY()){
            //dropSound.play();
            score+=60;
            mun_sum.remove();     
            mun_sum.setY(100);
        } 
    }
    
    public void muerte(){
        game.setScreen(new FinalScreen(game, this.score));
        dispose();
    }
    
    private void loadMunieco3(float startX, float startY) {
        TiledMapTileLayer monedas = (TiledMapTileLayer) map.getLayers().get("munieco_persigue_60");
        
        float endX = startX + monedas.getWidth();
        float endY = startY + monedas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (monedas.getCell(x, y) != null) {
                    if (monedas.getProperties().get("vis", Boolean.class) == true) {
                        monedas.setCell(x, y, null);
                        //this.mun_resta = new Munieco_Menos_60();                        
                        this.mun_resta.setPosition(x, y);
                        stage.addActor(mun_resta);                        
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    private void loadMunieco2(float startX, float startY) {
        TiledMapTileLayer monedas = (TiledMapTileLayer) map.getLayers().get("munieco_huye_60");
        
        float endX = startX + monedas.getWidth();
        float endY = startY + monedas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (monedas.getCell(x, y) != null) {
                    if (monedas.getProperties().get("visible", Boolean.class) == true) {
                        monedas.setCell(x, y, null);
                        this.mun_sum = new Munieco_Mas_60();                        
                        this.mun_sum.setPosition(x, y);
                        stage.addActor(mun_sum);                        
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    private void loadCoin(float startX, float startY) {
        TiledMapTileLayer monedas = (TiledMapTileLayer) map.getLayers().get("monedas");
        
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
    
     private void loadMunieco(float startX, float startY) {
        TiledMapTileLayer monedas = (TiledMapTileLayer) map.getLayers().get("fin_juego");
        
        float endX = startX + monedas.getWidth();
        float endY = startY + monedas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (monedas.getCell(x, y) != null) {
                    if (monedas.getProperties().get("visible", Boolean.class) == true) {
                        monedas.setCell(x, y, null);
                        munieco = new Munieco_Pasa_Level();                        
                        munieco.setPosition(x, y);
                        stage.addActor(munieco);                        
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
     
     public void overlapsMunieco(){
        if(munieco.getX()-1.5f < ninja.getX() &&munieco.getX()+1.5f > ninja.getX() &&munieco.getY()-1.5f < ninja.getY() && munieco.getY()+1.5f > ninja.getY()){
            game.setScreen(new FinalScreen(game, this.score));
                      
        }                  
    }
}
