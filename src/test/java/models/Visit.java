package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Visit {
    private String date = "2021/08/23";

    private String description = "vizita la veterinar";

    private int id = 0;

    private Pet pet;
}

