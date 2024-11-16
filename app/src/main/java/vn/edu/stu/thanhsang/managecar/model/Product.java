package vn.edu.stu.thanhsang.managecar.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String idProduct;
    private String nameProduct;
    private String yearProduct;
    private double priceProduct;
    private byte[] imageProduct;
    private String branchProduct;
    public Product(
            String idProduct,
            String nameProduct,
            String yearProduct,
            double priceProduct,
            byte[] imageProduct,
            String branchProduct
    ){
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.yearProduct = yearProduct;
        this.priceProduct = priceProduct;
        this.imageProduct = imageProduct;
        this.branchProduct = branchProduct;
    }
    public Product(){}

    public String getIdProduct(){
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getYearProduct() {
        return yearProduct;
    }

    public void setYearProduct(String yearProduct) {
        this.yearProduct = yearProduct;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public byte[] getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(byte[] imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getBranchProduct() {
        return branchProduct;
    }

    public void setBranchProduct(String branchProduct) {
        this.branchProduct = branchProduct;
    }
}
