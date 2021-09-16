package models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class Vet {
    private Integer id;
    private String firstName="";
    private String lastName="";
    public List<Specialty> specialties;
}
