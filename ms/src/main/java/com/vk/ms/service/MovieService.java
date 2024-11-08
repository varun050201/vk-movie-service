package com.vk.ms.service;

import com.vk.ms.entity.Movie;
import com.vk.ms.exception.InvalidDataException;
import com.vk.ms.exception.NotFoundException;
import com.vk.ms.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie create(Movie movie) {
        if (movie == null) {
            throw new InvalidDataException("Invalid movie data: Name, director, and actors list cannot be empty.");
        }
        return movieRepository.save(movie);
    }

    public Movie read(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie with id " + id + " not found"));
    }

    public void update(Long id, Movie update) {
        if (update == null || id == null || update.getName() == null || update.getDirector() == null) {
            throw new InvalidDataException("Invalid movie data: Name and director cannot be null.");
        }
        if (movieRepository.existsById(id)) {
            Movie movie = movieRepository.getReferenceById(id);

            // Updating the fields from the 'update' object
            movie.setName(update.getName());
            movie.setDirector(update.getDirector());
            movie.setActors(update.getActors());
            movieRepository.save(movie);
        } else {
            throw new NotFoundException("Movie with id " + id + " not found");
        }
    }

    public void delete(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            throw new NotFoundException("Movie with id " + id + " not found");
        }
    }
}
