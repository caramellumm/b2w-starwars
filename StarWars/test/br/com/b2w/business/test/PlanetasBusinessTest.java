package br.com.b2w.business.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;

import org.bson.types.ObjectId;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import br.com.b2w.business.PlanetasBusiness;
import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.document.Planetas;

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
		when(spy.inserirPlaneta(anyString(), anyString(), anyString()))
		.thenThrow(new BusinessException("Erro no fluxo de inserir planeta", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de inserir planeta", new Exception()));
		spy.inserirPlaneta("terra", "quente", "arenoso");	
		
	}
	
	@Test
	public void testListarPlanetas() throws IntegrationException, BusinessException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		JSONArray listarPlanetas = planetasBusiness.listarPlanetas();
		assertNotNull(listarPlanetas);

	}
	
	@Test(expected = BusinessException.class)
	public void testErroListarPlanetas() throws IntegrationException, BusinessException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.listarPlanetas())
		.thenThrow(new BusinessException("Erro no fluxo de listar planetas", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de listar planetas", new Exception()));
		spy.listarPlanetas();
	}
	
	@Test
	public void testListarPlanetasPorNome() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		String nome = "Terra";
		JSONArray listarPlanetas = planetasBusiness.listarPlanetas(nome);
		assertNotNull(listarPlanetas);
	}
	
	@Test(expected = BusinessException.class)
	public void testErroListarPlanetasPorNome() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.listarPlanetas(anyString()))
		.thenThrow(new BusinessException("Erro no fluxo de listar planetas", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de listar planetas", new Exception()));
		String nome = "Terra";
		spy.listarPlanetas(nome);
	}
	
	@Test
	public void testListarPlanetasPorId() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		ObjectId id = new ObjectId("5d432dd386b70577b5edb8fb");
		JSONObject listarPlanetas = planetasBusiness.listarPlanetas(id);
		assertNotNull(listarPlanetas);
	}
	
	
	@Test(expected = BusinessException.class)
	public void testErroListarPlanetasPorId() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when( spy.listarPlanetas(any(ObjectId.class)) )
		.thenThrow(new BusinessException("Erro no fluxo de listar planetas", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de listar planetas", new Exception()));
		ObjectId id = new ObjectId("5d42f35df564a3177a53b4f2");
		 spy.listarPlanetas(id);
	}
	
	@Test
	public void testRemoverPlaneta() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		String id = "5d44313e94dcc961b3120e91";
		JSONObject removerPlaneta = planetasBusiness.removerPlaneta(id);
		JSONObject jsonObjectListPlanetas = new JSONObject();
		jsonObjectListPlanetas.put("retorno", "Documento deletado com Sucesso");
		assertEquals(jsonObjectListPlanetas.get("retorno"), removerPlaneta.get("retorno"));
		
	}
	
	@Test(expected = BusinessException.class)
	public void testErroRemoverPlaneta() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		String id = "5d432dd386b7057";
		planetasBusiness.removerPlaneta(id);
		
	}
	
	@Test(expected = BusinessException.class)
	public void testErroRemoverPlaneta2() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		String id = "5d432dd386b70577b5edb8fb";
		when(spy.removerPlaneta(id))
		.thenThrow(new BusinessException("Erro no fluxo de remover planetas", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de remover planetas", new Exception()));
		spy.removerPlaneta(id);
	}
	
	@Test
	public void testBuscarAparicoesEmFilmes() throws Exception {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		List<Planetas> listPlanetas = new ArrayList<Planetas>();
		Planetas planeta = new Planetas();
		planeta.setNome("Yavin IV");
		planeta.setTerreno("arenoso");
		planeta.setClima("quente");
		listPlanetas.add(planeta);
		List<Planetas> buscarAparicoesEmFilmes = planetasBusiness.buscarAparicoesEmFilmes(listPlanetas);
		assertNotNull(buscarAparicoesEmFilmes.get(0));
		
	}
	
	@Test(expected = BusinessException.class)
	public void testErroBuscarAparicoesEmFilmes() throws Exception {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.buscarAparicoesEmFilmes(anyListOf(Planetas.class)))
		.thenThrow(new BusinessException("Erro no fluxo de buscar filmes", new Exception()));
		
		List<Planetas> listPlanetas = new ArrayList<Planetas>();
		Planetas planeta = new Planetas();
		planeta.setNome("Yavin IV");
		planeta.setTerreno("arenoso");
		planeta.setClima("quente");
		listPlanetas.add(planeta);

		spy.buscarAparicoesEmFilmes(listPlanetas);
		
	}
	
	@Test
	public void testConverterParaJson() throws BusinessException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		List<Planetas> listPlanetas = new ArrayList<Planetas>();
		Planetas planeta = new Planetas();
		planeta.setNome("Yavin IV");
		planeta.setTerreno("arenoso");
		planeta.setClima("quente");
		
		String filme = "https://swapi.co/api/films/1/";
		List<String> filmes = new ArrayList<String>();
		filmes.add(filme);
		
		planeta.setFilmes(filmes);
		listPlanetas.add(planeta);
		
		JSONArray converterParaJson = planetasBusiness.converterParaJson(listPlanetas);
		assertNotNull(converterParaJson);
	}
	
	@Test(expected = BusinessException.class)
	public void testErroConverterParaJson() throws BusinessException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.converterParaJson(anyListOf(Planetas.class)))
		.thenThrow(new BusinessException("Erro no fluxo de converter para Json", new Exception()));
		
		List<Planetas> listPlanetas = new ArrayList<Planetas>();
		Planetas planeta = new Planetas();
		planeta.setNome("Yavin IV");
		planeta.setTerreno("arenoso");
		planeta.setClima("quente");
		
		String filme = "https://swapi.co/api/films/1/";
		List<String> filmes = new ArrayList<String>();
		filmes.add(filme);
		
		planeta.setFilmes(filmes);
		listPlanetas.add(planeta);
		
		spy.converterParaJson(listPlanetas);
		
	}
	
	
}
