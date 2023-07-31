package logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

import persistence.ManagerJDBC;
import persistence.ProductException;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el directorio (ruta) del archivo CSV:");
		String pathFile = scanner.nextLine();
		
		ManagerCSV managerCSV = new ManagerCSV(pathFile);
		ManagerJDBC managerJDBC = new ManagerJDBC();
		
		Integer optionInput;
		do {
			System.out.println("\n---- OPCIONES -----");
            System.out.println("1. Extraer información de la base de datos SQL y almacenarla en un archivo CSV.");
            System.out.println("2. Mostrar los registros del archivo CSV.");
            System.out.println("3. Crear registros en el archivo CSV.");
            System.out.println("4. Exportar CSV a Base de Datos SQL");
            System.out.println("5. Salir");
            System.out.print("Ingrese la opción deseada y presione Enter: ");
            optionInput = scanner.nextInt();
            // Limpiamos el buffer
            scanner.nextLine();

            switch (optionInput) {
                case 1:
                	try {
                        ArrayList<Product> products = managerJDBC.recoveryProductDB();
                        managerCSV.writeCSV(products);
                    } catch (ProductException e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case 2:
                	ArrayList<Product> products = managerCSV.readCSV();
                    System.out.println("\n El contenido del archivo CSV es:");
                    if (products.isEmpty()) {
                        System.out.println("El archivo que intenta leer está vacío.");
                    }
                    for (Product product : products) {
                        System.out.println(product);
                    }
                    break;

                case 3:
                	 System.out.println("\nA continuación, ingrese los datos del nuevo producto:");
                     System.out.print("ID: ");
                     Integer id = scanner.nextInt();
                     // Limpiamos el buffer
                     scanner.nextLine();
                     System.out.print("Nombre: ");
                     String nameProduct = scanner.nextLine();
                     System.out.print("Descripción: ");
                     String descriptionProduct = scanner.nextLine();
                     System.out.print("Precio: ");
                     Double price = scanner.nextDouble();
                     // Limpiamos el buffer
                     scanner.nextLine();
                     System.out.print("Fecha de alta (Formato: DD/MM/AAAA): ");
                     String dischargeDate = scanner.nextLine();

                     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                     Date date = null;
                     try {
                    	 date = dateFormat.parse(dischargeDate);
                     } catch (ParseException e) {
                         System.out.println("La fecha de alta no tiene el formato correcto. Se grabara con la fecha actual.");
                         date = new Date();
                     }

                     Product newProduct = new Product(id, nameProduct, descriptionProduct, price, date);
                     ArrayList<Product> currentProducts = managerCSV.readCSV();
                     currentProducts.add(newProduct);
                     managerCSV.writeCSV(currentProducts);
                     break;
                
                case 4:
                	try {
                        ArrayList<Product> recoveredProducts = managerCSV.readCSV();
                        ArrayList<Product> productInDB = managerJDBC.recoveryProductDB();

                        // Creamos un HashSet con los IDs de los productos en la base de datos
                        HashSet<Integer> idsInDB = new HashSet<>();
                        for (Product product : productInDB) {
                        	idsInDB.add(product.getId());
                        }

                        // Creamos un ArrayList para almacenar los productos no repetidos
                        ArrayList<Product> newProducts = new ArrayList<>();

                        // Comparamos los registros del archivo CSV con los de la base de datos
                        for (Product product : recoveredProducts) {
                            if (!idsInDB.contains(product.getId())) {
                                newProducts.add(product);
                            }
                        }

                        // Guardamos los productos no repetidos en la base de datos
                        managerJDBC.saveProductDB(newProducts);
                        System.out.println("Los registros se guardaron correctamente en la BD.");
                        
                    } catch (ProductException e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case 5:
                    System.out.println("Saliendo...");
                    break;
                    
                default:
                    System.out.println("La opción ingresada es inválida. Intente nuevamente.");

            }
		} while (optionInput != 5);

		scanner.close();
	}
}
