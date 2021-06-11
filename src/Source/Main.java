package Source;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static DBFacade db;
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Start program");
        db = new DBFacade();
        db.ConnectDB();
        new MainForm();
        logger.info("Finish program");
    }
}
