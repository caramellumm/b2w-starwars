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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.client.FindIterable;

import br.com.b2w.business.PlanetasBusiness;
import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.connection.DBConnection;
import br.com.b2w.mongo.document.Planetas;

public class PlanetasBusinessTest extends JerseyTest{
	
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
	
	@Override
    protected Application configure() {
        return new ResourceConfig(PlanetasBusiness.class);
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
	public void testInserirPlaneta() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		
		JSONObject jsonObjectListPlanetas = new JSONObject();
		jsonObjectListPlanetas.put("retorno", "Insert Efetuado com Sucesso");

		JSONObject inserirPlaneta = planetasBusiness.inserirPlaneta(planeta1.getNome(), planeta1.getClima(), planeta1.getTerreno());
		assertEquals(jsonObjectListPlanetas.get("retorno"), inserirPlaneta.get("retorno"));
		
	}
	
	@Test(expected = BusinessException.class)
	public void testErroInserirPlaneta() throws BusinessException, IntegrationException {
		
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.inserirPlaneta(planeta1.getNome(), planeta1.getClima(), planeta1.getTerreno()))
		.thenThrow(new BusinessException("Erro no fluxo de inserir planeta", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de inserir planeta", new Exception())).thenCallRealMethod();
		spy.inserirPlaneta(planeta1.getNome(), planeta1.getClima(), planeta1.getTerreno());	
		
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
		JSONArray listarPlanetas = planetasBusiness.listarPlanetas(PLANETA_1);
		assertNotNull(listarPlanetas);
	}
	
	@Test(expected = BusinessException.class)
	public void testErroListarPlanetasPorNome() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when(spy.listarPlanetas(anyString()))
		.thenThrow(new BusinessException("Erro no fluxo de listar planetas", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de listar planetas", new Exception()));
		spy.listarPlanetas(PLANETA_1);
	}
	
	@Test
	public void testListarPlanetasPorId() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		JSONObject listarPlanetas = planetasBusiness.listarPlanetas(P1Id);
		assertNotNull(listarPlanetas);
	}
	
	
	@Test(expected = BusinessException.class)
	public void testErroListarPlanetasPorId() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		PlanetasBusiness spy = spy(planetasBusiness);
		when( spy.listarPlanetas(any(ObjectId.class)) )
		.thenThrow(new BusinessException("Erro no fluxo de listar planetas", new Exception()))
		.thenThrow(new IntegrationException("Erro no fluxo de listar planetas", new Exception()));
		 spy.listarPlanetas(P1Id);
	}
	
	@Test
	public void testRemoverPlaneta() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		JSONObject removerPlaneta = planetasBusiness.removerPlaneta(P2Id.toString());
		JSONObject jsonObjectListPlanetas = new JSONObject();
		jsonObjectListPlanetas.put("retorno", "Documento deletado com Sucesso");
		assertEquals(jsonObjectListPlanetas.get("retorno"), removerPlaneta.get("retorno"));
		
	}
	
	@Test(expected = BusinessException.class)
	public void testErroRemoverPlaneta() throws BusinessException, IntegrationException {
		PlanetasBusiness planetasBusiness = new PlanetasBusiness();
		planetasBusiness.removerPlaneta("5d432dd386b70577");
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
		planeta.setNome(PLANETA_4);
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
		planeta.setNome(PLANETA_4);
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
		planeta.setNome(PLANETA_4);
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
		planeta.setNome(PLANETA_4);
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
