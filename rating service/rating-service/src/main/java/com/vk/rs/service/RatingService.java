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
    RatingRepository ratingRepository;

    public Rating updateAverage(String name, double stars){

        Rating rating = ratingRepository.findByName(name);

        if(rating == null){
            rating = new Rating();
            rating.setName(name);
            rating.setAvgRating(stars);
            rating.setCount(1);
        } else {
            int count = rating.getCount();
            double newAverage = (rating.getAvgRating() * count + stars) / (count + 1);

            rating.setAvgRating(newAverage);
            rating.setCount(++count);
        }

        return ratingRepository.save(rating);
    }

    public Rating fetchRating(String name){
        Rating rating = ratingRepository.findByName(name);

        if(rating == null){
            throw new NotFoundException("Movie not found with name: " + name);
        }

        return rating;
    }
}