package models;

import java.util.List;
import java.util.Map;

public interface ITable {
    void insertRow(Map<String, Object> rowData);

    List<Row> queryRows(Map<String, Object> conditions);

    void deleteRows(Map<String, Object> conditions);

    void updateRows(Map<String, Object> conditions, Map<String, Object> updates);
}
