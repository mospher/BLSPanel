package com.linln.admin.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author 小懒虫 <auntvt＠163.com>
 * @date 2021/3/20
 */
@ComponentScan(basePackages = "com.linln.admin")
@EnableJpaRepositories(basePackages = "com.linln.admin")
@EntityScan(basePackages = "com.linln.admin")
public class AdminAutoConfig {
}
