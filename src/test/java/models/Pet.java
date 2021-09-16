package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pet {
    private String birthDate = "2021-08-23T08:21:51.031Z";

    private int id  = 15;

    private String name ="Rex";

    private int owner;

    private Type type;

    private List<Visit> visits;
}
