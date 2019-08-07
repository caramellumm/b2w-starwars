package br.com.b2w.rest;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse.Status;

import br.com.b2w.business.PlanetasBusiness;
	
@Path("/planetas")
public class PlanetasRest {
	
	private static final Logger LOGGER = LogManager.getLogManager().getLogger(PlanetasRest.class.getName());
	
	@PUT
	@Path("{nome}/{clima}/{terreno}")
	@Produces("application/json")
	public Response inserirPlaneta(@PathParam("nome") String nome, @PathParam("clima") String clima, @PathParam("terreno") String terreno) throws Exception{
		
		try {
			PlanetasBusiness pb = new PlanetasBusiness();
			JSONObject jsonObject = pb.inserirPlaneta(nome, clima, terreno);
			return Response.status(Status.CREATED).entity(jsonObject.toString()).build();
			
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao inserir um planeta : " + e.getMessage()).build();
		}
	}
	
	@GET
	@Produces("application/json")
	public Response listarPlanetas() {
		try {
			PlanetasBusiness pb = new PlanetasBusiness();
			JSONArray jsonObjectListaPlanetas = pb.listarPlanetas();
			
			return Response.status(Status.OK).entity(jsonObjectListaPlanetas.toString()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar os planetas: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("{nome}")
	@Produces("application/json")
	public Response listarPlanetas(@PathParam("nome") String nome) {
		try {
			
			PlanetasBusiness pb = new PlanetasBusiness();
			JSONArray jsonObjectListaPlanetas = pb.listarPlanetas(nome);
			
			return Response.status(Status.OK).entity(jsonObjectListaPlanetas.toString()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar os planetas por nome: " + e).build();
		}
	}
	
	
	@POST
	@Path("{id}")
	@Produces("application/json")
	public Response listarPlanetas(@PathParam("id") ObjectId id) {
		try {

			PlanetasBusiness pb = new PlanetasBusiness();
			JSONObject jsonObjectListaPlanetas = pb.listarPlanetas(id);

			return Response.status(Status.OK).entity(jsonObjectListaPlanetas.toString()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar os planetas por id: " + e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces("application/json")
	public Response removerPlaneta(@PathParam("id") String id) {
		try {

			PlanetasBusiness pb = new PlanetasBusiness();
			JSONObject jsonObjectListaPlanetas = pb.removerPlaneta(id);
			
			return Response.status(Status.OK).entity(jsonObjectListaPlanetas.toString()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover planta: " + e.getMessage()).build();
		}
	}
	

}
