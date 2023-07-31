package logic;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagerCSV {
    private String pathFile;

    public ManagerCSV(String pathFile){
        this.pathFile = pathFile;
    }

    public void writeCSV(ArrayList<Product> newProducts) {
        try {
            // INFO: Revisamos que registros existen
            ArrayList<Product> currentProducts = readCSV();

            File file = new File(pathFile);
            boolean isFile = file.exists();
            boolean isProduct = false;

            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            if (!isFile){
                // INFO: Si no existe
                bufferedWriter.write("#ID; NOMBRE; DESCRIPCION; PRECIO; FECHAALTA");
                bufferedWriter.newLine();
            }
            
            //Obtenemos todos los registros y validamos que el ID no exista.
            for (Product newProduct : newProducts) {                
            	if (!currentProducts.contains(newProduct)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dischargeDate = dateFormat.format(newProduct.getDischargeDate());
                    bufferedWriter.write(newProduct.getId() + ";" +
                            newProduct.getNameProduct() + ";" +
                            newProduct.getDescriptionProduct() + ";" +
                            newProduct.getPrice() + ";" +
                            dischargeDate);
                    bufferedWriter.newLine();
                    currentProducts.add(newProduct);
                    isProduct = false;
                } else {
                    isProduct = true;
                }
            }
            
            if (!isProduct) {
            	System.out.println("Registro agregado exitosamente.");
			}else {
				System.out.println("El ID que ha ingresado ya existe. Por favor, cargar otro ID.");
			}
            
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> readCSV(){
        ArrayList<Product> products = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile));
            String lineFile;

            //INFO: Realizamos un salto de linea para evitar la cabecera
            bufferedReader.readLine();

            //INFO: leemos cada linea dentro del archivo y la convertimos en un objeto
            while ((lineFile = bufferedReader.readLine()) != null) {
                String[] fieldString = lineFile.split(";");
                Integer id = Integer.parseInt(fieldString[0]);
                String nameProduct = fieldString[1];
                String descriptionProduct = fieldString[2];
                Double price = Double.parseDouble(fieldString[3]);
                Date dischargeDate = new SimpleDateFormat("dd/MM/yyyy").parse(fieldString[4]);

                Product product = new Product(id, nameProduct, descriptionProduct, price, dischargeDate);
                products.add(product);
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } 
        return products;
    } 
}
