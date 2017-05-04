package com.unisk.notify.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.unisk.notify.dao.OrderDao;
import com.unisk.notify.model.Order;

public class OrderDaoImpl implements OrderDao {
	private SqlMapClient sqlMap = null;

	public void setSqlMap(SqlMapClient sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public boolean updateorder(String ordernokey, String notifyverifycode,
			String resultcode, String resultdesc) {
		Map<String,Object> parms = new HashMap<String,Object>();		
		parms.put("ordernokey", ordernokey);
		parms.put("notifyverifycode", notifyverifycode);
		//parms.put("resultcode", resultcode);
		parms.put("resultdesc", resultdesc);
		if("00000".equalsIgnoreCase(resultcode))
		{
			parms.put("cooporderstatus", "SUCCESS");	
			parms.put("resultcode",0);
		}
		else
		{
			parms.put("cooporderstatus", "FAILED");
			parms.put("resultcode",Integer.parseInt(resultcode));
		}
		try {
			sqlMap.update("updateorder", parms);			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Order selectasynorder(String notifyverifycode, String ordernokey) {
		Map<String,Object> parms = new HashMap<String,Object>();		
		parms.put("ordernokey", ordernokey);
		parms.put("notifyverifycode", notifyverifycode);		
		try {
			return (Order)sqlMap.queryForObject("selectasynorder", parms);				
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
