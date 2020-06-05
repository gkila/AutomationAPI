package testAPI;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

public class SimulacaoApiTest {

	public static Response doDeleteRequest(String id) {
        RestAssured.defaultParser = Parser.JSON;

        return        		
        		
            given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                when().delete("http://localhost:8080/api/v1/simulacoes/"+id).
                then().contentType(ContentType.JSON).extract().response();
    }	
	
	public static Response doPostRequest(String endpoint, String json) {
        RestAssured.defaultParser = Parser.JSON;

        return        		
        		
            given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
            contentType(ContentType.JSON).	
            	body(json).
                when().post(endpoint).
                then().contentType(ContentType.JSON).extract().response();
    }	
	
	public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
            given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                when().get(endpoint).
                then().contentType(ContentType.JSON).extract().response();
    }	
	
	public static Response doPutRequest(String endpoint, String cpf, String body) {
        RestAssured.defaultParser = Parser.JSON;

        return
            given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
            	body(body).
                when().put(endpoint+"/"+cpf).
                then().contentType(ContentType.JSON).extract().response();
    }

	@BeforeClass
	public static void setup() {
		baseURI = "http://localhost:8080/api/v1/simulacoes";
	}	
	
	
	@Test
	public void criarEdeletarSimulacaoValida() throws Exception {
		String myJson = "{\"nome\": \"Ronaldo\",\"cpf\": 84809766099,\"email\": \"emailemail@email.com\",\"valor\": 11200,\"parcelas\": 3,\"seguro\": true}";
		Response response = doPostRequest(baseURI,myJson);
		int sc = response.getStatusCode();
		String id = response.jsonPath().getString("id");
    	 if(sc!=201) {
    	 throw new Exception("Exception message");
    	 }
    	 doDeleteRequest(id);
    	 
		         
	}
	
	@Test
	public void criarDeletarSimulacaoValida() throws Exception {
		String myJson = "{\"nome\": \"Ronaldo\",\"cpf\": 84809766099,\"email\": \"emailemail@email.com\",\"valor\": 11200,\"parcelas\": 3,\"seguro\": true}";
		Response response = doPostRequest(baseURI,myJson);
		int sc = response.getStatusCode();
		String id = response.jsonPath().getString("id");
    	 if(sc!=201) {
    	 throw new Exception("Exception message");
    	 }
    	 doDeleteRequest(id);
    	 
		         
	}
	
	@Test
	public void criarSimulacaoComMesmoCpf() throws Exception {
		String myJson = "{\"nome\": \"Ronaldo\",\"cpf\": 84809766099,\"email\": \"emailemail@email.com\",\"valor\": 11200,\"parcelas\": 3,\"seguro\": true}";
		Response response = doPostRequest(baseURI,myJson);
		int sc = response.getStatusCode();
		String id = response.jsonPath().getString("id");
    	 if(sc!=201) {
    	 throw new Exception("Exception message");
    	 }
    	 
    	 response = doPostRequest(baseURI,myJson);
 		 sc = response.getStatusCode(); 		
     	 if(sc!=400) {
     	 throw new Exception("Exception message");
     	 }
    	 doDeleteRequest(id);
    	 
    	 
		         
	}
	
	@Test
	public void criarSimulacaoFaltandoDados() throws Exception {
		String myJson = "{\"nome\": ,\"cpf\": 84809766099,\"email\": \"email2@email.com\",\"valor\": 11200,\"parcelas\": 3,\"seguro\": true}";
				
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(myJson)
		.when()
			.post(baseURI)
		.then()
			.log().all()
			.statusCode(400);
			    
		
		         
	}
	
	@Test
	public void consultarSimulacoes() throws Exception {
		int sc;
		ArrayList<String> listaID = new ArrayList<String> ();
		
		Response response = doGetRequest(baseURI);
		Response response2;
        List<String> jsonResponse = response.jsonPath().getList("$");
       
        for(int i = 0;i<jsonResponse.size();i++) {
        	listaID.add(response.jsonPath().getString("id["+i+"]"));
        	//Consulto a simulacao pelo cpf abaixo:
        	 response2 = doGetRequest(baseURI+"/"+response.jsonPath().getString("cpf["+i+"]"));
        	 sc = response2.getStatusCode();
        	 if(sc!=200) {
        	 throw new Exception("Exception message");
        	 }
        	 
        }
	}
        
        @Test
    	public void EditarSimulacao() throws Exception {
        	String myJson = "{\"nome\": \"Ronaldo\",\"cpf\": 84809766099,\"email\": \"emailemail@email.com\",\"valor\": 11200,\"parcelas\": 3,\"seguro\": true}";
        	String myJson2 = "{\"nome\": \"Rivaldo\",\"cpf\": 84809766099,\"email\": \"emailemail2@email.com\",\"valor\": 11200,\"parcelas\": 3,\"seguro\": true}";

        	Response response = doPostRequest(baseURI,myJson);
    		int sc = response.getStatusCode();
    		String cpf = response.jsonPath().getString("cpf");
    		String id = response.jsonPath().getString("id");
        	 if(sc!=201) {
        	 throw new Exception("Exception message");
        	 }
    		
    		Response response2 = doPutRequest(baseURI,cpf,myJson2);
    		
    		 sc = response2.getStatusCode();
        	 if(sc!=200) {
        	 throw new Exception("Exception message");
        	 }
        	 doDeleteRequest(id);
	

}
	}
