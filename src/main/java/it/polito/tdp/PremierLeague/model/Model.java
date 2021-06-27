package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private Graph<Match,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Match> idMap;
	
	public Model() {
		this.idMap=new HashMap<>();
		this.dao=new PremierLeagueDAO();
		for(Match m : dao.listAllMatches())
			idMap.put(m.getMatchID(), m);
	}
	
	public void creaGrafo(int month,int time) {
		
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getMatchesFromMonth(month));
		System.out.println(grafo.vertexSet().size());
		for(Adiacenza a : dao.getAdiacenze(month, time))
			Graphs.addEdgeWithVertices(grafo,idMap.get(a.getM1()), idMap.get(a.getM2()),a.getPeso());
		System.out.println(grafo.edgeSet().size());
			
		
	}
	public List<Adiacenza> getAdiacenze(int month,int time){
		List<Adiacenza> result =  dao.getAdiacenze(month,time);
		Collections.sort(result);
		return result;
	}
	public Match getMatch(int id) {
		return this.idMap.get(id);
	}
}
