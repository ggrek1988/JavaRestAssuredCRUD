import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BugsLocalhostPUTPATCHTest {

    private static Faker faker;
    private String title;
    private String description;
    private Integer emploeeId;
    private String status;


    @BeforeAll//wykona się metoda raz przed testami
    public static void beforeAll() {

        faker = new Faker();
    }

    @BeforeEach//wykonać się przed kazdym  testem
    public void BeforeEach() {

        title = faker.job().title();
        description = faker.color().name();
        emploeeId = faker.number().numberBetween(1, 5);


    }


    @Test
    public void localhostUpdateNewBugsPUT() {

        JSONObject user = new JSONObject();
        user.put("title", title);
        user.put("description", description);
        user.put("emploeeId", emploeeId);
        user.put("status", "newOpen");

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .pathParam("bugsID", 2)
                .when()
                .put(Globalsettings.URL + "/" + Globalsettings.BUGS + "/{bugsID}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(title, json.get("title"));
        assertEquals(description, json.get("description"));
        assertEquals("newOpen", json.get("status"));


    }

    @Test
    public void localhostUpdateNewBugsPATCH() {


        JSONObject userStatsu = new JSONObject();

        userStatsu.put("status", "close");

        Response response = given()
                .contentType("application/json")
                .body(userStatsu.toString())
                .pathParam("bugsID", 2)
                .when()
                .patch(Globalsettings.URL + "/" + Globalsettings.BUGS + "/{bugsID}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();


        assertEquals("close", json.get("status"));


    }
}
