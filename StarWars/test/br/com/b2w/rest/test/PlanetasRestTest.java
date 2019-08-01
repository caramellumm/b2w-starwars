package br.com.b2w.rest.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
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
	public void testListarPlanetas() {
		Response response = target("/planetas").request().get();
		assertEquals(response.getStatus(), 200);	
	}
	

    
	@Test
	public void testListarPlanetasPorNome() {
		Response response = target("/planetas/terra").request().get();
		assertEquals(response.getStatus(), 200);
	}

}
