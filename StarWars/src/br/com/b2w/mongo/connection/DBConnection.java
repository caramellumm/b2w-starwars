package br.com.b2w.mongo.connection;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

import br.com.b2w.exception.IntegrationException;
import br.com.b2w.mongo.document.Planetas;

public class DBConnection {
	public static MongoClient mongoClient;
	
	public DBConnection() throws IntegrationException {
		try {
			
			CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			
			MongoClientOptions optsWithCodecs = MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build();
			mongoClient = new MongoClient(new ServerAddress("172.18.0.2", 27017), optsWithCodecs);
			

		} catch (MongoSocketOpenException e) {
			throw new IntegrationException("Erro ao abrir a conexao", e);
			
		} catch (Exception e) {
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
    
    public void closeConnection() throws IntegrationException	 {
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
    
    public FindIterable<Planetas> buscarPlanetasPorNome(String nome) throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		BasicDBObject query = new BasicDBObject("nome", nome);
    		return collection.find(query);
		} catch (Exception e) {
			throw new IntegrationException("Erro ao buscarPlanetas por Nome", e);
		}
    	
    }
    
    public FindIterable<Planetas> buscarPlanetasPorId(ObjectId id) throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		return collection.find(eq("_id", id));
		} catch (Exception e) {
			throw new IntegrationException("Erro ao buscarPlanetas por ID", e);
		}
    	
    }
    
    public DeleteResult removerPlanetaPorNome(String nome) throws IntegrationException {
    	try {
    		MongoCollection<Planetas> collection = this.getCollection();
    		BasicDBObject query = new BasicDBObject("nome", nome);
    		return collection.deleteMany(query);
		} catch (Exception e) {
			throw new IntegrationException("Erro ao excluir planeta por nome", e);
		}
    }
    
    public DeleteResult removerPlanetaPorID(String id) throws IntegrationException {
    	try {
    		
    		MongoCollection<Planetas> collection = this.getCollection();
    		return collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
		} catch (Exception e) {
			throw new IntegrationException("Erro ao excluir planeta por nome", e);
		}
    }

}
