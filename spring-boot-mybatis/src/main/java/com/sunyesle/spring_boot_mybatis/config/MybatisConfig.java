package com.sunyesle.spring_boot_mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.sunyesle.spring_boot_mybatis")
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(mybatisProperties.resolveMapperLocations());
        sessionFactory.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        sessionFactory.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());

        if (mybatisProperties.getConfiguration() != null) {
            final org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            mybatisProperties.getConfiguration().applyTo(configuration);
            sessionFactory.setConfiguration(configuration);
        }

        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
