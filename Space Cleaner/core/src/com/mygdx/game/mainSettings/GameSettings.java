package com.mygdx.game.mainSettings;

import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.screens.GarageScreen;

public class GameSettings {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;
    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static  int getShipForceRatio(){
        if (MemoryManager.loadUpgrade4() && !MemoryManager.loadUpgrade5()) return 15;
        else if (MemoryManager.loadUpgrade5() && !MemoryManager.loadUpgrade6()) return 17;
        else if (MemoryManager.loadUpgrade6()) return 20;
        else return 13;
    }
    public static final int TRASH_VELOCITY = 20;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000L;
    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;

    public static long getShootingCoolDown(){
          if (MemoryManager.loadUpgrade1() && !MemoryManager.loadUpgrade2()) return 870L;
          else if (MemoryManager.loadUpgrade2() && !MemoryManager.loadUpgrade3()) return 830L;
          else if (MemoryManager.loadUpgrade3()) return 800L;
          else return 900L;
    }
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 45;
    public static final int BULLET_VELOCITY = 200;
    public static final short TRASH_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final int METEOR_VELOCITY = 200;
    public static final long METEOR_APPEARANCE_COOL_DOWN = 30000L;
    public static final int METEOR_WIDTH = 110;
    public static final int METEOR_HEIGHT = 200;
    public static final short METEOR_BIT = 8;
    public static final int BONUS_WIDTH = 40;
    public static final int BONUS_HEIGHT = 50;
    public static final short BONUS_BIT = 16;
    public static final long BONUS_APPEARANCE_COOL_DOWN =  50000L;
    public static final long COIN_APPEARANCE_COOL_DOWN = 6300L;
    public static float getTrashDamage(){
        if (MemoryManager.loadUpgrade7() && !MemoryManager.loadUpgrade8()) return 0.75f;
        else if (MemoryManager.loadUpgrade8() && !MemoryManager.loadUpgrade9()) return 0.6f;
        else if (MemoryManager.loadUpgrade9()) return 0.5f;
        else return 1f;
    }


}
