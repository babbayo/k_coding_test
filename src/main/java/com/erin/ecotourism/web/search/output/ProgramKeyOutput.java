/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.search.output;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProgramKeyOutput {
	public static final ProgramKeyOutput EMPTY = ProgramKeyOutput.builder().build();
	private String program;
}
