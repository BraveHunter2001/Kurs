package Source;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Start program");

        Task t2 = new Task(1,"stop", TaskStatus.Taken);
        List<Task> ts = new ArrayList<>();
        ts.add(t2);
        ts.add(new Task(2,"Peraliz", TaskStatus.Failed));
        TaskTable taskTable = new TaskTable(ts);

        List<Character> chs = new ArrayList<>();
        chs.add(CharactersTable.DefaultCharacter);
        chs.add(new Character(1, "Ilya", "Human", "Vil",MeetingStatus.Unmet));

        chs.get(1).SetTasks(ts);
        ts.get(0).SetCharacters(chs);

        CharactersTable chsTable = new CharactersTable(chs);
        new MainForm(chsTable, taskTable);
        logger.info("Finish program");
    }
}
