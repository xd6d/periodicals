package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Publication {
    private int id;
    private String title;
    private String titleEN;
    private String titleUK;
    private double price;
    private List<Topic> topics;

    public Publication(String title, double price, List<Topic> topics) {
        this.title = title;
        this.titleEN = title;
        this.price = price;
        this.topics = topics;
    }

    public Publication(int id, String title, double price, List<Topic> topics) {
        this.id = id;
        this.title = title;
        this.titleEN = title;
        this.price = price;
        this.topics = topics;
    }

    public Publication(String title, String titleUK, double price, List<Topic> topics) {
        this.title = title;
        this.titleEN = title;
        this.titleUK = titleUK;
        this.price = price;
        this.topics = topics;
    }

    public Publication(int id, String title, String titleUK, double price, List<Topic> topics) {
        this.id = id;
        this.title = title;
        this.titleEN = title;
        this.titleUK = titleUK;
        this.price = price;
        this.topics = topics;
    }

    public Publication(String title, double price) {
        this.title = title;
        this.titleEN = title;
        this.price = price;
        this.topics = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication)) return false;
        Publication that = (Publication) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
