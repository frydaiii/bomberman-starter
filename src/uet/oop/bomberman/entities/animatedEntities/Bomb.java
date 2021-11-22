package uet.oop.bomberman.entities.animatedEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity{
    private final long limitedTime = 2_000_000_000l; // 2s
    private boolean exploded;
    private int explodeRange;


    public Bomb(int xUnit, int yUnit, int explodeRange) {
        super(xUnit, yUnit, Sprite.bomb.getFxImage());
        this.explodeRange = explodeRange;
        exploded = false;
    }

    public boolean isExploded() {
        return exploded;
    }

    /** Add an explosion to position (x, y)
     * of the unit coordinates (not canvas coordinates),
     * and add it into entities */

    public void explode() {
        ExplosionController explosion = new ExplosionController(xUnit, yUnit, explodeRange);
        explosion.begin();
    }

    @Override
    public void update() {
        animate();
        if (animatedTime < limitedTime) {
            img = Sprite.movingSprite(Sprite.bomb,
                    Sprite.bomb_1,
                    Sprite.bomb_2,
                    animatedTime, 500_000_000).getFxImage();
        } else if (!exploded) {
            setVisible(false);
            explode();
            exploded = true;
        }
    }
}
