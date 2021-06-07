package Source;

public enum MeetingStatus  {
    Unmet(0),
    Met(1);
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
            if (status.toString().equals(str))
            {
                return status;
            }
        }
        throw  new IllegalArgumentException("No enum found with name: "+ str);
    }
}
