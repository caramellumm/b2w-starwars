package br.com.b2w.business;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;

import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;
import br.com.b2w.exception.ServiceException;
import br.com.b2w.mongo.connection.DBConnection;
import br.com.b2w.mongo.document.ApiStarWarsPlanet;
import br.com.b2w.mongo.document.Planetas;
import br.com.b2w.rest.client.ServiceClient;

public class PlanetasBusiness {

	
	public JSONObject inserirPlaneta(String nome, String clima, String terreno) throws BusinessException, IntegrationException {
		
		DBConnection conn = null;
		try {
			conn = new DBConnection();
			
			Planetas planeta = new Planetas();
			planeta.setNome(nome);
			planeta.setClima(clima);
			planeta.setTerreno(terreno);
			
			conn.inserirPlaneta(planeta);
			
			JSONObject jsonObjectListPlanetas = new JSONObject();
			
			jsonObjectListPlanetas.put("retorno", "Insert Efetuado com Sucesso");
			
			return jsonObjectListPlanetas;
			
		} catch (Exception e) {
			throw new BusinessException("Erro no fluxo de inserir planeta", e);
		} finally {
			conn.closeConnection();
		}
	}
	
	public JSONArray listarPlanetas() throws IntegrationException, BusinessException {
		DBConnection conn = null;
		try {
			conn = new DBConnection();
			FindIterable<Planetas> buscarPlanetas = conn.buscarPlanetas();

			List<Planetas> listPlanetas = new ArrayList<Planetas>();

			buscarPlanetas.forEach(new Consumer<Planetas>() {

				@Override
				public void accept(Planetas planeta) {
					listPlanetas.add(planeta);
				}
			});
			
			List<Planetas> planetasComAparicaoEmFilme = this.buscarAparicoesEmFilmes(listPlanetas);
			
			return this.converterParaJson(planetasComAparicaoEmFilme);

		} catch (IntegrationException e) {
			throw new BusinessException(e.getMessage(), e);

		} catch (Exception e) {
			throw new BusinessException("Erro no fluxo de listar planetas", e);
		} finally {
			conn.closeConnection();
		}

	}
	
	public JSONArray listarPlanetas(String nome) throws BusinessException, IntegrationException{
		
		DBConnection conn = null;
		try {
			conn = new DBConnection();
			FindIterable<Planetas> buscarPlanetas = conn.buscarPlanetasPorNome(nome);
			
			List<Planetas> listPlanetas = new ArrayList<Planetas>();

			buscarPlanetas.forEach(new Consumer<Planetas>() {

				@Override
				public void accept(Planetas planeta) {
					listPlanetas.add(planeta);
				}
			});
			
			List<Planetas> planetasComAparicaoEmFilme = this.buscarAparicoesEmFilmes(listPlanetas);
			
			return this.converterParaJson(planetasComAparicaoEmFilme);
			
		} catch (IntegrationException e) {
			throw new BusinessException(e.getMessage(), e);
			
		} catch (Exception e) {
			throw new BusinessException("Erro no fluxo de listar planetas", e);
		} finally {
			conn.closeConnection();
		}
		
	}
	
	public JSONObject listarPlanetas(ObjectId Id) throws BusinessException, IntegrationException {

		DBConnection conn = null;
		try {
			conn = new DBConnection();
			FindIterable<Planetas> buscarPlanetas = conn.buscarPlanetas();

			JSONObject jsonObjectListPlanetas = new JSONObject();

			buscarPlanetas.forEach(new Consumer<Planetas>() {

				@Override
				public void accept(Planetas planeta) {
					jsonObjectListPlanetas.put("Id", planeta.getId());
					jsonObjectListPlanetas.put("Nome", planeta.getNome());
					jsonObjectListPlanetas.put("Clima", planeta.getClima());
					jsonObjectListPlanetas.put("Terreno", planeta.getTerreno());
				}
			});
			return jsonObjectListPlanetas;

		} catch (IntegrationException e) {
			throw new BusinessException(e.getMessage(), e);

		} catch (Exception e) {
			throw new BusinessException("Erro no fluxo de listar planetas", e);
		} finally {
			conn.closeConnection();
		}

	}
	
	public JSONObject removerPlaneta(String id) throws BusinessException, IntegrationException {

		DBConnection conn = null;
		try {

			conn = new DBConnection();
			DeleteResult resultado = conn.removerPlanetaPorID(id);
			JSONObject jsonObjectListPlanetas = new JSONObject();
			
			if(resultado.wasAcknowledged()) {
				jsonObjectListPlanetas.put("retorno", "Documento deletado com Sucesso");
				jsonObjectListPlanetas.put("Numero de Documentos Deletados", resultado.getDeletedCount());
			} else {
				jsonObjectListPlanetas.put("retorno", "Nenhum documento deletado");
			}
			
			return jsonObjectListPlanetas;
			
		} catch (IntegrationException e) {
			throw new BusinessException(e.getMessage(), e);
			
		} catch (Exception e) {
			throw new BusinessException("Erro no fluxo de remover planeta", e);
		} finally {
			conn.closeConnection();
		}
	}
	
	public List<Planetas> buscarAparicoesEmFilmes(List<Planetas> listPlanetas) throws Exception {
		try {
			ServiceClient client = new ServiceClient();
			List<ApiStarWarsPlanet> consultarPlanetas = client.consultarPlanetas();
			
			for (Planetas planeta : listPlanetas) {
				for (ApiStarWarsPlanet planetApiStarWars : consultarPlanetas) {
					if(planetApiStarWars.getName().equals(planeta.getNome())){
						planeta.setFilmes(planetApiStarWars.getFilms());
					}
				}
			}
			
			return listPlanetas;
			
		} catch (ServiceException e) {
			throw new BusinessException(e.getMessage(), e);
			
		}  catch (Exception e) {
			throw new BusinessException("Erro no fluxo de buscar aparicoes em filmes", e);
		}
	}
	
	public JSONArray converterParaJson(List<Planetas> planetas) throws BusinessException {
		try {
			
			JSONArray jsonArrayPlanetas = new JSONArray();
			JSONObject jsonObjectListPlanetas;
			
			for (Planetas planeta : planetas) {
				jsonObjectListPlanetas = new JSONObject();
				jsonObjectListPlanetas.put("Id", planeta.getId());
				jsonObjectListPlanetas.put("Nome", planeta.getNome());
				jsonObjectListPlanetas.put("Clima", planeta.getClima());
				jsonObjectListPlanetas.put("Terreno", planeta.getTerreno());
				jsonObjectListPlanetas.put("Filmes", planeta.getFilmes());
				jsonArrayPlanetas.put(jsonObjectListPlanetas);
			}
			
			return jsonArrayPlanetas;
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	
}
