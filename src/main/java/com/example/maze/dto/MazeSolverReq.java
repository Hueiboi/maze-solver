package com.example.maze.dto;

public record MazeSolverReq(
        int[][] map,
        int[] start,
        int[] end,
        String algorithm
) {}
