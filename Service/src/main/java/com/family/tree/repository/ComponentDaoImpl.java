package com.family.tree.repository;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.family.tree.constant.ComponentConfigConstant;
import com.family.tree.model.Component;
import com.family.tree.model.ComponentConfig;

@Repository
public class ComponentDaoImpl implements ComponentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final Logger logger = LogManager.getLogger("ComponentDaoImpl");

	@Override
	public Component getComponent(Map<String, Object> componentSqlDeatils) throws Exception {
		
		logger.info("getComponent start, componentSqlDeatils = " + componentSqlDeatils.keySet().toString());
		
		StringBuilder finalSql = new StringBuilder(componentSqlDeatils.get(ComponentConfigConstant.FINAL_SQL.getName()).toString());
		
		
		Component component = new Component();
		boolean isPaginationProvided = false;
		long count = 0;
		
		if(componentSqlDeatils.get(ComponentConfigConstant.SORT_INFORMATION.getName()) != null) {
			finalSql.append(componentSqlDeatils.get(ComponentConfigConstant.SORT_INFORMATION.getName()));
		}
		
		
		if(componentSqlDeatils.get(ComponentConfigConstant.PAGINATION_INFORMATION.getName()) != null) {
			isPaginationProvided = true;
			String queryForCount = "SELECT COUNT(1) FROM (" + finalSql + ") TEMP_ALIAS_FOR_COUNT";
			count = namedParameterJdbcTemplate.queryForObject(queryForCount, (Map)componentSqlDeatils.get(ComponentConfigConstant.FILTER_BINDINGS.getName()), Long.class);
			logger.info("getComponent, count = " + count);
			if(count == 0) {
				return component;
			}
		}
		
		if(componentSqlDeatils.get(ComponentConfigConstant.PAGINATION_INFORMATION.getName()) != null) {
			finalSql.append(componentSqlDeatils.get(ComponentConfigConstant.PAGINATION_INFORMATION.getName()));
		}
		
		/*logger.info("getComponent, finalSql = \n"
				+ "===============================================================================================================================\n" 
				+ finalSql  
				+ "\n===============================================================================================================================");*/
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List data = namedParameterJdbcTemplate.queryForList(finalSql.toString(), (Map)componentSqlDeatils.get(ComponentConfigConstant.FILTER_BINDINGS.getName()));
		if(!isPaginationProvided && data != null) {
			count = data.size();
		}
		component.setData(data);
		component.setCount(count);
		
		logger.info("getComponent end, component = " + component);
		return component;
	}
	
	

}
