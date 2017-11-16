package by.teplohova.tunnel.parser;

public enum TunnelEnum {

    TUNNEL_CONFIG("tunnel-config"),
    COUNT_TRAIN("count-train"),
    RAIL_ROAD("rail-road"),
    NAME("name"),
    TIME("time"),
    COUNT_TRAIN_DIRECTION("count-train-direction");


    private String value;

    private TunnelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
