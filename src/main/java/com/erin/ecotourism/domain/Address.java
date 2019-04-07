/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain;

import static java.util.Collections.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

	private static final String SPACE = " ";
	private static final String COMMA = ",";

	private String value;

	public Set<Region> parseByRegion() {
		List<String> addressList = parse();
		Set<Region> regions = new HashSet<>();

		for (String address : addressList) {
			if (address.contains(SPACE)) {
				String[] wordList = address.split(SPACE);
				Region prev = null;
				Region curr = null;
				for (String word : wordList) {
					curr = Region.builder().name(word).parent(prev).build();
					prev = curr;
				}
				regions.add(curr);
			} else {
				regions.add(Region.builder().name(address).build());
			}
		}
		return regions;
	}

	List<String> parse() {
		return Optional.ofNullable(this.value)
			.filter(StringUtils::isNotBlank)
			.map(this::replaceComma)
			.map(this::excludeNumber)
			.map(this::excludeWord)
			.map(StringUtils::trim)
			.map(raw -> {
				if (raw.contains(COMMA)) {
					String[] split = raw.split(SPACE);
					List<String> list = new LinkedList<>();
					for (int i = 0; i < split.length; i++) {
						String currWord = split[i];
						if (currWord.contains(COMMA)) {
							String prevWord =
								i == 0 ? "" : Arrays.stream(split, 0, i).collect(Collectors.joining(SPACE));
							Arrays.stream(currWord.split(COMMA))
								.forEach(address -> list.add(prevWord + SPACE + address));
							break;
						}
					}
					return list;
				}
				return singletonList(raw);
			})
			.orElse(Collections.emptyList());
	}

	String replaceComma(String raw) {
		return Stream.of("~", "및", COMMA)
			.filter(raw::contains)
			.findFirst()
			.map(s -> raw.replaceAll("\\s" + s, s).replaceAll(s + "\\s", s).replaceAll(s, COMMA))
			.orElse(raw);
	}

	String excludeNumber(String raw) {
		StringBuilder sb = new StringBuilder();
		for (char c : raw.toCharArray()) {
			if (Character.isDigit(c)) {
				break;
			}
			sb.append(c);
		}
		return StringUtils.trim(sb.toString());
	}

	String excludeWord(String s) {
		if (s.contains(SPACE)) {
			String[] split = s.split(SPACE);
			for (int i = 0; i < split.length; i++) {
				if (StringUtils.endsWithAny(split[i], "일대", "일원", "등")) {
					String prevWord = i == 0 ? "" : Arrays.stream(split, 0, i).collect(Collectors.joining(SPACE));
					return StringUtils.trim(prevWord + SPACE + split[i].replaceAll("일대|일원|등", ""));
				}

				if (StringUtils.endsWithAny(split[i], "공원", "사무소", "산")) {
					return i == 0 ? "" : Arrays.stream(split, 0, i).collect(Collectors.joining(SPACE));
				}
			}
		}
		return s;
	}
}
