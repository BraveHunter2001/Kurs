package Source;

import java.util.ArrayList;
import java.util.List;

public class ConnectionTable {
    List<int[]> connectionList;

    public ConnectionTable()
    {
        connectionList = new ArrayList<>();
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
        connectionList.add(par);
    }

    public void UnConnect(int idCharacter, int idTask)
    {
        int[] par = new int[2];
        par[0] = idCharacter;
        par[1] = idTask;
        for (int i=0; i < connectionList.size(); i++)
        {
            int[] idPar = connectionList.get(i);
            if (idPar[0] == idCharacter && idPar[1] == idTask)
                connectionList.remove(i);
        }

    }

    public void UnConnectAllForCharacter(int id)
    {
        var hisTasks = this.GetTasksForCharacter(id);

        if (hisTasks != null)
            for (var task: hisTasks)
                this.UnConnect(id,task);
    }
    public void UnConnectAllForTask(int id)
    {
        var hisCharacter = this.GetCharactersForTask(id);

        if (hisCharacter != null)
            for (var chr: hisCharacter)
                this.UnConnect(id,chr);
    }

    public List<Integer> GetTasksForCharacter(int idCharacter)
    {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < connectionList.size(); i++)
        {
            if (connectionList.get(i)[0] == idCharacter)
                res.add(connectionList.get(i)[1]);
        }
        if (res.size() == 0)
            return null;
        return res;
    }

    public List<Integer> GetCharactersForTask(int idTask)
    {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < connectionList.size(); i++)
        {
            if (connectionList.get(i)[1] == idTask)
                res.add(connectionList.get(i)[0]);
        }
        if (res.size() == 0)
            return null;
        return res;
    }
}
