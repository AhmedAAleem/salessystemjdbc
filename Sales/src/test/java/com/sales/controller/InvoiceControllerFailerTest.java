package com.sales.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.Repo.InvoiceRepo;
import com.sales.entity.Invoice;
import com.sales.service.InvoiceService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerFailerTest {

	@Autowired
	private InvoiceService invoiceService;

	private InvoiceRepo invoiceRepo;

	@Autowired
	MockMvc mvc;

	@Test
	public void addInvoiceTest_FailAssertion() throws Exception {
		Invoice invoice = new Invoice();
		invoice.setId(10L); // Use the existing ID
		invoice.setAmount(100.0);
		invoice.setInvoiceNo(10);
		invoice.setInvoiceDate(Date.valueOf("2023-05-01"));
		invoice.setReceiver("First Client");

		ObjectMapper objectMapper = new ObjectMapper();
		String invoiceJson = objectMapper.writeValueAsString(invoice);

		// Use assertThrows to expect the NestedServletException and get the actual
		// exception
		Exception exception = assertThrows(NestedServletException.class, () -> {
			mvc.perform(post("/invoices/save").contentType(MediaType.APPLICATION_JSON).content(invoiceJson))
					.andExpect(status().isCreated()).andReturn(); // Expect a 201 status code
		});
		Throwable result = exception.getCause();
		assertTrue(result instanceof IllegalArgumentException);
		assertEquals("Invoice ID already exists", result.getMessage());
	}

	@Test
	public void getInvoicetByIdTest() throws Exception {

		Invoice invoice = new Invoice();
		invoice.setId(10L);
		invoice.setInvoiceNo(10);
		invoice.setReceiver("First Client");
		invoice.setAmount(100.0);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));
		ObjectMapper objectMapper = new ObjectMapper();
		String invoiceJson = objectMapper.writeValueAsString(invoice);

		mvc.perform(get("/invoices/getById/10").contentType(MediaType.APPLICATION_JSON).content(invoiceJson))
				.andExpect(status().isOk()).andExpect(result -> {
					assertAll(() -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()));

				});
	}

}
