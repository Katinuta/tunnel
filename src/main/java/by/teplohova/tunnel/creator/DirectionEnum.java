package by.teplohova.tunnel.creator;

public enum DirectionEnum {

    BACKWARD("backward"),
    FORWARD("forward");

    private String direction;

    private DirectionEnum(String direction) {
        this.direction = direction;
    }

    public String getDirection(){
        return this.direction;
    }
}
