package br.com.b2w.rest.client.test;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import br.com.b2w.exception.ServiceException;
import br.com.b2w.rest.client.ServiceClient;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class ServiceClientTest extends JerseyTest{
	
	private static final String OBJETO_CACHEADO = "OBJETO_CACHEADO";
	
	@Override
    protected Application configure() {
        return new ResourceConfig(ServiceClient.class);
    }
	
	@Test
	public void testConsultarPlanetas() throws ServiceException {
		ServiceClient serviceClient = new ServiceClient();
		CacheManager.getInstance().removeAllCaches();
		HashMap<String, List<String>> consultarPlanetas = serviceClient.consultarPlanetas();
		assertNotNull(consultarPlanetas.size());
	}

	
	@Test
	public void testConsultarPlanetasCache() throws ServiceException {
		ServiceClient serviceClient = new ServiceClient();
		
		CacheManager.getInstance().removeAllCaches();
		
		Cache cache = new Cache(new CacheConfiguration(OBJETO_CACHEADO, 5000).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				.eternal(false).timeToLiveSeconds(60).timeToIdleSeconds(90).persistence(new PersistenceConfiguration().strategy(Strategy.NONE)));
		CacheManager.getInstance().addCache(cache);
		HashMap<String, List<String>> consultarPlanetas = serviceClient.consultarPlanetas();
		assertNotNull(consultarPlanetas.size());
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
