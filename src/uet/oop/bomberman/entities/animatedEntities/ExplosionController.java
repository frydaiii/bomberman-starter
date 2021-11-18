package uet.oop.bomberman.entities.animatedEntities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.animatedEntities.flames.*;

public class ExplosionController {
    private int xUnit;
    private int yUnit;
    private int maxRange;

    public ExplosionController(int xUnit, int yUnit) {
        this.xUnit = xUnit;
        this.yUnit = yUnit;
        maxRange = 2;
    }

    public void begin() {
        Explosion center = new Explosion(xUnit, yUnit);
        BombermanGame.updateQueue.add(center);
        boolean breakUp = false;
        boolean breakDown = false;
        boolean breakLeft = false;
        boolean breakRight = false;
        for (int i = 0; i < maxRange; i++) {
            int range = i + 1;
            Explosion up, down, left, right;

            if (range == maxRange) {
                up = new TopLast(xUnit, yUnit - maxRange);
                down = new DownLast(xUnit, yUnit + maxRange);
                left = new LeftLast(xUnit - maxRange, yUnit);
                right = new RightLast(xUnit + maxRange, yUnit);
            } else {
                up = new Vertical(xUnit, yUnit - range);
                down = new Vertical(xUnit, yUnit + range);
                left = new Horizontal(xUnit - range, yUnit);
                right = new Horizontal(xUnit + range, yUnit);
            }
            if (up.canExplode() && !breakUp) {
                BombermanGame.updateQueue.add(up);
            } else {
                breakUp = true;
            }
            if (down.canExplode() && !breakDown) {
                BombermanGame.updateQueue.add(down);
            } else {
                breakDown = true;
            }
            if (left.canExplode() && !breakLeft) {
                BombermanGame.updateQueue.add(left);
            } else {
                breakLeft = true;
            }
            if (right.canExplode() && !breakRight) {
                BombermanGame.updateQueue.add(right);
            } else {
                breakRight = true;
            }
        }
    }
}
