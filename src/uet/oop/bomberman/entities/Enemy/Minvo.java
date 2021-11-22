package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    public Minvo(int x, int y, Image img, double speed, Bomber bomber) {
        super(x, y, img, speed, 300);
        super.fp = new findPathAdvanced(bomber, this);
        this.MAX_STEPS = Sprite.DEFAULT_SIZE * 3;
    }

    @Override
    public void chooseSprite() {
        switch (direction) {
            //move right
            case 1:
                img = Sprite.movingSprite(Sprite.minvo_right1,
                        Sprite.minvo_right2,
                        Sprite.minvo_right3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            //move left
            case 3:
                img = Sprite.movingSprite(Sprite.minvo_left1,
                        Sprite.minvo_left2,
                        Sprite.minvo_left3,
                        animatedTime, 200_000_000).getFxImage();
                break;
            //move up and down
            default:
                img = Sprite.minvo_left1.getFxImage();
                break;
        }
    }

    @Override
    public void afterDie() {
        if (dyingAnimatedTime > 0) {
            img = Sprite.movingSprite(Sprite.minvo_dead,
                    Sprite.minvo_dead,
                    Sprite.minvo_dead,
                    dyingAnimatedTime,
                    500_000_000).getFxImage();
            dyingAnimatedTime -= BombermanGame.TIME_UNIT;
        } else {
            BombermanGame.increaseScore(point);
            setVisible(false);
//            BombermanGame.entities.add(new Doll(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.doll_right1.getFxImage(), this.speed / 2));
//            BombermanGame.entities.remove(this);
        }
    }
}
