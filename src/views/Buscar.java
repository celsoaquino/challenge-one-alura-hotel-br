package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.HospedeController;
import controller.ReservaController;
import model.Hospede;
import model.Reserva;
import util.ConverterDate;

@SuppressWarnings("serial")
public class Buscar extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHospedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHospedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservaController reservaController;
	private HospedeController hospedeController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Buscar() {
		reservaController = new ReservaController();
		hospedeController = new HospedeController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Buscar.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblTitulo = new JLabel("SISTEMA DE BUSCA");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblTitulo.setBounds(331, 62, 280, 42);
		contentPane.add(lblTitulo);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 14));
		tbReservas.setRowHeight(24);
		panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/imagenes/reservado.png")),
				new JScrollPane(tbReservas), null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Data Check In");
		modelo.addColumn("Data Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");

		fillTableReservas();

		tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 14));
		tbHospedes.setRowHeight(24);
		panel.addTab("Hóspedes", new ImageIcon(Buscar.class.getResource("/imagenes/pessoas.png")),
				new JScrollPane(tbHospedes), null);
		modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("N. Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("N. Reserva");
		fillTableHospedes();

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Buscar.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Quando o usuário passa o mouse sobre o botão, ele muda de cor
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Quando o usuário remove o mouse do botão, ele retornará ao estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 0) {
					findReservaById(txtBuscar.getText());
				} else if (panel.getSelectedIndex() == 1) {
					findHospedeBySobrenome(txtBuscar.getText());
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 0 && tbReservas.getSelectedRow() != -1) {
					updateReserva();
				} else if (panel.getSelectedIndex() == 1 && tbHospedes.getSelectedRow() != -1) {
					updateHospede();
				}
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnDeletar = new JPanel();
		btnDeletar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panel.getSelectedIndex() == 0 && tbReservas.getSelectedRow() != -1) {
					deleteReservaById();
				} else if (panel.getSelectedIndex() == 1 && tbHospedes.getSelectedRow() != -1) {
					deleteHospedeById();
				}
			}
		});
		btnDeletar.setLayout(null);
		btnDeletar.setBackground(new Color(12, 138, 199));
		btnDeletar.setBounds(767, 508, 122, 35);
		btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnDeletar);

		JLabel lblExcluir = new JLabel("DELETAR");
		lblExcluir.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluir.setForeground(Color.WHITE);
		lblExcluir.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblExcluir.setBounds(0, 0, 122, 35);
		btnDeletar.add(lblExcluir);
		setResizable(false);
	}

	// Código que permite movimentar a janela pela tela seguindo a posição de "x" e
	// "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	private void fillTableReservas() {
		List<Reserva> reservas = this.reservaController.list();
		reservas.forEach(r -> modelo.addRow(new Object[] { r.getReservaId(),
				r.getDataEntrada().format(DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE)),
				r.getDataSaida().format(DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE)),
				NumberFormat.getCurrencyInstance().format(r.getValor()), r.getFormaPagamento() }));

		renderTableCellCenter(tbReservas, 0, 1, 2, 3, 4);
	}

	private void fillTableHospedes() {
		List<Hospede> hospedes = this.hospedeController.list();
		hospedes.forEach(h -> modeloHospedes.addRow(new Object[] { h.getHospedeId(), h.getNome(), h.getSobrenome(),
				h.getDataNascimento().format(DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE)),
				h.getNacionalidade(), h.getTelefone(), h.getIdReserva() }));
		renderTableCellCenter(tbHospedes, 0, 3, 4, 5, 6);
	}

	private void findReservaById(String idReserva) {
		List<Reserva> reservas = this.reservaController.findById(idReserva);
		modelo.setNumRows(0);
		if (reservas != null) {
			reservas.forEach(r -> modelo.addRow(new Object[] { r.getReservaId(),
					r.getDataEntrada().format(DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE)),
					r.getDataSaida().format(DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE)),
					NumberFormat.getCurrencyInstance().format(r.getValor()), r.getFormaPagamento() }));
		} else {
			JOptionPane.showMessageDialog(this, "Reserva não existe.");
			fillTableReservas();
			txtBuscar.setText("");
		}
	}

	private void findHospedeBySobrenome(String sobrenome) {
		List<Hospede> hospedes = this.hospedeController.findBySobrenome(sobrenome);
		modeloHospedes.setNumRows(0);
		if (!hospedes.isEmpty()) {
			hospedes.forEach(h -> modeloHospedes.addRow(new Object[] { h.getHospedeId(), h.getNome(), h.getSobrenome(),
					h.getDataNascimento().format(DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE)),
					h.getNacionalidade(), h.getTelefone(), h.getIdReserva() }));
		} else {
			JOptionPane.showMessageDialog(this, "Hospede não existe.");
			fillTableHospedes();
			txtBuscar.setText("");
		}
	}

	private void deleteHospedeById() {
		Integer reservaId = (Integer) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 6);
		JOptionPane.showMessageDialog(null, "Você deve excluir a reserva numero: " + reservaId);

		/*
		 * Object obj = modeloHospedes.getValueAt(tbHospedes.getSelectedRow(),
		 * tbHospedes.getSelectedColumn()); if (obj instanceof Integer) { Integer
		 * hospedeId = (Integer) obj; this.hospedeController.deleteById(hospedeId);
		 * modeloHospedes.removeRow(tbHospedes.getSelectedRow());
		 * JOptionPane.showMessageDialog(this, "Hospede excluído com sucesso!"); } else
		 * { JOptionPane.showMessageDialog(this, "Por favor, selecionar o ID"); }
		 */
	}

	private void updateHospede() {
		Object obj = (Object) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), tbHospedes.getSelectedColumn());
		if (obj instanceof Integer) {
			Integer hospedeId = (Integer) obj;
			String nome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 1);
			String sobrenome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 2);
			LocalDate dataNascimento = LocalDate.parse(
					(String) (modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 3)),
					DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE));
			String nacionalidade = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 4);
			String telefone = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 5);

			Hospede hospede = new Hospede(hospedeId, nome, sobrenome, dataNascimento, nacionalidade, telefone);
			this.hospedeController.update(hospede);

			JOptionPane.showMessageDialog(this, "Hospede editado com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar numero do hospede.");
		}
	}

	private void deleteReservaById() {
		Object obj = modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn());
		if (obj instanceof Integer) {
			if (isConfirmDelete()) {
				int row = tbReservas.getSelectedRow();
				Integer hospedeId = (Integer) obj;
				this.reservaController.deleteById(hospedeId);
				modelo.removeRow(row);
				modeloHospedes.removeRow(row);
				JOptionPane.showMessageDialog(this, "Dados excluído com sucesso!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o numero da reserva");
		}
	}

	private void updateReserva() {
		Object obj = (Object) modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn());
		if (obj instanceof Integer) {
			Integer reservaId = (Integer) obj;
			LocalDate dataEntrada = LocalDate.parse((String) (modelo.getValueAt(tbReservas.getSelectedRow(), 1)),
					DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE));
			LocalDate dataSaida = LocalDate.parse((String) (modelo.getValueAt(tbReservas.getSelectedRow(), 2)),
					DateTimeFormatter.ofPattern(ConverterDate.PATTERN_DATE));
			BigDecimal valor = parseBigDecimal((String) modelo.getValueAt(tbReservas.getSelectedRow(), 3));
			String formaPagamento = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
			Reserva reserva = new Reserva(reservaId, dataEntrada, dataSaida, valor, formaPagamento);
			this.reservaController.update(reserva);
			modelo.setRowCount(0);
			fillTableReservas();
			JOptionPane.showMessageDialog(this, "Reserva alterada com sucesso!");
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, selecionar o numero da reserva.");
		}
	}

	private BigDecimal parseBigDecimal(String valor) {
		valor = valor.contains("R") ? valor.substring(3) : valor;
		valor = valor.replace(".", "").replace(",", ".").trim();
		return new BigDecimal(valor);
	}

	private void renderTableCellCenter(JTable table, int... indexColumn) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < indexColumn.length; i++) {
			table.getColumnModel().getColumn(indexColumn[i]).setCellRenderer(centerRenderer);
		}
	}

	public boolean isConfirmDelete() {
		Integer reservaId = (Integer) modelo.getValueAt(tbReservas.getSelectedRow(), 0);
		Hospede hospede = this.hospedeController.findByIdReserva(reservaId);
		ImageIcon deleteHospedeIcon = new ImageIcon(Buscar.class.getResource("/imagenes/delete-hospede64px.png"));
		JLabel icon = new JLabel(deleteHospedeIcon);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(icon, BorderLayout.WEST);

		JPanel panelHospede = new JPanel();
		panelHospede.setLayout(new GridLayout(0, 1, 0, 8));
		panelHospede.setBorder(new EmptyBorder(5, 12, 5, 12));

		JLabel label1 = new JLabel("Este hóspede também será excluído.");
		label1.setVerticalAlignment(SwingConstants.TOP);
		label1.setFont(new Font("Arial", Font.BOLD, 16));
		panelHospede.add(label1);

		JLabel id = new JLabel("ID: " + hospede.getHospedeId());
		id.setFont(new Font("Arial", Font.BOLD, 14));
		id.setVerticalAlignment(SwingConstants.BOTTOM);
		panelHospede.add(id);

		JLabel nomeSobrenome = new JLabel("Nome: " + hospede.getNome() + " " + hospede.getSobrenome());
		nomeSobrenome.setFont(new Font("Arial", Font.BOLD, 14));
		nomeSobrenome.setVerticalAlignment(SwingConstants.TOP);
		panelHospede.add(nomeSobrenome);

		JLabel label2 = new JLabel("Você tem certeza que quer continuar?");
		label2.setVerticalAlignment(SwingConstants.BOTTOM);
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("Arial", Font.BOLD, 16));
		panelHospede.add(label2);
		panel.add(panelHospede);

		int input = JOptionPane.showConfirmDialog(null, panel, "Excluir reserva?", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (input == 0)
			return true;
		return false;
	}
}
