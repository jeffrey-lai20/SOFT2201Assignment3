package stickman.model.level;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.config.LevelSettings;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.impl.EnemyEntity;
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
  private ConfigurationProvider provider;

  public LevelImpl(ConfigurationProvider provider) {

    this.entitySpawner = new EntitySpawnerImpl();
    this.collisionHandler = new CollisionHandler();
    this.entities = new ArrayList<>();

    LevelSettings levelData = provider.getLevelData();
    this.width = levelData.getWidth();
    this.floorHeight = levelData.getFloorHeight();
    this.target = levelData.getTarget();

    this.provider = provider;
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
    int repeat;
    if ("DEATH".equals(outcome.toUpperCase())) {
//      System.out.println("\n=== YOU DIED! ===");
//      System.out.println("Oops! You should be more careful next time.");
      repeat = 17;
      isDead = true;
      stopMoving();
    } else {
//      System.out.println("\n=== YOU WON! ===");
//      System.out.println("Congratulations! You finished the level.");
      repeat = 16;
      levelNum++;
      isFinish = true;
    }

//    Duration elapsed = Duration.between(startTime, Instant.now());
//    System.out.println("Your time: " + prettyTimeFormat(elapsed));
//    System.out.println("=".repeat(repeat));

//    System.exit(0);
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
  public ConfigurationProvider getProvider() {
    return provider;
  }
}
