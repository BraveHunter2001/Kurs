package Source;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Start program");

        Task t2 = new Task(0,"stop", TaskStatus.Taken);
        List<Task> ts = new ArrayList<>();
        ts.add(t2);
        ts.add(new Task(7,"Peraliz", TaskStatus.Failed));

        List<Character> chs = new ArrayList<>();
        chs.add(CharactersTable.DefaultCharacter);
        chs.add(new Character(1, "Ilya", "Human", "Vil",MeetingStatus.Unmet, ts));
        CharactersTable chsTable = new CharactersTable(chs);
        new MainForm(chsTable);
        logger.info("Finish program");
    }
}
