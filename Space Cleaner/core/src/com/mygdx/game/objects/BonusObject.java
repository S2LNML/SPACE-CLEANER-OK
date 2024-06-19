package com.mygdx.game.objects;


import com.badlogic.gdx.physics.box2d.World;


public class BonusObject extends TrashObject{
    public BonusObject(int width, int height, String texturePath, World world, int velocity, short cBits) {
        super(width, height, texturePath, world, velocity, cBits);
    }
    @Override
    public void contact(){
        livesLeft -= 1;
        }
    }






