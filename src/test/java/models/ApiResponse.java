package models;
import io.restassured.response.Response;
import lombok.*;

@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ApiResponse {

    public static String responseString;

    public static Response response;

    public static int statusCode;

    public static Specialty createdSpecialty;
    public static Specialty initialSpecialty;
    public static Specialty deletedSpecialty;

    public static PetType createdPetType;
    public static PetType initialPetType;
    public static PetType deletedPetType;

    public static Vet createdVet;
    public static Vet initialVet;
    public static Vet deletedVet;

    public static void saveStringAndStatusCode(){
        responseString=response.then().extract().body().asString();
        statusCode= response.getStatusCode();
    }
}
