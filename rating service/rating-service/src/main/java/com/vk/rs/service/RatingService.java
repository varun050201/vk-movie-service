package com.vk.rs.service;

import com.vk.rs.exception.NotFoundException;
import com.vk.rs.model.Rating;
import com.vk.rs.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Transactional
    public double updateAverageRating(String name, Double stars) {
        Rating rating = ratingRepository.findByName(name);

        if (rating == null) {
            // If no rating exists, create a new one with the provided stars
            rating = new Rating();
            rating.setName(name);
            rating.setAvgRating(stars);
            rating.setCount(1);
        } else {
            // Calculate the new average rating if the rating already exists
            int Count =rating.getCount();
            double newAverageRating = ((rating.getAvgRating() * Count + stars) / (Count + 1));


            // Update the rating fields
            rating.setAvgRating(newAverageRating);
            rating.setCount(Count+1);
        }

        // Save the rating (whether new or updated)
        ratingRepository.save(rating);
        return rating.getAvgRating();
    }
    // Method to fetch the rating based on name
    public Rating fetchRating(String name) {
        Rating rating = ratingRepository.findByName(name);

        if (rating == null) {
            throw new NotFoundException("Rating for movie " + name + " not found");
        }

        return rating;
    }
}
