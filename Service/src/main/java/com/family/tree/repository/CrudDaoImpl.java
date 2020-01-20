package com.family.tree.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.family.tree.constant.CrudConfigConstant;
import com.family.tree.model.CrudConfig;
import com.family.tree.model.CrudConfigParams;

@Repository
public class CrudDaoImpl implements CrudDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = LogManager.getLogger("CrudDaoImpl");
	
	@Override
	public List<Integer> getVersionOfCrud(CrudConfig crudConfig, Map<String, Object> filters) {
		StringBuilder sql = new StringBuilder("SELECT VERSION FROM ").append(crudConfig.getEntityName());
		if(filters != null && !filters.isEmpty())
		{
			sql.append(" WHERE ");
			Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
			for(String filter : filters.keySet())
			{
				CrudConfigParams configParams = entityMappingDetails.get(filter);
				if(filters.get(filter) instanceof List)
				{
					sql.append(configParams.getEntityAttributeMapping()).append(" IN (:").append(filter).append(") AND ");
				}
				else
				{
					sql.append(configParams.getEntityAttributeMapping()).append("=:").append(filter).append(" AND ");
				}
				
			}
			sql.delete(sql.length()-4, sql.length()-1);
		}
		List<Integer> versions = namedParameterJdbcTemplate.query(sql.toString(), filters, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return rs.getInt(CrudConfigConstant.VERSION.getName());
			}
		});
		return versions;
	}
	
	@Override
	public Map<String, Object> addDomain(CrudConfig crudConfig, Map<String, Object> crudAttributes) throws Exception
	{
		logger.info("addDomain start, crudConfig = " + crudConfig + ", crudAttributes = " + crudAttributes);
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			StringBuilder primaryKeyColumn = new StringBuilder();
			String insertSql = generateCrudInsertStatement(crudConfig,crudAttributes.keySet(),primaryKeyColumn);
			logger.info("insertSql = " + insertSql);
			// System.out.println("insertSql : " + insertSql);
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource paramSource = new MapSqlParameterSource(crudAttributes); 
			if(primaryKeyColumn.length() > 0)
			{
				namedParameterJdbcTemplate.update(insertSql, paramSource, holder, new String[]{primaryKeyColumn.toString()});
				response.put(CrudConfigConstant.GENERATED_ID.getName(), holder.getKey().longValue());
				logger.info("GENERATED_ID = " + holder.getKey().longValue());
				// System.out.println(holder.getKey().longValue());
			}
			else
			{
				namedParameterJdbcTemplate.update(insertSql, paramSource);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} 
		response.put(CrudConfigConstant.STATUS.getName(), true);
		logger.info("addDomain end, response = " + response);
		return response;
	}

	@Override
	public Map<String, Object> bulkAddDomain(CrudConfig crudConfig, Map<String, Object>[]  crudAttributes)  throws Exception
	{
		Map<String, Object> response = new HashMap<String, Object>();
		try 
		{
			String insertSql = generateCrudInsertStatement(crudConfig,crudAttributes[0].keySet(), null);
			int[] count = namedParameterJdbcTemplate.batchUpdate(insertSql, crudAttributes);
			if(count != null)
			{
				response.put("newlyAddedRecords", count.length);
			}
			response.put(CrudConfigConstant.STATUS.getName(), true);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return response;
	}

	@Override
	public Map<String, Object> updateDomain(CrudConfig crudConfig, Map<String, Object> crudAttributes, Map<String, Object> filters)  throws Exception
	{
		logger.info("updateDomain start, crudConfig = " + crudConfig + ", crudAttributes = " + crudAttributes + ", filters = " + filters);
		int updatedRows = 0;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			String updateSql = generateCrudUpdateStatement(crudConfig, crudAttributes.keySet(), filters);
			// System.out.println(updateSql);
			crudAttributes.putAll(filters);
			SqlParameterSource paramSource = new MapSqlParameterSource(crudAttributes);
			updatedRows = namedParameterJdbcTemplate.update(updateSql, paramSource);
		} catch (Exception e) {
			logger.error("updateDomain Exception, Message = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		response.put(CrudConfigConstant.STATUS.getName(), true);
		response.put("updatedRecords", updatedRows);
		logger.info("updateDomain end, response = " + response);
		return response;
	}

	@Override
	public Map<String, Object> deleteDomain(CrudConfig crudConfig, Map<String, Object> filters)  throws Exception
	{
		logger.info("deleteDomain start, crudConfig = " + crudConfig + ", filters = " + filters);
		Map<String, Object> response = new HashMap<String, Object>();
		int deletedRows = 0;
		try {
			String deleteSql = generateCrudDeleteStatement(crudConfig, filters.keySet());
			SqlParameterSource paramSource = new MapSqlParameterSource(filters);
			deletedRows = namedParameterJdbcTemplate.update(deleteSql, paramSource);
			response.put(CrudConfigConstant.STATUS.getName(), true);
			response.put("deletedRecords", deletedRows);
		} catch (Exception e) {
			logger.error("deleteDomain Exception, Message = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		logger.info("deleteDomain end, response = " + response);
		return response;
	}
	
	private String generateCrudInsertStatement(CrudConfig crudConfig, Set<String>  crudAttributes, StringBuilder primaryKeyColumn)
	{
		StringBuilder insertStatement = new StringBuilder("INSERT INTO ").append(crudConfig.getEntityName()).append(" (");
		StringBuilder values = new StringBuilder(" VALUES(");
		if(crudConfig.getSequenceName() != null)
		{
			insertStatement.append(crudConfig.getPrimaryKeyColumn()).append(",");
			values.append(crudConfig.getSequenceName()).append(".nextval").append(",");
		}
		if(primaryKeyColumn != null && crudConfig.getPrimaryKeyColumn() !=null)
		{
			primaryKeyColumn.append(crudConfig.getPrimaryKeyColumn());
		}
		Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
		for(String crudAttribute : crudAttributes)
		{
			
			CrudConfigParams configParams = entityMappingDetails.get(crudAttribute);
			if(!configParams.getEntityAttributeMapping().equals(crudConfig.getPrimaryKeyColumn()))
			{
				insertStatement.append(configParams.getEntityAttributeMapping()).append(",");
				values.append(":").append(crudAttribute).append(",");
			}
			
		}
		insertStatement.deleteCharAt(insertStatement.length()-1);
		values.deleteCharAt(values.length()-1);
		insertStatement.append(" ) ").append(values.toString()).append(" ) ");
		return insertStatement.toString();
	}
	
	private String generateCrudUpdateStatement(CrudConfig crudConfig, Set<String>  crudAttributes, Map<String, Object> filters)
	{
		StringBuilder updateStatement = new StringBuilder("UPDATE ").append(crudConfig.getEntityName()).append(" SET ");
		Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
		for(String crudAttribute : crudAttributes)
		{
			CrudConfigParams configParams = entityMappingDetails.get(crudAttribute);
			if(!configParams.getEntityAttributeMapping().equalsIgnoreCase(CrudConfigConstant.VERSION.getName()))
			{
				updateStatement.append(configParams.getEntityAttributeMapping()).append("=:").append(crudAttribute).append(",");
			}
		}
		if(entityMappingDetails.containsKey(CrudConfigConstant.VERSION))
		{
			updateStatement.append(CrudConfigConstant.VERSION).append("=version+1,");
		}
		updateStatement.deleteCharAt(updateStatement.length()-1);
		if(filters != null && !filters.isEmpty())
		{
			updateStatement.append(" WHERE ");
			
			for(String filter : filters.keySet())
			{
				CrudConfigParams configParams = entityMappingDetails.get(filter);
				if(filters.get(filter) instanceof List)
				{
					updateStatement.append(configParams.getEntityAttributeMapping()).append(" IN (:").append(filter).append(") AND ");
				}
				else
				{
					updateStatement.append(configParams.getEntityAttributeMapping()).append("=:").append(filter).append(" AND ");
				}
			}
			/*if(entityMappingDetails.containsKey(IS_ACTIVE) && updateStatement.toString().toUpperCase().indexOf("IS_ACTIVE") == -1)
			{
				updateStatement.append(" IS_ACTIVE = 1 and ");
			}*/
			
			updateStatement.delete(updateStatement.length()-4, updateStatement.length()-1);
		}
		return updateStatement.toString();
	}
	
	private String generateCrudDeleteStatement(CrudConfig crudConfig, Set<String> filters)
	{
		StringBuilder deleteStatement = new StringBuilder("DELETE FROM ").append(crudConfig.getEntityName());
		Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
		if(filters != null && !filters.isEmpty())
		{
			deleteStatement.append(" WHERE ");
			for(String filter : filters)
			{
				CrudConfigParams configParams = entityMappingDetails.get(filter);
				deleteStatement.append(configParams.getEntityAttributeMapping()).append("=:").append(filter).append(" AND ");
			}
			deleteStatement.delete(deleteStatement.length()-4, deleteStatement.length()-1);
		}
		
		return deleteStatement.toString();
	}
	
	@Override
	public void generateHistory(CrudConfig crudConfigForHist, CrudConfig crudConfig, Map<String, Object> historyCrudAttributes, Map<String, Object> filters) throws Exception
	{
		try {
			String insertStatement = generateInsertSqlForHistory(crudConfigForHist, crudConfig, historyCrudAttributes.keySet(), filters);
			// System.out.println("history insertStatement : "+ insertStatement);
			filters.putAll(historyCrudAttributes);
			SqlParameterSource paramSource = new MapSqlParameterSource(filters);
			namedParameterJdbcTemplate.update(insertStatement, paramSource);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private String generateInsertSqlForHistory(CrudConfig crudConfigForHist, CrudConfig crudConfig, Set<String> historyCrudAttributes, Map<String, Object> filters)
	{
		StringBuilder insertStatement = new StringBuilder("INSERT INTO ").append(crudConfigForHist.getEntityName()).append(" (");
		StringBuilder values = new StringBuilder(" SELECT ");
		if(crudConfigForHist.getSequenceName() != null)
		{
			insertStatement.append(crudConfigForHist.getPrimaryKeyColumn()).append(",");
			values.append(crudConfigForHist.getSequenceName()).append(".nextval").append(",");
		}
		Map<String, CrudConfigParams> entityMappingDetailsForHistory = crudConfigForHist.getEntityMappingDetails();
		for(String historyCrudAttribute : historyCrudAttributes)
		{
			CrudConfigParams configParamsForHistory = entityMappingDetailsForHistory.get(historyCrudAttribute);
			if(!configParamsForHistory.getEntityAttributeMapping().equals(crudConfigForHist.getPrimaryKeyColumn()))
			{
				insertStatement.append(configParamsForHistory.getEntityAttributeMapping()).append(",");
				values.append(":").append(historyCrudAttribute).append(",");
			}
		}
		
		Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
		for(String crudAttribute : entityMappingDetails.keySet())
		{
			CrudConfigParams configParams = entityMappingDetails.get(crudAttribute);
			insertStatement.append(configParams.getEntityAttributeMapping()).append(",");
			values.append(configParams.getEntityAttributeMapping()).append(",");
		}
		insertStatement.deleteCharAt(insertStatement.length()-1);
		values.deleteCharAt(values.length()-1);
		values.append(" FROM ").append(crudConfig.getEntityName());
		if(filters != null && !filters.isEmpty())
		{
			values.append(" WHERE ");
			for(String filter : filters.keySet())
			{
				CrudConfigParams configParams = entityMappingDetails.get(filter);
				if(filters.get(filter) instanceof List)
				{
					values.append(configParams.getEntityAttributeMapping()).append(" IN (:").append(filter).append(") AND ");
				}
				else
				{
					values.append(configParams.getEntityAttributeMapping()).append("=:").append(filter).append(" AND ");
				}
			}
			values.delete(values.length()-4, values.length()-1);
		}
		insertStatement.append(" ) ").append(values.toString());
		return insertStatement.toString();
	}
	
	@Override
	public void deleteAnswer(Map<String, Object> request) {
		String mopSectionQueastionAnswerQuery = "DELETE FROM mop_section_question_answer WHERE mop_section_question_answer_id = :mopSectionQuestionAnswerId";
		String answerLibraryQuery = "DELETE FROM answer_library WHERE answer_id = :answerId";
		String documetQuery = "DELETE FROM document WHERE document_id = :documentId";
		
		SqlParameterSource namedParametersMopSectionQueastionAnswer = new MapSqlParameterSource("mopSectionQuestionAnswerId", request.get("mopSectionQuestionAnswerId"));
		SqlParameterSource namedParametersAnswerLibrary = new MapSqlParameterSource("answerId", request.get("answerId"));
		SqlParameterSource namedParametersDocumet = new MapSqlParameterSource("documentId", request.get("answerText"));
		
		namedParameterJdbcTemplate.update(mopSectionQueastionAnswerQuery, namedParametersMopSectionQueastionAnswer);
		namedParameterJdbcTemplate.update(answerLibraryQuery, namedParametersAnswerLibrary);
		namedParameterJdbcTemplate.update(documetQuery, namedParametersDocumet);
	}
	
	@Override
	public Map<String, Object> getLastMopNumber(){
		Map<String, Object> response = null;
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
			simpleJdbcCall.withProcedureName("auto_mop_number ");
			simpleJdbcCall.setAccessCallParameterMetaData(false);
			simpleJdbcCall.declareParameters(new SqlOutParameter("autoMopNumber",Types.NUMERIC));
			response =simpleJdbcCall.execute();
		} catch (Exception e) {
			logger.error("updateDomain Exception, Message = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		logger.info("updateDomain end, response = " + response);
		return response;
	}
}
