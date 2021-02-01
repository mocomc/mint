package pep.per.mint.database.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class StringYnTypeHandler implements TypeHandler<Boolean>{

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return parseBooleanValue(rs.getString(columnName));
	}


	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return parseBooleanValue(rs.getString(columnIndex));
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return parseBooleanValue(cs.getString(columnIndex));
	}

 
	
	@Override
	public void setParameter(PreparedStatement ps, int parameterIndex, Boolean parameterValue, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(parameterIndex, parseStringValue(parameterValue));
	}
	
	/**
	 * Y or y 일 경우에만 true 그외에는 모두 false
	 * @param string
	 * @return
	 */
	private Boolean parseBooleanValue(String string) {
		// TODO Auto-generated method stub
		return string == null ? Boolean.FALSE : ("Y".equals(string.toUpperCase()) ? Boolean.TRUE : Boolean.FALSE);
	}
	
	/**
	 * false or null 이면 N, true 이면 Y
	 * @param parameterValue
	 * @return
	 */
	private String parseStringValue(Boolean parameterValue) {
		// TODO Auto-generated method stub
		
		System.out.println("####mungmung:여기 실행되나?" +  parameterValue);
		
		return parameterValue == null ? "N" : parameterValue ? "Y" : "N";
	}
	
	/**
	 * for local test
	 * @param args
	 */
	public static void main(String[] args) {
		StringYnTypeHandler sth = new StringYnTypeHandler();
		System.out.println(sth.parseBooleanValue("Y"));
		System.out.println(sth.parseBooleanValue("y"));
		System.out.println(sth.parseBooleanValue(null));
		System.out.println(sth.parseBooleanValue("N"));
		System.out.println(sth.parseBooleanValue("n"));

		System.out.println(sth.parseStringValue(Boolean.FALSE));
		System.out.println(sth.parseStringValue(false));
		System.out.println(sth.parseStringValue(null));
		System.out.println(sth.parseStringValue(Boolean.TRUE));
		System.out.println(sth.parseStringValue(true));
	}

}
