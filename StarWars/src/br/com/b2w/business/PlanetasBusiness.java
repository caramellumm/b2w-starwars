package br.com.b2w.business;

import java.util.function.Consumer;

import org.json.JSONObject;

import com.mongodb.client.FindIterable;

import br.com.b2w.exception.BusinessException;
import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.connection.DBConnection;
import br.com.b2w.mongo.document.Planetas;

public class PlanetasBusiness {
	
	public JSONObject listarPlanetas() throws BusinessException, IntegrationException{
		
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
			throw new BusinessException(e);
			
		} finally {
			conn.closeConnection();
		}
		
	}
	
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
			throw new BusinessException(e);
		} finally {
			conn.closeConnection();
		}
	}
}
