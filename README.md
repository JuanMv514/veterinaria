# Sistema de Gestión – Hospital Veterinario (POO en Java)

## ¿Qué se hizo?
Se desarrolló un sistema por consola en **Java** para gestionar el ingreso, vacunación, asignación de jaulas, medicación, alta y reporte de pacientes (`Perro`, `Gato` y `AveExotica`) en una clínica veterinaria, utilizando únicamente arreglos de capacidad fija (`Paciente[10]` y `Jaula[6]`).

## ¿Cómo se construyó?
El proyecto se diseñó aplicando los pilares de la **Programación Orientada a Objetos (POO)**:

1. **Herencia y Polimorfismo:**
   - [`Paciente`](Paciente.java) es la clase base que concentra los atributos comunes (`nombre`, `peso`, `edad`, `estado`, `factorDosis`, `dosisAcumulada`, `jaula`) y el cálculo de dosis máxima, tope acumulado y tamaño de jaula.
   - [`Perro`](Perro.java) (`factorDosis = 10`), [`Gato`](Gato.java) (`factorDosis = 6`) y [`AveExotica`](AveExotica.java) (`factorDosis = 3`) heredan de `Paciente`, invocan a `super(...)` y sobrescriben los límites de peso, zona requerida y reglas específicas (como el tamaño mínimo `M` para aves con envergadura mayor a 60 cm o el nivel de estrés en gatos).
2. **Encapsulamiento:**
   - Todos los atributos de las clases son `private` y cuentan con *getters* y *setters* que validan los datos antes de modificar el estado del objeto.
3. **Manejo de Vectores y Reglas de Negocio:**
   - [`Hospital`](Hospital.java) centraliza la lógica del dominio usando vectores fijos (`Paciente[10]` con contador y `Jaula[6]`), precarga los datos iniciales en `cargarDatosIniciales()`, realiza búsquedas sin distinguir mayúsculas/minúsculas y compacta el vector al dar de alta a un paciente.
4. **Separación Dominio / Consola:**
   - [`Main`](Main.java) es la única clase que interactúa con el usuario mediante `Scanner` y `System.out`, delegando todas las validaciones y operaciones a `Hospital`.

---

## ¿Cómo funciona?

### 1. Compilación y ejecución
Desde la terminal, ubicado en la carpeta del proyecto:

```bash
javac *.java
java Main
```

### 2. Comandos disponibles
El programa lee un comando por línea hasta recibir `salir`:

| Comando | Descripción |
| :--- | :--- |
| `ingresar perro <nombre> <peso> <edad> <raza> <si/no>` | Registra un perro (si no está vacunado ingresa en `CUARENTENA`). |
| `ingresar gato <nombre> <peso> <edad>` | Registra un gato con estrés inicial `0` en estado `INGRESADO`. |
| `ingresar ave <nombre> <peso> <edad> <envergadura>` | Registra un ave exótica en estado `INGRESADO`. |
| `vacunar <nombre>` | Vacuna a un perro y cambia su estado a `INGRESADO`. |
| `asignar <nombre>` | Busca y enlaza la primera jaula libre compatible en zona (`A` o `B`) y tamaño (`S`, `M` o `L`). |
| `medicar <nombre> <mg>` | Administra medicación validando jaula, estrés y topes de dosis; pasa el paciente a `EN_TRATAMIENTO`. |
| `alta <nombre>` | Da de alta a un paciente en tratamiento, libera su jaula y compacta el vector de pacientes. |
| `reporte` | Muestra el listado actual de pacientes y la cantidad de jaulas libres. |
| `salir` | Finaliza la ejecución del programa. |
