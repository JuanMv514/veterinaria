import java.util.Locale;

public class Hospital {
    private Paciente[] pacientes;
    private int contadorPacientes;
    private Jaula[] jaulas;

    public Hospital() {
        this.pacientes = new Paciente[10];
        this.contadorPacientes = 0;
        this.jaulas = new Jaula[6];
        cargarDatosIniciales();
    }

    public void cargarDatosIniciales() {
        jaulas[0] = new Jaula(1, "S", "A");
        jaulas[1] = new Jaula(2, "M", "A");
        jaulas[2] = new Jaula(3, "M", "A");
        jaulas[3] = new Jaula(4, "L", "A");
        jaulas[4] = new Jaula(5, "S", "B");
        jaulas[5] = new Jaula(6, "M", "B");

        pacientes[0] = new Perro("Rocky", 30.0, 4, "labrador", true);
        pacientes[1] = new Gato("Misifu", 4.0, 3, 0);
        pacientes[2] = new AveExotica("Kiwi", 0.8, 2, 70.0);
        contadorPacientes = 3;
    }

    public Paciente[] getPacientes() {
        return pacientes;
    }

    public int getContadorPacientes() {
        return contadorPacientes;
    }

    public Jaula[] getJaulas() {
        return jaulas;
    }

    public int buscarIndicePaciente(String nombre) {
        if (nombre == null) {
            return -1;
        }
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public Paciente buscarPaciente(String nombre) {
        int indice = buscarIndicePaciente(nombre);
        if (indice == -1) {
            return null;
        }
        return pacientes[indice];
    }

    public String ingresarPerro(String nombre, double peso, int edad, String raza, String vacunadoTexto) {
        if (nombre == null || nombre.trim().isEmpty()
                || peso <= 0 || peso > 80.0
                || edad < 0 || edad > 30
                || raza == null || raza.trim().isEmpty()
                || vacunadoTexto == null
                || (!vacunadoTexto.equalsIgnoreCase("si") && !vacunadoTexto.equalsIgnoreCase("no"))) {
            return "ERROR: datos invalidos";
        }
        if (buscarPaciente(nombre.trim()) != null) {
            return "ERROR: nombre duplicado";
        }
        if (contadorPacientes >= pacientes.length) {
            return "ERROR: hospital lleno";
        }

        boolean vacunado = vacunadoTexto.equalsIgnoreCase("si");
        Perro perro = new Perro(nombre.trim(), peso, edad, raza.trim(), vacunado);
        pacientes[contadorPacientes] = perro;
        contadorPacientes++;

        if (vacunado) {
            return "OK: " + perro.getNombre() + " ingresado";
        } else {
            return "OK: " + perro.getNombre() + " ingresado en CUARENTENA";
        }
    }

    public String ingresarGato(String nombre, double peso, int edad) {
        if (nombre == null || nombre.trim().isEmpty()
                || peso <= 0 || peso > 15.0
                || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        if (buscarPaciente(nombre.trim()) != null) {
            return "ERROR: nombre duplicado";
        }
        if (contadorPacientes >= pacientes.length) {
            return "ERROR: hospital lleno";
        }

        Gato gato = new Gato(nombre.trim(), peso, edad, 0);
        pacientes[contadorPacientes] = gato;
        contadorPacientes++;

        return "OK: " + gato.getNombre() + " ingresado";
    }

    public String ingresarAve(String nombre, double peso, int edad, double envergadura) {
        if (nombre == null || nombre.trim().isEmpty()
                || peso <= 0 || peso > 15.0
                || edad < 0 || edad > 30
                || envergadura <= 0) {
            return "ERROR: datos invalidos";
        }
        if (buscarPaciente(nombre.trim()) != null) {
            return "ERROR: nombre duplicado";
        }
        if (contadorPacientes >= pacientes.length) {
            return "ERROR: hospital lleno";
        }

        AveExotica ave = new AveExotica(nombre.trim(), peso, edad, envergadura);
        pacientes[contadorPacientes] = ave;
        contadorPacientes++;

        return "OK: " + ave.getNombre() + " ingresado";
    }

    public String vacunar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (!(p instanceof Perro)) {
            return "ERROR: solo aplica a perros";
        }
        Perro perro = (Perro) p;
        if (perro.isVacunado()) {
            return "ERROR: ya vacunado";
        }

        perro.setVacunado(true);
        perro.setEstado("INGRESADO");
        return "OK: " + perro.getNombre() + " vacunado";
    }

    public String asignar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if ("CUARENTENA".equals(p.getEstado())) {
            return "ERROR: paciente en cuarentena";
        }
        if (p.getJaula() != null) {
            return "ERROR: paciente ya tiene jaula";
        }

        String zonaRequerida = p.getZonaRequerida();
        String tamanoRequerido = p.calcularTamanoJaula();
        Jaula jaulaCompatible = null;

        for (int i = 0; i < jaulas.length; i++) {
            Jaula j = jaulas[i];
            if (j != null && j.estaLibre()
                    && j.getZona().equals(zonaRequerida)
                    && j.getTamano().equals(tamanoRequerido)) {
                jaulaCompatible = j;
                break;
            }
        }

        if (jaulaCompatible == null) {
            return "ERROR: no hay jaula compatible";
        }

        jaulaCompatible.setOcupante(p);
        p.setJaula(jaulaCompatible);
        return "OK: " + p.getNombre() + " asignado a jaula " + jaulaCompatible.getNumero();
    }

    public String medicar(String nombre, double mg) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (Double.isNaN(mg) || mg <= 0) {
            return "ERROR: datos invalidos";
        }
        if ("CUARENTENA".equals(p.getEstado())) {
            return "ERROR: paciente en cuarentena";
        }
        if (p.getJaula() == null) {
            return "ERROR: paciente sin jaula";
        }
        if (p instanceof Gato && ((Gato) p).getNivelEstres() >= 8) {
            return "ERROR: gato con estres alto";
        }
        if (mg - p.calcularDosisMaxima() > 1e-9) {
            return "ERROR: dosis excede el maximo";
        }
        if ((p.getDosisAcumulada() + mg) - p.calcularTopeAcumulado() > 1e-9) {
            return "ERROR: tope acumulado superado";
        }

        p.setDosisAcumulada(p.getDosisAcumulada() + mg);
        p.setEstado("EN_TRATAMIENTO");
        if (p instanceof Gato) {
            ((Gato) p).subirEstres();
        }

        return "OK: " + p.getNombre() + " medicado con " + mg + " mg";
    }

    public String alta(String nombre) {
        int indice = buscarIndicePaciente(nombre);
        if (indice == -1) {
            return "ERROR: paciente no encontrado";
        }

        Paciente p = pacientes[indice];
        if (!"EN_TRATAMIENTO".equals(p.getEstado())) {
            return "ERROR: paciente no apto para alta";
        }

        if (p.getJaula() != null) {
            p.getJaula().setOcupante(null);
            p.setJaula(null);
        }

        for (int i = indice; i < contadorPacientes - 1; i++) {
            pacientes[i] = pacientes[i + 1];
        }
        pacientes[contadorPacientes - 1] = null;
        contadorPacientes--;

        return "OK: " + p.getNombre() + " dado de alta";
    }

    public int contarJaulasLibres() {
        int libres = 0;
        for (int i = 0; i < jaulas.length; i++) {
            if (jaulas[i] != null && jaulas[i].estaLibre()) {
                libres++;
            }
        }
        return libres;
    }

    public String reporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- REPORTE ---\n");
        sb.append("Pacientes: ").append(contadorPacientes).append("/10\n");
        for (int i = 0; i < contadorPacientes; i++) {
            Paciente p = pacientes[i];
            String pesoTexto = String.format(Locale.US, "%.1f", p.getPeso());
            String jaulaTexto = (p.getJaula() != null) ? String.valueOf(p.getJaula().getNumero()) : "-";
            sb.append(p.getNombre()).append(" | ")
              .append(p.getTipo()).append(" | ")
              .append(pesoTexto).append(" kg | ")
              .append(p.getEstado()).append(" | jaula: ")
              .append(jaulaTexto).append("\n");
        }
        sb.append("Jaulas libres: ").append(contarJaulasLibres());
        return sb.toString();
    }
}

