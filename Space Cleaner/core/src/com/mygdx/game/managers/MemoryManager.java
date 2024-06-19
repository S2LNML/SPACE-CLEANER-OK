package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.screens.GarageScreen;

import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");
    public static void saveSoundSettings(boolean isOn){
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }
    public static boolean loadIsSoundsOn(){
        return preferences.getBoolean("isSoundOn", true);
    }
    public static void saveMusicSettings(boolean isOn){
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }
    public static boolean loadIsMusicOn(){
        return preferences.getBoolean("isMusicOn", true);
    }
    public static void saveTableOfRecords(ArrayList<Integer> table){
        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("recordTable", tableInString);
        preferences.flush();
    }
    public static ArrayList<Integer> loadRecordsTable(){
        if (!preferences.contains("recordTable"))
            return null;

        String scores = preferences.getString("recordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }
    public static void saveExpense(long exp){
        if (exp < 0) exp = 0;
        preferences.putLong("expence", exp);
        preferences.flush();
    }
    public static long loadExpense(){
        return preferences.getLong("expence", 0);
    }
    public static void saveUpgrade1(boolean bought){
        preferences.putBoolean("bought1", bought);
        preferences.flush();
    }
    public static void saveUpgrade2(boolean bought){
        preferences.putBoolean("bought2", bought);
        preferences.flush();
    }
    public static void saveUpgrade3(boolean bought){
        preferences.putBoolean("bought3", bought);
        preferences.flush();
    }
    public static void saveUpgrade4(boolean bought){
        preferences.putBoolean("bought4", bought);
        preferences.flush();
    }
    public static void saveUpgrade5(boolean bought){
        preferences.putBoolean("bought5", bought);
        preferences.flush();
    }
    public static void saveUpgrade6(boolean bought){
        preferences.putBoolean("bought6", bought);
        preferences.flush();
    }
    public static void saveUpgrade7(boolean bought){
        preferences.putBoolean("bought7", bought);
        preferences.flush();
    }
    public static void saveUpgrade8(boolean bought){
        preferences.putBoolean("bought8", bought);
        preferences.flush();
    }
    public static void saveUpgrade9(boolean bought){
        preferences.putBoolean("bought9", bought);
        preferences.flush();
    }
    public static boolean loadUpgrade1(){
        return preferences.getBoolean("bought1");
    }
    public static boolean loadUpgrade2(){
        return preferences.getBoolean("bought2");
    }
    public static boolean loadUpgrade3(){
        return preferences.getBoolean("bought3");
    }
    public static boolean loadUpgrade4(){
        return preferences.getBoolean("bought4");
    }
    public static boolean loadUpgrade5(){
        return preferences.getBoolean("bought5");
    }
    public static boolean loadUpgrade6(){
        return preferences.getBoolean("bought6");
    }
    public static boolean loadUpgrade7(){
        return preferences.getBoolean("bought7");
    }
    public static boolean loadUpgrade8(){
        return preferences.getBoolean("bought8");
    }
    public static boolean loadUpgrade9(){
        return preferences.getBoolean("bought9");
    }

}
