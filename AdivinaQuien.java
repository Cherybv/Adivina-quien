import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdivinaQuien {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Juguemos a Adivina Quién!");

        // Se mantiene jugando mientras el usuario quiera
        while (true) {
            jugarAdivinaQuien(scanner);
            System.out.println("¿Deseas jugar de nuevo? (s/n): ");
            String jugarDeNuevo = scanner.nextLine();
            if (!jugarDeNuevo.equalsIgnoreCase("s")) {
                System.out.println("¡Gracias por jugar! Adiós.");
                break;
            }
        }
        scanner.close();
    }

    public static void jugarAdivinaQuien(Scanner scanner) {
        System.out.println("Piensa en una persona y responde con 's' para sí y 'n' para no.");

        // Cargar la base de datos de personajes desde un archivo de texto
        ArrayList<Persona> personas = cargarPersonajesDesdeArchivo("personajes.txt");

        // Realizar preguntas
        boolean hombre, barba, bigote, cabelloLargo, lentes, gorra, ojos, aretes;

        System.out.print("¿Es hombre? (s/n): ");
        hombre = scanner.nextLine().equalsIgnoreCase("s");

        if (hombre) {
            System.out.print("¿Tiene barba? (s/n): ");
            barba = scanner.nextLine().equalsIgnoreCase("s");

            System.out.print("¿Tiene bigote? (s/n): ");
            bigote = scanner.nextLine().equalsIgnoreCase("s");
        } else {
            barba = false;
            bigote = false;
        }

        System.out.print("¿Tiene cabello largo? (s/n): ");
        cabelloLargo = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Usa lentes? (s/n): ");
        lentes = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene gorra? (s/n): ");
        gorra = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene ojos azules? (s/n): ");
        ojos = scanner.nextLine().equalsIgnoreCase("s");

        if (hombre) {
            aretes = false;
        } else {
            System.out.print("¿Tiene aretes? (s/n): ");
            aretes = scanner.nextLine().equalsIgnoreCase("s");
        }

        // Adivinar el personaje
        boolean personajeAdivinado = false;
        for (Persona persona : personas) {
            if (persona.coincide(hombre, barba, bigote, cabelloLargo, lentes, gorra, ojos, aretes)) {
                System.out.println("Adivino que tu persona es " + persona.getNombre() + "!");
                personajeAdivinado = true;
                break;
            }
        }

        if (!personajeAdivinado) {
            System.out.println("No conozco a esta persona. ¿Deseas agregarla a la base de datos? (s/n):");
            String agregarPersona = scanner.nextLine();
            if (agregarPersona.equalsIgnoreCase("s")) {
                agregarNuevaPersona(personas, scanner);
            }
        }

        // Guardar la base de datos actualizada en el archivo
        guardarPersonajesEnArchivo("personajes.txt", personas);
    }

    static class Persona {
        private String nombre;
        private boolean hombre;
        private boolean barba;
        private boolean bigote;
        private boolean cabelloLargo;
        private boolean lentes;
        private boolean gorra;
        private boolean ojos;
        private boolean aretes;

        public Persona(String nombre, boolean hombre, boolean barba, boolean bigote, boolean cabelloLargo, boolean lentes, boolean gorra, boolean ojos, boolean aretes) {
            this.nombre = nombre;
            this.hombre = hombre;
            this.barba = barba;
            this.bigote = bigote;
            this.cabelloLargo = cabelloLargo;
            this.lentes = lentes;
            this.gorra = gorra;
            this.ojos = ojos;
            this.aretes = aretes;
        }

        public String getNombre() {
            return nombre;
        }

        public boolean coincide(boolean hombre, boolean barba, boolean bigote, boolean cabelloLargo, boolean lentes, boolean gorra, boolean ojos,  boolean aretes) {
            return this.hombre == hombre &&
                    this.barba == barba &&
                    this.bigote == bigote &&
                    this.cabelloLargo == cabelloLargo &&
                    this.lentes == lentes &&
                    this.gorra == gorra &&
                    this.ojos == ojos &&
                    this.aretes == aretes;
        }
    }

    public static ArrayList<Persona> cargarPersonajesDesdeArchivo(String nombreArchivo) {
        ArrayList<Persona> personas = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 9) {
                    String nombre = partes[0];
                    boolean hombre = partes[1].equalsIgnoreCase("s");
                    boolean barba = partes[2].equalsIgnoreCase("s");
                    boolean bigote = partes[3].equalsIgnoreCase("s");
                    boolean cabelloLargo = partes[4].equalsIgnoreCase("s");
                    boolean lentes = partes[5].equalsIgnoreCase("s");
                    boolean gorra = partes[6].equalsIgnoreCase("s");
                    boolean ojos = partes[7].equalsIgnoreCase("s");
                    boolean aretes = partes[8].equalsIgnoreCase("s");

                    personas.add(new Persona(nombre, hombre, barba, bigote, cabelloLargo, lentes, gorra, ojos, aretes));
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return personas;
    }

    public static void guardarPersonajesEnArchivo(String nombreArchivo, ArrayList<Persona> personas) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));

            for (Persona persona : personas) {
                bw.write(persona.getNombre() + ";");
                bw.write(persona.hombre ? "s;" : "n;");
                bw.write(persona.barba ? "s;" : "n;");
                bw.write(persona.bigote ? "s;" : "n;");
                bw.write(persona.cabelloLargo ? "s;" : "n;");
                bw.write(persona.lentes ? "s;" : "n;");
                bw.write(persona.gorra ? "s;" : "n;");
                bw.write(persona.ojos ? "s;" : "n;");
                bw.write(persona.aretes ? "s" : "n");
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void agregarNuevaPersona(ArrayList<Persona> personas, Scanner scanner) {
        System.out.println("Ingresa los datos de la nueva persona:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("¿Es hombre? (s/n): ");
        boolean hombre = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene barba? (s/n): ");
        boolean barba = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene bigote? (s/n): ");
        boolean bigote = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene cabello largo? (s/n): ");
        boolean cabelloLargo = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Usa lentes? (s/n): ");
        boolean lentes = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene gorra? (s/n): ");
        boolean gorra = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene ojos azules? (s/n): ");
        boolean ojos = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("¿Tiene aretes? (s/n): ");
        boolean aretes = scanner.nextLine().equalsIgnoreCase("s");

        Persona nuevaPersona = new Persona(nombre, hombre, barba, bigote, cabelloLargo, lentes, gorra, ojos, aretes);
        personas.add(nuevaPersona);
        System.out.println("Nueva persona agregada a la base de datos.");
    }
}
