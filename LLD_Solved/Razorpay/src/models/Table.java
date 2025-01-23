package models;

import java.util.*;

public class Table implements  ITable {
    private final String name;
    private final String id;
    private final Map<String, Row> rows;
    private final List<Schema> schemas;
    private final Map<String, Map<Object, String>> indexes;

    public Table(String name, List<Schema> schemas, String id) {
        this.name = name;
        this.id = id;
        this.rows = new HashMap<>();
        this.schemas = schemas;
        this.indexes = new HashMap<>();
    }

    @Override
    public void insertRow(Map<String, Object> rowData) {

            for (Schema schema : schemas){
                String columnName = schema.getName();
                if(!rowData.containsKey(columnName)){
                    throw new IllegalArgumentException("Missing value for column");
                }
            }

            Row row  = new Row(rowData);
            String rowId = UUID.randomUUID().toString();

            rows.put(rowId, row);

            for(Map.Entry<String, Map<Object, String>> index : indexes.entrySet()) {
                String column = index.getKey();
                index.getValue().put(rowData.get(column), rowId);
            }
    }

    @Override
    public List<Row> queryRows(Map<String, Object> conditions) {

        List<Row> result = new ArrayList<>();

        for(Map.Entry<String, Object> condition : conditions.entrySet()){
            String column = condition.getKey();
            Object value = condition.getValue();

            if(indexes.containsKey(column)){
                String indexedRowId = indexes.get(column).get(value);
                Row indexedRow = rows.get(indexedRowId);

                if(indexedRow != null){
                    result.add(indexedRow);
                }

                break;
            }
            else{
                for( Map.Entry<String, Row> rowEntry : rows.entrySet()){
                    if(value.equals(rowEntry.getValue().getObject(column))){
                       result.add(rowEntry.getValue());
                    }
                }
            }


        }
        return result;
    }

    @Override
    public void deleteRows(Map<String, Object> conditions) {
        for(Map.Entry<String, Row> rowMap : rows.entrySet()){
            Row row = rowMap.getValue();
            String rowId = rowMap.getKey();
            for(Map.Entry<String, Object> condition : conditions.entrySet()){
                if(condition.getValue().equals(row.getObject(condition.getKey()))){
                    rows.remove(rowId);
                }
            }
        }
    }

    @Override
    public void updateRows(Map<String, Object> conditions, Map<String, Object> updates) {
        for( Row row : queryRows(conditions)){
            updates.forEach((row::set));
        }
    }
}
