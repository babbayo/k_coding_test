/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.erin.ecotourism.domain.Address;
import com.erin.ecotourism.domain.Program;
import com.erin.ecotourism.domain.ProgramRepository;
import com.erin.ecotourism.domain.Region;
import com.erin.ecotourism.domain.RegionRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class DataInitializer {

	private ProgramRepository programRepository;
	private RegionRepository regionRepository;

	@PostConstruct
	public void init() throws IOException {
		List<Program> programRaw = getSampleProgramList();

		List<Region> regionRaw = programRaw.stream()
			.flatMap(p -> p.acquireRegions().stream())
			.distinct().map(s -> new Region(null, s))
			.collect(Collectors.toList());

		Iterable<Region> regions = regionRepository.saveAll(regionRaw);
		log.info("{} region save (e.g. {})", regionRaw.size(), regions.iterator().next());

		Iterable<Program> programs = programRepository.saveAll(programRaw);
		log.info("{} program save (e.g. {})", programRaw.size(), programs.iterator().next());
	}

	private List<Program> getSampleProgramList() throws IOException {
		return getSampleList()
			.stream()
			.map(x -> Program.builder()
				.number(Integer.valueOf(x[0]))
				.name(revise(x[1]))
				.theme(revise(x[2]))
				.address(new Address(revise(x[3])))
				.introduction(revise(x[4]))
				.detailIntroduction(revise(x[5]))
				.build()).collect(Collectors.toList());
	}

	private String revise(String text) {
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		String trim = StringUtils.trim(text);
		if (StringUtils.endsWith(trim, ",")) {
			return trim.substring(0, trim.length() - 1);
		}
		return trim;
	}

	List<String[]> getSampleList() throws IOException {
		String path = "data.csv";
		ClassPathResource resource = new ClassPathResource(path);
		CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(resource.getInputStream()))
			.withSkipLines(1)
			.build();

		return csvReader.readAll();
	}

}
