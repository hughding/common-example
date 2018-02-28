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
import pers.hugh.springdemo.config.properties.JdbcSysProperties;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 由于不是所有datasource都集成了Spring boot，所以此处提供了传统的数据源配置方式
 *
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/19</pre>
 */
@MapperScan(basePackages = "pers.hugh.springdemo.dal.sys", sqlSessionFactoryRef = "sysSqlSessionFactoryBean")
@EnableTransactionManagement
@Configuration
public class SysDataSourceConfig {

    @Autowired
    private JdbcSysProperties jdbcSysProperties;

    private DataSource readDataSource(){
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

    private DataSource writeDataSource(){
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

    /**
     * spring.datasource.schema配置会指定一个sql脚本，Spring boot会选一个datasource执行，需要默认一个主datasource,所以使用@Primary注解
     * spring.datasource.schema没有配置时，什么都不执行
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource dynamicSysDataSource() {
        DataSource readDataSource = readDataSource();
        DataSource writeDataSource = writeDataSource();

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DynamicDataSource.DatabaseTypeEnum.READ, readDataSource);
        targetDataSources.put(DynamicDataSource.DatabaseTypeEnum.WRITE, writeDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        //默认使用写库
        dataSource.setDefaultTargetDataSource(writeDataSource);
        return dataSource;
    }

    @Bean
    @Primary
    public DataSourceTransactionManager sysTransactionManager(@Qualifier("dynamicSysDataSource") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactoryBean sysSqlSessionFactoryBean(@Qualifier("dynamicSysDataSource") DataSource dataSource, ResourceLoader resourceLoader) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:sys-mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }
}
