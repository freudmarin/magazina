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
    private String invoiceNumber;
    private String type;
    private String agentName;
    @OneToOne
    private Agent agent;


    @OneToMany(mappedBy = "productTransaction")
    private List<ProductTransactionUnit> productTransactionUnits = new ArrayList<>();

    @ManyToOne
    Warehouse warehouse;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getAgentName() {
        return this.agentName;
    }

    public void setAgentName() {
        this.agentName = this.agent.getBusinessName();
    }

    //#TODO add agent
    public ProductTransaction(Date date, String invoiceNumber, String type) {
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
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
