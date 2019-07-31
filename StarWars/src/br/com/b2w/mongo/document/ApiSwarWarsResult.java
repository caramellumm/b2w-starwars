package br.com.b2w.mongo.document;

import java.io.Serializable;
import java.util.List;

public class ApiSwarWarsResult implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2400431330316063189L;
	
	private float count;
	 private String next;
	 private String previous;	
	 private List<PlanetApiStarWars> results;
	 
	public float getCount() {
		return count;
	}
	public void setCount(float count) {
		this.count = count;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	public List<PlanetApiStarWars> getResults() {
		return results;
	}
	public void setResults(List<PlanetApiStarWars> results) {
		this.results = results;
	}

}
