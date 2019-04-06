/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import static java.util.Collections.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Address {

	private String value;

	public List<String> parse() {
		return Optional.ofNullable(this.value)
			.filter(StringUtils::isNotBlank)
			.map(this::excludeNumber)
			.map(this::replaceComma)
			.map(raw -> {
				if (raw.contains(" ")) {
					return Arrays.stream(raw.split(" "))
						.flatMap(s -> s.contains(",") ? Arrays.stream(s.split(",")) : Stream.of(s))
						.map(s -> s.replaceAll("일대", ""))
						.map(s -> s.replaceAll("일원", ""))
						.filter(s -> !StringUtils.equalsAny(s, " ", ",", ""))
						.filter(s -> !StringUtils.endsWithAny(s, "공원", "사무소", "산"))
						.collect(Collectors.toList());
				}
				return singletonList(raw);
			})
			.orElse(Collections.emptyList());
	}

	private String replaceComma(String raw) {
		return Stream.of("~", "및", ",")
			.filter(raw::contains)
			.findFirst()
			.map(s -> raw.replaceAll(" " + s, ",").replaceAll(s + " ", ",").replaceAll(s, ","))
			.orElse(raw);

	}

	private String excludeNumber(String raw) {
		StringBuilder sb = new StringBuilder();
		for (char c : raw.toCharArray()) {
			if (Character.isDigit(c)) {
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
