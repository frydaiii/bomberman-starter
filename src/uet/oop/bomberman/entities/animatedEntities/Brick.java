package uet.oop.bomberman.entities.animatedEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends AnimatedEntity {
    private long animationTime = 500_000_000l;
    private boolean broken;

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public Brick(int x, int y) {
        super(x, y, Sprite.brick.getFxImage());
        broken = false;
    }

    @Override
    public void update() {
        if (!broken) {
            animate();
        } else {
            if (animationTime > 0) {
                img = Sprite.movingSprite(Sprite.brick_exploded,
                        Sprite.brick_exploded1,
                        Sprite.brick_exploded2,
                        animationTime,
                        250_000_000).getFxImage();
                animationTime -= BombermanGame.TIME_UNIT;
            } else {
                setVisible(false);
            }
        }
    }
}