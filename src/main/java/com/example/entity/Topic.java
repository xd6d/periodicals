package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Topic {
    private final int id;
    private String name;
    private String nameEN;
    private String nameUK;

    public Topic(int id, String name, String nameUK) {
        this.id = id;
        this.name = name;
        this.nameEN = name;
        this.nameUK = nameUK;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topic = (Topic) o;
        return getId() == topic.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
