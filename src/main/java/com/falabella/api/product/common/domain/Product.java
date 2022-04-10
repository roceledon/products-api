package com.falabella.api.product.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;
import java.util.List;

@Document("product")
public class Product {
    @Id
    @JsonIgnore
    private String id;
    private String sku;
    private String name;
    private String brand;
    private String size;
    private Float price;
    private URL principalImage;
    private List<URL> otherImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public URL getPrincipalImage() {
        return principalImage;
    }

    public void setPrincipalImage(URL principalImage) {
        this.principalImage = principalImage;
    }

    public List<URL> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<URL> otherImages) {
        this.otherImages = otherImages;
    }
}
