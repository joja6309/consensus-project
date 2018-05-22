package com.grid.graph.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class GraphGeneratorController {
    @RequestMapping(path = "/generate-hypercube", method = GET)
    @CrossOrigin()
    @GetMapping()
    @ResponseBody
    public ResponseEntity<String> generateHyperCube(@RequestParam(name = "dim") int dim) {
        if (Objects.isNull(dim)) {
            return ResponseEntity.badRequest().body("Missing width param");
        }
        return ResponseEntity.ok("");

    }
}
