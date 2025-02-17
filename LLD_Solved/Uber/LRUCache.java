//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;

class Node{
    private Integer key;
    Node next;
    Node prev;

    public Node(Integer key){
        this.key = key;
    }

    public Integer getKey(){
        return key;
    }
}

class DoublyLinkedList{
    Node head;
    Node tail;

    public DoublyLinkedList(){
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
    }

    void addNode(Node node)
    {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    void removeNode(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    void moveToFront(Node node){
        removeNode(node);
        addNode(node);
    }

    Node removeTail(){
        if(tail.prev == head){
            return null;
        }
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}

interface EvictionStrategy{
    void keyAccessed(Integer key);
    Integer evict();
}

class LRUEvictionPolicy implements EvictionStrategy{
    private Map<Integer, Node> nodeMap;
    private DoublyLinkedList doublyLinkedList;

    public LRUEvictionPolicy(){
        this.nodeMap = new HashMap<>();
        this.doublyLinkedList = new DoublyLinkedList();
    }


    @Override
    public void keyAccessed(Integer key){
        if(!nodeMap.containsKey(key)){
            Node node = new Node(key);
            nodeMap.put(key, node);
            doublyLinkedList.addNode(node);
        }
        else{
            Node node = nodeMap.get(key);
            doublyLinkedList.moveToFront(node);
        }
    }

    @Override
    public Integer evict(){
        Node node = doublyLinkedList.removeTail();
        if(node != null){
            Integer key  = node.getKey();
            nodeMap.remove(key);
            return key;
        }
        return null;
    }
}

class Cache{

    private Integer maxCapacity;
    private EvictionStrategy strategy;
    private Map<Integer, Integer> storage;
    private Integer currCapacity;

    public Cache(Integer capacity, EvictionStrategy strategy){
        this.maxCapacity = capacity;
        this.strategy = strategy;
        this.currCapacity = 0;
        this.storage = new HashMap<>();
    }

    public Integer get(Integer key){
        if(!storage.containsKey(key)){
            return -1;
        }
        Integer value = storage.get(key);
        strategy.keyAccessed(key);
        return value;
    }

    public void put(Integer key, Integer value){
        if(!storage.containsKey(key)){
            if(currCapacity == maxCapacity){
                Integer keyToEvict = strategy.evict();
                storage.remove(keyToEvict);
                currCapacity--;
            }
        }
        storage.put(key, value);
        currCapacity++;
        strategy.keyAccessed(key);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Try programiz.pro");

        Cache lruCache = new Cache(3, new LRUEvictionPolicy());
        lruCache.put(1, 14);
        lruCache.put(2, 15);
        lruCache.put(3, 16);
        System.out.println(lruCache.get(1)); // A (moves 1 to MRU)
        lruCache.put(4, 17); // Evicts 2 (LRU)
        System.out.println(lruCache.get(2)); // null (evicted)
        System.out.println(lruCache.get(3)); // C
    }
}