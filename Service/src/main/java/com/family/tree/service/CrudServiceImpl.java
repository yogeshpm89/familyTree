package com.family.tree.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.family.tree.constant.CrudComponent;
import com.family.tree.constant.CrudConfigConstant;
import com.family.tree.model.Component;
import com.family.tree.model.CrudConfig;
import com.family.tree.model.CrudConfigParams;
import com.family.tree.repository.CrudDao;

@Service
public class CrudServiceImpl implements CrudService 
{
	@Autowired
	private CrudDao crudDao;
	@Autowired
	private CrudConfigService crudConfigService;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	private static final Logger logger = LogManager.getLogger("CrudServiceImpl");
	@Autowired
	private ComponentService componentService;
	@Override
	public Map<String, Object> addDomain(CrudComponent crudComponent, Map<String, Object> crudAttributes, Map<String, Object> activity) throws Exception 
	{
		CrudConfig crudConfig = getCrudConfig(crudComponent.toString());
		
		Map<String, Object> response = null;
		validateInput(crudConfig, crudAttributes);
		manipulateNonBusinessAttributs(crudConfig, crudAttributes, "ADD", activity);
		response = crudDao.addDomain(crudConfig, crudAttributes);
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put(crudConfig.getEntityJsonMappingForPrimaryKey(), response.get(CrudConfigConstant.GENERATED_ID.getName()));
		generateHistory(crudConfig, activity, filters);
		
		response.put(CrudConfigConstant.GENERATED_ID_JSON_MAPPING.getName(), crudConfig.getEntityJsonMappingForPrimaryKey());
		return response;
	}

	@Override
	public Map<String, Object> bulkAddDomain(CrudComponent crudComponent, List<Map<String, Object>> crudAttributeList, Map<String, Object> activity) throws Exception 
	{
		CrudConfig crudConfig = getCrudConfig(crudComponent.toString());
		Long activityId = null;
		int arrayIndex = 0;
		if(crudAttributeList == null || crudAttributeList.isEmpty())
		{
			throw new RuntimeException("Input is blanks");
		}
		Map<String, Object>[]  crudAttributes = new Map[crudAttributeList.size()];
		Map<String, Object> response = null;
		for(Map<String, Object> crudAttribute : crudAttributeList)
		{
			validateInput(crudConfig, crudAttribute);
			manipulateNonBusinessAttributs(crudConfig, crudAttribute, "ADD", activity);
			crudAttributes[arrayIndex] = crudAttribute;
			arrayIndex++;
			if(activityId == null && crudAttribute.containsKey(CrudConfigConstant.ACTIVITY_ID.getName()) && crudConfig.getEntityMappingDetails().containsKey(CrudConfigConstant.ACTIVITY_ID.getName()))
			{
				activityId = Long.valueOf(crudAttribute.get(CrudConfigConstant.ACTIVITY_ID.getName()).toString());
			}
		}
		response = crudDao.bulkAddDomain(crudConfig, crudAttributes);
		Map<String, Object> filters = new HashMap<String, Object>();
		if(activityId != null)
		{
			filters.put(CrudConfigConstant.ACTIVITY_ID.getName(), activityId);
		}
		else
		{
			filters.put(CrudConfigConstant.CREATED_DATE.getName(), activity.get(CrudConfigConstant.CREATED_DATE.getName()));
			
		}
		generateHistory(crudConfig, activity, filters);
		
		return response;
	}

	@Override
	public Map<String, Object> updateDomain(CrudComponent crudComponent, Map<String, Object> crudAttributes, Map<String, Object> filters, Map<String, Object> activity)	throws Exception 
	{
		logger.info("updateDomain start, crudAttributes = " + crudAttributes + ", filters = " + filters + ", activity = " + activity);
		CrudConfig crudConfig = getCrudConfig(crudComponent.toString());
		Map<String, Object> response = null;
		validateInput(crudConfig, crudAttributes);
		validateInput(crudConfig, filters);
		if(crudAttributes.containsKey(CrudConfigConstant.VERSION.getName()))
		{
			int version = Integer.parseInt(crudAttributes.get(CrudConfigConstant.VERSION.getName()).toString());
			List<Integer> versions = crudDao.getVersionOfCrud(crudConfig, filters);
			if(versions != null && versions.size() == 1)
			{
				if(version != versions.get(0))
				{
					throw new RuntimeException("Data is modified by others");
				}
			}
		}
		manipulateNonBusinessAttributs(crudConfig, crudAttributes, "UPDATE", activity);
		/*Map<String, Object> workflow = getWorkFlow(crudAttributes);
		Integer makerAction = (Integer) workflow.get("maker_action");
		Integer ca = (Integer) workflow.get("checker_action");*/
		response = crudDao.updateDomain(crudConfig, crudAttributes, filters);
		generateHistory(crudConfig, activity, filters);
		/*try {
		if(makerAction != Integer.parseInt(crudAttributes.get("makerAction").toString())) {
			generateHistory(crudConfig, activity, filters);
		}else {
			Map<String, Object> workflow1 = getWorkFlow(crudAttributes);
			Integer checkerAction = (Integer) workflow1.get("checker_action");
			if(checkerAction != null) {
				generateHistory(crudConfig, activity, filters);
			}
		}
		}catch(Exception e) {
			if(ca != null) {
				if(ca != Integer.parseInt(crudAttributes.get("checkerAction").toString())) {
					generateHistory(crudConfig, activity, filters);
				}
			}
		}
	*/	
		logger.info("updateDomain end, response = " + response);
		return response;
	}
	public Map<String, Object> getWorkFlow(Map<String, Object> crudAttributes) throws Exception {
		Map<String, Object> req = new HashMap<String,Object>();
		req.put("inputRequest", "/component/lastWorkFlot");
		List<Map<String, Object>> filter = new ArrayList<Map<String,Object>>();
		Map<String, Object> filterKeyValue = new HashMap<>();
		filterKeyValue.put("FILTER_KEY", "workflow_id");
		filterKeyValue.put("FILTER_VALUE", crudAttributes.get("workflowId"));
		filterKeyValue.put("FILTER_TYPE", "NUMBER");
		filter.add(filterKeyValue);
		req.put("FILTER", filter);
		Component<Object> comp = componentService.getComponent(req);
		List<Object> workflowList = comp.getData();
		Map<String, Object> workflowData = (Map<String, Object>) workflowList.get(0);
		//int makerAction = (int) workflowData.get("maker_action");
		//return makerAction;
		return workflowData;
	}
	@Override
	public Map<String, Object> deleteDomain(CrudComponent crudComponent, Map<String, Object> filters, String version, Map<String, Object> activity) throws Exception 
	{
		CrudConfig crudConfig = getCrudConfig(crudComponent.toString());
		validateInput(crudConfig, filters);
		
		if(crudConfig.getEntityHardDeleteAllowed() == 0 && crudConfig.getEntityMappingDetails().containsKey(CrudConfigConstant.IS_ACTIVE.getName()))
		{
			Map<String, Object> crudAttributes = new HashMap<String, Object>();
			crudAttributes.put(CrudConfigConstant.IS_ACTIVE.getName(), 0);
			if(version != null)
			{
				crudAttributes.put(CrudConfigConstant.VERSION.getName(), version);
			}
			return updateDomain(crudComponent, crudAttributes, filters, activity);
		}
		return crudDao.deleteDomain(crudConfig, filters);
	}
	
	private void validateInput(CrudConfig crudConfig,Map<String, Object> crudAttributes)
	{
		StringBuilder validationFailureReason = new StringBuilder();
		if(crudAttributes == null || crudAttributes.isEmpty())
		{
			validationFailureReason.append("Empty input");
		}
		else
		{
			Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
			for(String attributes : crudAttributes.keySet())
			{
				if(!entityMappingDetails.containsKey(attributes))
				{
					validationFailureReason.append(" Invalid input attribute :").append(attributes);
				}
				else
				{
					CrudConfigParams configParams = entityMappingDetails.get(attributes);
					if(configParams.getIsMandatory() == 1 && (crudAttributes.get(attributes) == null || crudAttributes.get(attributes).toString().trim().isEmpty()))
					{
						validationFailureReason.append(" Attribute :").append(attributes).append(" can not be empty ");
					}
					else if(CrudConfigConstant.ENTITY_ATTR_TYPE_NUMBER.getName().equals(configParams.getEntityAttributType())
							|| CrudConfigConstant.ENTITY_ATTR_TYPE_DOUBLE.getName().equals(configParams.getEntityAttributType()))
					{
						
						if(crudAttributes.get(attributes) instanceof List)
						{
							List list = (List)crudAttributes.get(attributes);
							if(list == null || list.isEmpty())
							{
								validationFailureReason.append(" Attribute :").append(attributes).append(" can not be empty ");
							}
							else
							{
								for (Object object : list) 
								{
									if(object == null || !isNumberIsValid(object.toString(),CrudConfigConstant.ENTITY_ATTR_TYPE_NUMBER.getName().equals(configParams.getEntityAttributType())))
									{
										validationFailureReason.append(" Attribute :").append(attributes).append(" should be numeric ");
									}
								}
							}
						}
						else
						{
							String inputNumber = null;
							if(crudAttributes.get(attributes) != null)
							{
								inputNumber = crudAttributes.get(attributes).toString();
							}
							if(inputNumber != null && !isNumberIsValid(inputNumber, CrudConfigConstant.ENTITY_ATTR_TYPE_NUMBER.getName().equals(configParams.getEntityAttributType())))
							{
								validationFailureReason.append(" Attribute :").append(attributes).append(" should be numeric ");
							}
						}
					}
					else if(CrudConfigConstant.ENTITY_ATTR_TYPE_DATE.getName().equals(configParams.getEntityAttributType()))
					{
						if(crudAttributes.get(attributes) != null)
						{
							try
							{
								Timestamp timestamp = new Timestamp(dateFormat.parse(crudAttributes.get(attributes).toString().replaceAll("Z$", "+0000")).getTime());
								crudAttributes.put(attributes, timestamp);
							}
							catch(Exception e)
							{
								validationFailureReason.append(" Attribute :").append(attributes).append(" should be in yyyy-MM-dd'T'HH:mm:ssZ format");
							}
						}
					}
						
				}
			}
		}
		if(validationFailureReason.length() > 0)
		{
			throw new RuntimeException(validationFailureReason.toString());
		}
	}
	
	// change to camelCase
	private void manipulateNonBusinessAttributs(CrudConfig crudConfig,Map<String, Object> crudAttributes, String crudOperationType, Map<String, Object> activity)
	 {
		 Map<String, CrudConfigParams> entityMappingDetails = crudConfig.getEntityMappingDetails();
		 String[] NON_BUSINESS_ATTRIBUTES = {
				 CrudConfigConstant.CREATED_BY.getName(),
				 CrudConfigConstant.CREATED_DATE.getName(),
				 CrudConfigConstant.MODIFIED_BY.getName(),
				 CrudConfigConstant.MODIFIED_DATE.getName(),
				 CrudConfigConstant.IS_ACTIVE.getName(),
				 CrudConfigConstant.VERSION.getName(),
				 CrudConfigConstant.ACTIVITY_ID.getName()};
		 for(String nonBusinessAttribute : NON_BUSINESS_ATTRIBUTES)
		 {
			 if(!entityMappingDetails.containsKey(nonBusinessAttribute))
			 {
				 return;
			 }
		 }
		 crudAttributes.put(CrudConfigConstant.MODIFIED_DATE.getName(), activity.get(CrudConfigConstant.CREATED_DATE.getName()));
		 crudAttributes.put(CrudConfigConstant.MODIFIED_BY.getName(), activity.get(CrudConfigConstant.CREATED_BY.getName()));
		 crudAttributes.put(CrudConfigConstant.ACTIVITY_ID.getName(), activity.get(CrudConfigConstant.ACTIVITY_ID.getName()));
		 if("ADD".equalsIgnoreCase(crudOperationType))
		 {
			 crudAttributes.put(CrudConfigConstant.CREATED_BY.getName(), activity.get(CrudConfigConstant.CREATED_BY.getName()));
			 crudAttributes.put(CrudConfigConstant.CREATED_DATE.getName(), activity.get(CrudConfigConstant.CREATED_DATE.getName()));
			 crudAttributes.put(CrudConfigConstant.IS_ACTIVE.getName(), 1);
			 crudAttributes.put(CrudConfigConstant.VERSION.getName(), 0);
		 }
	 }
	 
	 @Override 
	 public Map<String, Object> generateActivity(String activityType, String user) throws Exception
	 {
		 Map<String, Object> crudAttributesForActivity = new HashMap<String, Object>();
		 CrudConfig crudConfigForActivity = crudConfigService.getCrudConfig("activity");
		 crudAttributesForActivity.put(CrudConfigConstant.ACTIVITY_NAME.getName(), activityType);
		 crudAttributesForActivity.put(CrudConfigConstant.CREATED_BY.getName(), user);
		 
		 long millis = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
		 crudAttributesForActivity.put(CrudConfigConstant.CREATED_DATE.getName(), new Timestamp(millis));
		 
		 Map<String, Object> activityGenerationResponse = crudDao.addDomain(crudConfigForActivity, crudAttributesForActivity);
		 if(activityGenerationResponse != null && activityGenerationResponse.containsKey(CrudConfigConstant.GENERATED_ID.getName()))
		 {
			 crudAttributesForActivity.put(CrudConfigConstant.ACTIVITY_ID.getName(), activityGenerationResponse.get(CrudConfigConstant.GENERATED_ID.getName()));
		 }
		 return crudAttributesForActivity;
	 }
	 private void generateHistory(CrudConfig crudConfig, Map<String, Object> activity, Map<String, Object> filters) throws Exception
	 {
		if(crudConfig.getEntityHistoryName() != null)
		{
			 CrudConfig crudConfigForHist = crudConfigService.getCrudConfig(crudConfig.getEntityHistoryName());
			 Map<String, Object> historyAttributes = new HashMap<String, Object>();
			 historyAttributes.put(CrudConfigConstant.HIST_ATTR_BEGIN_DATE.getName(), activity.get(CrudConfigConstant.CREATED_DATE.getName()));
			 // historyAttributes.put(HIST_ATTR_END_DATE, activity.get(CrudConfigConstant.CREATED_DATE.getName()));
			 crudDao.generateHistory(crudConfigForHist, crudConfig, historyAttributes, filters);
		}
	 }
	 private CrudConfig getCrudConfig(String crudComponent)
	 {
		 CrudConfig crudConfig = null;
		try {
			if(crudComponent != null && !crudComponent.isEmpty())
				crudConfig = crudConfigService.getCrudConfig(crudComponent);
			if(crudConfig == null)
				throw new Exception();
		} catch (Exception e) {
			throw new RuntimeException("Invalid component");
		}
		 return crudConfig;
	 }
	 
	 private boolean isNumberIsValid(String inputNumber, boolean isNumber)
	 {
		 if(isNumber)
		 {
			try 
			{
				Long.parseLong(inputNumber);
			} 
			 catch (Exception e) {
				return false;
			}
			 
		 }
		return inputNumber.matches("-?\\d+(\\.\\d+)?");
	 }
}
