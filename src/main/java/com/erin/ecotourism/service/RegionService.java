/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.service;

import org.springframework.stereotype.Service;
import com.erin.ecotourism.domain.region.Region;
import com.erin.ecotourism.domain.region.RegionRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegionService {

	private RegionRepository regionRepository;

	public Region getRegionOrInsert(Region region) {
		return regionRepository.findByName(region.getName()).orElseGet(() -> {
			if (region.getParent() != null) {
				Region parent = getRegionOrInsert(region.getParent());
				Region build = region.toBuilder().parent(parent).build();
				return regionRepository.save(build);
			}
			return regionRepository.save(region);
		});
	}
}
