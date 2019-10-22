package com.resto.brand.web.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.resto.brand.web.sharding.RanksSingleKeyTableShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by disvenk.dai on 2018-07-18 13:33
 */

public class ShardingConfig {

    @Autowired
    @Qualifier("sharding_0")
    DataSource dataSource;

    private DataSourceRule dataSourceRule() throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("ds_0", dataSource);
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap,"ds_0");
        return dataSourceRule;
    }

    private TableRule rankingsTableRule() throws SQLException {
        TableRule orderTableRule = TableRule.builder("tb_rankings").actualTables(Arrays.asList("tb_rankings_0", "tb_rankings_1","tb_rankings_2","tb_rankings_3"))
                .dataSourceRule(dataSourceRule()).build();
        return orderTableRule;
    }

    private ShardingRule shardingRule() throws SQLException {
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule())
                .tableRules(Arrays.asList(rankingsTableRule()))
                .tableShardingStrategy(new TableShardingStrategy("times", new RanksSingleKeyTableShardingAlgorithm ()))
                .build();
        return shardingRule;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        return ShardingDataSourceFactory.createDataSource(shardingRule());
    }

}
