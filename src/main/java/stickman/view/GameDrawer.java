package stickman.view;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.junit.Test;
import stickman.model.engine.GameEngine;
import stickman.model.entity.Entity;
import javafx.scene.text.Text;


/** A class for drawing objects onto the game window. */
public class GameDrawer {

  private GameEngine model;
  private Pane pane;
  private final int width;
  private final int height;

  private BackgroundDrawer backgroundDrawer;

  private double xViewportOffset = 0.0;
  private double yViewportOffset = 0.0;
  private static final double Y_VIEWPORT_MARGIN = 200.0;
  private static final double VIEWPORT_MARGIN = 280.0;

  private List<EntityView> entityViews;

  private Text time, lives, score, totalScore;
  private int quit = 0;

  public GameDrawer(GameEngine model, Pane pane, BackgroundDrawer backgroundDrawer, int width, int height) {

    this.model = model;
    this.pane = pane;
    this.backgroundDrawer = backgroundDrawer;
    this.width = width;
    this.height = height;

    this.entityViews = new ArrayList<>();

    time = new Text();
    lives = new Text();
    score = new Text();
    totalScore = new Text();
    this.pane.getChildren().addAll(time, lives, score, totalScore);
  }

  /** Draws and updates all currently displayed EntityView objects. */
  public void draw() {
    model.tick();

    List<Entity> entities = model.getCurrentLevel().getEntities();

    for (EntityView entityView : entityViews) {
      entityView.markForDelete();
    }

    double heroXPos = model.getCurrentLevel().getHeroX();
    double heroYPos = model.getCurrentLevel().getHeroY();

    heroXPos -= xViewportOffset;

    if (heroXPos < VIEWPORT_MARGIN) {
      if (xViewportOffset >= 0) { // Don't go further left than the start of the level
        xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
        if (xViewportOffset < 0) {
          xViewportOffset = 0;
        }
      }
    } else if (heroXPos > width - VIEWPORT_MARGIN) {
      xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
    }

    if (heroYPos <= Y_VIEWPORT_MARGIN) {
      yViewportOffset = heroYPos - Y_VIEWPORT_MARGIN;
    } else {
      yViewportOffset = 0;
    }

    backgroundDrawer.update(xViewportOffset);

    for (Entity entity : entities) {
      boolean notFound = true;
      for (EntityView view : entityViews) {
        if (view.matchesEntity(entity)) {
          notFound = false;
          view.updateView(xViewportOffset, yViewportOffset);
          break;
        }
      }
      if (notFound) {
        EntityView entityView = new EntityViewImpl(entity);
        entityViews.add(entityView);
        pane.getChildren().add(entityView.getNode());
      }
    }

    for (EntityView entityView : entityViews) {
      if (entityView.isMarkedForDelete()) {
        pane.getChildren().remove(entityView.getNode());
      }
    }
    entityViews.removeIf(EntityView::isMarkedForDelete);



    if (model.isFinish()) {
      model.startLevel();
      if (model.getWinner()) {
        model.startLevel();
        Text winner = new Text();
        winner.setText("Winner");
        winner.setFont(new Font(50));
        winner.setX(width / 2 - 100);
        winner.setY(height / 2);
        pane.getChildren().add(winner);
        DecimalFormat df = new DecimalFormat("#");
        totalScore.setText("Total Score: " + df.format(model.getTotalScore()));
        quit++;
      }
    } else if (model.getCurrentLevel().getIsDead()) {
      Text loser = new Text();
      loser.setText("Game Over");
      loser.setFont(new Font(50));
      loser.setX(width/2 - 125);
      loser.setY(height/2);
      pane.getChildren().add(loser);
      quit++;
    } else {
      Duration dur = Duration.between(model.getCurrentLevel().getStartTime(), Instant.now());
      DecimalFormat df = new DecimalFormat("#");
      time.setText("Time: " + model.getCurrentLevel().prettyTimeFormat(dur));
      time.setFont(new Font(20));
      time.setX(30);
      time.setY(30);
      lives.setText("Lives: Do I need to show? idk");
      lives.setFont(new Font(20));
      lives.setX(width-100);
      lives.setY(30);
      score.setText("Score: " + df.format(model.getLevelScore()));
      score.setFont(new Font(20));
      score.setX(150);
      score.setY(30);
      totalScore.setText("Total Score: " + df.format(model.getTotalScore()));
      totalScore.setFont(new Font(20));
      totalScore.setX(300);
      totalScore.setY(30);
    }
    if (quit == 300) {
      System.exit(0);
    }
  }

  public void removeView(Entity e) {
    entityViews.removeIf(v -> v.matchesEntity(e));
  }

  // exposed for testing
  public double getxViewportOffset() {
    return xViewportOffset;
  }

  // exposed for testing
  public double getyViewportOffset() {
    return yViewportOffset;
  }
}
