package br.com.b2w.rest.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.b2w.rest.PlanetasRest;	

public class PlanetasRestTest extends JerseyTest{
	
	public static String URL_SERVICE = "http://localhost:9998";
	public static Client client;
	
	  public PlanetasRestTest(){
		  client = Client.create();
	  }
	 
	
    @Override
    protected Application configure() {
        return new ResourceConfig(PlanetasRest.class);
    }
    
	@Test
	public void testInserirPlaneta() {
		String dados = new String();
		Response response = target("/planetas/Terra/Quente/Montanhoso").request().put(Entity.entity(dados, MediaType.APPLICATION_JSON));
		assertEquals(200, response.getStatus());
	}
    	
	@Test(expected = Exception.class)
	public void testErroInserirPlaneta() {
		
		WebResource service = client.resource(URL_SERVICE);
		WebResource serviceSpy = spy(service);
		doThrow(Exception.class).when(serviceSpy).post();

		serviceSpy.path("/planetas/Terra/Quente/Montanhoso").accept(MediaType.APPLICATION_JSON).put();
		
	}
    
	@Test
	public void testListarPlanetas() {
		Response response = target("/planetas").request().get();
		assertEquals(200, response.getStatus());
	}
    
	@Test
	public void testListarPlanetasPorNome() {
		Response response = target("/planetas/terra").request().get();
		assertEquals(200, response.getStatus());
	}
    
	@Test
	public void testListarPlanetasPorID() {
		String dados = new String();
		dados = "5d432dd386b70577b5edb8fb";
		Response response = target("/planetas/5d432dd386b70577b5edb8fb").request().post(Entity.entity(dados, MediaType.APPLICATION_JSON));
		assertEquals(200, response.getStatus());
	}
    
	@Test
	public void testRemoverPlaneta() {
		Response response = target("/planetas/5d432f1d7268812c2bddc411").request().delete();
		assertEquals(200, response.getStatus());
	}

}
