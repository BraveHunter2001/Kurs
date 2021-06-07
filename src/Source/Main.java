package Source;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        List<Character> mainChar = new ArrayList<Character>();
        Character ch1 = new Character(0,"Igor", "Human", "City of Kirov", "Take of bands", TaskStatus.Taken, MeetingStatus.Met);
        Character ch2 = new Character(1,"Manu", "Tifling", "Town", "Found ring", TaskStatus.NotTaken, MeetingStatus.Unmet);

        mainChar.add(ch1);
        mainChar.add(ch2);
        DataTable main = new DataTable(mainChar);
        new MainForm(main);
    }
}
