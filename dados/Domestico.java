package dados;

public class Domestico extends Robo {
	private int nivel;

	public Domestico(int id, String modelo, int nivel) {
		super(id, modelo);
		this.nivel = nivel;
	}

	public int getNivel() {
		return nivel;
	}

	public double calculaLocacao(long dias)
	{
		if (nivel == 1)
		{
			setValorDiario(10.0);
		}
		else if (nivel == 2)
		{
			setValorDiario(20.0);
		} else if (nivel == 3)
		{
			setValorDiario(50.0);
		}

		return getValorDiario()*dias;
	}

	@Override
	public String toString() {
		return super.toString() + ", Domestico [nivel=" + nivel + "]";
	}
}