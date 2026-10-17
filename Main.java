import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine().trim();
            if (linea.isEmpty()) {
                continue;
            }

            String[] partes = linea.split("\\s+");
            String comando = partes[0].toLowerCase();

            if (comando.equals("salir")) {
                break;
            }

            switch (comando) {
                case "ingresar":
                    procesarIngreso(hospital, partes);
                    break;
                case "vacunar":
                    if (partes.length != 2) {
                        System.out.println("ERROR: comando desconocido");
                    } else {
                        System.out.println(hospital.vacunar(partes[1]));
                    }
                    break;
                case "asignar":
                    if (partes.length != 2) {
                        System.out.println("ERROR: comando desconocido");
                    } else {
                        System.out.println(hospital.asignar(partes[1]));
                    }
                    break;
                case "medicar":
                    if (partes.length != 3) {
                        System.out.println("ERROR: datos invalidos");
                    } else {
                        double mg;
                        try {
                            mg = Double.parseDouble(partes[2]);
                        } catch (NumberFormatException e) {
                            mg = -1.0;
                        }
                        System.out.println(hospital.medicar(partes[1], mg));
                    }
                    break;
                case "alta":
                    if (partes.length != 2) {
                        System.out.println("ERROR: comando desconocido");
                    } else {
                        System.out.println(hospital.alta(partes[1]));
                    }
                    break;
                case "reporte":
                    if (partes.length != 1) {
                        System.out.println("ERROR: comando desconocido");
                    } else {
                        System.out.println(hospital.reporte());
                    }
                    break;
                default:
                    System.out.println("ERROR: comando desconocido");
                    break;
            }
        }

        scanner.close();
    }

    private static void procesarIngreso(Hospital hospital, String[] partes) {
        if (partes.length < 2) {
            System.out.println("ERROR: comando desconocido");
            return;
        }

        String subtipo = partes[1].toLowerCase();
        switch (subtipo) {
            case "perro":
                if (partes.length != 7) {
                    System.out.println("ERROR: datos invalidos");
                    return;
                }
                try {
                    String nombre = partes[2];
                    double peso = Double.parseDouble(partes[3]);
                    int edad = Integer.parseInt(partes[4]);
                    String raza = partes[5];
                    String vacunado = partes[6];
                    System.out.println(hospital.ingresarPerro(nombre, peso, edad, raza, vacunado));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: datos invalidos");
                }
                break;

            case "gato":
                if (partes.length != 5) {
                    System.out.println("ERROR: datos invalidos");
                    return;
                }
                try {
                    String nombre = partes[2];
                    double peso = Double.parseDouble(partes[3]);
                    int edad = Integer.parseInt(partes[4]);
                    System.out.println(hospital.ingresarGato(nombre, peso, edad));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: datos invalidos");
                }
                break;

            case "ave":
                if (partes.length != 6) {
                    System.out.println("ERROR: datos invalidos");
                    return;
                }
                try {
                    String nombre = partes[2];
                    double peso = Double.parseDouble(partes[3]);
                    int edad = Integer.parseInt(partes[4]);
                    double envergadura = Double.parseDouble(partes[5]);
                    System.out.println(hospital.ingresarAve(nombre, peso, edad, envergadura));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: datos invalidos");
                }
                break;

            default:
                System.out.println("ERROR: comando desconocido");
                break;
        }
    }
}

