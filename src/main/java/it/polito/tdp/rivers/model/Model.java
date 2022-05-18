package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	RiversDAO dao;
	private PriorityQueue<Flow> queue;

	public Model() {
		dao = new RiversDAO();
	}

	public void popolaRiver(River r) {
		this.getAllFlows(r);
	}

	public List<River> getAllRivers() {
		return dao.getAllRivers();
	}

	public List<Flow> getAllFlows(River r) {
		return dao.getFlows(r);
	}

	public int numeroMisurazioni(River river) {
		return river.getFlows().size();
	}

	public double getMedia(River river) {
		return river.getFlowAvg();
	}

	public LocalDate dataInizio(River river) {
		return river.getFlows().get(0).getDay();
	}

	public LocalDate dataFine(River river) {
		return river.getFlows().get(river.getFlows().size() - 1).getDay();
	}

	public Event run(River r, double k) {
		queue = new PriorityQueue<Flow>();
		queue.addAll(r.getFlows());

		List<Double> capacita = new ArrayList<Double>();

		double Q = k * 30 * convertiInGiorni(r.getFlowAvg());
		double C = Q / 2;
		double fOutMin = convertiInGiorni(r.getFlowAvg() * 0.8);
		int nGiorniFalliti = 0;

		Flow flow;
		while ((flow = queue.poll()) != null) {
			double fOut = fOutMin;

			if (Math.random() > 0.95) {
				fOut = 10 * fOutMin;
			}

			C += convertiInGiorni(flow.getFlow());

			if (C > Q) {
				C = Q;
			}

			if (C < fOut) {
				nGiorniFalliti++;
				C = 0;
			} else {
				C -= fOut;
			}
			capacita.add(C);
		}
		double capacitaAvg = 0;
		for (Double d : capacita) {
			capacitaAvg += d;
		}
		capacitaAvg = capacitaAvg / capacita.size();
		return new Event(capacitaAvg, nGiorniFalliti);
	}

	private double convertiInGiorni(double flowAvg) {
		return flowAvg * 60 * 60 * 24;
	}
}
