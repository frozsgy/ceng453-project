package group10.client.enums;

public enum Cards {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    private String name;
    Cards(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
