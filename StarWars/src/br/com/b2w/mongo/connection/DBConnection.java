package br.com.b2w.mongo.connection;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.document.Planetas;

public class DBConnection {
	public static MongoClient mongoClient;
	
	public DBConnection() throws IntegrationException {
		try {

			CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
			mongoClient = MongoClients.create(settings);

		} catch (MongoSocketOpenException e) {
			throw new IntegrationException("Erro ao abrir a conexao", e);
		}
	}
    
    public MongoCollection<Planetas> getCollection() throws IntegrationException {
    	try {
			
    		MongoDatabase database = mongoClient.getDatabase("starwars");
    		MongoCollection<Planetas> collection = database.getCollection("planetas", Planetas.class);
    		return collection;
		} catch (Exception e) {
			throw new IntegrationException("Erro ao recuperar o objeto collection", e);
		}
    }
    
    public void closeConnection() throws IntegrationException {
    	try {
    		mongoClient.close();
		} catch (Exception e) {
			throw new IntegrationException("Erro ao fechar a conexao", e);
		}
    }
    
    public FindIterable<Planetas> buscarPlanetas() throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		FindIterable<Planetas> find = collection.find();
			return find;
		} catch (Exception e) {
			throw new IntegrationException("Erro ao buscarPlanetas", e);
		}
    	
    }
    
    public void inserirPlaneta(Planetas planeta) throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		collection.insertOne(planeta);
		} catch (Exception e) {
			throw new IntegrationException("Erro ao inserir um planeta", e);
		}
    }
    
    public FindIterable<Planetas> buscarPlanetasPorNome(Planetas planeta) throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		BasicDBObject query = new BasicDBObject("nome", new BasicDBObject("$gt", planeta.getNome()));
    		return collection.find(query);
		} catch (Exception e) {
			throw new IntegrationException("Erro ao buscarPlanetas por Nome", e);
		}
    	
    }
    
    public FindIterable<Planetas> buscarPlanetasPorId(Planetas planeta) throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		BasicDBObject query = new BasicDBObject("_id", new BasicDBObject("$gt", planeta.getId()));
    		return collection.find(query);
		} catch (Exception e) {
			throw new IntegrationException("Erro ao buscarPlanetas por ID", e);
		}
    	
    }
    
    

}
