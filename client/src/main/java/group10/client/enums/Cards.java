package group10.client.enums;

public enum Cards {
    ACE("A", 1),
    TWO("2", 0),
    THREE("3", 0),
    FOUR("4",0),
    FIVE("5",0),
    SIX("6",0),
    SEVEN("7",0),
    EIGHT("8",0),
    NINE("9",0),
    TEN("10",0),
    JACK("J",1),
    QUEEN("Q",0),
    KING("K",0);

    private String name;
    private int points;

    Cards(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString(){
        return name;
    }
}
