/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "address_region",
		joinColumns = @JoinColumn(name = "address_id"),
		inverseJoinColumns = @JoinColumn(name = "region_id"))
	private List<Region> regions;

	public static Address build(String name) {

		// TODO 좀더 구체적 으로 테스트해서 세부 사항 구현 필요
		String[] split = StringUtils.split(name, " ");
		List<Region> regions = IntStream.range(0, split.length)
			.mapToObj(idx -> Region.builder().depth(idx).name(split[idx]).build())
			.collect(Collectors.toList());

		return Address.builder()
			.name(name)
			.regions(regions)
			.build();
	}
}
