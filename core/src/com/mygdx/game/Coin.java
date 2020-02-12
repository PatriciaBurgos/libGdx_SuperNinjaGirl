/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Coin extends Image {
    TextureRegion stand;
    Animation walk, jump;
    public static Texture items;
    
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Coin() {
        final float width = 0.5f;
        final float height = 0.8f;
        this.setSize(1, height / width);

        this.setY(20);
        this.setX(20);

        items = loadTexture("items.png");
        walk = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));
        walk.setPlayMode(Animation.PlayMode.LOOP);
    }
    public static Texture loadTexture (String file) {
            return new Texture(Gdx.files.internal(file));
    }

    public void act(float delta) {
        time = time + delta;

//        boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
//        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {
//            if (canJump) {
//                yVelocity = yVelocity + MAX_VELOCITY * 4;
//            }
//            canJump = false;
//        }
//
//        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched) {
//            xVelocity = -1 * MAX_VELOCITY;
//            isFacingRight = false;
//        }
//
//        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched) {
//            xVelocity = MAX_VELOCITY;
//            isFacingRight = true;
//        }

//        yVelocity = yVelocity + GRAVITY;
////
//        float x = this.getX();
//        float y = this.getY();
//        float xChange = xVelocity * delta;
//        float yChange = yVelocity * delta;
//
//        if (canMoveTo(x + xChange, y, false) == false) {
//            xVelocity = xChange = 0;
//        }
//
//        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
//            canJump = yVelocity < 0;
//            yVelocity = yChange = 0;
//        }
//
//        this.setPosition(x + xChange, y + yChange);
//
//        xVelocity = xVelocity * DAMPING;
//        if (Math.abs(xVelocity) < 0.5f) {
//            xVelocity = 0;
//        }
//        
        
    }

    
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        frame = (TextureRegion) walk.getKeyFrame(time);
//        if (yVelocity != 0) {
//            frame = (TextureRegion) jump.getKeyFrame(time);
//        } else if (xVelocity != 0) {
//            frame = (TextureRegion) walk.getKeyFrame(time);
//        } else {
//            frame = stand;
//        }

        
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
    }

//    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
//        float endX = startX + this.getWidth();
//        float endY = startY + this.getHeight();
//
//        int x = (int) startX;
//        while (x < endX) {
//
//            int y = (int) startY;
//            while (y < endY) {
//                if (layer.getCell(x, y) != null) {
//                    if (shouldDestroy) {
//                        layer.setCell(x, y, null);
//                    }
//                    return false;
//                }
//                y = y + 1;
//            }
//            x = x + 1;
//        }
//
//        return true;
//    }
}
