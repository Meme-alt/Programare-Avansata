package Lab2;

public class Main {
    public static void main(String[] args){
        int nr_loc = 1000;
        int nr_roads = 5000;
        Problem test = new Problem(nr_loc, nr_roads);
        generate(test, nr_loc, nr_roads);
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long initialTime = System.currentTimeMillis();
        Solution result = test.ShortestPath(test.getLocations()[0], test.getLocations()[nr_loc - 1]);
        long runningTime = System.currentTimeMillis() - initialTime;
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = usedMemoryAfter - usedMemoryBefore;
        System.out.println("Time: " + runningTime);
        System.out.println("Memory increase: " + memoryIncrease);
    }
    public static void generate(Problem p, int nr_loc, int nr_roads){
        for(int i = 0; i < nr_loc; i++){
            String name = "Loc_" + i;
            float x = (float) (Math.random() * 1000);
            float y = (float) (Math.random() * 1000);
            int type = (int) (Math.random() * 3);
            if(type == 0){
                p.addLocation(new city(name, x, y, (int)(Math.random() * 100000)));
            }
            else if(type == 1){
                p.addLocation(new airport(name, x, y, (int)(Math.random() * 20)));
            }
            else{
                p.addLocation(new GasStation(name, x, y, (int)(Math.random() * 7)));
            }
        }
        for(int i = 0; i < nr_roads; i++){
            int startInd = (int)(Math.random() * nr_loc);
            int endInd = (int)(Math.random() * nr_loc);
            if(startInd != endInd){
                Location start = p.getLocations()[startInd];
                Location end = p.getLocations()[endInd];
                float length = (float)(Math.random() * 100) + 5;
                int speedLimit = (int)(Math.random() * 100) + 20;
                int nr = (int)(Math.random() * 4);
                Road.Roadtype type_road;
                if(nr == 0){
                    type_road = Road.Roadtype.FREEWAY;
                }
                else if(nr == 1){
                    type_road = Road.Roadtype.HIGHWAY;
                }
                else if(nr == 2){
                    type_road = Road.Roadtype.URBAN;
                }
                else{
                    type_road = Road.Roadtype.RESIDENTIAL;
                }
                p.addRoad(new Road("Road_" + i, type_road, length, speedLimit, start, end));
            }
        }
    }
}
