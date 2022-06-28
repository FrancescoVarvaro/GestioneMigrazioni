package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulazione {
	
	//Coda degli eventi
	private PriorityQueue<Evento> coda;
	
	//parametri di simulazione
	private int nInizialeMigranti;
	private Country nazioneIniziale;
	
	//output simulazione
	private int numeroPassi; // T
	Map<Country, Integer> persone; //mappa che associa ad ogni stato, il n. di migranti stanziali
								   // oppure List<CountryAndNumber> personeStanziali;
	
	//stato del mondo simulato
	private Graph <Country, DefaultEdge> grafo; 
	
	
	public Simulazione(Graph<Country, DefaultEdge> grafo) {
		super();
		this.grafo = grafo;
	}

	public void init(Country partenza, int migranti) {
		this.nazioneIniziale = partenza;
		this.nInizialeMigranti = migranti;
		
		this.persone = new HashMap<Country, Integer>();
		for(Country c : grafo.vertexSet()) {
			this.persone.put(c, 0);
		}
		this.coda = new PriorityQueue<>();
		this.coda.add(new Evento(1, this.nazioneIniziale, this.nInizialeMigranti));  // LoadQueue
		
	}
	
	public void run() {
		while(!this.coda.isEmpty()) { //in questo caso la terminazione è esaurimento della coda
			Evento e = this.coda.poll();
			processEvent(e);
		}
	}

	private void processEvent(Evento e) {
		// TODO Auto-generated method stub
		int stanziali = e.getPersone()/2;
		int migranti = e.getPersone() - stanziali;
		int nStatiConfinanti = this.grafo.degreeOf(e.getNazione()); // degreeOf mi da gli stati confinanti
		int gruppiMigranti = migranti/nStatiConfinanti;
		
		// il resto della divisione devono essere aggiunti a stanziali
		stanziali += migranti % nStatiConfinanti;
		
		//aggiorno lo stato del mondo, prendo le persone che c'erano prima nella nazione e aggiungo quelli nuovi stanziali
		this.persone.put(e.getNazione(), this.persone.get(e.getNazione())+stanziali); // LA MAPPA NON HA DUPLICATI
		numeroPassi = e.getTime();
		
		// predispongo lo stato futuro.
		// in questo caso lo switch su eventType perchè abbiamo solo un tipo di evento
		if(gruppiMigranti != 0) {
			for(Country vicino : Graphs.neighborListOf(this.grafo, e.getNazione())) {  // dato un vertice mi estrae una lista di vertici adiacenti
				this.coda.add(new Evento (e.getTime()+1, vicino, gruppiMigranti));
			}
		}
	}

	public int getNumeroPassi() {
		return numeroPassi;
	}

	public Map<Country, Integer> getPersone() {
		return persone;
	}
	
	
	
	
	
	
}
