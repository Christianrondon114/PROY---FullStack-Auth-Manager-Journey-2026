package com.attemp3.sc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private double discount;
    private String brand;
    private String imageUrl;
    private boolean available;
    private LocalDateTime releaseDate;

    public Product(String name, String description, double price, int stock, String category, double discount, String brand, String imageUrl, boolean available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.discount = discount;
        this.brand =brand;
        this.imageUrl = imageUrl;
        this.available = available;
    }
}
