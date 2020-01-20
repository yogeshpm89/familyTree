package com.family.tree.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.family.tree.model.CrudConfig;
import com.family.tree.model.CrudConfigParams;

@Repository
public class CrudConfigDaoImpl implements CrudConfigDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	@Override
	public CrudConfig getCrudConfig(String inputRequest) {
		
		String componentConfigSql = "Select * from crud_config where crud_component = :inputRequest";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("inputRequest", inputRequest);
		final CrudConfig crudConfig = namedParameterJdbcTemplate.queryForObject(componentConfigSql, paramMap, new RowMapper<CrudConfig>()
				
		{
			public CrudConfig mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				CrudConfig crudConfig = new CrudConfig();
				crudConfig.setCrudConfigId(rs.getLong("crud_config_id"));
				crudConfig.setEntityName(rs.getString("entity_name"));
				crudConfig.setEntityHardDeleteAllowed(rs.getInt("HARD_DELETE"));
				crudConfig.setPrimaryKeyColumn(rs.getString("primary_key_column"));
				crudConfig.setSequenceName(rs.getString("sequence_name"));
				crudConfig.setEntityHistoryName(rs.getString("entity_history_name"));
				return crudConfig;
			}
		
		});
		
		String paramConfigSql = "Select * from crud_config_param where crud_config_id = :crudConfigId and is_active = 1";
		final Map<String, Object> paramMapForParamConfoig = new HashMap<String, Object>();
		paramMapForParamConfoig.put("crudConfigId", crudConfig.getCrudConfigId());
		final Map<String, CrudConfigParams> entityMappingDetails = new HashMap<String, CrudConfigParams>();
		namedParameterJdbcTemplate.query(paramConfigSql, paramMapForParamConfoig, new RowMapper<CrudConfigParams>() {
			public CrudConfigParams mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				CrudConfigParams crudConfigParams = new CrudConfigParams();
				crudConfigParams.setEntityAttributeName(rs.getString("entity_attribute_name"));
				crudConfigParams.setEntityAttributeMapping(rs.getString("entity_attribute_mapping"));
				crudConfigParams.setEntityAttributType(rs.getString("entity_attribute_type"));
				crudConfigParams.setIsActive(rs.getInt("is_active"));
				crudConfigParams.setIsMandatory(rs.getInt("is_mandatory"));
				if(crudConfigParams.getEntityAttributeMapping().equalsIgnoreCase(crudConfig.getPrimaryKeyColumn()))
				{
					crudConfig.setEntityJsonMappingForPrimaryKey(crudConfigParams.getEntityAttributeName());
				}
				entityMappingDetails.put(rs.getString("entity_attribute_name"), crudConfigParams);
				return crudConfigParams;
			}
		});
		crudConfig.setEntityMappingDetails(entityMappingDetails);
		return crudConfig;
	}
}
