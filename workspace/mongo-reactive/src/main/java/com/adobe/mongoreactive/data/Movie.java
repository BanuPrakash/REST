package com.adobe.mongoreactive.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document // instead of @Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private  String id;

    private String title;
    private int year;
    private String genre;
    private  double imdbRating;
}
