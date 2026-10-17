public class AveExotica extends Paciente {
    private double envergadura;

    public AveExotica(String nombre, double peso, int edad, double envergadura) {
        super(nombre, peso, edad, "INGRESADO", 3);
        setEnvergadura(envergadura);
    }

    public double getEnvergadura() {
        return envergadura;
    }

    public void setEnvergadura(double envergadura) {
        if (envergadura > 0) {
            this.envergadura = envergadura;
        }
    }

    @Override
    public double getPesoMaximo() {
        return 15.0;
    }

    @Override
    public String calcularTamanoJaula() {
        String tamano = super.calcularTamanoJaula();
        if (envergadura > 60.0 && "S".equals(tamano)) {
            return "M";
        }
        return tamano;
    }

    @Override
    public String getZonaRequerida() {
        return "B";
    }

    @Override
    public String getTipo() {
        return "Ave";
    }
}

