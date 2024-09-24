package dados;

public abstract class Robo {
	private int id;
	private String modelo;
	private double valorDiario;
	private boolean disponivel;

	public Robo(int id, String modelo) {
		this.id = id;
		this.modelo = modelo;
		this.disponivel = true;
	}

	public int getId() {
		return id;
	}

	public String getModelo() {
		return modelo;
	}

	public double getValorDiario() {
		return valorDiario;
	}

	protected void setValorDiario(double valorDiario) {
		this.valorDiario = valorDiario;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public abstract double calculaLocacao(long dias);

	@Override
	public String toString() {
		return "Robo [id=" + id + ", modelo=" + modelo + ", valorDiario=" + valorDiario + ", disponivel=" + disponivel + "]";
	}
}