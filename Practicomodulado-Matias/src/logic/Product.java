package logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Product {
	private Integer id;
	private String nameProduct;
	private String descriptionProduct;
	private Double price;
	private Date dischargeDate;
	
	//INFO: Constructor por defecto.
	public Product() {}
	
	//INFO: Constructor por parámetro.
	public Product( 
					Integer id, 
					String nameProduct, 
					String descriptionProduct, 
					Double price, 
					Date dischargeDate
					){
						this.id = id;
						this.nameProduct = nameProduct;
						this.descriptionProduct = descriptionProduct;
						this.price = price;
						this.dischargeDate = dischargeDate;
					}
			
	//INFO: Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getDescriptionProduct() {
		return descriptionProduct;
	}

	public void setDescriptionProduct(String descriptionProduct) {
		this.descriptionProduct = descriptionProduct;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return "\n--------------REGISTRO--------------"
				+"\n#ID: " + id 
				+ "\nNombre: " + nameProduct 
				+ "\nDescripcion: " + descriptionProduct 
				+ "\nPrecio " + price 
				+ "\nFecha de Alta: " + dateFormat.format(dischargeDate)
				+ "\n------------------------------------";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
}