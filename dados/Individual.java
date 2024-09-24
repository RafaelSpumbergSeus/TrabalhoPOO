package dados;

public class Individual extends Cliente {
	private String cpf;

	public Individual(int codigo, String nome, String cpf) {
		super(codigo, nome);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	@Override
	public double calculaDesconto(int quantidadeRobos) {
		if (quantidadeRobos > 1) {
			return 0.05;
		}
		return 0.0;
	}

	@Override
	public String toString() {
		return super.toString() + ", Individual [cpf=" + cpf + "]";
	}
}