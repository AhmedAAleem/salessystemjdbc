package com.sales.jdbc.model;

import java.util.Date;

public class Invoice {


    private int id;
    private int invoiceNo;
    private String receiver;
    private Double amount;
    private Date invoiceDate;
    
    public Invoice() {}
    
	public Invoice(int invoiceNo, String receiver, Double amount, Date invoiceDate) {
		this.invoiceNo = invoiceNo;
		this.receiver = receiver;
		this.amount = amount;
		this.invoiceDate = invoiceDate;
	}


	public Invoice(int id, int invoiceNo, String receiver, Double amount, Date invoiceDate) {
		this.id = id;
		this.invoiceNo = invoiceNo;
		this.receiver = receiver;
		this.amount = amount;
		this.invoiceDate = invoiceDate;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public Date setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
		return invoiceDate;
	}
    
  
}
