package controller;

import java.sql.Connection;
import java.util.List;

import dao.HospedeDAO;
import db.ConnectionFactory;
import model.Hospede;

public class HospedeController {
	
	private HospedeDAO hospedeDAO;

	public HospedeController() {
		Connection conn = new ConnectionFactory().getConnection();
		this.hospedeDAO = new HospedeDAO(conn);
	}
	
	public void save(Hospede hospede) {
		this.hospedeDAO.save(hospede);
	}
	
	public List<Hospede> list() {
		return hospedeDAO.list();
	}
	
	public List<Hospede> findBySobrenome(String sobrenome) {
		return this.hospedeDAO.findBySobrenome(sobrenome);
	}
	
	public void deleteById(Integer hospedeId) {
		 this.hospedeDAO.deleteById(hospedeId);
	}
	
	public void update(Hospede hospede) {
		this.hospedeDAO.update(hospede);
	}
	
	public Hospede findByIdReserva(Integer idReserva) {
		return this.hospedeDAO.findByIdReserva(idReserva);
	}
}
