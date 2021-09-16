package util;


import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;


public final class World {

  @Getter
  @Setter
  private static Response response;
}
