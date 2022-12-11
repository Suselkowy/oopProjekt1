package project;

import java.util.Random;

public class HellMap extends AbstractWorldMap{

    Random rand = new Random();

    public HellMap(int width, int height) {
        super(width, height);
    }

    private Vector2d getRandomPosition(){
        int x = rand.nextInt(this.width);
        int y = rand.nextInt(this.height);
        return new Vector2d(x,y);
    }

    @Override
    public Vector2d customMove(Vector2d newPosition) {
        return getRandomPosition();
    }

    @Override
    public Vector2d[] printLimit() {
        return new Vector2d[] {this.lowerLeft, this.upperRight};
    }
}
