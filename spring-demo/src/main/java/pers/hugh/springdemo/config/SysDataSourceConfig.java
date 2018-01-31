package pers.hugh.springdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pers.hugh.springdemo.config.properties.JdbcSysProperties;

import javax.sql.DataSource;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/19</pre>
 */
@MapperScan(basePackages = "pers.hugh.springdemo.dal.sys", sqlSessionFactoryRef = "sysSqlSessionFactoryBean")
@Configuration
@EnableTransactionManagement
public class SysDataSourceConfig{

    @Autowired
    private JdbcSysProperties jdbcSysProperties;


    @Bean
    public DataSource sysDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(jdbcSysProperties.getUrl());
        datasource.setDriverClassName(jdbcSysProperties.getDriverClassName());
        datasource.setUsername(jdbcSysProperties.getUsername());
        datasource.setPassword(jdbcSysProperties.getPassword());
        datasource.setInitialSize(jdbcSysProperties.getInitialSize());
        datasource.setMinIdle(jdbcSysProperties.getMinIdle());
        datasource.setMaxWait(jdbcSysProperties.getMaxWait());
        datasource.setMaxActive(jdbcSysProperties.getMaxActive());
        datasource.setTimeBetweenEvictionRunsMillis(jdbcSysProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(jdbcSysProperties.getMinEvictableIdleTimeMillis());
        return datasource;
    }

    @Bean
    public DataSourceTransactionManager sysTransactionManager(@Qualifier("sysDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sysSqlSessionFactoryBean(@Qualifier("sysDataSource") DataSource dataSource, ResourceLoader resourceLoader) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:sys-mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }
}
