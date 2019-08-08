package br.com.b2w.mongo.connection.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Application;

import org.bson.types.ObjectId;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;

import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.connection.DBConnection;
import br.com.b2w.mongo.document.Planetas;	

public class DBConnectionTest extends JerseyTest{
	
	public static String PLANETA_1 = "Terra Teste";
	public static String PLANETA_2 = "Jupter Teste";
	public static String PLANETA_3 = "Venus Teste";
	public static String PLANETA_4 = "Saturno Teste";
	
	public static ObjectId P1Id;
	public static ObjectId P2Id;
	public static ObjectId P3Id;
	
	public static DBConnection connection;
	
	@Override
    protected Application configure() {
        return new ResourceConfig(DBConnection.class);
    }
	
	@Before
	public void inserirMassaParaTestes() throws IntegrationException {
		
		connection = new DBConnection();
		
		Planetas planeta1 = new Planetas();
		planeta1.setNome(PLANETA_1);
		planeta1.setTerreno("arenoso");
		planeta1.setClima("quente");
		connection.inserirPlaneta(planeta1);
		
		
		
		Planetas planeta2 = new Planetas();
		planeta2.setNome(PLANETA_2);
		planeta2.setTerreno("arenoso");
		planeta2.setClima("quente");
		connection.inserirPlaneta(planeta2);
		

		Planetas planeta3 = new Planetas();
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
	}
	
	@Test
	public void testGetCollection() throws IntegrationException {
		connection = new DBConnection();
		MongoCollection<Planetas> collection = connection.getCollection();
		String databaseName = collection.getNamespace().getDatabaseName();
		assertEquals("starwars", databaseName);
	}
	
	@Test(expected = IntegrationException.class)
	public void testErroGetCollection() throws IntegrationException {
		connection = new DBConnection();
		DBConnection spy = spy(connection);
		when(spy.getCollection()).thenThrow(new IntegrationException("Erro ao recuperar a conexão", new Exception()));
		spy.getCollection();
	}
	
	@Test
	public void testCloseConnection() throws IntegrationException {
		connection = new DBConnection();
		connection.closeConnection();
	}
	
	@Test
	public void testBuscarPlanetas() throws IntegrationException {
		connection = new DBConnection();
		FindIterable<Planetas> buscarPlanetas = connection.buscarPlanetas();
		assertNotNull(buscarPlanetas.first());
	}
	
	@Test(expected = IntegrationException.class)
	public void testErroBuscarPlanetas() throws IntegrationException {
		connection = new DBConnection();
		DBConnection spy = spy(connection);
		when(spy.buscarPlanetas()).thenThrow(new IntegrationException("Erro no fluxo de buscar planetas", new Exception()));
		spy.buscarPlanetas();
		
	}
	
	@Test
	public void testInserirPlaneta() throws IntegrationException {
		connection = new DBConnection();
		Planetas planeta4 = new Planetas();
		planeta4.setNome(PLANETA_4);
		planeta4.setTerreno("arenoso");
		planeta4.setClima("quente");
		connection.inserirPlaneta(planeta4);
	}
	
	
	@Test
	public void testbuscarPlanetasPorNome() throws IntegrationException {
		connection = new DBConnection();
		FindIterable<Planetas> buscarPlanetasPorNome = connection.buscarPlanetasPorNome(PLANETA_1);
		assertNotNull(buscarPlanetasPorNome.first());
	}
	
	
	@Test(expected = IntegrationException.class)
	public void testErrobuscarPlanetasPorNome() throws IntegrationException {
		connection = new DBConnection();
		DBConnection spy = spy(connection);
		when(spy.buscarPlanetasPorNome(anyString())).thenThrow(new IntegrationException("Erro no fluxo de buscar planetas", new Exception()));
		spy.buscarPlanetasPorNome(PLANETA_1);
		
	}
	
	@Test
	public void testBuscarPlanetasPorId() throws IntegrationException {
		connection = new DBConnection();
		FindIterable<Planetas> buscarPlanetasPorId = connection.buscarPlanetasPorId(P1Id);
		assertNotNull(buscarPlanetasPorId.first());
	}
	
	@Test(expected = IntegrationException.class)
	public void testErroBuscarPlanetasPorId() throws IntegrationException {
		connection = new DBConnection();
		DBConnection spy = spy(connection);
		when(spy.buscarPlanetasPorId(anyObject())).thenThrow(new IntegrationException("Erro no fluxo de buscar planetas", new Exception()));
		spy.buscarPlanetasPorId(P1Id);
	}
	
	@Test
	public void testRemoverPlanetaPorNome() throws IntegrationException {
		connection = new DBConnection();
		DeleteResult removerPlanetaPorNome = connection.removerPlanetaPorNome(PLANETA_2);
		Long deletedCount = removerPlanetaPorNome.getDeletedCount();
		Long deletados = new Long("1");
		assertEquals(deletados, deletedCount);
	}
	
	@Test(expected = IntegrationException.class)
	public void testErroRemoverPlanetaPorNome() throws IntegrationException {
		connection = new DBConnection();
		DBConnection spy = spy(connection);
		when(spy.removerPlanetaPorNome(anyString())).thenThrow(new IntegrationException("Erro no fluxo de remover Planeta", new Exception()));
		spy.removerPlanetaPorNome(PLANETA_2);
	}
	
	@Test
	public void testRemoverPlanetaPorID() throws IntegrationException {
		connection = new DBConnection();
		DeleteResult removerPlanetaPorID = connection.removerPlanetaPorID(P3Id.toString());
		Long deletedCount = removerPlanetaPorID.getDeletedCount();
		Long long1 = new Long("1");
		assertEquals(long1, deletedCount);
	}
	
	@Test(expected = IntegrationException.class)
	public void testErroRemoverPlanetaPorID() throws IntegrationException {
		connection = new DBConnection();
		DBConnection spy = spy(connection);
		when(spy.removerPlanetaPorID(P3Id.toString())).thenThrow(new IntegrationException("Erro no fluxo de remover planeta", new Exception()));
		String id = P3Id.toString();
		spy.removerPlanetaPorID(id);
	}

}
