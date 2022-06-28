package it.polito.tdp.borders.model;

public class Evento implements Comparable <Evento> {
	
	private int time;
	private Country nazione;
	private int persone;
	
	
	public Evento(int time, Country nazione, int persone) {
		super();
		this.time = time;
		this.nazione = nazione;
		this.persone = persone;
	}

	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}

	public Country getNazione() {
		return nazione;
	}

	public void setNazione(Country nazione) {
		this.nazione = nazione;
	}

	public int getPersone() {
		return persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.time-o.time;
	}
	
	@Override
	public String toString() {
		return "Event [time=" + time + ", nazione=" + nazione + ", persone=" + persone + "]";
	}
	
}
