package domain;

public enum Gender {
    MALE("male"), FEMALE("female");

    private String description;

    Gender(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
