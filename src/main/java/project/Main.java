package project;

public class Main {
    public static void main(String[] args){
//        Application.launch(App.class, args);
        Animal.GENOM_LENGTH = 4;
        Animal.USED_ENERGY = 12;
        Animal.START_ENERGY = 4;

        AbstractWorldMap map = new HellMap(10, 10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };

        SimulationEngine engine = new SimulationEngine(map, positions, new int[][]{{0,1,2,3},{0,4,0,4}});

        try {
            engine.run();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}