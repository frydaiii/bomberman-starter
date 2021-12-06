package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntity;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.soundEffect.Sound;

public abstract class Enemy extends AnimatedEntity {
    protected int point;
    // Tốc độ của các con quái là khác nhau
    protected double speed;

    //Class chứa hàm tìm đường đi
    protected findPath fp;

    // Làm mượt chuyển động của quái
    protected double MAX_STEPS;
    protected final double rest;
    protected double steps;
    protected int direction = -1;
    protected boolean moving = true;

    // Kiểm tra dead or alive
    protected boolean alive = false;
    protected long dyingAnimatedTime = 1_000_000_000l;
    protected boolean throughtWall = false;


    public Enemy(int x, int y, Image img, double speed, int point) {
        super(x, y, img);
        this.speed = speed;
        this.point = point;

        MAX_STEPS = 32;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = MAX_STEPS;
    }

    public void calculateMove() {
        int xa = 0, ya = 0;
        if(steps <= 0) {
            direction = fp.calculateDirection();
            steps = MAX_STEPS;
        }

        /*
        1: move right
        3: move left
        0: move down
        2: move up
         */
        if(direction == 0) ya--;
        if(direction == 2) ya++;
        if(direction == 3) xa--;
        if(direction == 1) xa++;

        if(canMove(x + xa, y) && canMove(x, y + ya)) {
            steps -= 1 + rest;
            move(xa * speed, ya * speed);
            moving = true;
        } else {
            steps = 0;
            moving = false;
        }
    }

    public void calculateMove2() {
        int left = -1, right = 1, up = -1, down = 1;
        int xa, ya;


    }


    public void move(double xa, double ya) {
        if (!alive) {
            return;
        }
        y += ya;
        x += xa;
    }

    public boolean canMove(int x, int y) {
        if (throughtWall) {
            for (Entity entity : BombermanGame.entities) {
                if (entity.existOnSquare(x, y) && entity.isVisible()) {
                    String className = entity.getClass().getTypeName();
                    if (className.contains("flames")) {
                        setAlive(false);
                    }
                    if (className.contains("Bomb")) {
                        return false;
                    }
                }

            }
        } else {
            for (Entity entity : BombermanGame.stillObjects) {
                String className = entity.getClass().getTypeName();
                if (entity.existOnSquare(x, y) && !className.contains("Grass")) {
                    return false;
                }
            }

//        for (Entity entity: BombermanGame.entities) {
//            if (this.existOnSquare(entity.getX(), entity.getY()) &&
//                    entity.getClass().getTypeName().contains("Bomber")) {
//                ((Bomber) entity).setAlive(false);
//                break;
//            }
//        }

            for (Entity entity : BombermanGame.entities) {
                if (entity.existOnSquare(x, y) && entity.isVisible()) {
                    String className = entity.getClass().getTypeName();
                    if (className.contains("flames")) {
                        setAlive(false);
                    }
                    if (className.contains("Bomber")) {
                        ((Bomber) entity).setAlive(false);
                    }
                    if (className.contains("Bomb") || className.contains("Brick")) {
                        return false;
                    }
                }
            }
        }


        return true;
    }

    public void update() {
        if (alive) {
            animate();
            calculateMove();
            chooseSprite();
        } else {
            afterDie();
        }
    }

    public abstract void chooseSprite();
    public abstract void afterDie();


    public void setAlive(boolean alive) {
        this.alive = alive;
        Sound.death();
    }
}
