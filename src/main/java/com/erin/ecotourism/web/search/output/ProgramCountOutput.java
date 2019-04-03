/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.search.output;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProgramCountOutput {

	public static final ProgramCountOutput EMPTY = ProgramCountOutput.builder().build();

	private String keyword;
	private long count;
}
