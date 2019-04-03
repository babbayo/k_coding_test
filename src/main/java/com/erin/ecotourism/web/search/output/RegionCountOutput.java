/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.search.output;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegionCountOutput {

	public static final RegionCountOutput EMPTY = RegionCountOutput.builder().build();

	private String keyword;
	private Set<CountByRegion> programs;
}
