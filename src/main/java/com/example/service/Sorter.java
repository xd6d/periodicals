package com.example.service;

import com.example.entity.Publication;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    public List<Publication> sort(String sortName, String reversedName, List<Publication> publications) {
        if (sortName == null) {
            publications.sort(Comparator.comparingInt(Publication::getId).reversed());
            return publications;
        }
        if (sortName.equals("title"))
            publications.sort(Comparator.comparing(Publication::getTitle));
        if (sortName.equals("price"))
            publications.sort(Comparator.comparingDouble(Publication::getPrice));
        if (reversedName == null || reversedName.equals("false"))
            return publications;
        if (reversedName.equals("true"))
            Collections.reverse(publications);
        return publications;
    }
}
