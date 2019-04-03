/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

	private String address;

	private String introduction;

	@Lob
	private String detailIntroduction;

	public Program(String name) {
		this.name = name;
	}

	//	테마 컬럼, 프로그램 소개 컬럼, 그리고 프로그램 상세 소개 컬럼을 모두 사용하시고 가중치를 계산하는 로직이 포함되어야 합니다.
	public int getScoreFromKeyword(String keyword) {
		return countKeywordFromTheme(keyword) +
			countKeywordFromIntroduction(keyword) +
			countKeywordFromDetailIntroduction(keyword);
	}

	int countKeywordFromTheme(String keyword) {
		return theme.contains(keyword) ? 1 : 0;
	}

	int countKeywordFromIntroduction(String keyword) {
		return introduction.contains(keyword) ? 1 : 0;
	}

	public int countKeywordFromDetailIntroduction(String keyword) {
		return detailIntroduction.contains(keyword) ? 1 : 0;
	}
}
