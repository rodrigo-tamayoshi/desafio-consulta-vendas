package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<List<SaleReportDTO>> getReport(@RequestParam(required = false) String minDate,
									   @RequestParam(required = false) String maxDate,
									   @RequestParam(required = false) String name) {

        List<SaleReportDTO> saleReportDTOList = service.searchReport(minDate, maxDate, name);

		return ResponseEntity.ok(saleReportDTOList);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(@RequestParam(required = false) String minDate,
														   @RequestParam(required = false) String maxDate) {

		List<SaleSummaryDTO> saleSummaryDTOList = service.searchSummary(minDate, maxDate);
		return ResponseEntity.ok(saleSummaryDTOList);
	}
}
