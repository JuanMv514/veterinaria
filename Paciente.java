public class Paciente {
    private String nombre;
    private double peso;
    private int edad;
    private String estado;
    private int factorDosis;
    private double dosisAcumulada;
    private Jaula jaula;

    public Paciente(String nombre, double peso, int edad, String estado, int factorDosis) {
        setNombre(nombre);
        setPeso(peso);
        setEdad(edad);
        setEstado(estado);
        setFactorDosis(factorDosis);
        this.dosisAcumulada = 0.0;
        this.jaula = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso > 0 && peso <= getPesoMaximo()) {
            this.peso = peso;
        }
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad >= 0 && edad <= 30) {
            this.edad = edad;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado != null && (estado.equals("INGRESADO")
                || estado.equals("CUARENTENA")
                || estado.equals("EN_TRATAMIENTO"))) {
            this.estado = estado;
        }
    }

    public int getFactorDosis() {
        return factorDosis;
    }

    public void setFactorDosis(int factorDosis) {
        if (factorDosis > 0) {
            this.factorDosis = factorDosis;
        }
    }

    public double getDosisAcumulada() {
        return dosisAcumulada;
    }

    public void setDosisAcumulada(double dosisAcumulada) {
        if (dosisAcumulada >= 0 && dosisAcumulada - calcularTopeAcumulado() <= 1e-9) {
            this.dosisAcumulada = dosisAcumulada;
        }
    }

    public Jaula getJaula() {
        return jaula;
    }

    public void setJaula(Jaula jaula) {
        this.jaula = jaula;
    }

    public double getPesoMaximo() {
        return 80.0;
    }

    public double calcularDosisMaxima() {
        return peso * factorDosis;
    }

    public double calcularTopeAcumulado() {
        return 3.0 * calcularDosisMaxima();
    }

    public String calcularTamanoJaula() {
        if (peso < 5.0) {
            return "S";
        } else if (peso <= 25.0) {
            return "M";
        } else {
            return "L";
        }
    }

    public String getZonaRequerida() {
        return "A";
    }

    public String getTipo() {
        return "Paciente";
    }
}
