package com.erin.ecotourism.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressTest {

	@Test
	public void testParse() {
		List<String> parse = new Address("강원도 속초").parse();
		assertThat(parse, is(contains("강원도 속초")));
		assertThat(parse, is(hasSize(1)));
	}

	@Test
	public void testParse2() {
		List<String> parse = new Address("강원도 속초, 양양, 고성").parse();
		assertThat(parse, is(contains("강원도 속초", "강원도 양양", "강원도 고성")));
		assertThat(parse, is(hasSize(3)));
	}

	@Test
	public void testParse3() {
		List<String> parse = new Address("강원도 원주시 소초면 학곡리 900번지").parse();
		assertThat(parse, is(contains("강원도 원주시 소초면 학곡리")));
		assertThat(parse, is(hasSize(1)));
	}

	@Test
	public void testParse4() {
		List<String> parse = new Address("전라남도 영암군 영암읍 천황사로 280-43 월출산국립공원 사무소").parse();
		assertThat(parse, is(contains("전라남도 영암군 영암읍 천황사로")));
		assertThat(parse, is(hasSize(1)));
	}

	@Test
	public void testParse5() {
		List<String> parse = new Address("전라남도 신안군 흑산도 ~ 홍도 일원").parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("전라남도 신안군 흑산도", "전라남도 신안군 홍도")));
		assertThat(parse, is(hasSize(2)));
	}

	@Test
	public void testParse6() {
		List<String> parse = new Address("경상남도 산청군, 하동군등").parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("경상남도 산청군","경상남도 하동군")));
		assertThat(parse, is(hasSize(2)));
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