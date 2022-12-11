package project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IPositionChangeObserver{

    protected final Vector2d lowerLeft;
    protected final Vector2d upperRight;
    protected final int width;
    protected final int height;
    protected int numOfAnimals;

    Map<Vector2d, List<Animal>> animals;
    List<Animal> orderList;
    Map<Vector2d, Grass> grass;
    protected MapVisualizer mapVisualizer;

    public AbstractWorldMap(int width, int height){
        this.animals = new HashMap<>();
        this.grass = new HashMap<>();
        this.mapVisualizer = new MapVisualizer(this);
        this.orderList = new LinkedList<>();

        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width,height);
        this.width = width;
        this.height = height;
    }

    public boolean canMoveTo(Vector2d position) {
        return lowerLeft.precedes(position) && upperRight.follows(position);
    }

    public abstract Vector2d customMove(Vector2d newPosition);

    public boolean place(Animal animal, Vector2d newPosition){
        animals.computeIfAbsent(animal.getPosition(), k -> new LinkedList<Animal>());
        animals.get(newPosition).add(animal);
        this.orderList.add(animal);
        this.numOfAnimals += 1;
        return true;
    }

    public boolean isOccupied(Vector2d position) {
        if(animals.get(position) != null && animals.get(position).size() > 0){
            return true;
        }
        return false;
    }

    public Object objectAt(Vector2d position) {
        if (animals.get(position).size() > 0){
            return animals.get(position).get(0);
        }else if(grass.get(position)!=null){
            return grass.get(position);
        }
        return null;
    }

    public Grass grassAt(Vector2d position) {
        return grass.get(position);
    }

    public List<Animal> animalsAt(Vector2d position){
        return animals.get(position);
    }

    public int getNumOfAnimals() {
        return numOfAnimals;
    }

    public void moveAnimals(){
        int index = 0;
        for(Animal animal : this.orderList){
            animal.move();
            System.out.println(index);
            index += 1;
        }
    }

//    public Map<Vector2d, List<Animal>> copyAnimals(){
//        Map<Vector2d, List<Animal>> copyMap = new HashMap<>();
//
//        for (Map.Entry<Vector2d, List<Animal>> entry : this.animals.entrySet()) {
//            // using put method to copy one Map to Other
//            copyMap.put(entry.getKey(), new LinkedList<Animal>(entry.getValue()));
//        }
//
//        return copyMap;
//    }

    public void cleanAnimals(){
        for(Animal animal : this.orderList){
            if(animal.getEnergy() > 0){
                this.cleanAnimal(animal, animal.getPosition());
                this.cleanFromOrderList(animal);
                this.numOfAnimals -=1;
            }
        }
    }

    private void cleanAnimal(Animal animal, Vector2d oldPosition){
        int indexOfAnimal = this.animalsAt(oldPosition).indexOf(animal);
        animals.get(oldPosition).remove(indexOfAnimal);
    }

    private void cleanFromOrderList(Animal animal){
        this.orderList.remove(animal);
    }

    public abstract Vector2d[] printLimit();

    @Override
    public String toString() {
        Vector2d[] limits = printLimit();
        return mapVisualizer.draw(limits[0], limits[1]);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        this.cleanAnimal(animal, oldPosition);
        animals.computeIfAbsent(animal.getPosition(), k -> new LinkedList<Animal>());
        animals.get(newPosition).add(animal);
    }



}


