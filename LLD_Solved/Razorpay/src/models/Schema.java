package models;

public class Schema {
    private String name;
    private int minLength;
    private int maxLength;

    public Schema(String name, int minLength, int maxLength) {
        this.name = name;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }


    public String getName() {
        return name;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }
}

