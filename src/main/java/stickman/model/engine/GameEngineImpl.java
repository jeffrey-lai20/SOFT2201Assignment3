package stickman.model.engine;

import stickman.config.ConfigurationProvider;
import stickman.model.level.Level;
import stickman.model.level.LevelImpl;

import java.io.FileNotFoundException;
import java.net.FileNameMap;
import java.time.Duration;
import java.time.Instant;

/** The implementation class of the GameEngine interface. */
public class GameEngineImpl implements GameEngine {

  private ConfigurationProvider provider;
  private Level currentLevel;
  private boolean winner = false;
  private double totalScore = 0;
  private double currentScore = 0;
  private double scoreBuffer = 0;
  private double savedTotalScore = 0;
  private double savedCurrentScore = 0;
  private Originator originator = new Originator();
  private CareTaker careTaker = new CareTaker();
  private double levels;
  private int currentLevelNum = 0;

  public GameEngineImpl(String configPath) {
    provider = new ConfigurationProvider(configPath);
    currentLevel = new LevelImpl(provider);
    this.levels = currentLevel.getLevels();
    startLevel();
  }

  @Override
  public Level getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public void startLevel() {
    if (!winner) {
      if (currentLevelNum == 0) {
        totalScore = 0;
        currentScore = currentLevel.getTarget();
        currentLevel.start(provider);
        this.currentLevelNum = 1;
      } else if (currentLevelNum < levels) {
        currentLevelNum++;
        char levelNum = (char) (currentLevelNum + '0');
        provider = new ConfigurationProvider("level" + levelNum + ".json");
        totalScore += getLevelScore();
        currentLevel = new LevelImpl(provider);
        currentLevel.setLevelNum(currentLevelNum);
        currentScore = currentLevel.getTarget();
        currentLevel.start(provider);
        System.out.println("It is now" + currentLevelNum);
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
      currentScore = 0;
      scoreBuffer = currentLevel.prettyTimeFormat(dur);
      return 0;
    }
    return currentScore-currentLevel.prettyTimeFormat(dur);
  }

  @Override
  public void killScore() {
    if (currentScore <= 0) currentScore = 100 + scoreBuffer;
    else currentScore += 100;
  }

  @Override
  public void saveGame() {
    originator.setState(currentLevel);
    careTaker.add(originator.saveStateToMemento());
    savedCurrentScore = currentScore;
    savedTotalScore = totalScore;
  }

  @Override
  public void quickLoad() {
    if (!isFinish() && !currentLevel.getIsDead()) {
      try {
        originator.getStateFromMemento(careTaker.get(careTaker.size()-1));
        currentLevel = originator.getState();
        currentScore = savedCurrentScore;
        totalScore = savedTotalScore;
      } catch (Exception e) {
        System.out.println("Error: Save needs to be used before load. No valid saved state found.");
      }
    }
  }
}
