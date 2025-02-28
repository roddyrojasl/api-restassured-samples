package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.Matchers.*;

public class MascotaSteps {

    public Response response;
    public int idMascota;
    public String nombreMascota= "";
    public String nuevoNombre="";


    @Given("que se prepara la tienda de mascotas")
    public void loadApiMascota(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @When("se añade la mascota nro {int} de nombre {string}")
    public void añadirMascota(int id, String nombre) throws Exception {
        idMascota = id;
        nombreMascota = nombre;
        String mascota = new String(Files.readAllBytes(Paths.get("src/test/resources/imput/Mascota.json")), StandardCharsets.UTF_8);

        JSONObject requestBody = new JSONObject(mascota);
        requestBody.put("id", id);
        requestBody.put("name",nombre);

        Files.write(Paths.get("src/test/resources/imput/Mascota.json"),requestBody.toString(4).getBytes(StandardCharsets.UTF_8));

        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("/pet");
        idMascota = response.jsonPath().getInt("id");
        System.out.println("id mascota:" +idMascota);
        System.out.println("nombre Mascota:" +nombreMascota);
    }

    @When("se consulta la mascota creada nro {int}")
    public void consultaMascota(int id){

        idMascota = id;

        response = RestAssured.given()
                .pathParam("id", idMascota)
                .when()
                .get("pet/{id}");


    }

    @When("se cambia nombre de mascota nro {int} a {string}")
    public void updateNOmbreMascota(int id, String cambiNombre) throws IOException {
        nuevoNombre = cambiNombre;
        idMascota = id;
        String mascotaCambio = new String(Files.readAllBytes(Paths.get("src/test/resources/imput/Mascota.json")), StandardCharsets.UTF_8);
        JSONObject requestBody = new JSONObject(mascotaCambio);
        requestBody.put("name",cambiNombre);
        Files.write(Paths.get("src/test/resources/imput/Mascota.json"),requestBody.toString(4).getBytes(StandardCharsets.UTF_8));

        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .put("/pet");
    }

    @When("se elimina a la mascota nro {int}")
    public void eliminarMascota(int id) {

        response = RestAssured.given()
                .pathParam("id", id)
                .when()
                .delete("pet/{id}");
    }

    @Then("se valida consulta de mascota")
    public void validarResponseConsulta(){

        while (response.statusCode()==404)
            consultaMascota(idMascota);

        response.then()
                .statusCode(200)
                .log().all();

    }

    @Then("se valida creacion de nueva mascota")
    public void validaCreacionMascota() {
        response.then()
                .statusCode(200)
                .body("id", equalTo(idMascota))
                .body("name",equalTo(nombreMascota))
                .log().all();
    }

    @Then("se valida cambio de nombre")
    public void validarCambioDeNombre() {

        response.then()
                .statusCode(200)
                .body("id", equalTo(idMascota))
                .body("name",equalTo(nuevoNombre))
                .log().all();
    }

    @Then("se valida eliminacion de mascota")
    public void validarEliminacion() {

        response.then()
                .statusCode(200)
                .log().all();

    }
}
