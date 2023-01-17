package controller;

import java.sql.Connection;
import java.util.List;

import dao.ReservaDAO;
import db.ConnectionFactory;
import model.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDAO;

	public ReservaController() {
		Connection conn = new ConnectionFactory().getConnection();
		this.reservaDAO = new ReservaDAO(conn);
	}
	
	public Reserva save(Reserva reserva) {
		return this.reservaDAO.save(reserva);
	}
	
	public List<Reserva> list() {
		return this.reservaDAO.list();
	}
	
	public List<Reserva> findById(String idReserva) {
		return this.reservaDAO.findById(idReserva);
	}
	
	public void deleteById(Integer reservaId) {
		 this.reservaDAO.deleteById(reservaId);
	}
	
	public void update(Reserva reserva) {
		this.reservaDAO.update(reserva);
	}
}
