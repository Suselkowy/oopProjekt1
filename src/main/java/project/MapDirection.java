package project;

import java.lang.reflect.Array;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NORTHWEST,
    NORTHEAST,
    SOUTHWEST,
    SOUTHEAST;

    private static final MapDirection[] directions = values();

    public MapDirection spin(int step) {
        return directions[(this.ordinal() + step) % directions.length];
    }

    public String toString(){
        return switch (this){
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTHWEST -> "Północny zachód";
            case NORTHEAST -> "Północny wschód";
            case SOUTHEAST -> "Połódniowy wschód";
            case SOUTHWEST -> "Połódniowy zachód";
        };
    }

//    public String toString(){
//        return (String)switchMapDirection(
//                new String[] {"Północ","Północny wschód","Wschód","Południowy wschód","Południe","Południowy zachód","Zachód","Północny zachód"}
//        );
//    }
//
//    private Object switchMapDirection(Object returnValues[]){
//        return switch (this){
//            case NORTH -> returnValues[0];
//            case NORTHEAST -> returnValues[1];
//            case EAST -> returnValues[2];
//            case SOUTHEAST -> returnValues[3];
//            case SOUTH -> returnValues[4];
//            case SOUTHWEST -> returnValues[5];
//            case WEST -> returnValues[6];
//            case NORTHWEST -> returnValues[7];
//        };
//    }



    public MapDirection next(){
        return switch (this){
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public MapDirection previous(){
        return switch (this){
            case NORTH -> NORTHWEST;
            case NORTHEAST -> NORTH;
            case EAST -> NORTHEAST;
            case SOUTHEAST -> EAST;
            case SOUTH -> SOUTHEAST;
            case SOUTHWEST -> SOUTH;
            case WEST -> SOUTHWEST;
            case NORTHWEST -> WEST;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTHWEST -> new Vector2d(-1,1);
            case NORTHEAST -> new Vector2d(1,1);
            case SOUTHEAST -> new Vector2d(-1,-1);
            case SOUTHWEST -> new Vector2d(1,-1);

        };
    }
}
