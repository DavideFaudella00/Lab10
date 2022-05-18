package it.polito.tdp.rivers.model;

public class Event {
	double media;
	int numeroGiorni;

	public Event(double media, int numeroGiorni) {
		super();
		this.media = media;
		this.numeroGiorni = numeroGiorni;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public int getNumeroGiorni() {
		return numeroGiorni;
	}

	public void setNumeroGiorni(int numeroGiorni) {
		this.numeroGiorni = numeroGiorni;
	}

}
