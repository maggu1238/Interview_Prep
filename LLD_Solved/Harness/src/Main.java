


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Task{
    private final int taskId;
    private String name;
    private String desc;

    public Task(int taskId, String name, String desc){

        this.taskId = taskId;
        this.name = name;
        this.desc = desc;

    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setName(String name){
        this.name = name;
    }
}

class TodoList{
    private final int todoListId;
    private List<Integer> taskIds;
    private String name;


    public TodoList(int todoListId, String name){
        this.todoListId = todoListId;
        taskIds = new ArrayList<>();
    }

    public synchronized void addTask(int taskId){
        taskIds.add(taskId);
    }

    public void updateName(String name){
        this.name = name;
    }
}

class TodoListManagementSystem{
    int todoListIdCounter;
    int taskIdCounter;
    Map<Integer, TodoList> todoListMap;
    Map<Integer, Task> taskMap;


    public TodoListManagementSystem(){
        this.taskIdCounter = 0;
        this.todoListIdCounter = 0;
        todoListMap = new HashMap<>();
        taskMap = new HashMap<>();
    }


    public void createTodoList(String name){
        TodoList todoList = new TodoList(todoListIdCounter, name);
        todoListMap.put(todoListIdCounter, todoList);
        todoListIdCounter++;
    }

    public synchronized void createTask(String name, String desc, String todoListId){
        Task task = new Task(taskIdCounter, name, desc);
        TodoList todoList = todoListMap.get(todoListId);
        todoList.addTask(taskIdCounter);
        taskIdCounter++;
    }
}
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}