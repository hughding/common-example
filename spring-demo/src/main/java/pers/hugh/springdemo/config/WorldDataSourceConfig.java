package pers.hugh.springdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pers.hugh.springdemo.config.properties.JdbcWorldProperties;

import javax.sql.DataSource;


/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/19</pre>
 */
@MapperScan(basePackages = "pers.hugh.springdemo.dal.world", sqlSessionFactoryRef = "worldSqlSessionFactoryBean")
@EnableTransactionManagement
@Configuration
public class WorldDataSourceConfig {

    @Autowired
    private JdbcWorldProperties jdbcWorldProperties;

    @Bean
    public DataSource worldDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(jdbcWorldProperties.getUrl());
        datasource.setDriverClassName(jdbcWorldProperties.getDriverClassName());
        datasource.setUsername(jdbcWorldProperties.getUsername());
        datasource.setPassword(jdbcWorldProperties.getPassword());
        datasource.setInitialSize(jdbcWorldProperties.getInitialSize());
        datasource.setMinIdle(jdbcWorldProperties.getMinIdle());
        datasource.setMaxWait(jdbcWorldProperties.getMaxWait());
        datasource.setMaxActive(jdbcWorldProperties.getMaxActive());
        datasource.setTimeBetweenEvictionRunsMillis(jdbcWorldProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(jdbcWorldProperties.getMinEvictableIdleTimeMillis());
        return datasource;
    }

    @Bean
    public DataSourceTransactionManager worldTransactionManager(@Qualifier("worldDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean worldSqlSessionFactoryBean(@Qualifier("worldDataSource") DataSource dataSource, ResourceLoader resourceLoader) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:world-mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }
}
