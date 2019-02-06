package com.dev.magazina.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "product_transactions")
public class ProductTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private Date date;
    private int invoiceNumber;
    private String type;


    @OneToMany(mappedBy = "productTransaction")
    private List<ProductTransactionUnit> productTransactionUnits = new ArrayList<>();

    //#TODO add agent
    public ProductTransaction(Date date, int invoiceNumber, String type) {
        this.date = date;
        this.invoiceNumber = invoiceNumber;
        this.type = type;
    }

    public ProductTransaction() {
    }

    public int getId() {
        return id;
    }

    public List<ProductTransactionUnit> getProductTransactionUnits() {
        return productTransactionUnits;
    }

    public void setProductTransactionUnits(List<ProductTransactionUnit> productTransactionUnits) {
        this.productTransactionUnits = productTransactionUnits;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = new Date(date);
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addProductTransactionUnit(ProductTransactionUnit ptu) {
        this.productTransactionUnits.add(ptu);
    }
}