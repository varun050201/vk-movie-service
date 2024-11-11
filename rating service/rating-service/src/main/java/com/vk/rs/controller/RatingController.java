package com.vk.rs.controller;

import com.vk.rs.model.Rating;
import com.vk.rs.model.RatingRequest;
import com.vk.rs.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Endpoint to fetch the rating for a movie by name
    @GetMapping("/{name}")
    public ResponseEntity<Rating> fetchRating(@PathVariable String name) {
        Rating rating = ratingService.fetchRating(name);
        return ResponseEntity.ok(rating);
    }

    // Endpoint to update the rating for a movie
    @PostMapping
    public Map<String, Double> updateRating(@RequestBody RatingRequest ratingRequest) {
        // Update the rating using RatingService and fetch the new average rating
        double newAverage = ratingService.updateAverageRating(ratingRequest.getName(), ratingRequest.getStars());

        // Return the updated average rating in the response
        return Map.of("average", newAverage);
    }
}
