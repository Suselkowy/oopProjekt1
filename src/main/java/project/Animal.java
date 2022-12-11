package project;

import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement{
    static int GENOM_LENGTH;
    static int START_ENERGY;
    static int USED_ENERGY;
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    private int[] genome;
    private int currGenomeIndex = 0;

    private int energy = START_ENERGY;

    private final AbstractWorldMap map;
    private List<IPositionChangeObserver> observers;

    public Animal(AbstractWorldMap map, Vector2d initialPosition, int[] genome){
        this.map = map;
        this.position = initialPosition;
        this.observers = new LinkedList<>();
        this.genome = genome;

        this.addObserver((IPositionChangeObserver) map);
    }

    public String toString() {
        return switch(orientation){
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
            case NORTHWEST -> "NW";
            case NORTHEAST -> "NE";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
        };
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

//    @Override
//    public String getResourcePath() {
//        return switch(orientation){
//            case NORTH -> "src/main/resources/up.png";
//            case WEST -> "src/main/resources/left.png";
//            case EAST -> "src/main/resources/right.png";
//            case SOUTH -> "src/main/resources/down.png";
//        };
//    }

    private void incrementCurrGenomeIndex(){
        this.currGenomeIndex = (this.currGenomeIndex + 1)%GENOM_LENGTH;
    }

    public void move(){
        this.orientation = this.orientation.spin(genome[currGenomeIndex]);
        incrementCurrGenomeIndex();
        Vector2d oldPosition = this.position;
        Vector2d newPosition = position.add(orientation.toUnitVector());

        if (!map.canMoveTo(newPosition)){
            newPosition = map.customMove(newPosition);
            if(map instanceof HellMap){
                this.energy -= Animal.USED_ENERGY;
            }
        }
        position = newPosition;
        positionChanged(oldPosition, newPosition, this);
        this.energy -=1;
    }

    public Vector2d getPosition(){
        return this.position;
    }
    public int[] getGenome(){
        return this.genome;
    }
    public int getEnergy(){
        return this.energy;
    }

    void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

//    void removeObserver(IPositionChangeObserver observer){
//        observers.remove(observer);
//    }

    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPosition, newPosition, animal);
        }
    }
}
