package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class findPathAdvanced extends findPath {
    Bomber player;
    Enemy enemy;

    public findPathAdvanced(Bomber player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public int calculateDirection() {

        if(player == null)
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if (vertical == 1) {
            int tmp = calculateRowDirection();
            if (tmp != -1) {
                return tmp;
            } else {
                return calculateColDirection();
            }
        } else {
            int tmp = calculateColDirection();

            if (tmp != -1) {
                return tmp;
            } else {
                return calculateRowDirection();
            }

        }

    }

    public int calculateColDirection() {
        if (player.getX() < enemy.getX()) {
            return 3;
        } else if (player.getX() > enemy.getX()) {
            return 1;
        }

        return -1;
    }

    public int calculateRowDirection() {
        if (player.getY() < enemy.getY()) {
            return 0;
        } else if (player.getY() > enemy.getY()) {
            return 2;
        }

        return -1;
    }

}
