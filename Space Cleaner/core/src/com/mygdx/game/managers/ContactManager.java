package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.mygdx.game.mainSettings.GameSession;
import com.mygdx.game.mainSettings.GameSettings;
import com.mygdx.game.objects.BonusObject;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.ShipObject;

import javax.swing.text.html.parser.Entity;

public class ContactManager {
    World world;

    public ContactManager(World world) {
        this.world = world;
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();

                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;

                if ((cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.BULLET_BIT)
                        || (cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.BULLET_BIT)) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                    GameSession.destructionRegistration();
                }
                if ((cDef == GameSettings.TRASH_BIT && cDef2 == GameSettings.SHIP_BIT)
                        || (cDef2 == GameSettings.TRASH_BIT && cDef == GameSettings.SHIP_BIT) ||
                        (cDef == GameSettings.METEOR_BIT && cDef2 == GameSettings.SHIP_BIT) ||
                        (cDef2 == GameSettings.METEOR_BIT && cDef == GameSettings.SHIP_BIT)) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                }
                if ((cDef == GameSettings.METEOR_BIT && cDef2 == GameSettings.BULLET_BIT)
                        || (cDef2 == GameSettings.METEOR_BIT && cDef == GameSettings.BULLET_BIT)) {
                    ((GameObject) fixA.getUserData()).hit();
                    ((GameObject) fixB.getUserData()).hit();
                    GameSession.destructionCometsRegistration();
                }
                if ((cDef == GameSettings.SHIP_BIT && cDef2 == GameSettings.BONUS_BIT) ||
                        (cDef2 == GameSettings.SHIP_BIT && cDef == GameSettings.BONUS_BIT)) {
                    ((GameObject) fixA.getUserData()).contact();
                    ((GameObject) fixB.getUserData()).contact();
                    if (!GameSession.spacePirateMode) {
                        if (ShipObject.isUsedHill()) {
                            GameSession.unusedBonusRegistration();
                        }
                        ((GameObject) fixA.getUserData()).check();
                        ((GameObject) fixB.getUserData()).check();
                    } else {
                        GameSession.coinsUsed();
                    }

                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                Fixture fixA = contact.getFixtureA();
                Fixture fixB = contact.getFixtureB();
                int cDef = fixA.getFilterData().categoryBits;
                int cDef2 = fixB.getFilterData().categoryBits;
                    if((cDef == GameSettings.BONUS_BIT  && cDef2 == GameSettings.BULLET_BIT) ||
                            (cDef2 == GameSettings.BONUS_BIT && cDef == GameSettings.BULLET_BIT) ||
                            (cDef == GameSettings.BONUS_BIT && cDef2 == GameSettings.TRASH_BIT) ||
                            (cDef2 == GameSettings.BONUS_BIT && cDef == GameSettings.TRASH_BIT) ||
                            (cDef2 == GameSettings.METEOR_BIT && cDef == GameSettings.BONUS_BIT) ||
                            (cDef == GameSettings.METEOR_BIT && cDef2 == GameSettings.BONUS_BIT))
                        contact.setEnabled(false);
                }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        }
        );
    }
}






