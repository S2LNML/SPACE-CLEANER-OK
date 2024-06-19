package com.mygdx.game.mainSettings;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.managers.MemoryManager;

import java.util.ArrayList;
import java.util.Random;

public class GameSession {
    private  int score;
    static int destructedTrashNumber;
    public GameState state;
    long pauseStartTime;
    long nextTrashSpawnTime;
    long sessionStartTime;
    long nextMeteorSpawnTime;
    long nextBonusSpawnTime;
    long nextCoinSpawnTime;
    static int destructedComets;
    static int unusedBonus;
    public static boolean spacePirateMode;
    public static int coinsK, usedCoins;
    public static int shouldCoins;
    public static int coinsNow;

    public GameSession() {
    }

    public void startGame() {
        Random r = new Random();
        state = GameState.PLAYING;
        score = 0;
        coinsNow = 0;
        destructedTrashNumber = 0;
        destructedComets = 0;
        unusedBonus = 0;
        usedCoins = 0;
        shouldCoins = r.nextInt(6) + (5 * coinsK);
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown());
        nextMeteorSpawnTime = sessionStartTime + GameSettings.METEOR_APPEARANCE_COOL_DOWN;
        nextBonusSpawnTime = sessionStartTime + GameSettings.BONUS_APPEARANCE_COOL_DOWN;
        nextCoinSpawnTime = sessionStartTime + (long) (GameSettings.COIN_APPEARANCE_COOL_DOWN * Math.random());
    }

    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }
    public boolean shouldSpawnCoins() {
        if (nextCoinSpawnTime <= TimeUtils.millis()) {
            nextCoinSpawnTime = TimeUtils.millis() + GameSettings.COIN_APPEARANCE_COOL_DOWN + (long) (GameSettings.COIN_APPEARANCE_COOL_DOWN
                    * Math.random());
            return true;
        }
        return false;
    }


    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }
    public void pauseGame(){
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }
    public void resumeGame(){
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }
    public static void destructionRegistration(){
        destructedTrashNumber += 1;
    }
    public static void destructionCometsRegistration(){
        destructedComets += 1;
    }
    public static void unusedBonusRegistration(){
        unusedBonus += 1;
    }
    public static void coinsUsed(){
        coinsNow += 1;
    }
    public void updateScore(){
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100 + destructedComets * 1000 + unusedBonus * 500;
    }
    public int getScore(){
        return score;
    }
    public int getCoins(){
        return coinsNow;
    }

    public void endGame(){
        updateScore();
        state = GameState.ENDED;
        if (!spacePirateMode) {
            ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
            if (recordsTable == null) {
                recordsTable = new ArrayList<>();
            }
            int foundIdx = 0;
            for (; foundIdx < recordsTable.size(); foundIdx++) {
                if (recordsTable.get(foundIdx) < getScore()) break;
            }
            recordsTable.add(foundIdx, getScore());
            MemoryManager.saveTableOfRecords(recordsTable);
        }
        if (spacePirateMode && GameSession.coinsNow == GameSession.shouldCoins){
            MemoryManager.saveExpense(MemoryManager.loadExpense() + getCoins());
        }
    }

    public boolean shouldMeteorSpawn() {
        if (nextMeteorSpawnTime <= TimeUtils.millis()) {
            nextMeteorSpawnTime = TimeUtils.millis() + GameSettings.METEOR_APPEARANCE_COOL_DOWN;
            return true;
        }
        return false;
    }
    public boolean shouldBonusSpawn() {
        if (nextBonusSpawnTime <= TimeUtils.millis()) {
            nextBonusSpawnTime = TimeUtils.millis() + GameSettings.BONUS_APPEARANCE_COOL_DOWN;
            return true;
        }
        return false;
    }
}
