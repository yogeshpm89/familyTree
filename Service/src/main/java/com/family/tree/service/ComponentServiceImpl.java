package com.family.tree.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.family.tree.constant.ComponentConfigConstant;
import com.family.tree.constant.ComponentConfigFilter;
import com.family.tree.constant.FilterComponents;
import com.family.tree.model.Component;
import com.family.tree.model.ComponentConfig;
import com.family.tree.model.ComponentParams;
import com.family.tree.repository.ComponentDao;

@Service
public class ComponentServiceImpl implements ComponentService
{
	@Autowired
	private ComponentConfigService componentConfigService;
	@Autowired
	private ComponentDao componentDao;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final Logger logger = LogManager.getLogger("ComponentServiceImpl");

	@Override
	public Component getComponent(Map<String, Object> request) throws Exception {
		
		logger.info("get component start, request = " + request);
		
		String inputRequest = (String )request.get("inputRequest");
		ComponentConfig componentConfig = componentConfigService.getComponentConfig(inputRequest);
		Map<String, Object> componentSqlDeatils = getComponentSql(componentConfig, request);
		componentSqlDeatils.put(ComponentConfigConstant.PAGINATION_INFORMATION.getName(), getPaginationInformation(request));
		componentSqlDeatils.put(ComponentConfigConstant.class.getName(), getSortInformation(componentConfig,request));
		
		Component component = componentDao.getComponent(componentSqlDeatils);
		logger.info("get component end, component.getCount() = " + component.getCount());
		return component;
	}
	
	private Map<String, Object> getComponentSql(ComponentConfig componentConfig, Map<String, Object> request) {
		
		logger.info("get getComponentSql, request = " + request);
		logger.info("componentConfig = " + componentConfig);
		Map<String, Object> componentSqlDeatils =  new HashMap<String, Object>();
		StringBuilder finalSql = new StringBuilder(componentConfig.getSql());
		Map<String,StringBuilder> master = new HashMap<String, StringBuilder>(); 
		int staticFilterCount = 0;
		Map<String, Object> filterBindings = new HashMap<String, Object>();
		if(request.get(ComponentConfigFilter.FILTER.getName()) != null) {
			List filters = (List) request.get(ComponentConfigFilter.FILTER.getName());
			Map<String, ComponentParams> componentParamsMap = componentConfig.getComponentParams();
			for(Object obj : filters)
			{
				Map filter = (Map) obj;
				validateFilter(filter);
				ComponentParams componentParams = componentParamsMap.get(filter.get(ComponentConfigFilter.KEY.getName()));
				if(componentParams == null) {
					throw new RuntimeException("Filter " + filter.get(ComponentConfigFilter.KEY.getName()) + " is not configured");					
				}
				
				if(componentParams.getIsStaticParam() == 0) {
					String dbOperator = getDBOperator(componentParams,filter);
					if(!"N".equalsIgnoreCase(componentParams.getChildInMaster())) {
						if(!master.containsKey(componentParams.getChildInMaster())) {
							StringBuilder sb = new StringBuilder(componentConfig.getComponentMaster().get(componentParams.getChildInMaster()).getMasterSql());
							sb.deleteCharAt(sb.length() - 1);
							master.put(componentParams.getChildInMaster(), sb);
						}
						StringBuilder sb = master.get(componentParams.getChildInMaster());
						sb.append(" AND ").append(componentParams.getComponentParamDBAlias()).append(dbOperator);
					} else {
						finalSql.append(" AND ").append(componentParams.getComponentParamDBAlias()).append(dbOperator);
					}
				} else {
					staticFilterCount++;
				}
				if(ComponentConfigFilter.DATE_RANGE.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()).toString())) {
					filterBindings.put(filter.get(ComponentConfigFilter.KEY.getName()).toString()+ComponentConfigFilter.START_DATE.getName(), filter.get(ComponentConfigFilter.START_DATE.getName()));
					filterBindings.put(filter.get(ComponentConfigFilter.KEY.getName()).toString()+ComponentConfigFilter.END_DATE.getName(), filter.get(ComponentConfigFilter.END_DATE.getName()));
				} else {
					filterBindings.put(filter.get(ComponentConfigFilter.KEY.getName()).toString(), filter.get(ComponentConfigFilter.VALUE.getName()));
				}
			}
			
			for(String key : master.keySet()) {
				finalSql.append(" ").append(master.get(key).toString()).append(" ) ");
			}
			
		}
		if(componentConfig.getStaticFilter() != null && staticFilterCount != componentConfig.getStaticFilter().size()) {
			throw new RuntimeException("Missing static filters");	
		}
		
		componentSqlDeatils.put(ComponentConfigConstant.FINAL_SQL.getName(), finalSql.toString());
		componentSqlDeatils.put(ComponentConfigConstant.FILTER_BINDINGS.getName(), filterBindings);
		
		logger.info("get getComponentSql end");
		return componentSqlDeatils;
	}

	private String getDBOperator(ComponentParams componentParams,Map<String, Object> filter) {
		
		logger.info("get getDBOperator start, componentParams = " + componentParams + ", filter = " + filter);
		
		String paramName = componentParams.getComponentParamName();
		String dbOperator = null;
		String filterType = filter.get(ComponentConfigFilter.TYPE.getName()).toString();
		boolean isExactSearch = false;
		boolean isNot = false;
		if (filter.get(ComponentConfigFilter.IS_EXACT.getName()) != null) {
			isExactSearch = Boolean.valueOf(filter.get(ComponentConfigFilter.IS_EXACT.getName()).toString()).booleanValue();
		} 

		if (filter.get(ComponentConfigFilter.IS_NOT.getName()) != null) {
			isNot = Boolean.valueOf(filter.get(ComponentConfigFilter.IS_NOT.getName()).toString()).booleanValue();
		}
		
		if(ComponentConfigFilter.LIST.getName().equalsIgnoreCase(filterType)) {
			if(isNot) {
				dbOperator = " NOT IN (:";
			} else {
				dbOperator = " IN (:";
			}
			dbOperator = dbOperator + paramName + ")";
		} else if(ComponentConfigFilter.DOUBLE.getName().equalsIgnoreCase(filterType) || ComponentConfigFilter.NUMBER.getName().equalsIgnoreCase(filterType) || ComponentConfigFilter.DATE.getName().equalsIgnoreCase(filterType)) {
			
			if(isNot) {
				dbOperator = " <>:" + paramName;
			} else {
				dbOperator = " =:"+paramName;
			}
		} else if(ComponentConfigFilter.CHARS.getName().equalsIgnoreCase(filterType)) {
			if(isExactSearch) {
				dbOperator = " =:" + paramName;
			} else {
				String filterValue = filter.get(ComponentConfigFilter.VALUE.getName()).toString();
				filter.put(ComponentConfigFilter.VALUE.getName(), "%"+filterValue+"%");
				dbOperator = " like :"+paramName;
			}
			if(isNot && !isExactSearch) {
				dbOperator = " NOT " + dbOperator;
			} else if(isNot && isExactSearch) {
				dbOperator = " <>:"+ paramName;
			}
		} else if(ComponentConfigFilter.DATE_RANGE.getName().equalsIgnoreCase(filterType)) {
			dbOperator = " between :" + paramName + ComponentConfigFilter.START_DATE.getName() + " AND :" + paramName + ComponentConfigFilter.END_DATE.getName();
		} else if(ComponentConfigFilter.IS_NULL.getName().equalsIgnoreCase(filterType)) {
			dbOperator = " IS NULL";
		}
		
		logger.info("get getDBOperator end, dbOperator = " + dbOperator);
		return dbOperator;
	}
	
	private void validateFilter(Map<String, Object> filter)
	{
		
		logger.info("validateFilter start, filter = " + filter);
		
		String validationMessage = "";
		if(filter == null || filter.isEmpty()) {
			validationMessage = "Filter can not be empty";
		} else {
			for(String key : filter.keySet()) {
				if(key == null || key.trim().isEmpty()) {
					validationMessage += "Invalid filter";
				} else if(filter.get(key) == null || filter.get(key).toString().isEmpty()) {
					validationMessage += "Invalid filter";
				} else if(ComponentConfigFilter.NUMBER.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()))) {
					try {
						Long.parseLong(filter.get(ComponentConfigFilter.VALUE.getName()).toString());
					} catch (Exception e) {
						validationMessage += "Filter type is number, but filter value is non-numeric";
					}
				} else if (ComponentConfigFilter.DOUBLE.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()))) {
					if (!filter.get(ComponentConfigFilter.VALUE.getName()).toString().matches("-?\\d+(\\.\\d+)?")) {
						validationMessage = validationMessage + "Filter type is double, but filter value is non-numeric";
					}
				} else if(ComponentConfigFilter.LIST.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()))) {
					if(!(filter.get(ComponentConfigFilter.TYPE.getName()) instanceof List)) {
						validationMessage += "Filter type is List, but filter value is not in List format";
					}
				} else if (ComponentConfigFilter.DATE.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()))) {
					try {
						dateFormat.parse(filter.get(ComponentConfigFilter.TYPE.getName()).toString());
					} catch (Exception e) {
						validationMessage = validationMessage + "Date format should be yyyy-MM-dd";
					}
				} else if (ComponentConfigFilter.DATE_RANGE.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()))) {
					try {
						dateFormat.parse(filter.get(ComponentConfigFilter.START_DATE.getName()).toString());
						this.dateFormat.parse(filter.get(ComponentConfigFilter.END_DATE.getName()).toString());
					} catch (Exception e) {
						validationMessage = validationMessage + "Date format should be yyyy-MM-dd";
					}
				} else if (ComponentConfigFilter.CHARS.getName().equals(filter.get(ComponentConfigFilter.TYPE.getName()))) {
					
				} else {	// if ComponentConfigFilter.FILTER_TYPE.getName() not matching with anyone, it might be attack
					validationMessage = validationMessage + "Invalid filter type";
				}
			}
		}
		
		
		for(String key : FilterComponents.FILTER_COMPONENTS) {
			if (!filter.containsKey(key) && !ComponentConfigFilter.DATE_RANGE.getName().equalsIgnoreCase(filter.get(ComponentConfigFilter.TYPE.getName()).toString())) {
				validationMessage += " Missing " + key + " in filter";
			}
		}
		
		if(!validationMessage.isEmpty()) {
			logger.error("validationMessage = " + validationMessage);
			throw new RuntimeException(validationMessage);
		}
		logger.info("validateFilter end, validationMessage = " + validationMessage);
	}
	
	private String getPaginationInformation(Map<String, Object> request) {
		
		logger.info("getPaginationInformation start, request = " + request);
		
		String pagination = null;
		if(request.get(ComponentConfigFilter.PAGE_NO.getName()) != null && request.get(ComponentConfigFilter.PAGE_SIZE.getName())!= null) {
			try  {
				int pageNo = Integer.valueOf(request.get(ComponentConfigFilter.PAGE_NO.getName()).toString());
				int pageSize = Integer.valueOf(request.get(ComponentConfigFilter.PAGE_SIZE.getName()).toString());
				if(pageNo <=0 || pageSize <=0)
					throw new Exception();
				pageNo--;
				pageNo = (pageNo*pageSize);
				pagination = " LIMIT " + pageNo + "," + pageSize;
			} catch (Exception e) {
				logger.error("Exception = " + e.getMessage());
				throw new RuntimeException("Invalid pagination.");
			}
		}
		
		logger.info("getPaginationInformation end, pagination = " + pagination);
		return pagination;
	}
	
	
	private String getSortInformation(ComponentConfig componentConfig, Map<String, Object> request) {
		
		logger.info("getSortInformation start, componentConfig = " + componentConfig + ", request = " + request);
		StringBuilder sortInformation = new StringBuilder();
		if(!request.containsKey(ComponentConfigFilter.ORDER_BY.getName()) || (request.containsKey(ComponentConfigFilter.ORDER_BY.getName()) && "".equalsIgnoreCase(request.get(ComponentConfigFilter.ORDER_BY.getName()).toString()))) {
			if(componentConfig.getDefaultSort() != null)
				sortInformation.append(" "+componentConfig.getDefaultSort());
		} else if(componentConfig.getComponentParams().containsKey(request.get(ComponentConfigFilter.ORDER_BY.getName()))) {
			sortInformation.append(" order by "+componentConfig.getComponentParams().get(request.get(ComponentConfigFilter.ORDER_BY.getName())).getComponentParamDBAlias());
			
			if(request.get(ComponentConfigFilter.ORDER_TYPE.getName()) != null && request.get(ComponentConfigFilter.ORDER_TYPE.getName()).toString().equalsIgnoreCase("DESC")) {
				sortInformation.append(" DESC ");
			}
		}
		
		logger.info("getSortInformation end, sortInformation = " + sortInformation);
		return sortInformation.toString();
	}
}
