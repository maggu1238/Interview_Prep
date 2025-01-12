package model;

public interface Observable {

    public void addObserver(User user);

    public void removeObserver(User user);

    public void notifyObservers();
//
//    public void setState(String state);
//
//    public String getState();
}
