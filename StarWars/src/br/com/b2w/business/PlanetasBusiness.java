package br.com.b2w.business;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;

import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;
import br.com.b2w.exception.ServiceException;
import br.com.b2w.mongo.connection.DBConnection;
import br.com.b2w.mongo.document.ApiStarWarsPlanet;
import br.com.b2w.mongo.document.Planetas;
import br.com.b2w.rest.client.ServiceClient;

public class PlanetasBusiness {

	
	public JSONObject inserirPlaneta(String nome, String clima, String terreno) throws IntegrationException, BusinessException {
		
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
			throw new BusinessException(e.getMessage(), e);
		} finally {
			conn.closeConnection();
		}
	}
	
	public JSONObject listarPlanetas() throws IntegrationException, BusinessException {
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

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);

		} finally {
			conn.closeConnection();
		}

	}
	
	public JSONObject listarPlanetas(String nome) throws BusinessException, IntegrationException{
		
		DBConnection conn = null;
		try {
			conn = new DBConnection();
			FindIterable<Planetas> buscarPlanetas = conn.buscarPlanetasPorNome(nome);
			
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
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
			
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

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);

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
						planeta.getFilmes().addAll(planetApiStarWars.getFilms());
					}
				}
			}
			
			return listPlanetas;
			
		} catch (ServiceException e) {
			throw new BusinessException(e.getMessage(), e);
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public JSONObject converterParaJson(List<Planetas> planetas) throws BusinessException {
		try {
			
			JSONObject jsonObjectListPlanetas = new JSONObject();
			
			for (Planetas planeta : planetas) {
				jsonObjectListPlanetas.put("Id", planeta.getId());
				jsonObjectListPlanetas.put("Nome", planeta.getNome());
				jsonObjectListPlanetas.put("Clima", planeta.getClima());
				jsonObjectListPlanetas.put("Terreno", planeta.getTerreno());
			}
			
			return jsonObjectListPlanetas;
			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	
}
