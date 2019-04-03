/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int depth;
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "regions")
	private List<Address> address;

}