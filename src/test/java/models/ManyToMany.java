package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class ManyToMany {
    Integer id1;
    Integer id2;
}
