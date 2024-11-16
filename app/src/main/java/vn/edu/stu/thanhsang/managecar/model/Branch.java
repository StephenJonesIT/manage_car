package vn.edu.stu.thanhsang.managecar.model;

import java.io.Serializable;
import java.util.Arrays;

public class Branch implements Serializable {
    private String id;
    private String name;
    private String base;
    private byte[] image;

    public Branch(String id, String name, String base, byte[] image) {
        this.id = id;
        this.name = name;
        this.base = base;
        this.image = image;
    }

    public Branch() {
    }

    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", base='" + base + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}

