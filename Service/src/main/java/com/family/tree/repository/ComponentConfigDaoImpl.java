package com.family.tree.repository;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.family.tree.model.ComponentConfig;
import com.family.tree.model.ComponentMaster;
import com.family.tree.model.ComponentParams;
import com.family.tree.resources.UsersApiController;


@Repository
public class ComponentConfigDaoImpl implements ComponentConfigDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponentConfigDaoImpl.class);
	
	@Override
	public ComponentConfig getComponent(String inputRequest) {
		LOGGER.info("getComponent, inputRequest = " + inputRequest);
		String componentConfigSql = "Select * from component_config where component_url = :inputRequest";
		// System.out.println("componentConfigSql : " + componentConfigSql);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("inputRequest", inputRequest);
		ComponentConfig componentConfig = namedParameterJdbcTemplate.queryForObject(componentConfigSql, paramMap, new RowMapper<ComponentConfig>()
				
		{
			public ComponentConfig mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				ComponentConfig componentConfig = new ComponentConfig();
				componentConfig.setComponentId(rs.getLong("component_id"));
				componentConfig.setCompnentURL(rs.getString("component_url"));
				final Blob componentSql = rs.getBlob("component_sql");
				componentConfig.setSql(new String(componentSql.getBytes(1l, (int) componentSql.length())));
				componentConfig.setDefaultSort(rs.getString("component_default_sort"));
				return componentConfig;
			}
		
		});
		
		String paramConfigSql = "Select * from component_filter_param_config where component_id = :componentId";
		final Map<String, Object> paramMapForParamConfoig = new HashMap<String, Object>();
		paramMapForParamConfoig.put("componentId", componentConfig.getComponentId());
		final Set<String> staticFilter = new HashSet<String>();
		final Map<String, ComponentParams> componentParamsMap = new HashMap<String, ComponentParams>();
		namedParameterJdbcTemplate.query(paramConfigSql, paramMapForParamConfoig, new RowMapper<ComponentParams>() {
			public ComponentParams mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				ComponentParams componentParams = new ComponentParams();
				componentParams.setComponentId(rs.getLong("component_id"));
				componentParams.setComponentParamId(rs.getLong("component_filter_param_id"));
				componentParams.setComponentParamName(rs.getString("param_identifier"));
				componentParams.setComponentParamDBAlias(rs.getString("param_db_code"));
				componentParams.setDbOperator(rs.getString("db_operator"));
				componentParams.setIsStaticParam(rs.getInt("is_static_param"));
				componentParams.setChildInMaster(rs.getString("child_in_master"));
				componentParamsMap.put(rs.getString("param_identifier"), componentParams);
				if(componentParams.getIsStaticParam() == 1)
				{
					staticFilter.add(rs.getString("param_identifier"));
				}
				return componentParams;
			}
		});
		
		String masterConfigSql = "Select * from component_filter_master where component_id = :componentId";
		final Map<String, ComponentMaster> componentMasterMap = new HashMap<String, ComponentMaster>();
		
		namedParameterJdbcTemplate.query(masterConfigSql, paramMapForParamConfoig, new RowMapper<ComponentMaster>() {
			public ComponentMaster mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				ComponentMaster componentMaster = new ComponentMaster();
				componentMaster.setComponentId(rs.getLong("component_id"));
				componentMaster.setChildInMaster(rs.getString("child_in_master"));
				componentMaster.setComponentMasterId(rs.getLong("component_master_filter_id"));
				final Blob masterSql = rs.getBlob("master_sql");
				componentMaster.setMasterSql(new String(masterSql.getBytes(1l, (int) masterSql.length())));
				componentMasterMap.put(rs.getString("child_in_master"), componentMaster);
				return componentMaster;
			}
		});
		componentConfig.setComponentParams(componentParamsMap);
		componentConfig.setStaticFilter(staticFilter);
		componentConfig.setComponentMaster(componentMasterMap);
		return componentConfig;
	}

}
