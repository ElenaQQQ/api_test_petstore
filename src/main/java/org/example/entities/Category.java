package org.example.entities;

import lombok.Data;

@Data
public class Category {
    private long id;
    private String name;

    public Category() {
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
