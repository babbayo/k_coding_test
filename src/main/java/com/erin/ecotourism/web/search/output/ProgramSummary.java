/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.search.output;

import com.erin.ecotourism.domain.Program;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgramSummary {
	private String prgmName;
	private String theme;

	public static ProgramSummary create(Program program) {
		return new ProgramSummary(program.getName(), program.getTheme());
	}
}

