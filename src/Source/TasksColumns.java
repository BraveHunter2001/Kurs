package Source;

public enum TasksColumns {

    ID(0),
    Name(1),
    TaskStatus(2);


    private int id;

    TasksColumns(int id){
        this.id = id;
    }

    public int GetId()
    {
        return id;
    }
}
