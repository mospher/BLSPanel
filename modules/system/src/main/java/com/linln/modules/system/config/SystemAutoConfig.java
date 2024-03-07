package com.linln.modules.system.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author 小懒虫 <auntvt＠163.com>
 * @date 2021/3/19
 */
@ComponentScan(basePackages = "com.linln.modules.system")
@EnableJpaRepositories(basePackages = "com.linln.modules.system")
@EntityScan(basePackages = "com.linln.modules.system")
public class SystemAutoConfig {
}
