package com.erin.ecotourism.domain.program;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ProgramUnitTest {

	@Test
	public void testDetailCount() {

		Program program = Program.builder()
			.detailIntroduction(" 수려한 경관의 치악산 국립공원은 강원권의 교통요지인 원주시에 인접해 있어 수도권과의 거리가 가깝습니다.\n" +
				" 자연을 제대로 느낄 수 있는 국립공원 에코투어의 다양한 프로그램을 통해 색다른 수학여행을 즐길 수 있습니다.\n" +
				" 문화가 있는 수학여행으로서 박경리 문학관과 고판화 박물관을 통해 다양한 전시물 관람과 판화 체험 등을 할 수 있습니다.\n" +
				" 아름다운 자연과 함께 하고 싶다면 야영을, 체험형 숙박을 원한다면 템플스테이를, 지역주민의 따뜻한 마음을 느끼고 싶다면 민박을 선택 할 수 있는 숙박의 다양화가 구축되어 있습니다.\n" +
				" 자연의 향기를 제대로 느끼고 색다른 체험을 원한다면 '자연과 문화가 있는 치악산 수학여행'에 참여해 보시길 바랍니다.")
			.build();

		int count = program.countKeywordFromDetailIntroduction("문화");

		assertThat(count, is(2));
	}

	@Test
	public void testDetailWeight() {

		String keyword = "문화";
		Program program = Program.builder()
			.theme("생태문화")
			.introduction("오대산국립공원 힐링캠프")
			.detailIntroduction(" 수려한 경관의 치악산 국립공원은 강원권의 교통요지인 원주시에 인접해 있어 수도권과의 거리가 가깝습니다.\n" +
				" 자연을 제대로 느낄 수 있는 국립공원 에코투어의 다양한 프로그램을 통해 색다른 수학여행을 즐길 수 있습니다.\n" +
				" 문화가 있는 수학여행으로서 박경리 문학관과 고판화 박물관을 통해 다양한 전시물 관람과 판화 체험 등을 할 수 있습니다.\n" +
				" 아름다운 자연과 함께 하고 싶다면 야영을, 체험형 숙박을 원한다면 템플스테이를, 지역주민의 따뜻한 마음을 느끼고 싶다면 민박을 선택 할 수 있는 숙박의 다양화가 구축되어 있습니다.\n" +
				" 자연의 향기를 제대로 느끼고 색다른 체험을 원한다면 '자연과 문화가 있는 치악산 수학여행'에 참여해 보시길 바랍니다.")
			.build();

		assertThat(program.countKeywordFromDetailIntroduction(keyword), is(2));
		assertThat(program.scoreKeywordFromTheme(keyword), is(1.0));
		assertThat(program.scoreKeywordFromIntroduction(keyword), is(0.0));
		assertThat(program.scoreKeywordFromDetailIntroduction(keyword), is(0.4));
		assertThat(program.acquireScoreFromKeyword(keyword), is(1.4));
	}

	@Test
	public void testDetailWeight2() {

		String keyword = "국립공원";
		Program program = Program.builder()
			.theme("아동·청소년 체험학습")
			.introduction("1일차: 어름치마을 인근 탐방, 2일차: 오대산국립공원 탐방, 3일차: 봉평마을 탐방")
			.detailIntroduction("1일차: 백룡동굴, 민물고기생태관 체험, 칠족령 트레킹\n" +
				" 2일차: 대관령 양떼목장, 신재생 에너지전시관, 오대산국립공원\n" +
				" 3일차: 이효석 문학관, 봉평마을")
			.build();

		assertThat(program.countKeywordFromDetailIntroduction(keyword), is(1));
		assertThat(program.scoreKeywordFromTheme(keyword), is(0.0));
		assertThat(program.scoreKeywordFromIntroduction(keyword), is(allOf(greaterThan(0.3), lessThan(0.4))));
		assertThat(program.scoreKeywordFromDetailIntroduction(keyword), is(allOf(greaterThan(0.3), lessThan(0.4))));
		assertThat(program.acquireScoreFromKeyword(keyword), is(allOf(greaterThan(0.6), lessThan(1.0))));
	}

	@Test
	public void testDetailWeight3() {

		String keyword = "국립공원";
		Program program = Program.builder()
			.theme("자연생태")
			.introduction("소금강, 삼산테마파크")
			.detailIntroduction("오대산국립공원의 대표 경관인 소금강지구에서 대자연의 아름다움을 제대로 즐긴다.")
			.build();

		assertThat(program.countKeywordFromDetailIntroduction(keyword), is(1));
		assertThat(program.scoreKeywordFromTheme(keyword), is(0.0));
		assertThat(program.scoreKeywordFromIntroduction(keyword), is(0.0));
		assertThat(program.scoreKeywordFromDetailIntroduction(keyword), is(1.0));
		assertThat(program.acquireScoreFromKeyword(keyword), is(1.0));
	}

	@Test
	public void testDetailWeight4() {

		String keyword = "생태체험";
		Program program = Program.builder()
			.theme("건강나누리캠프")
			.introduction("남해 편백 숲 속에서 진행되는 아토피 천식 극복캠프(남해금산, 편백휴양림, 나비생태공원)")
			.detailIntroduction("남해 바다를 옆에 둔 울창한 편백림 속에서 숲해설가, 전문 의료진과 함께하는 자연 속 건강한 생활 체험")
			.build();

		assertThat(program.countKeywordFromDetailIntroduction(keyword), is(0));
		assertThat(program.scoreKeywordFromTheme(keyword), is(0.0));
		assertThat(program.scoreKeywordFromIntroduction(keyword), is(0.0));
		assertThat(program.scoreKeywordFromDetailIntroduction(keyword), is(0.0));
		assertThat(program.acquireScoreFromKeyword(keyword), is(0.0));
	}

	@Test
	public void testDetailWeight5() {

		String keyword = "생태체험";
		Program program = Program.builder()
			.theme("자연생태체험")
			.introduction("두모마을 전통어로체험 '개매기 체험' 참여, '내가 좋아하는 청정바다 생물' 해설프로그램 진행")
			.detailIntroduction("밀물과 썰물 자연의 원리를 이용한 전통어로체험과 국립공원 자연해설을 통해 한려해상 국립공원 청정바다를 체험합니다")
			.build();

		assertThat(program.countKeywordFromDetailIntroduction(keyword), is(0));
		assertThat(program.scoreKeywordFromTheme(keyword), is(1.0));
		assertThat(program.scoreKeywordFromIntroduction(keyword), is(0.0));
		assertThat(program.scoreKeywordFromDetailIntroduction(keyword), is(0.0));
		assertThat(program.acquireScoreFromKeyword(keyword), is(1.0));
	}
}