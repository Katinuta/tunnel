package by.teplohova.tunnel;


import java.util.ArrayList;

public class TunnelConfig {
    private int countTrain;
    private ArrayList<String> railRoadNames;
    private int timeGoInRailRoad;
    private int countTrainInDirection;

    public TunnelConfig() {
        railRoadNames=new ArrayList<>();
    }


    public int getCountTrain() {
        return countTrain;
    }

    public void setCountTrain(int countTrain) {
        this.countTrain = countTrain;
    }

    public ArrayList<String> getRailRoadNames() {
        return railRoadNames;
    }

    public void setRailRoadName(String railRoadNames) {
         this.railRoadNames.add(railRoadNames);
    }

    public int getTimeGoInRailRoad() {
        return timeGoInRailRoad;
    }

    public void setTimeGoInRailRoad(int timeGoInRailRoad) {
        this.timeGoInRailRoad = timeGoInRailRoad;
    }

    public int getCountTrainInDirection() {
        return countTrainInDirection;
    }

    public void setCountTrainInDirection(int countTrainInDirection) {
        this.countTrainInDirection = countTrainInDirection;
    }


    @Override
    public String toString() {
        return "TunnelConfig{" +
                "countTrain=" + countTrain +
                ", railRoadName='" + railRoadNames+ '\'' +
                ", timeGoInRailRoad=" + timeGoInRailRoad +
                ", countTrainInDirection=" + countTrainInDirection +
                '}';
    }
}
