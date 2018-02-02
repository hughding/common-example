package pers.hugh.springdemo.dal.sys.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pers.hugh.springdemo.CommonTest;
import pers.hugh.springdemo.constant.TransactionManagerName;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/2/2</pre>
 */
@Transactional(TransactionManagerName.SYS_TRANSACTION_MANAGER)
@Rollback
public class SysConfigDaoTest extends CommonTest {

    @Autowired
    private SysConfigDao sysConfigDao;

    @Test
    public void countByExample() {
    }

    @Test
    public void deleteByExample() {
    }

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByExampleWithRowbounds() {
    }

    @Test
    public void selectByExample() {
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void updateByExampleSelective() {
    }

    @Test
    public void updateByExample() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
    }
}