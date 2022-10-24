package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Topic {
    private final int id;
    private final String name;

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
