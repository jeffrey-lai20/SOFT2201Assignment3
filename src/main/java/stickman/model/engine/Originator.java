package stickman.model.engine;

import stickman.config.ConfigurationProvider;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.factory.EntityFactory;
import stickman.model.entity.factory.EntityFactoryImpl;
import stickman.model.entity.impl.AbstractEntity;
import stickman.model.entity.impl.CloudEntity;
import stickman.model.entity.impl.EnemyEntity;
import stickman.model.entity.impl.HeroEntity;
import stickman.model.entity.spawner.EntitySpawner;
import stickman.model.level.Level;
import stickman.model.level.LevelImpl;
import stickman.model.level.collision.CollisionHandler;
import stickman.view.GameManager;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Originator {

    private Level state;

    public void setState(Level state) {
        this.state = initState(state);
    }

    public Level getState() {
        return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }

    static Level initState(Level state) {
//        ConfigurationProvider provider = state.getProvider();
//        System.out.println(provider.getLevelData().getEntityData().toJSONString());
        List<Entity> currentEntity = new ArrayList<>();
        for (int i = 0; i < state.getEntities().size(); i++) {
            Entity currentEnt = state.getEntities().get(i);
//
            if (currentEnt.getStrategy() != null) {
                Entity newEnt = new EnemyEntity(currentEnt.getImagePath(), currentEnt.getXPos(), currentEnt.getYPos(), currentEnt.getWidth(), currentEnt.getHeight(), currentEnt.getLayer(), currentEnt.getStrategy());
                currentEntity.add(newEnt);
            } else if (!Double.isNaN(currentEnt.getVelocity())) {
                double vel = (currentEnt.getVelocity()/GameManager.FRAMEFRATE_MS)*1000;
                Entity newEnt = new CloudEntity(currentEnt.getImagePath(), currentEnt.getXPos(), currentEnt.getYPos(), currentEnt.getWidth(), currentEnt.getHeight(), currentEnt.getLayer(), vel);
                currentEntity.add(newEnt);
            } else {
                currentEntity.add(currentEnt);
            }


        }

        EntitySpawner currentEntitySpawner = state.getEntitySpawner();
        CollisionHandler currentCollisionHandler = state.getCollisionHandler();
// HEY THIS IS RIGHT
        Controllable currentHero = state.getHero();
        Controllable savedHero = new HeroEntity(currentHero.getImagePath(), currentHero.getXPos(), currentHero.getYPos(), currentHero.getWidth(), currentHero.getHeight(), currentHero.getLayer(), currentHero.getLives());
      // HEY THIS IS RIGHT
        Instant currentStartTime = Instant.now().minusSeconds(state.getStartTime().getEpochSecond());
        int currentLevelNum = state.getLevelNum();
        boolean currentIsFinish = state.getIsFinish();
        double currentTarget = state.getTarget();
        boolean currentIsDead = state.getIsDead();
        boolean currentEnemyKill = state.getEnemyKill();

        Level currentLevel = new LevelImpl(currentEntitySpawner, currentCollisionHandler, currentEntity,
                savedHero, state.getWidth(), state.getFloorHeight(), currentStartTime, currentLevelNum,
                currentIsFinish, currentTarget, currentIsDead, currentEnemyKill);
        return currentLevel;
    }
}
