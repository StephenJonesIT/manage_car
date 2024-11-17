package vn.edu.stu.thanhsang.managecar.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;

public class Product implements Parcelable {
    private String idProduct;
    private String nameProduct;
    private String yearProduct;
    private String priceProduct;
    private byte[] imageProduct;
    private String branchProduct;

    public Product(
            String idProduct,
            String nameProduct,
            String yearProduct,
            String priceProduct,
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

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
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

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "idProduct='" + idProduct + '\'' +
                ", nameProduct='" + nameProduct + '\'' +
                ", yearProduct='" + yearProduct + '\'' +
                ", priceProduct='" + priceProduct + '\'' +
                ", imageProduct=" + Arrays.toString(imageProduct) +
                ", branchProduct='" + branchProduct + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idProduct);
        dest.writeString(nameProduct);
        dest.writeString(yearProduct);
        dest.writeString(priceProduct);
        dest.writeByteArray(imageProduct);
        dest.writeString(branchProduct);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    protected Product(Parcel in) {
        idProduct = in.readString();
        nameProduct = in.readString();
        yearProduct = in.readString();
        priceProduct = in.readString();
        imageProduct = in.createByteArray();
        branchProduct = in.readString();
    }
}
