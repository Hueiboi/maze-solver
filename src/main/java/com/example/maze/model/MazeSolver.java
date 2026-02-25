package com.example.maze.model;

import java.util.*;

class MyNode implements Comparable<MyNode> {
    int x, y;
    int g, h, f;

    public MyNode(int x, int y, int g, int h) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    @Override
    public int compareTo(MyNode o) {
        return Integer.compare(this.f, o.f);
    }
}

public class MazeSolver {
    int[][] maze;


    boolean isValid(int[][] maze, int nx, int ny, boolean[][] visited) {
        return nx >= 0 && nx < maze.length &&
                ny >= 0 && ny < maze[0].length &&
                maze[nx][ny] == 0 && !visited[nx][ny];
    }

    List<int[]> reconstructPath(Map<String, int[]> parentMap, int[] end) {
        List<int[]> path = new ArrayList<>();
        int[] current = end;

        while (current != null) {
            path.add(current);
            String key = current[0] + "," + current[1];
            current = parentMap.get(key);
        }

        Collections.reverse(path); // vì truy vết từ đích => đảo
        return path;
    }

    int getManhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public List<int[]> solveBFS(int[][] maze, int[] start, int[] end) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Map<String, int[]> parentMap = new HashMap<>();

        visited[start[0]][start[1]] = true;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();

            if(curr[0] == end[0] && curr[1] == end[1]) return reconstructPath(parentMap, end);


            for(int[] d : dirs) {
                int nx = curr[0] + d[0];
                int ny = curr[1] + d[1];

                if(isValid(maze, nx, ny, visited) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    parentMap.put(nx + "," + ny, curr);
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        return null;
    };

    public List<int[]> solveDFS(int[][] maze, int[] start, int[] end) {
        Stack<int[]> stack = new Stack<>();
        stack.push(start);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        Map<String, int[]> parentMap = new HashMap<>();

        visited[start[0]][start[1]] = true;

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while(!stack.isEmpty()) {
            int[] curr = stack.pop();

            if(curr[0] == end[0] && curr[1] == end[1]) return reconstructPath(parentMap, end);

            for(int[] d : dirs) {
                int nx = curr[0] + d[0];
                int ny = curr[1] + d[1];

                if(isValid(maze, nx, ny, visited) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    parentMap.put(nx + "," + ny, curr);
                    stack.push(new int[]{nx, ny});
                }
            }
        }
        return null;
    };

    public List<int[]> solveAstar(int[][] maze, int[] start, int[] end) {
        PriorityQueue<MyNode> pq = new PriorityQueue<>();
        Map<String, int[]> parentMap = new HashMap<>();
        // Lưu giá trị G nhỏ nhất từng đạt được tới mỗi ô
        Map<String, Integer> gValues = new HashMap<>();

        int startH = getManhattanDistance(start[0], start[1], end[0], end[1]);
        pq.add(new MyNode(start[0], start[1], 0, startH));
        gValues.put(start[0] + "," + start[1], 0);

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        while (!pq.isEmpty()) {
            MyNode curr = pq.poll();

            // Nếu chạm đích
            if (curr.x == end[0] && curr.y == end[1]) {
                return reconstructPath(parentMap, end);
            }

            for (int[] d : dirs) {
                int nx = curr.x + d[0];
                int ny = curr.y + d[1];

                if (nx >= 0 && nx < maze.length && ny >= 0 && ny < maze[0].length && maze[nx][ny] == 0) {
                    int newG = curr.g + 1;
                    String key = nx + "," + ny;

                    if (!gValues.containsKey(key) || newG < gValues.get(key)) {
                        gValues.put(key, newG);
                        parentMap.put(key, new int[]{curr.x, curr.y});
                        int h = getManhattanDistance(nx, ny, end[0], end[1]);
                        pq.add(new MyNode(nx, ny, newG, h));
                    }
                }
            }
        }
        return null;
    }
}


