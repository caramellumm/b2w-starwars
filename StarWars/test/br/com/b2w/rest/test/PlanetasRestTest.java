package br.com.b2w.rest.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import br.com.b2w.rest.PlanetasRest;	

public class PlanetasRestTest extends JerseyTest{
	
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
