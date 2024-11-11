package com.vk.ms.controller;

import com.vk.ms.entity.Movie;
import com.vk.ms.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Create a new movie
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        log.info("Received movie: {}", movie);
        try {
            Movie createdMovie = movieService.create(movie);
            log.info("Created movie: {}", createdMovie);
            return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error creating movie: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        log.info("Received request to get movie with id: {}", id);
        try {
            Movie movie = movieService.read(id);
            log.info("Returning movie with id: {}", movie.getId());
            return ResponseEntity.ok(movie);
        } catch (RuntimeException e) {
            log.error("Error fetching movie: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing movie
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        log.info("Received request to update movie with id: {}. Updated data: {}", id, updatedMovie);
        try {
            movieService.update(id, updatedMovie);
            log.info("Movie with id: {} updated successfully", id);
            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            log.error("Error updating movie: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a movie by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            movieService.delete(id);
            log.info("Movie with id: {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all movies (optional)

}
