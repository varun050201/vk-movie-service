package com.vk.rs.model;

import lombok.Data;

@Data
public class RatingRequest {
    private String name;
    private double stars;
}