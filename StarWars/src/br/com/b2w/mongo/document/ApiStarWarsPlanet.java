package br.com.b2w.mongo.document;

import java.io.Serializable;
import java.util.List;

public class ApiStarWarsPlanet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4339223136264166137L;
	private String name;
	private List<String> films;
		
	public ApiStarWarsPlanet() {

	}

	public ApiStarWarsPlanet(String name, List<String> films) {
		this.name = name;
		this.films = films;
	}

	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

}
