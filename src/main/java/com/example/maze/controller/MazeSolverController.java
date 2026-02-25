package com.example.maze.controller;

import com.example.maze.dto.MazeSolverReq;
import com.example.maze.model.MazeAlgorithm;
import com.example.maze.model.MazeSolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/maze")

public class MazeSolverController {
    @PostMapping("/solve")
    ResponseEntity<?> solve(@RequestBody MazeSolverReq req) {
        MazeAlgorithm algo = MazeAlgorithm.getAlgorithm(req.algorithm());
        MazeSolver solver = new MazeSolver();

        List<int[]> path;
        switch (algo) {
            case BFS -> path = solver.solveBFS(req.map(), req.start(), req.end());
            case DFS -> path = solver.solveDFS(req.map(), req.start(), req.end());
            default -> path = solver.solveAstar(req.map(), req.start(), req.end());
        }
        if (path == null) {
            return ResponseEntity.ok(new ArrayList<int[]>());
        }
        return ResponseEntity.ok(path);
    }
}
