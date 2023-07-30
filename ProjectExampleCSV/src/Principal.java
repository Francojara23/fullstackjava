
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta del archivo CSV:");
        String rutaDelArchivo = scanner.nextLine();

        GestorDeCSV gestorDeCSV = new GestorDeCSV(rutaDelArchivo);
        GestorDeProductosJDBC gestorDeProductosJDBC = new GestorDeProductosJDBC();
        Integer opcion;

        do {
            System.out.println("\n*** MENU ***");
            System.out.println("1. Recuperar datos de la base de datos SQL y guardar en archivo CSV");
            System.out.println("2. Leer archivo CSV y mostrar contenido.");
            System.out.println("3. Escribir en el archivo CSV.");
            System.out.println("4. Exportar CSV a Base de Datos SQL");
            System.out.println("5. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiamos el buffer

            switch (opcion) {
                case 1:
                	try {
                        ArrayList<Producto> productos = gestorDeProductosJDBC.recuperarProductosDeLaBaseDeDatos();
                        gestorDeCSV.escribirCSV(productos);
                    } catch (ProductoException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    ArrayList<Producto> productos = gestorDeCSV.leerCSV();
                    System.out.println("\nContenido del archivo CSV:");
                    if (productos.isEmpty()) {
                        System.out.println("El archivo está vacío.");
                    }
                    for (Producto producto : productos) {
                        System.out.println(producto);
                    }
                    break;
                case 3:
                    System.out.println("\nIngrese los datos del nuevo producto:");
                    System.out.print("ID: ");
                    Integer id = scanner.nextInt();
                    scanner.nextLine(); // Limpiamos el buffer
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Precio: ");
                    Double precio = scanner.nextDouble();
                    scanner.nextLine(); // Limpiamos el buffer
                    System.out.print("Fecha de alta (Formato: dd/MM/yyyy): ");
                    String fechaAltaStr = scanner.nextLine();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaAlta = null;
                    try {
                        fechaAlta = dateFormat.parse(fechaAltaStr);
                    } catch (ParseException e) {
                        System.out.println("Fecha de alta inválida. Se usará la fecha actual.");
                        fechaAlta = new Date();
                    }

                    Producto nuevoProducto = new Producto(id, nombre, descripcion, precio, fechaAlta);
                    ArrayList<Producto> productosActuales = gestorDeCSV.leerCSV();
                    productosActuales.add(nuevoProducto);
                    gestorDeCSV.escribirCSV(productosActuales);
                    break;
                case 4:
                	try {
                        ArrayList<Producto> productosRecuperados = gestorDeCSV.leerCSV();
                        ArrayList<Producto> productosEnBaseDeDatos = gestorDeProductosJDBC.recuperarProductosDeLaBaseDeDatos();

                        // Creamos un HashSet con los IDs de los productos en la base de datos
                        HashSet<Integer> idsEnBaseDeDatos = new HashSet<>();
                        for (Producto producto : productosEnBaseDeDatos) {
                            idsEnBaseDeDatos.add(producto.getId());
                        }

                        // Creamos un ArrayList para almacenar los productos no repetidos
                        ArrayList<Producto> nuevosProductos = new ArrayList<>();

                        // Comparamos los registros del archivo CSV con los de la base de datos
                        for (Producto producto : productosRecuperados) {
                            if (!idsEnBaseDeDatos.contains(producto.getId())) {
                                nuevosProductos.add(producto);
                            }
                        }

                        // Guardamos los productos no repetidos en la base de datos
                        gestorDeProductosJDBC.guardarProductosEnLaBaseDeDatos(nuevosProductos);

                    } catch (ProductoException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}