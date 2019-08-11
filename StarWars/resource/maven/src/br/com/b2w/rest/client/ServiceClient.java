package br.com.b2w.rest.client;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import br.com.b2w.exception.ServiceException;
import br.com.b2w.mongo.document.ApiStarWarsPlanet;
import br.com.b2w.mongo.document.ApiSwarWarsResult;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class ServiceClient {

	private static final String URL_SERVICE = "https://swapi.co/api/planets/";
	private static final String OBJETO_CACHEADO = "OBJETO_CACHEADO";
	private static final String OBJETO_SWAPI = "swapi";

	public HashMap<String, List<String>> consultarPlanetas() throws ServiceException {

		try {

			boolean cacheExists = CacheManager.getInstance().cacheExists(OBJETO_CACHEADO);

			if (!cacheExists) {
								
				ApiSwarWarsResult apiSwarWarsResult = getPlanets();
				HashMap<String, List<String>> mapPlanetas = listaParaMap(apiSwarWarsResult.getResults());
				Cache cache = new Cache(new CacheConfiguration(OBJETO_CACHEADO, 5000).memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
						.eternal(false).timeToLiveSeconds(60).timeToIdleSeconds(90).persistence(new PersistenceConfiguration().strategy(Strategy.NONE)));
				
				CacheManager.getInstance().addCache(cache);
				CacheManager.getInstance().getCache(OBJETO_CACHEADO).put(new Element(OBJETO_SWAPI, mapPlanetas));
				
				return mapPlanetas;
				
			} else {
				Element element = CacheManager.getInstance().getCache(OBJETO_CACHEADO).get(OBJETO_SWAPI);
				
				if(null == element) {
					ApiSwarWarsResult apiSwarWarsResult = getPlanets();
					HashMap<String, List<String>> mapPlanetas = listaParaMap(apiSwarWarsResult.getResults());
					CacheManager.getInstance().getCache(OBJETO_CACHEADO).put(new Element(OBJETO_SWAPI, mapPlanetas));
					element = CacheManager.getInstance().getCache(OBJETO_CACHEADO).get(OBJETO_SWAPI);
				}
				
				HashMap<String, List<String>> mapPlanetas = (HashMap<String, List<String>>) element.getObjectValue();
				return mapPlanetas;
				
			}

		} catch (Exception e) {
			throw new ServiceException("Erro ao conectar com a API swapi.co", e);
		}
	}
	
	private ApiSwarWarsResult getPlanets() throws ServiceException {
		
		try {
			
			Client client = Client.create();
			WebResource webResource = client.resource(URL_SERVICE);
			Builder accept = webResource.header("Content-Type", "application/x-www-form-urlencoded")
					.header("User-Agent", "").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
			ClientResponse response = accept.get(ClientResponse.class);
			
			if (response.getStatus() != Status.OK.getStatusCode()) {
				throw new RuntimeException(
						"Erro ao recuperar dados da API swapi.co: Erro HTTP: " + response.getStatus());
			}
			
			ApiSwarWarsResult apiSwarWarsResult = response.getEntity(ApiSwarWarsResult.class);
			
			return apiSwarWarsResult;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	
	private HashMap<String, List<String>> listaParaMap(List<ApiStarWarsPlanet> list){
		
		HashMap<String, List<String>> mapFilmes = new HashMap<String, List<String>>();
		for (ApiStarWarsPlanet apiStarWarsPlanet : list) {
			mapFilmes.put(apiStarWarsPlanet.getName(), apiStarWarsPlanet.getFilms());
		}
		
		return mapFilmes;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
