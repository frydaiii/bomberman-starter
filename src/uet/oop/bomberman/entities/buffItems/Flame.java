package uet.oop.bomberman.entities.buffItems;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    public Flame(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.powerup_flames.getFxImage());
    }

    public void update() {

    }
}
