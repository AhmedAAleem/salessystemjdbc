package com.sales.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.entity.Invoice;
import com.sales.service.InvoiceService;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc
public class InvoiceControllerTest {

	@MockBean
	private InvoiceService invoiceService;

	@Autowired
	MockMvc mvc;

	@Test
	public void addInvoiceTest() throws Exception {

		Invoice invoice = new Invoice();
		invoice.setAmount(100.0);
		invoice.setInvoiceNo(10);
		invoice.setInvoiceDate(Date.valueOf("2023-05-01"));
		invoice.setReceiver("First Client");

		when(invoiceService.insertInvoice(invoice)).thenReturn(invoice);

		ObjectMapper objectMapper = new ObjectMapper();
		String invoiceJson = objectMapper.writeValueAsString(invoice);

		mvc.perform(post("/invoices/save").contentType(MediaType.APPLICATION_JSON).content(invoiceJson))
				.andExpect(status().isCreated());

	}

	@Test
	public void getInvoicetByIdTest() throws Exception {
		Invoice invoice = new Invoice();
		invoice.setId(10L);
		invoice.setInvoiceNo(10);
		invoice.setReceiver("First Client");
		invoice.setAmount(100.0);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));

		when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(invoice);

		ObjectMapper objectMapper = new ObjectMapper();
		String invoiceJson = objectMapper.writeValueAsString(invoice);

		mvc.perform(get("/invoices/getById/11").contentType(MediaType.APPLICATION_JSON).content(invoiceJson))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void getAllInvoicesTest() throws Exception {

		Invoice invoice = new Invoice();
		invoice.setId(10L);
		invoice.setInvoiceNo(10);
		invoice.setReceiver("First Client");
		invoice.setAmount(100.0);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));

		Invoice invoice2 = new Invoice();
		invoice.setId(20L);
		invoice.setInvoiceNo(20);
		invoice.setReceiver("Second Client");
		invoice.setAmount(200.0);
		invoice.setInvoiceDate(Date.valueOf("2023-05-10"));

		List<Invoice> invoiceList = new ArrayList<>();
		invoiceList.add(invoice);
		invoiceList.add(invoice2);

		when(invoiceService.getAllInvoices()).thenReturn(invoiceList);

		mvc.perform(get("/invoices/getAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

	}

	@Test
	public void updateInvoiceTest() throws Exception {

		Invoice invoice = new Invoice();
		invoice.setId(10L);
		invoice.setInvoiceNo(10);
		invoice.setReceiver("First Client");
		invoice.setAmount(100.0);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));

		when(invoiceService.updateInvoice(any(Invoice.class), eq(invoice.getId()))).thenReturn(invoice);

		ObjectMapper objectMapper = new ObjectMapper();
		String invoiceJson = objectMapper.writeValueAsString(invoice);

		mvc.perform(put("/invoices/update/10").contentType(MediaType.APPLICATION_JSON).content(invoiceJson))
				.andExpect(status().isOk()).andExpect(content().json(invoiceJson));
	}

	@Test
	public void deleteInvoiceByIdTest() throws Exception {
		Long invoiceId = 10L;

		MvcResult result = mvc.perform(delete("/invoices/delete/{id}", invoiceId)).andExpect(status().isOk())
				.andReturn();

		int statusCode = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), statusCode);

		verify(invoiceService).deleteInvoice(invoiceId);
	}

}
