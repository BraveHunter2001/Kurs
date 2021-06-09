package Source;

import java.util.Locale;

public enum MeetingStatus  {
    Unmet(1),
    Met(2);
    private int id;

    private MeetingStatus(int id) {
        this.id = id;
    }

    public int GetId() {
        return id;
    }

    public static MeetingStatus getStatusByID(int id) {
        for (MeetingStatus status : values()) {
            if (status.GetId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum found with id: " + id);
    }

    public static MeetingStatus getStatusByStr(String str)
    {
        for (MeetingStatus status :values())
        {
            if (status.toString().toLowerCase().equals(str.toLowerCase()))
            {
                return status;
            }
        }
        throw  new IllegalArgumentException("No enum found with name: "+ str);
    }
}
