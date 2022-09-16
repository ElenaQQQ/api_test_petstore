package org.example.entities;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class Tag {
    private long id;
    private String name;

    public Tag() {
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
