package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mainSettings.GameResources;
import com.mygdx.game.mainSettings.GameSettings;
import com.mygdx.game.mainSettings.MyGdxGame;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;



public class GarageScreen extends ScreenAdapter {
    ButtonView rateOfFireButton, maneuverabilityButton, titleButton, enduranceButton, shipObject, bought1Button, bought2Button, bought3Button, bought4Button, bought5Button, bought6Button, bought7Button, bought8Button, bought9Button;
    TextView returnTextView, balanceTextView, titleText, rateOfFireText, maneuverabilityText, enduranceText;
    MovingBackgroundView backgroundView;
    MyGdxGame myGdxGame;
    int price;


    public GarageScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        price = 175;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleButton = new ButtonView(0, 1100, 780, 300, GameResources.BLACKOUT_TOP_IMG_PATH);
        returnTextView = new TextView(myGdxGame.commonWhiteFont, 563, 1130, "RETURN");
        shipObject = new ButtonView(280, 800, GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, GameResources.SHIP_IMG_PATH);
        titleText = new TextView(myGdxGame.commonWhiteFont, 240, 1200, "UPGRADE YOUR SHIP");
        rateOfFireButton = new ButtonView(160, 600, 400, 90, GameResources.BUTTON_LONG_BG_IMG_PATH);
        maneuverabilityButton = new ButtonView(160, 400, 400, 90, GameResources.BUTTON_LONG_BG_IMG_PATH);
        enduranceButton = new ButtonView(160, 200, 400, 90, GameResources.BUTTON_LONG_BG_IMG_PATH);
        rateOfFireText = new TextView(myGdxGame.commonBlackFont, 170, 620, "RATE OF FIRE                      175");
        maneuverabilityText = new TextView(myGdxGame.commonBlackFont, 170, 420, "MANEUVERABILITY            175");
        enduranceText = new TextView(myGdxGame.commonBlackFont, 170, 220, "ENDURANCE                        175");
        bought1Button = new ButtonView(165, 647, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought2Button = new ButtonView(280, 647, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought3Button = new ButtonView(400, 647, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought4Button = new ButtonView(165, 447, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought5Button = new ButtonView(280, 447, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought6Button = new ButtonView(400, 447, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought7Button = new ButtonView(165, 247, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought8Button = new ButtonView(280, 247, 120, 40, GameResources.STRIPES_IMG_PATH);
        bought9Button = new ButtonView(400, 247, 120, 40, GameResources.STRIPES_IMG_PATH);
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        titleButton.draw(myGdxGame.batch);
        returnTextView.draw(myGdxGame.batch);
        balanceTextView = new TextView(myGdxGame.commonWhiteFont, 47, 1130, "BALANCE: " + MemoryManager.loadExpense());
        balanceTextView.draw(myGdxGame.batch);
        titleText.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        rateOfFireButton.draw(myGdxGame.batch);
        rateOfFireText.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade1()) bought1Button.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade2()) bought2Button.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade3()) bought3Button.draw(myGdxGame.batch);
        maneuverabilityButton.draw(myGdxGame.batch);
        maneuverabilityText.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade4()) bought4Button.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade5()) bought5Button.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade6()) bought6Button.draw(myGdxGame.batch);
        enduranceButton.draw(myGdxGame.batch);
        enduranceText.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade7()) bought7Button.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade8()) bought8Button.draw(myGdxGame.batch);
        if (MemoryManager.loadUpgrade9()) bought9Button.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnTextView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (rateOfFireButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (MemoryManager.loadExpense() >= price && !MemoryManager.loadUpgrade1()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn){
                        myGdxGame.audioManager.upgradeSound.play(0.5f);}
                    MemoryManager.saveUpgrade1(true);
                }
                 else if (MemoryManager.loadUpgrade1() && MemoryManager.loadExpense() >= price && !MemoryManager.loadUpgrade2()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade2(true);
                }
                 else if (MemoryManager.loadExpense() >= price && MemoryManager.loadUpgrade2() && !MemoryManager.loadUpgrade3()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade3(true);
                }
            }
            if (maneuverabilityButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (MemoryManager.loadExpense() >= price && !MemoryManager.loadUpgrade4()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade4(true);
                }
                 else if (MemoryManager.loadExpense() >= price && MemoryManager.loadUpgrade4() && !MemoryManager.loadUpgrade5()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade5(true);
                }
                 else if (MemoryManager.loadExpense() >= price && MemoryManager.loadUpgrade5() && !MemoryManager.loadUpgrade6()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade6(true);
                }
            }
            if (enduranceButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (MemoryManager.loadExpense() >= price && !MemoryManager.loadUpgrade7()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade7(true);
                }
                else if (MemoryManager.loadExpense() >= price && MemoryManager.loadUpgrade7() && !MemoryManager.loadUpgrade8()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade8(true);
                }
                else if (MemoryManager.loadExpense() >= price && MemoryManager.loadUpgrade8() && !MemoryManager.loadUpgrade9()) {
                    MemoryManager.saveExpense(MemoryManager.loadExpense() - price);
                    if (myGdxGame.audioManager.isSoundsOn)
                        myGdxGame.audioManager.upgradeSound.play(0.5f);
                    MemoryManager.saveUpgrade9(true);
                }
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        titleButton.dispose();
        returnTextView.dispose();
        balanceTextView.dispose();
        shipObject.dispose();
        rateOfFireButton.dispose();
        maneuverabilityButton.dispose();
        enduranceButton.dispose();
        bought1Button.dispose();
        bought2Button.dispose();
        bought3Button.dispose();
        bought4Button.dispose();
        bought5Button.dispose();
        bought6Button.dispose();
        bought7Button.dispose();
        bought8Button.dispose();
        bought9Button.dispose();
    }
}

