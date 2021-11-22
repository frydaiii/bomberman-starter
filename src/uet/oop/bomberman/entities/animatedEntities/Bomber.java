package uet.oop.bomberman.entities.animatedEntities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Bomber extends AnimatedEntity {
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String CENTER = "CENTER";

    private int maxBombs;
    private int maxExplodeRange;
    private String direction;
    private ArrayList<Bomb> plannedBomb;

    private boolean alive;
    private long dyingAnimatedTime = 1_000_000_000l;

    /** buff state; -1 is inactive. */
    private long bombsBuff = -1;

    /** time limit of buff. */
    private long bombsBuffTime = 5_000_000_000l; // 5s

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        direction = CENTER;
        maxBombs = 1;
        maxExplodeRange = 2;
        plannedBomb = new ArrayList<>();
        alive = true;
    }

    public void controlPressing(KeyCode keyCode) {
        switch (keyCode) {
            case RIGHT:
                setDirection(Bomber.RIGHT);
                break;
            case LEFT:
                setDirection(Bomber.LEFT);
                break;
            case UP:
                setDirection(Bomber.UP);
                break;
            case DOWN:
                setDirection(Bomber.DOWN);
                break;
            case SPACE:
                planBomb();
                break;
        }
    }

    public void controlReleasing(KeyCode keyCode) {
        if (keyCode == KeyCode.UP ||
                keyCode == KeyCode.DOWN ||
                keyCode == KeyCode.LEFT ||
                keyCode == KeyCode.RIGHT) {
            setDirection(Bomber.CENTER);
        }
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private void moveTo(int x, int y) {
        if (!alive) {
            return;
        }
        for (Entity entity: BombermanGame.stillObjects) {
            String className = entity.getClass().getTypeName();
            if (className.contains("Grass")) {
                continue;
            } else {
                if (entity.existOnSquare(x, y)) {
                    int diffX = this.x % Sprite.SCALED_SIZE;
                    int diffY = this.y % Sprite.SCALED_SIZE;
                    if (diffX < 5 && diffY == 0) {
                        this.x -= diffX;
                    }
                    if (diffY < 5 && diffX == 0) {
                        this.y -= diffY;
                    }
                    if (diffX > 25 && diffY == 0) {
                        this.x += Sprite.SCALED_SIZE - diffX;
                    }
                    if (diffY > 25 && diffX == 0) {
                        this.y += Sprite.SCALED_SIZE - diffY;
                    }
                    return;
                }
            }
        }

        for (Entity entity: BombermanGame.entities) {
            String className = entity.getClass().getTypeName();
//            if (className.contains("Bomber") ||
//                className.contains("Bomb") ||
//                !entity.isVisible()) {
//                continue;
//            }
//            if (!className.contains("flames") ||
////                !className.contains("buffItems") ||
//                !entity.isVisible()) {
//                continue;
//            }
            if (className.contains("flames") && entity.existOnSquare(x, y) &&
                entity.isVisible()) {
                setAlive(false);
            }

            if (entity.existOnSquare(x, y)) {
                if (className.contains("buffItems")) {
                    if (className.contains("Bomb") && entity.isVisible()) {
                        maxBombs++;
//                        bombsBuff = 0; // active buff
                        entity.setVisible(false);
                    }
                    if (className.contains("Flame") && entity.isVisible()) {
                        maxExplodeRange++;
//                        bombsBuff = 0; // active buff
                        entity.setVisible(false);
                    }
                }
//                return;
            }
        }
        this.x = x;
        this.y = y;
    }

    public void moveRight() {
        moveTo(x + BombermanGame.MOVING_UNIT, y);
        img = Sprite.movingSprite(Sprite.player_right,
                Sprite.player_right_1,
                Sprite.player_right_2,
                animatedTime, 200_000_000).getFxImage();
    }

    public void moveLeft() {
        moveTo(x - BombermanGame.MOVING_UNIT, y);
        img = Sprite.movingSprite(Sprite.player_left,
                Sprite.player_left_1,
                Sprite.player_left_2,
                animatedTime, 200_000_000).getFxImage();
    }

    public void moveUp() {
        moveTo(x, y - BombermanGame.MOVING_UNIT);
        img = Sprite.movingSprite(Sprite.player_up,
                Sprite.player_up_1,
                Sprite.player_up_2,
                animatedTime, 200_000_000).getFxImage();
    }

    public void moveDown() {
        moveTo(x, y + BombermanGame.MOVING_UNIT);
        img = Sprite.movingSprite(Sprite.player_down,
                Sprite.player_down_1,
                Sprite.player_down_2,
                animatedTime, 200_000_000).getFxImage();
    }

    public void planBomb() {
        int numOfActiveBombs = (int) plannedBomb.stream()
                .filter(bomb -> !bomb.isExploded())
                .count();
        if (maxBombs <= numOfActiveBombs) {
            return;
        }

        int xBomb = (int) Math.round((1.0 * x / Sprite.SCALED_SIZE));
        int yBomb = (int) Math.round((1.0 * y / Sprite.SCALED_SIZE));
        Bomb bomb = new Bomb(xBomb, yBomb, maxExplodeRange);
        BombermanGame.updateQueue.add(bomb);
        plannedBomb.add(bomb);
    }

    private void updateBuff() {
        if (bombsBuff != -1) { // -1 is inactive state
            bombsBuffTime -= BombermanGame.TIME_UNIT;
        }
        if (bombsBuffTime < 0) { // expiration of activation
            bombsBuff = -1;
            maxBombs = 1;
            bombsBuffTime = 5_000_000_000l;
        }
    }

    @Override
    public void update() {
//        updateBuff();

        if (alive) {
            animate();
        } else {
            if (dyingAnimatedTime > 0) {
                img = Sprite.movingSprite(Sprite.player_dead1,
                        Sprite.player_dead2,
                        Sprite.player_dead3,
                        dyingAnimatedTime,
                        500_000_000).getFxImage();
                dyingAnimatedTime -= BombermanGame.TIME_UNIT;
            } else {
                setVisible(false);
            }
        }

        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            case CENTER:
                break;
        }
    }
}
