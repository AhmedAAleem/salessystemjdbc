package com.sales.jdbc.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.jdbc.model.Invoice;
import com.sales.jdbc.service.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/invoices")
	public ResponseEntity<?> getInvoice() throws Exception {
		try {
			List<Invoice> invoices = invoiceService.getInvoices();
			return ResponseEntity.ok(invoices);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getInvoiceById(@PathVariable("id") int id) {
		try {
			Invoice invoice = invoiceService.getInvoiceById(id);
			return ResponseEntity.ok(invoice);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateInvoice(@RequestBody Invoice invoice, @PathVariable("id") int id) {
		try {
			invoiceService.updateInvoice(invoice, id);
			return ResponseEntity.ok("Invoice Updated Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insertInvoice(@RequestBody Invoice invoice) {
		try {
			invoiceService.insertInvoice(invoice);
			return ResponseEntity.ok(invoice);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInvoice(@PathVariable("id") int id) throws Exception {
		try {
			String result = invoiceService.deleteInvoiceById(id);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Error occurred while deleting invoice: " + e.getMessage());
		}
	}

}
