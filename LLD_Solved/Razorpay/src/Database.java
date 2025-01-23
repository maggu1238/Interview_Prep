import models.*;

import java.util.HashMap;
import java.util.*;

public class Database {

    private  final Map<String, Table> tables;

    public Database(){
        this.tables = new HashMap<>();

    }

    public void createTable(String id, String name, List<Schema> schemas){
        if(tables.containsKey(id)){
            throw new IllegalArgumentException("Table already exists");
        }
        tables.put(name, new Table(name, schemas, id));
    }

    public Table getTable(String id){
        return tables.get(id);
    }

    public void deleteTable(String id){
        tables.remove(id);
    }

}
