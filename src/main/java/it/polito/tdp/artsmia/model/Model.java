package it.polito.tdp.artsmia.model;


import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private Graph<ArtObject, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	public Model() {
		dao = new ArtsmiaDAO();
		idMap= new HashMap<Integer, ArtObject>();
	}
	
	public void creaGrafo() {
		grafo =new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// Aggiungere vertici 
		// 1. recupero tutti gli art object dall DB
		// 2. li inserisco come vertici
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		// Aggiungere gli archi 
		// Approccio 1  (va bene per pochi vertici!!)
		// -> doppio ciclo for sui vertici dati
		// -> dati due vertici , controllo se sono collegati
		/*for(ArtObject a1 : this.grafo.vertexSet()) {
			for(ArtObject a2: this.grafo.vertexSet()) {
				if(!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
					// devo collegare a1 ad a2?
					int peso = dao.getPeso(a1,a2);
					if(peso>0) {
						Graphs.addEdge(this.grafo, a1, a2,peso);
					}
				}
			}
		}*/
		
		// Approccio 3 
		
		for(Adiacenza a: dao.getAdiacenze()) {
				Graphs.addEdge(this.grafo, idMap.get(a.getId1()),
						idMap.get(a.getId2()), a.getPeso());
 		}
		System.out.println("Grafo Creato!");
		System.out.println("# Vertici : "+ grafo.vertexSet().size());
		System.out.println("# Archi : "+ grafo.edgeSet().size());
		
	}
}
