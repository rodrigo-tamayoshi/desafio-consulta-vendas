package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleReportDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) {

		DateRange dateRange = parseDates(minDate, maxDate);

		String sellerName = (name == null) ? "" : name;

		return repository.searchReport(dateRange.min(), dateRange.max(), sellerName, pageable).map(SaleReportDTO::new);
	}

	@Transactional(readOnly = true)
	public List<SaleSummaryDTO> searchSummary(String minDate, String maxDate){
		DateRange dateRange = parseDates(minDate, maxDate);
		return repository.searchSummary(dateRange.min(), dateRange.max());
	}

	private record DateRange(LocalDate min, LocalDate max) {}

	private DateRange parseDates(String minDate, String maxDate){

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate max;
		LocalDate min;

		if(maxDate == null || maxDate.isBlank()){
			max = today;
		} else {
			max = LocalDate.parse(maxDate);
		}

		if(minDate == null || minDate.isBlank()){
			min = max.minusYears(1L);
		} else {
			min = LocalDate.parse(minDate);
		}

		return new DateRange(min, max);
	}
}
