/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain.program;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.StringUtils;
import com.erin.ecotourism.domain.region.Region;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "regions")
@Getter
@Entity
@Builder
public class Program {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer number;
	private String name;

	private String theme;

	private Address address;

	private String introduction;

	@Lob
	private String detailIntroduction;

	@Setter
	@ManyToMany
	@JoinTable(name = "PROGRAM_REGION",
		joinColumns = @JoinColumn(name = "program_id"),
		inverseJoinColumns = @JoinColumn(name = "region_id"))
	private List<Region> regions;

	public Program(String name) {
		this.name = name;
	}

	//	테마 컬럼, 프로그램 소개 컬럼, 그리고 프로그램 상세 소개 컬럼을 모두 사용하시고 가중치를 계산하는 로직이 포함되어야 합니다.
	public double acquireScoreFromKeyword(String keyword) {

		return scoreKeywordFromTheme(keyword) +
			scoreKeywordFromIntroduction(keyword) +
			scoreKeywordFromDetailIntroduction(keyword);
	}

	double scoreKeywordFromTheme(String keyword) {
		return Optional.ofNullable(theme)
			.map(el -> {
				int splitCount = (el.contains(",") ? StringUtils.countMatches(el, ",") : 0) + 1;
				return ((double)StringUtils.countMatches(el, keyword)) / splitCount;
			}).orElse(0.0);
	}

	double scoreKeywordFromIntroduction(String keyword) {
		return Optional.ofNullable(introduction)
			.map(el -> {
				int splitCount = (el.contains(", ") ? StringUtils.countMatches(el, ", ") : 0) + 1;
				return ((double)StringUtils.countMatches(el, keyword)) / splitCount;
			}).orElse(0.0);
	}

	double scoreKeywordFromDetailIntroduction(String keyword) {
		return Optional.ofNullable(detailIntroduction)
			.map(el -> {
				int splitCount = (el.contains("\n") ? StringUtils.countMatches(el, "\n") : 0) + 1;
				return ((double)StringUtils.countMatches(el, keyword)) / splitCount;
			}).orElse(0.0);
	}

	public int countKeywordFromDetailIntroduction(String keyword) {
		return StringUtils.countMatches(detailIntroduction, keyword);
	}

	public Set<Region> acquireRegions() {
		return address.parseByRegion();
	}

	@JsonGetter("address")
	public String getAddress() {
		return Optional.ofNullable(address).map(Address::getValue).orElse(null);
	}

	public String acquireKey() {
		return "prg" + id;
	}
}
