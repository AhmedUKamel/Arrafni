package org.ahmedukamel.sohagy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "keywords")
public class Keyword {
    @Id
    private String word;

    public Keyword(String word) {
        this.word = word;
    }

    @ManyToMany
    @JoinTable(name = "keyword_businesses")
    private Set<Business> businesses = new HashSet<>();
}