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

## Paso a paso de cómo funciona

### Paso 1: Inicio y carga de datos iniciales
Al ejecutar el programa, `Main` instancia un objeto `Hospital`, el cual ejecuta automáticamente `cargarDatosIniciales()`:
- **Se crean las 6 jaulas:** `1 (A, S)`, `2 (A, M)`, `3 (A, M)`, `4 (A, L)`, `5 (B, S)` y `6 (B, M)`.
- **Se registran 3 pacientes iniciales (sin jaula asignada):**
  - `Rocky` (Perro, 30.0 kg, 4 años, labrador, vacunado, estado `INGRESADO`).
  - `Misifu` (Gato, 4.0 kg, 3 años, estrés 0, estado `INGRESADO`).
  - `Kiwi` (Ave, 0.8 kg, 2 años, envergadura 70 cm, estado `INGRESADO`).

### Paso 2: Ingreso de nuevos pacientes (`ingresar`)
Cuando se ingresa un perro, gato o ave:
1. Se validan los datos (nombre no vacío, peso mayor a 0 y dentro del máximo de su especie, edad entre 0 y 30, envergadura > 0).
2. Se verifica que el nombre no esté duplicado (sin distinguir mayúsculas/minúsculas) y que haya cupo en el vector (máximo 10 pacientes).
3. Si es un **perro no vacunado (`no`)**, entra en estado `CUARENTENA`; cualquier otro paciente entra como `INGRESADO`.

### Paso 3: Vacunación (`vacunar`)
- Si un perro ingresó en `CUARENTENA`, no puede recibir jaula ni medicación hasta ser vacunado.
- Con `vacunar <nombre>`, se valida que el paciente exista, que sea un `Perro` y que no esté ya vacunado. Al vacunarlo, su estado pasa a `INGRESADO`.

### Paso 4: Asignación de jaula (`asignar`)
- Con `asignar <nombre>`, el sistema verifica que el paciente exista, que no esté en `CUARENTENA` y que aún no tenga jaula.
- Calcula la **zona requerida** (`A` para perros y gatos; `B` para aves) y el **tamaño requerido** según su peso (`< 5 kg` = `S`, `5 a 25 kg` = `M`, `> 25 kg` = `L`; si es un ave con envergadura `> 60 cm` y peso `< 5 kg`, sube a `M`).
- Busca la **primera jaula libre** en el vector `Jaula[6]` que coincida en zona y tamaño, y enlaza ambos objetos (`jaula.setOcupante(paciente)` y `paciente.setJaula(jaula)`).

### Paso 5: Medicación (`medicar`)
- Con `medicar <nombre> <mg>`, el sistema verifica en orden:
  1. Que el paciente exista y que los `mg` sean mayores a `0`.
  2. Que no esté en `CUARENTENA` y que tenga una jaula asignada.
  3. Si es `Gato`, que su nivel de estrés sea menor a `8`.
  4. Que la dosis no supere su **dosis máxima por aplicación** ($\text{peso} \times \text{factorDosis}$).
  5. Que el nuevo acumulado no supere su **tope acumulado** ($3 \times \text{dosis máxima}$).
- Si todo es válido, suma los `mg` a `dosisAcumulada`, cambia el estado del paciente a `EN_TRATAMIENTO` y, si es gato, incrementa su estrés en `2` (con tope `10`).

### Paso 6: Alta médica (`alta`)
- Con `alta <nombre>`, se comprueba que el paciente exista y que su estado sea `EN_TRATAMIENTO`.
- Se libera su jaula asignada (`ocupante = null`) y se elimina al paciente del vector `Paciente[10]` **compactándolo** (desplazando los elementos siguientes una posición a la izquierda y disminuyendo `contadorPacientes`).

### Paso 7: Reporte y salida (`reporte` / `salir`)
- `reporte` recorre el vector de pacientes activos mostrando su nombre, tipo, peso con un decimal, estado y número de jaula (o `-`), además del total de jaulas libres.
- `salir` detiene la lectura de comandos y cierra el programa.

---

## Cómo compilar y probar el programa

### 1. Compilar y ejecutar en terminal
```bash
javac *.java
java Main
```

### 2. Ejemplo de flujo completo (comandos de prueba)
Puedes pegar estas líneas en la consola para verificar el paso a paso:

```text
reporte
ingresar perro Max 20.5 5 pastor no
asignar Max
vacunar Max
asignar Max
asignar Rocky
medicar Rocky 300
alta Rocky
reporte
salir
```

**Salida esperada:**
```text
--- REPORTE ---
Pacientes: 3/10
Rocky | Perro | 30.0 kg | INGRESADO | jaula: -
Misifu | Gato | 4.0 kg | INGRESADO | jaula: -
Kiwi | Ave | 0.8 kg | INGRESADO | jaula: -
Jaulas libres: 6
OK: Max ingresado en CUARENTENA
ERROR: paciente en cuarentena
OK: Max vacunado
OK: Max asignado a jaula 2
OK: Rocky asignado a jaula 4
OK: Rocky medicado con 300.0 mg
OK: Rocky dado de alta
--- REPORTE ---
Pacientes: 3/10
Misifu | Gato | 4.0 kg | INGRESADO | jaula: -
Kiwi | Ave | 0.8 kg | INGRESADO | jaula: -
Max | Perro | 20.5 kg | INGRESADO | jaula: 2
Jaulas libres: 5
```
