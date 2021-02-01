package pep.per.mint.database.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.util.Util;

public class ObjectTypeHandler implements TypeHandler{

 
	
	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		try{
			String strValue = rs.getString(columnName);
			Object res = parseObjectValue(strValue);
			return res;
		}catch(Exception e){
			throw new SQLException(e);
		}
	}


	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		try{
			String strValue = rs.getString(columnIndex);
			Object res = parseObjectValue(strValue);
			return res;
		}catch(Exception e){
			throw new SQLException(e);
		}
		
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		try{
			String strValue = cs.getString(columnIndex);
			Object res = parseObjectValue(strValue);
			return res;
		}catch(Exception e){
			throw new SQLException(e);
		}
	}

	@Override
	public void setParameter(PreparedStatement ps, int parameterIndex, Object parameterValue, JdbcType jdbcType) throws SQLException {
		ps.setObject(parameterIndex, parameterValue);
	}
	
	Object parseObjectValue(String string) throws Exception{
		
		if(Util.isEmpty(string))return null;
		
		Map<String, Object> map = Util.jsonToMap(string);
		
		if(map == null) return null;
		
		Object objectType = map.get("type");
		Object objectValue = map.get("value");
		if(objectType == null) return null;
		if(objectValue == null) return null;
		Integer type = (Integer)objectType;
		String value = (String)objectValue;
		
		Object res = null;
		
		switch(type){
		case FieldDefinition.FIELD_TYPE_BINARY :
			res = null;
			break;
		case FieldDefinition.FIELD_TYPE_STR_SHORT :
			res = Short.parseShort(value);
			break;			
		case FieldDefinition.FIELD_TYPE_STR_INTEGER :
			res = Integer.parseInt(value);
			break;
		case FieldDefinition.FIELD_TYPE_STR_LONG :
			res = Long.parseLong(value);
			break;
		case FieldDefinition.FIELD_TYPE_STR_FLOAT :
			res = Float.parseFloat(value);
			break;
		case FieldDefinition.FIELD_TYPE_STR_DOUBLE :
			res = Double.parseDouble(value);			
			break;
		case FieldDefinition.FIELD_TYPE_BIN_SHORT :
			res = Short.parseShort(value);
			break;			
		case FieldDefinition.FIELD_TYPE_BIN_INTEGER :
			res = Integer.parseInt(value);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_LONG :
			res = Long.parseLong(value);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_FLOAT :
			res = Float.parseFloat(value);
			break;
		case FieldDefinition.FIELD_TYPE_BIN_DOUBLE :
			res = Double.parseDouble(value);	
			break;
		case FieldDefinition.FIELD_TYPE_BOOLEAN :
			res = Boolean.parseBoolean(value);
			break;
		case FieldDefinition.FIELD_TYPE_STRING :
			res = value;
			break;
		default:
			res = value;
		}
		return res;
	}


}
