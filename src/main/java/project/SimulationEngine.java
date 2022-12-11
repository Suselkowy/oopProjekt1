package project;


import java.util.concurrent.TimeUnit;

public class SimulationEngine{
    private AbstractWorldMap map;

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions, int[][] genoms){
        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal = new Animal(map, position, genoms[index]);
            map.place(currAnimal, position);
            index += 1;
        }
        this.map = map;
    }

    public void run() throws InterruptedException {
        while (this.map.getNumOfAnimals() > 0){
            this.map.moveAnimals();
            System.out.println(this.map.getNumOfAnimals());
            this.map.cleanAnimals();

            TimeUnit.SECONDS.sleep(1);
        }
    }

}
