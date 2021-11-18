package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.Bomber;

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

    protected int calculateColDirection() {
        if (player.getXPlayer() < enemy.getXEnemy()) {
            return 3;
        } else if (player.getXPlayer() > enemy.getXEnemy()) {
            return 1;
        }

        return -1;
    }

    protected int calculateRowDirection() {
        if (player.getYPlayer() < enemy.getYEnemy()) {
            return 0;
        } else if (player.getYPlayer() > enemy.getYEnemy()) {
            return 2;
        }

        return -1;
    }

}
