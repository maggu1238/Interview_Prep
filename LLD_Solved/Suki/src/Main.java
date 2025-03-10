//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//note
 //list of sections


//sections update add tet remove text
//        undo action
//                auditibality ->

//history of note

/*
* note-> sections -> content
* add/remove a content is some section of some note
* undo ->
* */


import java.util.ArrayDeque;
import java.util.Deque;// t1,c1
import java.util.HashMap;
import java.util.Map;

// t2,c2
       // t1,c1
queue<> timestamp, content
//ation -> recorded -> timestamp
// getAllHistoryfor a section
// undo ->


abc
add(bt)


class Section{

    private String sectionId;
    private String content;
    private Deque<String> history;
    private Map<Long, String> auditLog;

    public Section(String sectionId){
        this.content = "";
        this.sectionId = sectionId;
        this.history = new ArrayDeque<>();
        this.auditLog = new HashMap<>();
    }

    public void addContent(String newcontent){
        history.push(content);
        content = content + newcontent;
        long timeStamp = System.currentTimeMillis();
        auditLog.putIfAbsent(timeStamp, content);
    }

    public void undo(){
        if(!history.isEmpty()){
            String tempContent = history.pop();
            content = tempContent;

            long timeStamp = System.currentTimeMillis();
            auditLog.putIfAbsent(timeStamp, tempContent);
        }
    }

    public String getCurrentVersion(){
        return content;
    }

    public Map<Long, String> getAuditLog(){
        return auditLog;
    }
}



class Note{
    private Map<String, Section> sections;
    private Deque<String> historyOfSectionIds;
    private String currentSectionId;

    public Note(){
        this.sections = new HashMap<>();
        this.historyOfSectionIds = new ArrayDeque<>();
        this.currentSectionId = null;
    }

    public void addSection(String sectionId){
        sections.put(sectionId ,new Section(sectionId));
    }

    public void addText(String text){

        if(currentSectionId != null){
            sections.get(currentSectionId).addContent(text);
            historyOfSectionIds.push(currentSectionId);
        }

    }

    public void changeSectionId(String sectionId){
        this.currentSectionId = sectionId;
    }

    public void undoNote(){
        String sectionIdToUndo = historyOfSectionIds.pop();
        sections.get(sectionIdToUndo).undo();
    }

    public Map<Long, String>  getSectionHistory(String sectionId){
        return sections.get(sectionId).getAuditLog();
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