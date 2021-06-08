package Source;
public enum CharacterColumns {

    ID(0),
    Name(1),
    Apperance(2),
    Location(3),
    MeetingStatus(4),
    Tasks(5);

    private int id;

    CharacterColumns(int id){
        this.id = id;
    }

    public int GetId()
    {
        return id;
    }
}
