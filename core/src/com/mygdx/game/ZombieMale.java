/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author patri
 */
public class ZombieMale extends Image{
TextureRegion stand;
    Animation walk, jump;

    float x_pos;
    float y_pos;
    
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = false;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 1f;
    final float DAMPING = 0.87f;

    public ZombieMale() {
        x_pos = this.getX();
        y_pos = this.getY();
        
        final float width = 33;
        final float height = 40; //120
        this.setSize(1, height / width);

        Texture ninjaTexture = new Texture("zombie.png");
        TextureRegion[][] grid = TextureRegion.split(ninjaTexture, (int) width, (int) height);
        
        stand = grid[0][0];
        walk = new Animation(0.15f, grid[0][1], grid[1][0], grid[1][1], grid[2][0],grid[2][1]);        
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public void movimiento_zombie(float delta, float ninja_x) {
        time = time + delta;

        if(ninja_x<43){
            if(ninja_x>getX()){
                this.xVelocity = MAX_VELOCITY;
                this.isFacingRight = true;
            } else{
                xVelocity = -1 * MAX_VELOCITY;
                this.isFacingRight = false;
            }
        }
        //Si x es mayor o igual a esto, haz algo
        
        
//        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched) {
//IZQUIERDA
//            if(this.getX()>=5 && !this.isFacingRight){
//                xVelocity = -1 * MAX_VELOCITY;
//                this.isFacingRight = false;
//            }else if(15<=this.getX() && this.isFacingRight){
//                xVelocity = MAX_VELOCITY;
//                this.isFacingRight = true;
//            }
            
//            isFacingRight = false;
//        }
//
//        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched) {
//DERECHA
//            xVelocity = MAX_VELOCITY;
//            isFacingRight = true;
//        }

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }

        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (xVelocity == 0) {
            frame = stand; //STAND
        }else{
            frame = (TextureRegion) walk.getKeyFrame(time);
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layer.setCell(x, y, null);
                    }
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
}