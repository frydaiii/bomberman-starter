package uet.oop.bomberman.entities.Enemy;

import java.util.Random;

public abstract class findPath {
    protected Random random = new Random();

    public abstract int calculateDirection();
}
