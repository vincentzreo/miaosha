package com.zzq.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BookModel {
    private Integer id;
    //书名
    @NotBlank(message = "书名不能为空")
    private String title;
    //作者
    @NotBlank(message = "作者不能为空")
    private String auther;
    //出版社
    @NotBlank(message = "出版社不能为空")
    private String press;
    //价格
    @NotNull(message = "价格不能为空")
    @Min(value = 0,message = "价格必须大于0")
    private BigDecimal price;
    //类别
    @NotBlank(message = "图书类别不能为空")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //库存
    @NotNull(message = "库存不能为空")
    private Integer stock;

    //描述
    @NotBlank(message = "描述不能为空")
    private String description;
    //销量
    private Integer sales;
    //图片的url
    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
