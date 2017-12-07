package by.teplohova.tunnel.parser;

import by.teplohova.tunnel.entity.TunnelConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;

public class TunnelConfigSAXBuilder  {

    private static final Logger LOGGER = LogManager.getLogger();
    private TunnelConfigHandler configHandler;
    private TunnelConfig config;
    private XMLReader reader;

    public TunnelConfigSAXBuilder() {
        configHandler = new TunnelConfigHandler();

        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(configHandler);

        } catch (SAXException e) {
            LOGGER.log(Level.ERROR, "Error SAX-parser " + e);
        }
    }

    public TunnelConfig buildTunnelConfig(String fileName) {
        try {
            reader.parse(fileName);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "File is not found or file error" + fileName + " " + e);
            throw new RuntimeException("File is not found or file error " + fileName + " " + e);
        } catch (SAXException e) {
            LOGGER.log(Level.ERROR, "Error SAX-parser " + e);
        }
        config= configHandler.getTunnelConfig();
        return config;
    }
}
