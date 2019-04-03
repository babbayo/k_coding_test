/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.search.output;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegionOutput {

	public static final RegionOutput EMPTY = RegionOutput.builder().build();

	private String region;
	private List<ProgramSummary> programs;
}
