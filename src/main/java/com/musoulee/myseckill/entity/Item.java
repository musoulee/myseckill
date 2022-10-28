package com.musoulee.myseckill.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Item {
    @Length(min = 32, max = 32, message = "id必须为32位UUID")
    private String id;
    @NotEmpty(message = "商品名称不能为空")
    private String title;
    @NotNull
    @Min(value = 0, message = "商品价格不能小于0")
    private BigDecimal price;

    private Integer sales;
    @NotEmpty(message = "必须为商品设置一个图片URL")
    private String imageUrl;
    @NotEmpty(message = "商品描述不能为空")
    private String description;
    @NotNull(message = "商品库存不能为空")
    private ItemStock itemStock;
    private Promotion promotion;

    public ItemStock getItemStock() {
        return itemStock;
    }

    public void setItemStock(ItemStock itemStock) {
        this.itemStock = itemStock;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}