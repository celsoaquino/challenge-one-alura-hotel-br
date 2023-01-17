package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reserva {

	private Integer reservaId;
	private LocalDate dataEntrada;
	private LocalDate dataSaida;
	private BigDecimal valor;
	private String formaPagamento;
	public static final BigDecimal DIARIA  = new BigDecimal("174.95"); 

	public Reserva(Integer reservaId, LocalDate dataEntrada, LocalDate dataSaida, BigDecimal valor,
			String formaPagamento) {
		super();
		this.reservaId = reservaId;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.formaPagamento = formaPagamento;
	}

	public Reserva(LocalDate dataEntrada, LocalDate dataSaida, BigDecimal valor, String formaPagamento) {
		super();
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.formaPagamento = formaPagamento;
	}

	public Integer getReservaId() {
		return reservaId;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setReservaId(Integer reservaId) {
		this.reservaId = reservaId;
	}
}
