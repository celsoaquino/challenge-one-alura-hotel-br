package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.Reserva;

public class ReservaDAO {

	private Connection connection;

	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}

	public Reserva save(Reserva reserva) {
		try {
			String sql = "INSERT INTO reserva (dataEntrada, dataSaida, valor, formaPagamento) VALUES (?, ?, ?, ?)";

			try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				ps.setDate(1, Date.valueOf(reserva.getDataEntrada()));
				ps.setDate(2, Date.valueOf(reserva.getDataSaida()));
				ps.setBigDecimal(3, reserva.getValor());
				ps.setString(4, reserva.getFormaPagamento());

				ps.execute();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					while (rs.next()) {
						reserva.setReservaId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		return reserva;
	}

	public List<Reserva> list() {
		try {
			List<Reserva> reservas = new ArrayList<>();
			String sql = "SELECT reservaId, dataEntrada, dataSaida, valor, formaPagamento FROM reserva";

			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.execute();

				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						Reserva reserva = new Reserva(rs.getInt(1), rs.getDate(2).toLocalDate(),
								rs.getDate(3).toLocalDate(), rs.getBigDecimal(4), rs.getString(5));
						reservas.add(reserva);
					}
				}
			}
			return reservas;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public List<Reserva> findById(String idReserva) {
		List<Reserva> reservas = new ArrayList<>();
		String sql = "SELECT reservaId, dataEntrada, dataSaida, valor, formaPagamento FROM reserva WHERE reservaId LIKE ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%" + idReserva + "%");
			ps.execute();

			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
				Reserva	reserva = new Reserva(rs.getInt(1), rs.getDate(2).toLocalDate(), rs.getDate(3).toLocalDate(),
							rs.getBigDecimal(4), rs.getString(5));
				reservas.add(reserva);
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		return reservas;
	}

	public void deleteById(Integer reservaId) {
		String sql = "DELETE FROM reserva WHERE reservaId = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, reservaId);
			ps.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public void update(Reserva reserva) {
		String sql = "UPDATE reserva r SET r.dataEntrada = ?, r.dataSaida = ?, r.valor = ?, r.formaPagamento = ? WHERE reservaId = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(reserva.getDataEntrada()));
			ps.setDate(2, Date.valueOf(reserva.getDataSaida()));
			ps.setBigDecimal(3, reserva.getValor());
			ps.setString(4, reserva.getFormaPagamento());
			ps.setInt(5, reserva.getReservaId());
			ps.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}
