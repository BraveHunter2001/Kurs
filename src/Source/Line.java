package Source;

public abstract class Line {

    Object[] line;

    public Line() {}

    public Line(Object[] ln) throws IllegalArgumentException
    {
        if (ln == null || ln.length == 0)
            throw new IllegalArgumentException("argument was null or empty!");
        if (ln.length != Columns.values().length)
            throw new IllegalArgumentException("array length doesn't match column count!");
        line = ln;

    }


    int GetID()
    {
        return Integer.parseInt(line[0].toString());
    }

    public Object[] GetData()
    {
        return line;
    }


}
