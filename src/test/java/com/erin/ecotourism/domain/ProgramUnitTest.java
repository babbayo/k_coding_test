package com.erin.ecotourism.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ProgramUnitTest {

	@Test
	public void testDetailCount() {

		int expected = 2;
		Program program = Program.builder()
			.detailIntroduction(" 수려한 경관의 치악산 국립공원은 강원권의 교통요지인 원주시에 인접해 있어 수도권과의 거리가 가깝습니다.\n" +
				" 자연을 제대로 느낄 수 있는 국립공원 에코투어의 다양한 프로그램을 통해 색다른 수학여행을 즐길 수 있습니다.\n" +
				" 문화가 있는 수학여행으로서 박경리 문학관과 고판화 박물관을 통해 다양한 전시물 관람과 판화 체험 등을 할 수 있습니다.\n" +
				" 아름다운 자연과 함께 하고 싶다면 야영을, 체험형 숙박을 원한다면 템플스테이를, 지역주민의 따뜻한 마음을 느끼고 싶다면 민박을 선택 할 수 있는 숙박의 다양화가 구축되어 있습니다.\n" +
				" 자연의 향기를 제대로 느끼고 색다른 체험을 원한다면 '자연과 문화가 있는 치악산 수학여행'에 참여해 보시길 바랍니다.")
			.build();

		int count = program.countKeywordFromDetailIntroduction("문화");

		assertThat(expected, is(count));
	}
}