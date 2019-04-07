/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "child")
@Getter
@Entity
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String name;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Region parent;

	@JsonIgnore
	@OneToMany(mappedBy = "parent")
	private List<Region> child = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "regions")
	private List<Program> programs;

	public String acquireKey() {
		return "reg" + id;
	}

	public String acquireFullName() {
		return Optional.ofNullable(parent)
			.map(Region::acquireFullName)
			.map(p -> p + " " + name)
			.orElse(name);
	}

	public List<Region> getMeAndChild() {
		if (CollectionUtils.isEmpty(child)) {
			return Collections.singletonList(this);
		}
		return Stream.concat(child.stream().flatMap(c -> c.getMeAndChild().stream()), Stream.of(this))
			.collect(Collectors.toList());
	}
}
