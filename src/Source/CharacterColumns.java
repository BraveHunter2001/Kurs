package Source;
public enum CharacterColumns {

    ID(0),
    Name(1),
    Apperance(2),
    Location(3),
    Task(4),
    TaskStatus(5),
    MeetingStatus(6);

    private int id;

    CharacterColumns(int id){
        this.id = id;
    }

    public int GetId()
    {
        return id;
    }
}
