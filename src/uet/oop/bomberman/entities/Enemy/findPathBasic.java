package uet.oop.bomberman.entities.Enemy;

public class findPathBasic extends findPath {
    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
