package domain;

public enum Emoji {
    SMILEY("&#x1F60A"), DEVIL("&#x1F608"), CRY("&#x1F622"), HUG("&#x1F917");

    private String hex;

    Emoji(String hex){this.hex = hex;}

    public String getHex(){
        return this.hex;
    }
}
