package dados;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Locacao {
	private int numero;
	private Cliente cliente;
	private List<Robo> robos;
	private Status situacao;
	private LocalDate dataInicio;
	private LocalDate dataFim;

	public Locacao(int numero, Cliente cliente, LocalDate dataInicio, LocalDate dataFim) {
		this.numero = numero;
		this.cliente = cliente;
		this.robos = new ArrayList<>();
		this.situacao = Status.CADASTRADA;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public int getNumero() {
		return numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Robo> getRobos() {
		return robos;
	}

	public Status getSituacao() {
		return situacao;
	}

	public void setSituacao(Status situacao) {
		if (this.situacao != Status.FINALIZADA && this.situacao != Status.CANCELADA) {
			this.situacao = situacao;
		}
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void adicionarRobo(Robo robo) {
		this.robos.add(robo);
	}

	public double calculaValorFinal() {
		double valorTotal = 0;
		long dias = ChronoUnit.DAYS.between(dataInicio, dataFim);
		for (Robo robo : robos) {
			valorTotal += robo.calculaLocacao(dias);
		}
		double desconto = cliente.calculaDesconto(robos.size());
		return valorTotal - desconto;
	}

	@Override
	public String toString() {
		return "Locacao [numero=" + numero + ", cliente=" + cliente + ", robos=" + robos + ", situacao=" + situacao + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + "]";
	}

	public enum Status {
		CADASTRADA,
		EXECUTANDO,
		FINALIZADA,
		CANCELADA
	}
}