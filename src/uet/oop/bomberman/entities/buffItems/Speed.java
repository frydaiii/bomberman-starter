package uet.oop.bomberman.entities.buffItems;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Speed extends Entity {
    public Speed(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_speed.getFxImage());
    }

    public void update() {

    }
}
