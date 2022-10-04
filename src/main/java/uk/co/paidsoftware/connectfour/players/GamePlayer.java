package uk.co.paidsoftware.connectfour.players;

public class GamePlayer implements Player {
    private static int total = 0;

    private final String name;
    private int id = ++total;

    public GamePlayer(String name) {
        this.name = name;
        id = ++total;
    }

    public GamePlayer(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getIdentity() {
        return id;
    }
}
