/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package garcia_marcos_ejercicio06;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author usuario
 */
class Socio implements Comparable, Serializable{
    //Atributos
    String dni; //Tendrá un atributo DNI
    String nombre; //Tendrá un nombre
    LocalDate fechaAlta; //Tendrá un atributo para la fecha en la que se dio de alta

    //Constructor
    public Socio(String dni, String nombre, LocalDate fechaAlta){
        this.dni = dni; //Establecemos el DNI en el constructor
        this.nombre = nombre; //Establecemos el nombre en el constructor
        this.fechaAlta = fechaAlta; //Establecemos la fecha en el constructor
    }

    //Métodos
    public long antiguedad(){ //Con este método averiguaremos la antigüedad de un Socio
        return ChronoUnit.YEARS.between(fechaAlta, LocalDate.now()); //Devolvemos la antigüedad
    }
    public String getDni(){ //Devolveremos el DNI
        return dni; //Devolvemos el DNI
    }
    public void setNombre(String nombre){ //Estableceremos el nombre
        this.nombre = nombre; //Establecemos el nombre
    }
    public String getNombre(){ //Devolveremos el nombre
        return nombre; //Devolvemos el nombre
    }
    public LocalDate getFechaAlta(){ //Devolveremos la fecha
        return fechaAlta; //Devolvemos la fecha
    }
    @Override
    public boolean equals(Object objeto){ //Usaremos el equals para saber si 2 DNI son iguales
        if(this == objeto){ //Si las referencia son iguales
            return true; //Devolvemos un true
        }
        if(objeto == null || this.getClass() != objeto.getClass()){ //Si objeto es null o es de otra clase
            return false; //Devolvemos un false
        }
        Socio socio = (Socio) objeto; //Hacemos un cast al objeto
        return Objects.equals(dni, socio.dni); //Devolvemos la comparación
    }
    @Override
    public int compareTo(Object objeto){ //Usaremos este método para comparar por DNI
        Socio socio = (Socio) objeto; //Hacemos un cast
        return this.dni.compareTo(socio.dni); //Hacemos la comparación
    }
    @Override
    public String toString(){ //Con este método mostraremos los datos en un String
        return "Socio{"+"dni="+dni+", nombre="+nombre+", fechaAlta="+fechaAlta+'}'; //Mostramos los datos
    }
}
public class Garcia_Marcos_Ejercicio06 {
    static Set<Socio> listaSocios = new TreeSet<>(); //Creamos un conjunto para los socios
    static String fichero = "socios.dat"; //Creamos e inicializamos el fichero
    /**Vamos a crear la clase Socio y la gestionaremos
     * guardando datos en un fichero .dat
     */
    public static void guardarFichero(){ //Con esta función guardaremos los datos en un fichero binario
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichero))){ //Intentaremos meter los datos en el fichero
            out.writeObject(listaSocios); //Escribimos la lista de socios en el fichero
        }catch (FileNotFoundException e){ //Capturamos una excepción de file
            System.out.println("No se ha encontrado ese fichero"); //Mostramos un mensaje explicativo
        }catch (IOException ex){ //Capturaremos una excepción IO también
            System.out.println("Ha ocurrido un error en el proceso IO"); //Mostramos otro mensaje para esta excepción
        }
    }
    public static void listadoDNI(){ //Con esta función listaremos por DNI
        if(listaSocios.isEmpty()){ //Si no hay socios
            System.out.println("No hay socios en la lista"); //Mostramos un mensaje
        }else{
            for(Socio socio : listaSocios){ //Con este for mostraremos los socios
                System.out.println(socio); //Mostramos los socios
            }
        }
    }
    public static void modificar(Scanner sc){ //Con esta función modificaremos un socio
        System.out.println("Introduce el DNI del socio a modificar"); //Pedimos el DNI del socio
        String dni = sc.nextLine(); //Leemos el DNI
        for(Socio socio : listaSocios){ //Por cada socio de la lista de socios
            if(socio.getDni().equals(dni)){ //Si los DNI coinciden
                System.out.println("Introduce el nuevo nombre"); //Pedimos el nombre nuevo
                String nombre = sc.nextLine(); //Leemos el nuevo nombre
                socio.setNombre(nombre); //Cambiamos el nombre
            }
        }
        System.out.println("No se ha encontrado el socio"); //Mostramos un mensaje de error
    }
    public static void baja(Scanner sc){ //Con esta función eliminaremos socios
        System.out.println("Introduce el DNI del socio que vamos a remover"); //Pedimos el DNI del socio
        String dni = sc.nextLine(); //Leemos el DNI
        if(listaSocios.remove(new Socio(dni, "", LocalDate.now()))){ //Si el borrado sale bien
            System.out.println("Socio eliminado del sistema"); //Mostramos un mensaje de éxito
        }else{
            System.out.println("No se ha podido eliminar al socio"); //Mostramos un mensaje de error
        }
    }
    public static void alta(Scanner sc){ //Con esta función daremos de alta a un socio
        System.out.println("Introduce el DNI"); //Pedimos el DNI
        String dni = sc.nextLine(); //Leemos el DNI
        System.out.println("Introduce el nombre"); //Pedimos el nombre
        String nombre = sc.nextLine(); //Leemos el nombre
        LocalDate hoy = LocalDate.now(); //Creamos una variable con la fecha de hoy
        Socio nuevoSocio = new Socio(dni, nombre, hoy); //Creamos un nuevo socio
        if(listaSocios.add(nuevoSocio)){ //Si se ha añadido el socio
            System.out.println("Se ha aniadido un nuevo socio al fichero"); //Mostramos un mensaje
        }else{
            System.out.println("No se ha podido aniadir el socio"); //Mostramos un mensaje de error
        }
    }
    public static void cargarFichero(){ //Con esta función cargaremos el fichero al inicio
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichero))){ //Intentamos con recursos
            listaSocios = (TreeSet<Socio>) in.readObject(); //Leemos el fichero
        }catch (FileNotFoundException e){ //Capturamos una excepción de File
            System.out.println("No se ha encontrado el fichero"); //Mostramos un mensaje de que no se encuentra el fichero
        }catch (IOException ex){ //Capturamos una excepción de IO
            System.out.println("Ha ocurrido un error de IO"); //Mostramos un mensaje de IO
        }catch (ClassNotFoundException ex2){ //Capturamos una excepción de clase
            System.out.println("No se encuentra la clase"); //Mostramos un mensaje
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in); //Creamos el objeto sc
        cargarFichero(); //Cargamos el fichero si existe
        int opcion; //Declaramos la opción
        do{ //Con el do mostramos el menú
            System.out.println("1.Alta");
            System.out.println("2.Baja");
            System.out.println("3.Modificacion");
            System.out.println("4.Listado por DNI");
            System.out.println("5.Listado por antiguedad");
            System.out.println("6.Salir");
            System.out.println("Introduce la opcion que quieres");
            opcion = sc.nextInt(); //Leemos la opción
            sc.nextLine();
            switch(opcion){ //Con el switch haremos acciones dependiendo de la opción
                case 1:
                    alta(sc); //LLamamos a la función para el alta
                    break; //Rompemos el switch
                case 2:
                    baja(sc); //LLamamos a la función de la baja
                    break; //Rompemos el switch
                case 3:
                    modificar(sc); //LLamamos a la función para modificar un usuario
                    break; //Rompemos el switch
                case 4:
                    listadoDNI(); //LLamamos a la función para listar por DNI
                    break; //Rompemos el switch
                case 5:
                    Comparator<Socio> comparator = new Comparator<>(){ //Usaremos un Comparator para la antigüedad
                        @Override
                        public int compare(Socio objeto1, Socio objeto2){ //Con este método compararemos
                            return (int) (objeto2.antiguedad()-objeto1.antiguedad());
                        }
                    };
                    break; //Rompemos el switch
                case 6:
                    System.out.println("Hasta otra ocasion"); //Mostramos un mensaje de despedida
                    guardarFichero(); //Guardamos los datos en un fichero
                    break; //Rompemos el switch
                default:
                    System.out.println("Esa opcion no es valida"); //Mostramos un mensaje de error
            }
        }while(opcion != 6); //El do se repetirá mientras la opción no sea 6
    }

}
