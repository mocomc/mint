/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.fetch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pep.per.mint.fetch.database.FetchDatabaseRunner;

/**
 * <pre>
 * pep.per.mint.fetch
 * FetchConfig.java
 * </pre>
 * @author whoana
 * @date Apr 29, 2019
 */
@Configuration
public class FetchConfig {

	@Bean(name= {"fetchDatabaseRunner"})
	public FetchDatabaseRunner fetchDatabaseRunner() {
		FetchDatabaseRunner fdr = new FetchDatabaseRunner();
		return fdr;
	}

}
