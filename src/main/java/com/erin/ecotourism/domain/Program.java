/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
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
	public int acquireScoreFromKeyword(String keyword) {
		return countKeywordFromTheme(keyword) +
			countKeywordFromIntroduction(keyword) +
			countKeywordFromDetailIntroduction(keyword);
	}

	int countKeywordFromTheme(String keyword) {
		return StringUtils.countMatches(theme, keyword);
	}

	int countKeywordFromIntroduction(String keyword) {
		return StringUtils.countMatches(introduction, keyword);
	}

	public int countKeywordFromDetailIntroduction(String keyword) {
		return StringUtils.countMatches(detailIntroduction, keyword);
	}

	public List<String> acquireRegions() {
		return address.parse();
	}

	@JsonGetter("address")
	public String getAddress() {
		return address.getValue();
	}

	public String acquireKey() {
		return "prg" + id;
	}
}
