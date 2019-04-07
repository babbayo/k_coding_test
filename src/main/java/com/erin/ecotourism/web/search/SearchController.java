/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.web.search;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.erin.ecotourism.domain.ProgramRepository;
import com.erin.ecotourism.domain.Region;
import com.erin.ecotourism.domain.RegionRepository;
import com.erin.ecotourism.web.search.output.CountByRegion;
import com.erin.ecotourism.web.search.output.ProgramCountOutput;
import com.erin.ecotourism.web.search.output.ProgramKeyOutput;
import com.erin.ecotourism.web.search.output.ProgramSummary;
import com.erin.ecotourism.web.search.output.RegionCountOutput;
import com.erin.ecotourism.web.search.output.RegionOutput;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {

	private ProgramRepository programRepository;
	private RegionRepository regionRepository;

	//	생태 관광지 중에 서비스 지역 컬럼에서 특정 지역에서 진행되는 프로그램명과 테마를 출력하는 API 를 개발하세요.
	@GetMapping("/summary")
	private RegionOutput getByRegionName(@RequestBody SearchDto condition) {
		return Optional.of(condition)
			.map(SearchDto::getRegion)
			.flatMap(regionRepository::findByName)
			.map(region -> {

				List<ProgramSummary> programs = region.getPrograms().stream()
					.map(ProgramSummary::create)
					.collect(Collectors.toList());

				return RegionOutput.builder()
					.region(region.acquireKey())
					.programs(programs)
					.build();
			})
			.orElse(RegionOutput.EMPTY);
	}

	//	생태 정보 데이터에 "프로그램 소개” 컬럼에서 특정 문자열이 포함된 레코드에서 서비스 지역 개수를 세어 출력하는 API 를 개발하세요.
	@GetMapping("/counting/region")
	private RegionCountOutput getByKeyword(@RequestBody SearchDto condition) {
		return Optional.of(condition)
			.map(SearchDto::getKeyword)
			.map(keyword -> {
				Map<String, Long> collect = programRepository.findAllByIntroductionContaining(keyword)
					.stream()
					.flatMap(p -> p.getRegions().stream().map(Region::acquireFullName))
					.collect(Collectors.groupingBy(p -> p, Collectors.counting()));

				Set<CountByRegion> collectForRegion = collect.entrySet().stream()
					.map(set -> new CountByRegion(set.getKey(), set.getValue()))
					.collect(Collectors.toSet());

				return RegionCountOutput.builder()
					.keyword(keyword)
					.programs(collectForRegion)
					.build();
			})
			.orElse(RegionCountOutput.EMPTY);
	}

	//	모든 레코드에 프로그램 상세 정보를 읽어와서 입력 단어의 출현빈도수를 계산하여 출력 하는 API 를 개발하세요.
	@GetMapping("/counting/program")
	private ProgramCountOutput getAllByKeyword(@RequestBody SearchDto condition) {
		return Optional.of(condition)
			.map(SearchDto::getKeyword)
			.map(keyword -> {

				int count = programRepository.findAllByDetailIntroductionContaining(keyword)
					.stream()
					.mapToInt(p -> p.countKeywordFromDetailIntroduction(keyword))
					.sum();

				return ProgramCountOutput.builder()
					.keyword(keyword)
					.count(count)
					.build();
			})
			.orElse(ProgramCountOutput.EMPTY);
	}

	//	생태관광 정보를 기반으로 생태관광 프로그램을 추천해주려고 합니다. 지역명과 관광 키 워드를 입력받아 프로그램 코드를 출력하는 API 를 개발하세요.
	//	단, 프로그램을 추천 시 키워드(keyword)를 텍스트와 비교하는 로직이 필요한 데,
	//	테마 컬럼, 프로그램 소개 컬럼, 그리고 프로그램 상세 소개 컬럼을 모두 사용하시고 가중치를 계산하는 로직이 포함되어야 합니다.,
	@GetMapping("/program")
	private ProgramKeyOutput getProgramByKeyword(@RequestBody SearchDto condition) {
		return Optional.of(condition)
			.flatMap(dto -> regionRepository.findByNameContaining(dto.getRegion()).stream()
				.flatMap(r -> r.getMeAndChild().stream())
				.flatMap(r -> r.getPrograms().stream())
				.max(Comparator.comparingDouble(p -> p.acquireScoreFromKeyword(dto.getKeyword())))
				.map(p -> ProgramKeyOutput.builder().program(p.acquireKey()).build()))
			.orElse(ProgramKeyOutput.EMPTY);
	}
}
