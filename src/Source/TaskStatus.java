package Source;

public enum TaskStatus {
    Failed(-1),
    NotTaken(0),
    Taken(1),
    Done(2);

    private int id;

    TaskStatus(int id) {
        this.id = id;
    }

    public int GetId() {
        return id;
    }


    public static TaskStatus getStatusByID(int id) {
        for (TaskStatus status : values()) {
            if (status.GetId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum found with id: " + id);
    }

    public static TaskStatus getStatusByStr(String str)
    {
        for (TaskStatus status :values())
        {
            if (status.toString().equals(str))
            {
                return status;
            }
        }
        throw  new IllegalArgumentException("No enum found with name: "+ str);
    }
}
