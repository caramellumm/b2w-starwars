package br.com.b2w.rest.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse.Status;

import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.connection.DBConnection;
import br.com.b2w.mongo.document.Planetas;
import br.com.b2w.rest.PlanetasRest;	

public class PlanetasRestTest extends JerseyTest{
	
	public static String URL_SERVICE = "http://localhost:9998";
	public static Client client;
	
	public static String PLANETA_1 = "Terra Teste";
	public static String PLANETA_2 = "Jupter Teste";
	public static String PLANETA_3 = "Venus Teste";
	public static String PLANETA_4 = "Yavin IV";
	
	public static ObjectId P1Id;
	public static ObjectId P2Id;
	public static ObjectId P3Id;
	
	public static DBConnection connection;
	
	public static Planetas planeta1;
	public static Planetas planeta2;
	public static Planetas planeta3;
	
	  public PlanetasRestTest(){
		  client = Client.create();
	  }
	 
    @Override
    protected Application configure() {
        return new ResourceConfig(PlanetasRest.class);
    }
	
	@Before
	public void inserirMassaParaTestes() throws IntegrationException {
		
		connection = new DBConnection();
		
		planeta1 = new Planetas();
		planeta1.setNome(PLANETA_1);
		planeta1.setTerreno("arenoso");
		planeta1.setClima("quente");
		connection.inserirPlaneta(planeta1);
		
		planeta2 = new Planetas();
		planeta2.setNome(PLANETA_2);
		planeta2.setTerreno("arenoso");
		planeta2.setClima("quente");
		connection.inserirPlaneta(planeta2);

		planeta3 = new Planetas();
		planeta3.setNome(PLANETA_3);
		planeta3.setTerreno("arenoso");
		planeta3.setClima("quente");
		connection.inserirPlaneta(planeta3);
		
		FindIterable<Planetas> dadosPlaneta1 = connection.buscarPlanetasPorNome(planeta1.getNome());
		Planetas p1 = dadosPlaneta1.first();
		P1Id = p1.getId();
		

		FindIterable<Planetas> dadosPlaneta2 = connection.buscarPlanetasPorNome(planeta2.getNome());
		Planetas p2 = dadosPlaneta2.first();
		P2Id = p2.getId();
		

		FindIterable<Planetas> dadosPlaneta3 = connection.buscarPlanetasPorNome(planeta3.getNome());
		Planetas p3 = dadosPlaneta3.first();
		P3Id = p3.getId();
		
	}
	
	@After
	public void removerMassaTeste() throws IntegrationException {
		connection = new DBConnection();
		connection.removerPlanetaPorNome(PLANETA_1);
		connection.removerPlanetaPorNome(PLANETA_2);
		connection.removerPlanetaPorNome(PLANETA_3);
		connection.removerPlanetaPorNome(PLANETA_4);
		connection.removerPlanetaPorNome("");
	}
    
	@Test
	public void testInserirPlaneta() {
		String dados = new String();
		Response response = target("/planetas/" + planeta1.getNome() +"/" + planeta1.getClima() + "/" + planeta1.getTerreno()).request().put(Entity.entity(dados, MediaType.APPLICATION_JSON));
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
    	
	@Test
	public void testListarPlanetas() {
		Response response = target("/planetas").request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
    
	@Test
	public void testListarPlanetasPorNome() {
		Response response = target("/planetas/"+planeta1.getNome()).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
    
	@Test
	public void testListarPlanetasPorID() {
		String dados = new String();
		dados = P1Id.toString();
		Response response = target("/planetas/"+ P1Id.toString()).request().post(Entity.entity(dados, MediaType.APPLICATION_JSON));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
    
	@Test
	public void testRemoverPlaneta() {
		Response response = target("/planetas/"+ P1Id.toString()).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

}
