package models;

public abstract class Place {
    String placename;
    String placeId;

    public Place(String placeId, String name){
        this.placeId = placeId;
        this.placename = name;
    }

    public String getCityId() {
        return placeId;
    }

}
