package org.example.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class Pet {
    private long id;
    private Category category;
    private String name;
    private ArrayList<String> photoUrls;
    private ArrayList<Tag> tags;
    private String status;

    public  Pet() {
    }

    public Pet(long id, Category category, String name, ArrayList<String> photoUrls, ArrayList<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }
}
