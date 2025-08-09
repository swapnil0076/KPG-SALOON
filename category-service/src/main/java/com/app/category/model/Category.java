package com.app.category.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Random;

@Entity
@Data
public class Category {

    @Id
    @Column(length = 13)
    private String id;

    @PrePersist
    public void generateCustomId() {
        if (this.id == null) {
            this.id = generateUniqueId();
        }
    }

    private String generateUniqueId() {
        long number = Math.abs(new Random().nextLong() % 1_000_000_000_000L);
        return String.format("%012d", number) + "A";
    }



    @Column(unique = false)
    private String name;

    private String image;

    private Long salonId;


}
