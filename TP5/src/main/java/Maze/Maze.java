package Maze;

import java.util.*;
import java.util.stream.Collectors;

public class Maze {
    /** TODO
     * Returns the distance of the shortest path within the maze
     * @param maze 2D table representing the maze
     * @return Distance of the shortest path within the maze, null if not solvable
     */
    public static Integer findShortestPath(ArrayList<ArrayList<Tile>> maze) {
        if (maze.isEmpty())
            return null;

        final int ROWS = maze.size();
        final int COLS = maze.get(0).size();

        ArrayList<Coord> extremities = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (maze.get(i).get(j) == Tile.Exit)
                    extremities.add(new Coord(i, j));
            }
        }

        if (extremities.size() != 2)
            return null;

        Coord start = extremities.get(0);
        boolean[][] visited = new boolean[ROWS][COLS];
        visited[start.row][start.col] = true;

        Queue<queueNode> q = new LinkedList<>();
        q.add(new queueNode(start, 0));

        while (!q.isEmpty()) {
            queueNode current = q.poll();
            Coord coordCurrent = current.coord;

            for (int i = 0; i < 4; i++) {
                Coord neighbour = coordCurrent.direction(i);
                int row = neighbour.row;
                int col = neighbour.col;

                if (row >= 0 && row < ROWS && col >= 0 && col < COLS
                        && maze.get(row).get(col) != Tile.Wall && !visited[row][col]) {
                    if (maze.get(row).get(col) == Tile.Exit)
                        return current.dist + 1;

                    visited[row][col] = true;
                    q.add(new queueNode(neighbour, current.dist + 1));
                }
            }
        }
        return null;
    }

    private static class Coord {
        final int row;
        final int col;

        Coord(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Coord direction(int i) {
            return switch (i) {
                case 0 -> new Coord(row + 1, col);
                case 1 -> new Coord(row - 1, col);
                case 2 -> new Coord(row, col + 1);
                case 3 -> new Coord(row, col - 1);
                default -> this;
            };
        }
    }

    private static class queueNode {
        final Coord coord;
        final int dist;

        queueNode(Coord coord, int dist) {
            this.coord = coord;
            this.dist = dist;
        }
    }
    public static void printMaze(ArrayList<ArrayList<Tile>> maze) {
        for (ArrayList<Tile> row : maze) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining("")));
        }
    }
}

