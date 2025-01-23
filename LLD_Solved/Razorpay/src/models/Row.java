package models;

//    Int numofElements in row
//    List<String, Object>

//    get(String columnName)
//    set(String Colum, Object value)
//    getData(){
//        Return data;
//    }

import java.util.HashMap;
import java.util.Map;

public class Row {
    private Map<String, Object> data;

    public Row(Map<String, Object> data){
        this.data = new HashMap<>(data);
    }

    public Object getObject(String colum){
        return data.get(colum);
    }

    public boolean set(String column, Object value){
        if(data.containsKey(column)){
            data.put(column, value);
            return true;
        }
        else{
            System.out.println("Key Not found");
        }

        return false;
    }

    public Map<String, Object> getData(){
        return data;
    }



}
