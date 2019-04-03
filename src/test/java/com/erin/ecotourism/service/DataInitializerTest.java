package com.erin.ecotourism.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataInitializerTest {

	@Test
	public void testCsvReader() throws IOException {
		List<String[]> list = new DataInitializer(null, null)
			.getSampleList();

		Assert.assertEquals(110, list.size());
		list.forEach(x -> {
			assertEquals(6, x.length);
			assertNotNull(x[0]);
			assertNotNull(x[1]);
			assertNotNull(x[2]);
			assertNotNull(x[3]);
			assertNotNull(x[4]);
			assertNotNull(x[5]);
		});

		log.info("{} converted - {}", list.size(), list.get(0));
	}
}