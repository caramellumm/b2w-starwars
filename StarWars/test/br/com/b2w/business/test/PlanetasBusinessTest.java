package br.com.b2w.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import br.com.b2w.business.PlanetasBusiness;
import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;

public class PlanetasBusinessTest extends JerseyTest{
	
	@Override
    protected Application configure() {
        return new ResourceConfig(PlanetasBusiness.class);
    }
	
	@Test
	public void testInserirPlaneta() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		
		JSONObject jsonObjectListPlanetas = new JSONObject();
		jsonObjectListPlanetas.put("retorno", "Insert Efetuado com Sucesso");

		JSONObject inserirPlaneta = planetasBusiness.inserirPlaneta("Plutao", "Quente", "Arenoso");
		assertEquals(jsonObjectListPlanetas.get("retorno"), inserirPlaneta.get("retorno"));
		
	}
	
	@Test(expected = BusinessException.class)
	public void testErroInserirPlaneta() throws BusinessException, IntegrationException {
		
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.inserirPlaneta(anyString(), anyString(), anyString())).thenThrow(new BusinessException("Erro no fluxo de inserir planeta", new Exception()));
		spy.inserirPlaneta("terra", "quente", "arenoso");	
		
	}
	
	@Test
	public void testListarPlanetas() throws IntegrationException, BusinessException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		JSONArray listarPlanetas = planetasBusiness.listarPlanetas();
		assertNotNull(listarPlanetas);

	}
	
	@Test
	public void testListarPlanetasPorNome() {
		
	}
	
	@Test
	public void testListarPlanetasPorNoId() {
		
	}
	
	@Test
	public void testRemoverPlaneta() {
		
	}
	
	@Test
	public void testBuscarAparicoesEmFilmes() {
		
	}
	
	@Test
	public void testConverterParaJson() {
		
	}
	
	
}
