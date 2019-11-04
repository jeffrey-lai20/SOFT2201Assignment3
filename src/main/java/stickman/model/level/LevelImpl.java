package stickman.model.level;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.config.LevelSettings;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.spawner.EntitySpawner;
import stickman.model.entity.spawner.EntitySpawnerImpl;
import stickman.model.level.collision.CollisionHandler;

/** The implementation class of the Level interface. */
public class LevelImpl implements Level {

  private EntitySpawner entitySpawner;
  private CollisionHandler collisionHandler;

  private List<Entity> entities;
  private Controllable hero;

  private double width;
  private double floorHeight;
  private Instant startTime;

  private int levelNum = 1;
  private boolean isFinish = false;
  private double target;
  private boolean isDead = false;
  private boolean enemyKill = false;
  private double levels;

  public LevelImpl(ConfigurationProvider provider) {

    this.entitySpawner = new EntitySpawnerImpl();
    this.collisionHandler = new CollisionHandler();
    this.entities = new ArrayList<>();

    LevelSettings levelData = provider.getLevelData();
    this.width = levelData.getWidth();
    this.floorHeight = levelData.getFloorHeight();
    this.target = levelData.getTarget();
    this.levels = levelData.getLevels();
  }

  public LevelImpl(EntitySpawner entitySpawner, CollisionHandler collisionHandler, List<Entity> entities,
                    Controllable hero, double width, double floorHeight, Instant startTime, int levelNum,
                    boolean isFinish, double target, boolean isDead, boolean enemyKill) {
    this.entitySpawner = entitySpawner;
    this.collisionHandler = collisionHandler;

    this.entities = new ArrayList<>();
    this.hero = hero;
    this.entities.add(hero);
    entities.remove(0);
    this.entities.addAll(entities);

    this.width = width;
    this.floorHeight = floorHeight;
    this.startTime = startTime;

    this.levelNum = levelNum;
    this.isFinish = isFinish;
    this.target = target;
    this.isDead = isDead;
    this.enemyKill = enemyKill;
  }

  @Override
  public void start(ConfigurationProvider provider) {

    spawnHero(provider);
    spawnEntities(provider);
    populateScene(provider);

    startTime = Instant.now();
    isFinish = false;
  }

  @Override
  public void finish(String outcome) {
    if ("DEATH".equals(outcome.toUpperCase())) {
      isDead = true;
      stopMoving();
    } else {
      levelNum++;
      isFinish = true;
    }
  }

  @Override
  public List<Entity> getEntities() {
    return this.entities;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getFloorHeight() {
    return this.floorHeight;
  }

  @Override
  public double getHeroX() {
    return hero.getXPos();
  }

  @Override
  public double getHeroY() {
    return hero.getYPos();
  }

  @Override
  public Instant getStartTime() {
    return startTime;
  }

  @Override
  public boolean jump() {
    return hero.setJumping(true);
  }

  @Override
  public boolean moveLeft() {
    return hero.setMovingLeft(true);
  }

  @Override
  public boolean moveRight() {
    return hero.setMovingRight(true);
  }

  @Override
  public boolean stopMoving() {
    hero.setMovingRight(false);
    hero.setMovingLeft(false);
    return true;
  }

  @Override
  public void tick() {
    entities.forEach(e -> e.move(this));
    collisionHandler.detectCollisions(this, hero);
  }

  private void spawnHero(ConfigurationProvider provider) {
    this.hero = entitySpawner.createHero(provider, this);
    entities.add(this.hero);
  }

  private void spawnEntities(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createEntities(provider, this));
  }

  private void populateScene(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createBackgroundEntities(provider, this));
  }

  @Override
  public Long prettyTimeFormat(Duration duration) {
    return duration.getSeconds();
//    return duration.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
  }

  @Override
  public int getLevelNum() {
    return levelNum;
  }

  @Override
  public void setLevelNum(int levelNum) {
    this.levelNum = levelNum;
  }

  @Override
  public boolean isFinish() {
    return isFinish;
  }

  @Override
  public double getTarget() {
    return target;
  }

  @Override
  public boolean getIsDead() {
    return isDead;
  }

  @Override
  public boolean enemyKill() {
    return enemyKill;
  }

  @Override
  public void killedEnemy() {
    enemyKill = true;
  }

  @Override
  public void noKill() {
    enemyKill = false;
  }

  @Override
  public EntitySpawner getEntitySpawner() {
    return entitySpawner;
  }

  @Override
  public CollisionHandler getCollisionHandler() {
    return collisionHandler;
  }

  @Override
  public Controllable getHero() {
    return hero;
  }

  @Override
  public boolean getIsFinish() {
    return isFinish;
  }

  @Override
  public boolean getEnemyKill() {
    return enemyKill;
  }

  @Override
  public double getLevels() {
    return levels;
  }
}
