package com.epam.esm.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Major entity class for creating {@code Gift Certificate} objects.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate implements Serializable {

    private long id;
    private String name;
    private String description;
    private int price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<Tag> tags;

    public Certificate(String name, String description, int price, int duration,
                       LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

}