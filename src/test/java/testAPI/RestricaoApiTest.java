package testAPI;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import com.jayway.restassured.http.ContentType;
import java.util.Random;
import org.junit.BeforeClass;



public class RestricaoApiTest {

	@BeforeClass
	public static void setup() {
		baseURI = "http://localhost:8080/api/v1/restricoes";
	}

	@Test
	public void consultarCpfComRestricao() {
		String[] cpfInvalidos = {"97093236014","60094146012","84809766080","62648716050","26276298085","01317496094","55856777050","19626829001","24094592008","58063164083"};
		Random r = new Random();		
		int size = cpfInvalidos.length;
		String cpf = cpfInvalidos[r.nextInt(size)];
		
		given()
			.log().all()
			.contentType(ContentType.JSON)			
		.when()
			.get(baseURI+"/"+cpf)
		.then()
			.log().all()
			.statusCode(200)			
			.body("mensagem", containsString("O CPF " + 
					cpf +" tem problema"))
		;        
    	 	         
	}
	
	@Test
	public void consultarCpfSemRestricao() {
		String[] cpfValidos = {"97093236011","60094146011","84809766081","62648716051","26276298081"};
		Random r = new Random();		
		int size = cpfValidos.length;
		String cpf = cpfValidos[r.nextInt(size)];
		
		given()
			.log().all()
			.contentType(ContentType.JSON)			
		.when()
			.get(baseURI+"/"+cpf)
		.then()
			.log().all()
			.statusCode(204)		
		;        
    	 	         
	}
	

}
