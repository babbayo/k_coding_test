/**
 * Copyright 2019 Naver Corp. All rights Reserved.
 * Naver PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.erin.ecotourism.domain.program;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProgramRepository extends CrudRepository<Program, Long> {


	List<Program> findAllByAddressValueContaining(String address);

	List<Program> findAllByIntroductionContaining(String keyword);

	List<Program> findAllByDetailIntroductionContaining(String keyword);
}
