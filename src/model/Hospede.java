package model;

import java.time.LocalDate;

public class Hospede {

	private Integer hospedeId;
	private String nome;
	private String sobrenome;
	private LocalDate dataNascimento;
	private String nacionalidade;
	private String telefone;
	private Integer idReserva;

	public Hospede(String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade, String telefone) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
	}

	public Hospede(String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade, String telefone,
			Integer idReserva) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
		this.idReserva = idReserva;
	}

	public Hospede(Integer hospedeId, String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade,
			String telefone, Integer idReserva) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
		this.idReserva = idReserva;
	}

	public Hospede(Integer hospedeId, String nome, String sobrenome, LocalDate dataNascimento, String nacionalidade,
			String telefone) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
		this.telefone = telefone;
	}

	public Integer getHospedeId() {
		return hospedeId;
	}

	public String getNome() {
		return nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setHospedeId(Integer hospedeId) {
		this.hospedeId = hospedeId;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}
}
