package dados;

public class Industrial extends Robo {
	private String setor;

	public Industrial(int id, String modelo, String setor) {
		super(id, modelo);
		this.setor = setor;
		setValorDiario(90.0);
	}

	public String getSetor() {
		return setor;
	}

	public double calculaLocacao(long dias)
	{
		return getValorDiario()*dias;
	}

	@Override
	public String toString() {
		return super.toString() + ", Industrial [setor=" + setor + "]";
	}
}