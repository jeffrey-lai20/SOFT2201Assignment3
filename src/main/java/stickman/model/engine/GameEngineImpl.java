package stickman.model.engine;

import stickman.config.ConfigurationProvider;
import stickman.model.level.Level;
import stickman.model.level.LevelImpl;

import java.time.Duration;
import java.time.Instant;

/** The implementation class of the GameEngine interface. */
public class GameEngineImpl implements GameEngine {

  private ConfigurationProvider provider;
  private Level currentLevel;
  private boolean winner = false;
  private double totalScore = 0;
  private double currentScore = 0;

  private Originator originator = new Originator();
  private CareTaker careTaker = new CareTaker();

  public GameEngineImpl(String configPath) {

    provider = new ConfigurationProvider(configPath);
    currentLevel = new LevelImpl(provider);

    startLevel();
  }

  @Override
  public Level getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public void startLevel() {
    if (!winner) {
      if (currentLevel.getLevelNum() == 1) {
        totalScore = 0;
        currentScore = currentLevel.getTarget();
        currentLevel.start(provider);
      } else if (currentLevel.getLevelNum() == 2) {

        provider = new ConfigurationProvider("level2.json");
//      totalScore = totalScore + currentLevel.getTarget();
        totalScore += getLevelScore();
        currentLevel = new LevelImpl(provider);
        currentLevel.setLevelNum(2);
        currentScore = currentLevel.getTarget();
        currentLevel.start(provider);
      } else if (currentLevel.getLevelNum() == 3) {
        provider = new ConfigurationProvider("level3.json");
//      totalScore = totalScore + currentLevel.getTarget();
        totalScore += getLevelScore();

        currentLevel = new LevelImpl(provider);
        currentLevel.setLevelNum(3);
        currentScore = currentLevel.getTarget();
        currentLevel.start(provider);
      } else {
        totalScore += getLevelScore();
        winner = true;
      }
    }

  }

  @Override
  public boolean jump() {
    return currentLevel.jump();
  }

  @Override
  public boolean moveLeft() {
    return currentLevel.moveLeft();
  }

  @Override
  public boolean moveRight() {
    return currentLevel.moveRight();
  }

  @Override
  public boolean stopMoving() {
    return currentLevel.stopMoving();
  }

  @Override
  public void tick() {
    if (currentLevel.enemyKill()) {
      killScore();
      currentLevel.noKill();
    }
    currentLevel.tick();

  }

  @Override
  public boolean isFinish() {
    return currentLevel.isFinish();
  }

  @Override
  public boolean getWinner() {
    return winner;
  }

  @Override
  public double getTotalScore() {
    return totalScore;
  }

  @Override
  public double getLevelScore() {
    Duration dur = Duration.between(currentLevel.getStartTime(), Instant.now());
    if (currentScore-currentLevel.prettyTimeFormat(dur) <= 0) {
      return 0;
    }
    return currentScore-currentLevel.prettyTimeFormat(dur);
  }

  @Override
  public void killScore() {
    currentScore += 100;
  }

  @Override
  public void saveGame() {
    originator.setState(currentLevel);
    careTaker.add(originator.saveStateToMemento());
  }

  @Override
  public void quickLoad() {
    originator.getStateFromMemento(careTaker.get(careTaker.size()-1));
    this.currentLevel = originator.getState();
    currentLevel.start(currentLevel.getProvider());
  }
}
