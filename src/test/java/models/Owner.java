package models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class Owner {

  private String id;

  private String address = "str. Test nr 10";

  private String city = "Arad";

  private String firstName = "";

  private String lastName = "";

  private String telephone = "0764321456";

  private List<Pet> pets;
}
