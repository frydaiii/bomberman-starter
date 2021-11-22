package uet.oop.bomberman.entities.animatedEntities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.animatedEntities.flames.*;

public class ExplosionController {
    private int xUnit;
    private int yUnit;
    private int maxRange;

    public ExplosionController(int xUnit, int yUnit, int maxRange) {
        this.xUnit = xUnit;
        this.yUnit = yUnit;
        this.maxRange = maxRange;
    }

    public void begin() {
        Explosion center = new Explosion(xUnit, yUnit);
        if (center.canExplode()) {
            BombermanGame.updateQueue.add(center);
        }
        boolean breakTop = false;
        boolean breakDown = false;
        boolean breakLeft = false;
        boolean breakRight = false;
        for (int i = 0; i < maxRange; i++) {
            if (!breakTop) {
                int range = i + 1;
                Explosion top;

                if (range == maxRange) {
                    top = new TopLast(xUnit, yUnit - maxRange);
                } else {
                    top = new Vertical(xUnit, yUnit - range);
                }
                if (top.canExplode()) {
                    BombermanGame.updateQueue.add(top);
                } else {
                    breakTop = true;
                }
            }
        }
        for (int i = 0; i < maxRange; i++) {
            if (!breakDown) {
                int range = i + 1;
                Explosion down;

                if (range == maxRange) {
                    down = new DownLast(xUnit, yUnit + maxRange);
                } else {
                    down = new Vertical(xUnit, yUnit + range);
                }
                if (down.canExplode()) {
                    BombermanGame.updateQueue.add(down);
                } else {
                    breakDown = true;
                }
            }
        }
        for (int i = 0; i < maxRange; i++) {
            if (!breakRight) {
                int range = i + 1;
                Explosion right;

                if (range == maxRange) {
                    right = new RightLast(xUnit + maxRange, yUnit);
                } else {
                    right = new Horizontal(xUnit + range, yUnit);
                }
                if (right.canExplode()) {
                    BombermanGame.updateQueue.add(right);
                } else {
                    breakRight = true;
                }
            }
        }
        for (int i = 0; i < maxRange; i++) {
            if (!breakLeft) {
                int range = i + 1;
                Explosion left;

                if (range == maxRange) {
                    left = new LeftLast(xUnit - maxRange, yUnit);
                } else {
                    left = new Horizontal(xUnit - range, yUnit);
                }
                if (left.canExplode()) {
                    BombermanGame.updateQueue.add(left);
                } else {
                    breakLeft = true;
                }
            }
        }
    }
}
