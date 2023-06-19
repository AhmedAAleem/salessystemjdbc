package com.sales.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.entity.Invoice;
import com.sales.service.InvoiceService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTest2 {

	@Autowired
	private MockMvc mvc;

	@MockBean
	InvoiceService invoiceService;

	@Test
	public void addInvoiceTest() throws Exception {
		// Arrange
		Invoice invoice = new Invoice();
		invoice.setAmount(100.0);
		invoice.setInvoiceNo(10);
		invoice.setInvoiceDate(Date.valueOf("2023-05-01"));
		invoice.setReceiver("First Client");

		ObjectMapper objectMapper = new ObjectMapper();
		String inputInJson = objectMapper.writeValueAsString(invoice);

		Mockito.when(invoiceService.insertInvoice(Mockito.any(Invoice.class))).thenReturn(invoice);

		mvc.perform(MockMvcRequestBuilders.post("/invoices/save").contentType(MediaType.APPLICATION_JSON)
				.content(inputInJson).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().json(inputInJson));
	}

//	@Test
//	@Ignore
//	public void addInvoiceTest2() throws Exception {
//
//		Invoice invoice = new Invoice();
//		invoice.setAmount(100.0);
//		invoice.setInvoiceNo(10);
//		invoice.setInvoiceDate(new Date(2023 - 05 - 01));
//		invoice.setReceiver("First Client");
//
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		String inputInJson = objectMapper.writeValueAsString(invoice);
//
////		String inputInJson = this.mapToJson(invoice);
//
//		String URI = "/invoices/addInvoice";
//
//		Mockito.when(invoiceService.insertInvoice(Mockito.any(Invoice.class))).thenReturn(invoice);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
//				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String outputInJson = response.getContentAsString();
//
//		assertThat(outputInJson).isEqualTo(inputInJson);
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//	}

	@Test
	public void getInvoicetByIdAnotherTest() throws Exception {

		Invoice invoice = new Invoice();
		invoice.setId(10L);
		invoice.setAmount(100.0);
		invoice.setInvoiceNo(10);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));
		invoice.setReceiver("First Client");

		Mockito.when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(invoice);

		String URI = "/invoices/getById/10";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		ObjectMapper objectMapper = new ObjectMapper();

		String expectedJson = objectMapper.writeValueAsString(invoice);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	public void getInvoicetByIdTest() throws Exception {
		Invoice invoice = new Invoice();
		invoice.setId(10L);
		invoice.setInvoiceNo(10);
		invoice.setReceiver("First Client");
		invoice.setAmount(100.0);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));

		Mockito.when(invoiceService.getInvoiceById(invoice.getId())).thenReturn(invoice);

		String URI = "/invoices/getById/10";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andReturn();

		String actualJson = result.getResponse().getContentAsString();

		ObjectMapper objectMapper = new ObjectMapper();
		String expectedJson = objectMapper.writeValueAsString(invoice);

		assertThat(actualJson).isEqualTo(expectedJson);
	}

	@Test
	public void getAllInvoicesTest() throws Exception {

		Invoice invoice = new Invoice();
		invoice.setAmount(100.0);
		invoice.setInvoiceNo(10);
		invoice.setInvoiceDate(Date.valueOf("2023-05-05"));
		invoice.setReceiver("First Client");

		Invoice invoice2 = new Invoice();
		invoice2.setAmount(200.0);
		invoice2.setInvoiceNo(20);
		invoice2.setInvoiceDate(Date.valueOf("2023-05-05"));
		invoice2.setReceiver("Second Client");

		List<Invoice> invoiceList = new ArrayList<>();
		invoiceList.add(invoice);
		invoiceList.add(invoice2);

		Mockito.when(invoiceService.getAllInvoices()).thenReturn(invoiceList);

		String URI = "/invoices/getAll";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		ObjectMapper objectMapper = new ObjectMapper();

		String expectedJson = objectMapper.writeValueAsString(invoiceList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);

	}

	@Test
	public void deleteInvoiceByIdTest() throws Exception {

		Long invoiceId = 1L;

		mvc.perform(MockMvcRequestBuilders.delete("/invoices/delete/{id}", invoiceId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Mockito.verify(invoiceService, Mockito.times(1)).deleteInvoice(invoiceId);
	}

	@Test
	public void updateInvoiceTest() throws Exception {

		Long invoiceId = 1L;

		Invoice invoice = new Invoice();
		invoice.setAmount(100.0);
		invoice.setInvoiceNo(10);
		invoice.setInvoiceDate(Date.valueOf("2023-05-01"));
		invoice.setReceiver("existing data");

		Invoice updatedInvoice = new Invoice();
		updatedInvoice.setAmount(200.0);
		updatedInvoice.setInvoiceNo(20);
		updatedInvoice.setInvoiceDate(Date.valueOf("2023-05-05"));
		updatedInvoice.setReceiver("updated data");

		ObjectMapper objectMapper = new ObjectMapper();
		String inputInJson = objectMapper.writeValueAsString(updatedInvoice);
		System.out.println("test ------------ > " + inputInJson);

		Mockito.when(invoiceService.updateInvoice(Mockito.any(Invoice.class), Mockito.eq(invoiceId)))
				.thenReturn(updatedInvoice);

		mvc.perform(MockMvcRequestBuilders.put("/invoices/update/{id}", invoiceId)
				.contentType(MediaType.APPLICATION_JSON).content(inputInJson).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(mvcResult -> {

					String jsonResponse = mvcResult.getResponse().getContentAsString();
					JSONAssert.assertEquals(inputInJson, jsonResponse, true);
				});

	}
}
