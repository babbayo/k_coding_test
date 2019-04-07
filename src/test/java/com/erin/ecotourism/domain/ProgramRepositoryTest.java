package com.erin.ecotourism.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.erin.ecotourism.domain.program.Program;
import com.erin.ecotourism.domain.program.ProgramRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@RunWith(SpringRunner.class)
public class ProgramRepositoryTest {

	@Autowired
	private ProgramRepository repository;

	@Before
	public void init() {
		Program post = new Program("test");
		Program program = repository.save(post);
		System.out.println(program);
	}

	@Test
	public void testFindAll() {
		Iterable<Program> posts = repository.findAll();
		assertThat(posts, not(emptyIterable()));
	}

	@Test
	public void testFindById() {
		Program postOne = new Program("test");
		repository.save(postOne);

		Optional<Program> findOne = repository.findById(postOne.getId());

		assertThat(findOne.isPresent(), is(true));
		assertThat(findOne.get().getId(), is(postOne.getId()));
		log.debug("program: {}", findOne);
	}
}