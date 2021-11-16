package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends AnimatedEntity {
    // Tốc độ của các con quái là khác nhau
    protected double speed;

    //Class chứa hàm tìm đường đi
    protected findPath fp;

    // Làm mượt chuyển động của quái
    protected double MAX_STEPS;
    protected final double rest;
    protected double steps;
    protected int direction = -1;

    // Kiểm tra dead or alive
    protected boolean isDead = false;
    protected long dyingAnimatedTime = 1_000_000_000l;

    public Enemy(int x, int y, Image img, double speed) {
        super(x, y, img);
        this.speed = speed;

        MAX_STEPS = Sprite.DEFAULT_SIZE * 5 / speed;
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
            //_moving = true;
        } else {
            steps = 0;
            //_moving = false;
        }
    }


    public void move(double xa, double ya) {
        y += ya;
        x += xa;
    }

    public boolean canMove(int x, int y) {
        for (Entity entity: BombermanGame.stillObjects) {
            String className = entity.getClass().getTypeName();
            if (entity.existOnSquare(x, y) && !className.equals("uet.oop.bomberman.entities.staticEntities.Grass")) {
                return false;
            }
        }

        return true;
    }

    public void update() {
        if (!isDead) {
            calculateMove();
            chooseSprite();
        } else {
            afterDie();
        }

    }

    public abstract void chooseSprite();
    public abstract void afterDie();

    public int getXEnemy() {
        return this.x;
    }

    public int getYEnemy() {
        return this.y;
    }
}
