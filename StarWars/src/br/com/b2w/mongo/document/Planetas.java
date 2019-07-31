package br.com.b2w.mongo.document;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class Planetas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4261227010751759929L;
	public ObjectId id;
	public String nome;
	public String clima;
	public String terreno;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getClima() {
		return clima;
	}
	public void setClima(String clima) {
		this.clima = clima;
	}
	public String getTerreno() {
		return terreno;
	}
	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}
	
	
}
