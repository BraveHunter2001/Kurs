package Source;

public enum ProgramMode {
    Both(0),
    Characters(1),
    Tasks(2);


    private int id;

    ProgramMode(int id){
        this.id = id;
    }

    public int GetId()
    {
        return id;
    }
}
