package br.com.b2w.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/planetas")
public class PlanetasRest {
	
	@PUT
	@Path("{nome}/{clima}/{terreno}")
	@Produces("application/json")
	public Response inserirPlaneta(@PathParam("nome") String nome, @PathParam("clima") String clima, @PathParam("terreno") String terreno) {
		
		try {
			
			JSONObject jsonObject = new JSONObject();
			//Logica para buscar planetas
			jsonObject.put("Id", "");
			jsonObject.put("Nome", nome);
			jsonObject.put("Clima", clima);
			jsonObject.put("Terreno", terreno);
			
			return Response.status(200).entity(jsonObject.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity("Erro ao .... : " + e.getMessage()).build();
		}
	}
	
	@POST
	@Produces("application/json")
	public Response listarPlanetas() {
		try {
			
			JSONObject jsonObject = new JSONObject();
			//Logica para buscar planetas
			jsonObject.put("Id", "");
			jsonObject.put("Nome", "");
			jsonObject.put("Clima", "");
			jsonObject.put("Terreno", "");
			
			return Response.status(200).entity(jsonObject.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity("Erro ao .... : " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("{nome}")
	@Produces("application/json")
	public Response listarPlanetas(@PathParam("nome") String nome) {
		try {
			
			JSONObject jsonObject = new JSONObject();
			//Logica para buscar planetas
			jsonObject.put("Id", "");
			jsonObject.put("Nome", "");
			jsonObject.put("Clima", "");
			jsonObject.put("Terreno", "");
			
			return Response.status(200).entity(jsonObject.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity("Erro ao .... : " + e.getMessage()).build();
		}
	}
	
	
	@POST
	@Path("{id}")
	@Produces("application/json")
	public Response listarPlanetas(@PathParam("id") Integer id) {
		try {
			
			JSONObject jsonObject = new JSONObject();
			//Logica para buscar planetas
			jsonObject.put("Id", "");
			jsonObject.put("Nome", "");
			jsonObject.put("Clima", "");	
			jsonObject.put("Terreno", "");
			
			return Response.status(200).entity(jsonObject.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity("Erro ao .... : " + e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public Response removerPlaneta(@PathParam("id") Integer Id) {
		try {
			
			JSONObject jsonObject = new JSONObject();
			//Logica para buscar planetas
			jsonObject.put("Id", "");
			
			return Response.status(200).entity(jsonObject.toString()).build();
		} catch (Exception e) {
			return Response.status(500).entity("Erro ao .... : " + e.getMessage()).build();
		}
	}
	

}
