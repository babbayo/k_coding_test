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

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.erin.ecotourism.domain.Program;
import com.erin.ecotourism.domain.ProgramRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class DataInitializer {

	private ProgramRepository programRepository;

	@PostConstruct
	public void init() throws IOException {
		List<Program> programRaw = getSampleProgramList();

		Iterable<Program> programs = programRepository.saveAll(programRaw);
		log.info("{} program save (e.g. {})", programRaw.size(), programs.iterator().next());
	}

	private List<Program> getSampleProgramList() throws IOException {
		return getSampleList()
			.stream()
			.map(x -> Program.builder()
				.number(Integer.valueOf(x[0]))
				.name(x[1])
				.theme(x[2])
				.address(x[3])
				.introduction(x[4])
				.detailIntroduction(x[5])
				.build()).collect(Collectors.toList());
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
