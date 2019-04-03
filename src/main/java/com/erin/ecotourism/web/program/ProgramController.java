/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.program;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.erin.ecotourism.domain.Program;
import com.erin.ecotourism.domain.ProgramRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/programs")
public class ProgramController {

	private ProgramRepository programRepository;

	@GetMapping("/{id}")
	private Program getById(@PathVariable Long id) {
		return programRepository.findById(id).orElse(null);
	}

	@GetMapping
	private Iterable<Program> getAll() {
		return programRepository.findAll();
	}

	@PostMapping
	private Program save(@RequestBody Program program) {
		return programRepository.save(program);
	}

	@PutMapping
	private Program update(@RequestBody Program program) {
		return programRepository.save(program);
	}

}
