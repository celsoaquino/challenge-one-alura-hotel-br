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
import model.Hospede;

public class HospedeDAO {

	private Connection connection;

	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}

	public void save(Hospede hospede) {

		try {
			String sql = "INSERT INTO hospede (nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				ps.setString(1, hospede.getNome());
				ps.setString(2, hospede.getSobrenome());
				ps.setDate(3, Date.valueOf(hospede.getDataNascimento()));
				ps.setString(4, hospede.getNacionalidade());
				ps.setString(5, hospede.getTelefone());
				ps.setInt(6, hospede.getIdReserva());

				ps.execute();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					while (rs.next()) {
						hospede.setHospedeId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public List<Hospede> list() {
		try {
			List<Hospede> hospedes = new ArrayList<>();
			String sql = "SELECT hospedeId, nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva FROM hospede";

			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.execute();

				try (ResultSet rs = ps.getResultSet()) {
					while (rs.next()) {
						Hospede hospede = new Hospede(rs.getInt(1), rs.getString(2), rs.getString(3),
								rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getInt(7));
						hospedes.add(hospede);
					}
				}
			}
			return hospedes;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public List<Hospede> findBySobrenome(String sobrenome) {
		List<Hospede> hospedes = new ArrayList<>();
		String sql = "SELECT hospedeId, nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva FROM hospede WHERE sobrenome LIKE ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, "%" + sobrenome + "%");
			ps.execute();

			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
					Hospede hospede = new Hospede(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getDate(4).toLocalDate(), rs.getString(5), rs.getString(6), rs.getInt(7));
					hospedes.add(hospede);
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		return hospedes;
	}

	public void deleteById(Integer hospedeId) {
		String sql = "DELETE FROM hospede WHERE hospedeId = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, hospedeId);
			ps.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public void update(Hospede hospede) {
		String sql = "UPDATE hospede h SET h.nome = ?, h.sobrenome = ?, h.dataNascimento = ?, h.nacionalidade = ?, h.telefone = ? WHERE hospedeId = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, hospede.getNome());
			ps.setString(2, hospede.getSobrenome());
			ps.setDate(3, Date.valueOf(hospede.getDataNascimento()));
			ps.setString(4, hospede.getNacionalidade());
			ps.setString(5, hospede.getTelefone());
			ps.setInt(6, hospede.getHospedeId());
			ps.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public Hospede findByIdReserva(Integer idReserva) {
		String sql = "SELECT hospedeId, nome, sobrenome, dataNascimento, nacionalidade, telefone FROM hospede WHERE idReserva = ?";
		Hospede hospede = null;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idReserva);
			ps.execute();

			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
					hospede = new Hospede(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate(),
							rs.getString(5), rs.getString(6));
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		return hospede;
	}
}
