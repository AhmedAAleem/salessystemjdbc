//package com.sales.service;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.util.Date;
//import java.util.Optional;
//import javax.transaction.Transactional;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import com.sales.Repo.InvoiceRepo;
//import com.sales.entity.Invoice;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class InvoiceServiceTest {
//
//	@Autowired
//	private InvoiceService invoiceService;
//
//	@Autowired
//	private InvoiceRepo invoiceRepo;
//
//	@Test
//	@Transactional
//	public void testGetInvoice() {
//		Invoice invoice = new Invoice();
//		invoice.setInvoiceNo(10);
//		invoice.setInvoiceDate(new Date(2023 - 05 - 01));
//		invoice.setReceiver("First Client");
//		invoice.setAmount(100.0);
//		
//		invoiceRepo.save(invoice);
//
//		Invoice result = invoiceService.getInvoiceById(invoice.getId());
//		
//		assertNotNull(invoice.getId());
//		assertEquals(invoice.getInvoiceNo(), result.getInvoiceNo());
//		assertEquals(invoice.getInvoiceDate(), result.getInvoiceDate());
//		assertEquals(invoice.getReceiver(), result.getReceiver());
//		assertEquals(invoice.getAmount(), result.getAmount());
//
//	}
//
//	@Test
//	@Transactional
//	public void testInsertInvoice() {
//		Invoice invoice = new Invoice();
//		invoice.setInvoiceNo(10);
//		invoice.setInvoiceDate(new Date(2023 - 05 - 01));
//		invoice.setId(1L);
//		invoice.setReceiver("Second Client");
//		invoice.setAmount(100.0);
//		invoiceRepo.save(invoice);
//
//		Invoice savedInvoice = invoiceService.insertInvoice(invoice);
//		
//		assertNotNull(invoice.getId());
//		assertEquals(invoice.getInvoiceNo(), savedInvoice.getInvoiceNo());
//		assertEquals(invoice.getInvoiceDate(), savedInvoice.getInvoiceDate());
//		assertEquals(invoice.getReceiver(), savedInvoice.getReceiver());
//		assertEquals(invoice.getAmount(), savedInvoice.getAmount());
//
//	}
//
//	@Test
//	@Transactional
//	public void testGetAllInvoice() {
//
//		int count = invoiceService.getAllInvoices().size();
//		Invoice invoice = new Invoice();
//		invoice.setInvoiceNo(10);
//		invoice.setInvoiceDate(new Date(2023 - 05 - 01));
//		invoice.setId(1L);
//		invoice.setReceiver("Second Client");
//		invoice.setAmount(100.0);
//		invoiceRepo.save(invoice);
//
//		int newCount = invoiceService.getAllInvoices().size();
//
//		assertTrue(newCount > count);
//
//	}
//
//
//	@Test
//	@Transactional
//	public void testDeleteInvoiceById() {
//
//		Invoice invoice = new Invoice();
//		invoice.setInvoiceNo(123);
//		invoice.setAmount(1000.0);
//		invoice.setInvoiceDate(new Date(2023 - 05 - 02));
//		invoice.setReceiver("Client");
//		invoice.setId(50L);
//
//		Invoice savedInvoice = invoiceRepo.save(invoice);
//		Long invoiceId = savedInvoice.getId();
//
//		invoiceService.deleteInvoice(invoiceId);
//
//		Optional<Invoice> deletedInvoice = invoiceRepo.findById(invoiceId);
//		assertFalse(deletedInvoice.isPresent());
//	}
//	
//	@Test
//    @Transactional
//    public void testUpdateInvoice2() {
//        Invoice invoice = new Invoice();
//        invoice.setInvoiceNo(001);
//        invoice.setAmount(1000.00);
//        invoice.setInvoiceDate(new Date(2023-05-05));
//        invoice.setReceiver("test");
//
//        invoiceRepo.save(invoice);
//
//        Invoice updatedInvoice = new Invoice();
//        updatedInvoice.setInvoiceNo(2);
//        updatedInvoice.setAmount(2000.0);
//        updatedInvoice.setInvoiceDate(new Date(2023-05-07));
//        updatedInvoice.setReceiver("Jane Smith");
//
//        Invoice result = invoiceService.updateInvoice(updatedInvoice, invoice.getId());
//
//        Invoice savedInvoice = invoiceRepo.getById(invoice.getId());
//        
//		assertNotNull(invoice.getId());                          
//        assertEquals(updatedInvoice.getInvoiceNo(), savedInvoice.getInvoiceNo());
//        assertEquals(updatedInvoice.getAmount(), savedInvoice.getAmount());
//        assertEquals(updatedInvoice.getInvoiceDate(), savedInvoice.getInvoiceDate());
//        assertEquals(updatedInvoice.getReceiver(), savedInvoice.getReceiver());
//
//        assertEquals(savedInvoice, result);
//    }
//}
//
//
