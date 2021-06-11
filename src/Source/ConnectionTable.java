package Source;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTable {
    List<int[]> connectionList;
    private static String[] DBcolumns = {"character_id", "task_id"};

    public ConnectionTable()
    {
        connectionList = new ArrayList<>();
    }

    public ConnectionTable(ArrayList<int[]> arr)
    {
        connectionList = arr;
    }

    public void Connect(int idCharacter, int idTask)
    {
        int[] par = new int[2];
        par[0] = idCharacter;
        par[1] = idTask;

        for (int i=0; i < connectionList.size(); i++)
        {
            int[] idPar = connectionList.get(i);
            if (idPar[0] == idCharacter && idPar[1] == idTask)
                return;
        }
        InsertIntoDB(toObjects(par));
        connectionList.add(par);
    }
    private Object[] toObjects(int[] par)
    {
        Object[] objpar = new Object[2];
        objpar[0] = par[0];
        objpar[1] = par[1];
        return objpar;
    }

    public void UnConnect(int idCharacter, int idTask)
    {
        for (int i=0; i < connectionList.size(); i++)
        {
            int[] idPar = connectionList.get(i);
            if (idPar[0] == idCharacter && idPar[1] == idTask) {
                DeleteFromDB(toObjects(idPar));
                connectionList.remove(i);
            }
        }

    }

    public void UnConnectAllForItem(int id, List<Integer> idsItems)
    {

        if (idsItems != null)
            for (var item: idsItems)
                this.UnConnect(id,item);
    }


    public List<Integer> GetTasksFromCharacter(int idCharacter)
    {

        try {
            var rs = Main.db.ExcecuteQuery("SELECT task_id FROM characters_tasks WHERE character_id=" + idCharacter);
            List<Integer> res = new ArrayList<>();
            while(rs.next()) {
                    res.add(rs.getInt(1));

            }
            return res;
        } catch (DBFacade.DBNotConnectedException e) {
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    public List<Integer> GetCharactersFromTask(int idTask)
    {
        try {
            var rs = Main.db.ExcecuteQuery("SELECT character_id FROM characters_tasks WHERE task_id=" + idTask);
            List<Integer> res = new ArrayList<>();
            while(rs.next()) {
                res.add(rs.getInt(1));

            }

            return res;
        } catch (DBFacade.DBNotConnectedException e) {
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    private void InsertIntoDB(Object[] idsConnects) {
        try {
            var questionsLine = new String(new char[DBcolumns.length]).replace("\0", "?,");
            var lineQuery = Main.db.InsertQuery("insert into characters_tasks("
                    + String.join(", ", DBcolumns)
                    + ") values("
                    + questionsLine.substring(0, questionsLine.length() - 1)
                    + ")", idsConnects);
            lineQuery.execute();
        } catch (DBFacade.DBNotConnectedException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void DeleteFromDB(Object[] idsConnects) {
        try {
            String query = "delete from characters_tasks where character_id = " + idsConnects[0].toString()
                    +"AND task_id =" +  idsConnects[1].toString();
            Main.db.GetStatement().executeUpdate(query);
        } catch (DBFacade.DBNotConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ConnectionTable GetConnectionFromDB(){
        try {
            var rs = Main.db.ExcecuteQuery("SELECT "+String.join(", ", DBcolumns)+" FROM characters_tasks");
            ArrayList<int[]> lns = new ArrayList<>();
            while(rs.next())
            {
                var vals = new int[DBcolumns.length];
                for(int i = 0; i < DBcolumns.length; i++)
                    vals[i] = rs.getInt(i+1);

                lns.add(vals);
            }
            return new ConnectionTable(lns);
        } catch (DBFacade.DBNotConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void ClearConnectionTable()
    {
        for(int i =0; i < connectionList.size(); i++)
            UnConnect(connectionList.get(i)[0],connectionList.get(i)[1]);
    }
}
