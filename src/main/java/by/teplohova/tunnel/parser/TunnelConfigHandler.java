package by.teplohova.tunnel.parser;

import by.teplohova.tunnel.TunnelConfig;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class TunnelConfigHandler extends DefaultHandler {
    private TunnelConfig currentConfig;
    private TunnelEnum currentEnum;

    public TunnelConfigHandler() {
    }

    public TunnelConfig getTunnelConfig() {
        return currentConfig;
    }


    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (TunnelEnum.TUNNEL_CONFIG.getValue().equals(localName)) {
            currentConfig= new TunnelConfig();
        }  else {
            currentEnum = TunnelEnum.valueOf(localName.toUpperCase().replace("-", "_"));
        }

    }

    public void endElement(String uri, String localName, String qName) {
        if (TunnelEnum.TUNNEL_CONFIG.getValue().equals(localName)) {


        }
    }

    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).trim();

        if (currentEnum != null) {
            switch (currentEnum) {
                case COUNT_TRAIN:
                    currentConfig.setCountTrain(Integer.parseInt(value));
                    break;
                case NAME:
                    currentConfig.setRailRoadName(value);
                    break;
                case TIME:
                    currentConfig.setTimeGoInRailRoad(Integer.parseInt(value));
                    break;
                case COUNT_TRAIN_DIRECTION:
                    currentConfig.setCountTrainInDirection(Integer.parseInt(value));
                    break;
            }
        }
        currentEnum = null;
    }

}
