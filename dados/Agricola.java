package dados;

public class Agricola extends Robo {
	private double area;
	private String uso;

	public Agricola(int id, String modelo, double area, String uso) {
		super(id, modelo);
		this.area = area;
		this.uso = uso;
		setValorDiario(10.0 * area);
	}

	public double getArea() {
		return area;
	}

	public String getUso() {
		return uso;
	}

	public double calculaLocacao(long dias)
	{
		return getValorDiario()*dias;
	}

	@Override
	public String toString() {
		return super.toString() + ", Agricola [area=" + area + ", uso=" + uso + "]";
	}
}