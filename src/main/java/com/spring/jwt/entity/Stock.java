package com.spring.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


@Entity
public class Stock {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer stockId;

    @Column(nullable = false, length = 45)
    private String invoiceNo;

    @Column(length = 45)
    private String invDate;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String address;

    @Column(length = 45)
    private String mobileNo;

    @Column(length = 45)
    private String gstin;

    @Column(length = 45)
    private String spareName;

    @Column(length = 45)
    private String mrp;

    @Column(length = 45)
    private String rate;

    @Column(length = 45)
    private String qty;

    @Column(length = 45)
    private String gst;

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(final Integer stockId) {
        this.stockId = stockId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(final String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(final String invDate) {
        this.invDate = invDate;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(final String gstin) {
        this.gstin = gstin;
    }

    public String getSpareName() {
        return spareName;
    }

    public void setSpareName(final String spareName) {
        this.spareName = spareName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(final String mrp) {
        this.mrp = mrp;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(final String rate) {
        this.rate = rate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(final String qty) {
        this.qty = qty;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(final String gst) {
        this.gst = gst;
    }

}
