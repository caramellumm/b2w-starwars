package br.com.b2w.rest.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import br.com.b2w.exception.ServiceException;
import br.com.b2w.mongo.document.ApiSwarWarsResult;
import br.com.b2w.mongo.document.ApiStarWarsPlanet;

public class ServiceClient {

	private final String URL_SERVICE = "https://swapi.co/api/planets/";	

	public List<ApiStarWarsPlanet> consultarPlanetas() throws ServiceException {

		try {

			Client client = Client.create();
			WebResource webResource = client.resource(URL_SERVICE);
			Builder accept = webResource.header("Content-Type", "application/x-www-form-urlencoded")
					.header("User-Agent", "").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
			ClientResponse response = accept.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			ApiSwarWarsResult apiSwarWarsResult = response.getEntity(ApiSwarWarsResult.class);
			return apiSwarWarsResult.getResults();

		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
