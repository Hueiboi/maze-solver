package com.example.maze.model;

public enum MazeAlgorithm {
    BFS, DFS, A_star;

    public static MazeAlgorithm getAlgorithm(String val) {
        try {
            return MazeAlgorithm.valueOf(val.toUpperCase());
        } catch (IllegalArgumentException e) {
            return A_star;
        }
    }
}
