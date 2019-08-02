package br.com.b2w.rest.client.test;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import br.com.b2w.exception.ServiceException;
import br.com.b2w.mongo.document.ApiStarWarsPlanet;
import br.com.b2w.rest.client.ServiceClient;

public class ServiceClientTest  extends JerseyTest{
	
	@Override
    protected Application configure() {
        return new ResourceConfig(ServiceClient.class);
    }
	
	@Test
	public void testConsultarPlanetas() throws ServiceException {
		ServiceClient serviceClient = new ServiceClient();
		List<ApiStarWarsPlanet> consultarPlanetas = serviceClient.consultarPlanetas();
		assertNotNull(consultarPlanetas.get(0));
	}
	
	@Test(expected = ServiceException.class)
	public void testErroConsultarPlanetas() throws ServiceException {
		ServiceClient serviceClient = new ServiceClient();
		ServiceClient spy = spy(serviceClient);
		when(spy.consultarPlanetas())
		.thenThrow(new ServiceException("Erro no fluxo de remover planetas", new Exception()));
		spy.consultarPlanetas();
	}

}
