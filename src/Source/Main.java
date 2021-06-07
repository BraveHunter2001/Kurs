package Source;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Start program");
        new MainForm(null);
        logger.info("Finish program");
    }
}
