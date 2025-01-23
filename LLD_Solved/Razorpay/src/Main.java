//
//
//Features that need to be implemented
//Create Table - Capability to define a new table.
//Delete Table - Ability to remove an existing table.
//Insert Row - Function to add a new row to a table.
//Get Row - Fetch rows based on user query
//Update Row - Feature to modify an existing row in a table.
//Delete Row - Option to remove a row from a table.
//Create Index - Ability to build an index on a single column for faster query responses.
//
//        Transactions - Support for transaction processing to ensure data integrity.
//Concurrency - Handle simultaneous operations on the database efficiently
//
//
//Row{
//    Int numofElements in row
//    List<String, Object>

//    get(String columnName)
//    set(String Colum, Object value)
//    getData(){
//        Return data;
//    }
//
//    Schema{
//        String name;
//        Int maxLength
//        Int minLength;
//    }
//
//
//
//
//    Table{
//        List<Schema> Schemas;
//        Map<rowId, Row> rows
//
//        Map<ColumnName, Map<Object, rowId>> indexes;
//        Id
//                Name
//        Insert(Map<String, Object> rowDat){}
//
//        delete
//
//
//    }
//    System
//    {
//        map<tableId, Table>
//
//
//    } -> managing all the tables and getting all the operations
//    Table rows and column
//    Row


import models.Schema;
import models.Table;

import java.util.ArrayList;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Database  db = new Database();

        List<Schema> schemas = new ArrayList<>();
        schemas.add(new Schema("id", 2,5));
        schemas.add(new Schema("name", 2,5));
        schemas.add(new Schema("age", 2,5));

        db.createTable(UUID.randomUUID().toString(), "users", schemas);


        Table users = db.getTable("users");
        users.insertRow(Map.of("id",1, "name", "Alice", "age", 30));
        users.insertRow(Map.of("id",2, "name", "Bob", "age", 24));
        users.insertRow(Map.of("id",3, "name", "Charlie", "age", 35));

        System.out.println(users.queryRows(Map.of("age", 24)));

//        System.out.println(users);



    }
}