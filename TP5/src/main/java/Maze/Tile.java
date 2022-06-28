package Maze;

public enum Tile {
    Floor,
    Wall,
    Exit;

    @Override
    public String toString() {
        String tileContent = "";
        switch (this) {
            case Exit :
                tileContent = "*";
                break;
            case Floor : tileContent= "_";
                break;
            case Wall :
                tileContent = "#";
                break;
        };
        return tileContent;
    }
}
