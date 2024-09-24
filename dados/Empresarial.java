package dados;

public class Empresarial extends Cliente {
	private int anoFundacao;

	public Empresarial(int codigo, String nome, int anoFundacao) {
		super(codigo, nome);
		this.anoFundacao = anoFundacao;
	}

	public int getAnoFundacao() {
		return anoFundacao;
	}

	@Override
	public double calculaDesconto(int quantidadeRobos) {
		if (quantidadeRobos >= 2 && quantidadeRobos <= 9) {
			return 0.03;
		} else if (quantidadeRobos > 10) {
			return 0.07;
		}
		return 0.0;
	}

	@Override
	public String toString() {
		return super.toString() + ", Empresarial [anoFundacao=" + anoFundacao + "]";
	}
}