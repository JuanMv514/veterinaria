public class Gato extends Paciente {
    private int nivelEstres;

    public Gato(String nombre, double peso, int edad) {
        this(nombre, peso, edad, 0);
    }

    public Gato(String nombre, double peso, int edad, int nivelEstres) {
        super(nombre, peso, edad, "INGRESADO", 6);
        setNivelEstres(nivelEstres);
    }

    public int getNivelEstres() {
        return nivelEstres;
    }

    public void setNivelEstres(int nivelEstres) {
        if (nivelEstres >= 0 && nivelEstres <= 10) {
            this.nivelEstres = nivelEstres;
        }
    }

    public void subirEstres() {
        int nuevoEstres = this.nivelEstres + 2;
        if (nuevoEstres > 10) {
            nuevoEstres = 10;
        }
        setNivelEstres(nuevoEstres);
    }

    @Override
    public double getPesoMaximo() {
        return 15.0;
    }

    @Override
    public String getZonaRequerida() {
        return "A";
    }

    @Override
    public String getTipo() {
        return "Gato";
    }
}

