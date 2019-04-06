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
		assertThat(parse, is(contains("강원도", "속초")));
		assertThat(parse, is(hasSize(2)));
	}

	@Test
	public void testParse2() {
		List<String> parse = new Address("강원도 속초, 양양, 고성").parse();
		assertThat(parse, is(contains("강원도", "속초", "양양", "고성")));
		assertThat(parse, is(hasSize(4)));
	}

	@Test
	public void testParse3() {
		List<String> parse = new Address("강원도 원주시 소초면 학곡리 900번지").parse();
		assertThat(parse, is(contains("강원도", "원주시", "소초면", "학곡리")));
		assertThat(parse, is(hasSize(4)));
	}

	@Test
	public void testParse4() {
		List<String> parse = new Address("전라남도 영암군 영암읍 천황사로 280-43 월출산국립공원 사무소").parse();
		assertThat(parse, is(contains("전라남도", "영암군", "영암읍", "천황사로")));
		assertThat(parse, is(hasSize(4)));
	}

	@Test
	public void testParse5() {
		List<String> parse = new Address("전라남도 신안군 흑산도 ~ 홍도 일원").parse();
		log.debug("{}", parse);
		assertThat(parse, is(contains("전라남도", "신안군", "흑산도", "홍도")));
		assertThat(parse, is(hasSize(4)));
	}
}