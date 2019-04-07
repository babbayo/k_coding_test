package com.erin.ecotourism.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressTest {

	@Test
	public void testParse() {
		Address address = new Address("강원도 속초");
		List<String> parse = address.parse();
		assertThat(parse, is(contains("강원도 속초")));
		assertThat(parse, is(hasSize(1)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("강원도").build();
		Region region2 = Region.builder().name("속초").parent(region1).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2)));
	}

	@Test
	public void testParse2() {
		Address address = new Address("강원도 속초, 양양, 고성");
		List<String> parse = address.parse();
		assertThat(parse, is(contains("강원도 속초", "강원도 양양", "강원도 고성")));
		assertThat(parse, is(hasSize(3)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("강원도").build();
		Region region2 = Region.builder().name("속초").parent(region1).build();
		Region region3 = Region.builder().name("양양").parent(region1).build();
		Region region4 = Region.builder().name("고성").parent(region1).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3, region4)));
	}

	@Test
	public void testParse3() {
		Address address = new Address("강원도 원주시 소초면 학곡리 900번지");
		List<String> parse = address.parse();
		assertThat(parse, is(contains("강원도 원주시 소초면 학곡리")));
		assertThat(parse, is(hasSize(1)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("강원도").build();
		Region region2 = Region.builder().name("원주시").parent(region1.toBuilder().parent(null).build()).build();
		Region region3 = Region.builder().name("소초면").parent(region2.toBuilder().parent(null).build()).build();
		Region region4 = Region.builder().name("학곡리").parent(region3.toBuilder().parent(null).build()).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3, region4)));
	}

	@Test
	public void testParse4() {
		Address address = new Address("전라남도 영암군 영암읍 천황사로 280-43 월출산국립공원 사무소");
		List<String> parse = address.parse();
		assertThat(parse, is(contains("전라남도 영암군 영암읍 천황사로")));
		assertThat(parse, is(hasSize(1)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("전라남도").build();
		Region region2 = Region.builder().name("영암군").parent(region1.toBuilder().parent(null).build()).build();
		Region region3 = Region.builder().name("영암읍").parent(region2.toBuilder().parent(null).build()).build();
		Region region4 = Region.builder().name("천황사로").parent(region3.toBuilder().parent(null).build()).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3, region4)));
	}

	@Test
	public void testParse5() {
		Address address = new Address("전라남도 신안군 흑산도 ~ 홍도 일원");
		List<String> parse = address.parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("전라남도 신안군 흑산도", "전라남도 신안군 홍도")));
		assertThat(parse, is(hasSize(2)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("전라남도").build();
		Region region2 = Region.builder().name("신안군").parent(region1.toBuilder().parent(null).build()).build();
		Region region3 = Region.builder().name("흑산도").parent(region2.toBuilder().parent(null).build()).build();
		Region region4 = Region.builder().name("홍도").parent(region2.toBuilder().parent(null).build()).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3, region4)));
	}

	@Test
	public void testParse6() {
		Address address = new Address("경상남도 산청군, 하동군등");
		List<String> parse = address.parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("경상남도 산청군", "경상남도 하동군")));
		assertThat(parse, is(hasSize(2)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("경상남도").build();
		Region region2 = Region.builder().name("산청군").parent(region1.toBuilder().parent(null).build()).build();
		Region region3 = Region.builder().name("하동군").parent(region1.toBuilder().parent(null).build()).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3)));
	}

	@Test
	public void testParse7() {
		Address address = new Address("강원도 원주시 소초면 무쇠점2길");
		List<String> parse = address.parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("강원도 원주시 소초면 무쇠점")));
		assertThat(parse, is(hasSize(1)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("강원도").build();
		Region region2 = Region.builder().name("원주시").parent(region1.toBuilder().parent(null).build()).build();
		Region region3 = Region.builder().name("소초면").parent(region2.toBuilder().parent(null).build()).build();
		Region region4 = Region.builder().name("무쇠점").parent(region3.toBuilder().parent(null).build()).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3, region4)));
	}

	@Test
	public void testParse8() {
		Address address = new Address("강원도 원주시 소초면 학곡리 900번지 ");
		List<String> parse = address.parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("강원도 원주시 소초면 학곡리")));
		assertThat(parse, is(hasSize(1)));

		Set<Region> regions = address.parseByRegion();
		Region region1 = Region.builder().name("강원도").build();
		Region region2 = Region.builder().name("원주시").parent(region1.toBuilder().parent(null).build()).build();
		Region region3 = Region.builder().name("소초면").parent(region2.toBuilder().parent(null).build()).build();
		Region region4 = Region.builder().name("학곡리").parent(region3.toBuilder().parent(null).build()).build();
		assertThat(regions, is(containsInAnyOrder(region1, region2, region3, region4)));
	}

	@Test
	public void testReplaceComma() {
		String actual = new Address().replaceComma("전라남도 신안군 흑산도 ~ 홍도 일원");
		assertThat(actual, is("전라남도 신안군 흑산도,홍도 일원"));
	}

	@Test
	public void testReplaceComma2() {
		String actual = new Address().replaceComma("강원도 속초, 양양, 고성");
		assertThat(actual, is("강원도 속초,양양,고성"));
	}

	@Test
	public void testExcludeNumber() {
		String actual = new Address().excludeNumber("경상북도 영주시 풍기읍 수철리 산 86-51번지");
		assertThat(actual, is("경상북도 영주시 풍기읍 수철리 산"));
	}

	@Test
	public void testExcludeWord() {
		String actual = new Address().excludeWord("경상남도 거제 일원");
		assertThat(actual, is("경상남도 거제"));
	}
}