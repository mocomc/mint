package pep.per.mint.common.message;

import java.util.Collection;
import java.util.LinkedHashMap;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * <pre>
 * MessageSet 생성 시점에 호출되어 MessageSet 내에서 필드패스 또는 필드ID를 키값으로 FieldDefinition을 키검색 가능하도록 검색용 Map을 구성하는 유틸리티 클래스
 * </pre>
 * </blockquote>
 * @author Solution TF
 *
 */
public class PathInitializer {
	
	/**
	 * <blockquote>
	 * <pre>
	 * MessageSet 생성 시점에 호출되어 MessageSet 내에서 필드패스 또는 필드ID를 키값으로 FieldDefinition을 키검색 가능하도록 검색용 Map을 구성하는 유틸리티 함
	 * </pre>
	 * </blockquote>
	 * @param msgSet
	 * @param caller
	 * @param debugBuff
	 */
	public static void initializePath(MessageSet msgSet, Object caller, StringBuffer debugBuff) {
		if(debugBuff != null){
			debugBuff
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(caller.getClass().getName())
				.append(" initializePath start.").append(Util.LINE_SEPARATOR);
		}
		
		LinkedHashMap<Object, FieldDefinition> map = msgSet.getFieldDefinitionMap();
		Collection<FieldDefinition> c = map.values();
		String msgsetName = msgSet.getName();
		for (FieldDefinition fd : c) {
			FieldPath path = new FieldPath(msgsetName);
			setPath(fd, path, debugBuff, msgSet);
		}
		
		if(debugBuff != null){
			debugBuff
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(caller.getClass().getName())
				.append(" initializePath end.").append(Util.LINE_SEPARATOR);
		}
	}

	public static void setPath(FieldDefinition fd, FieldPath path, StringBuffer debugBuff, MessageSet msgSet) {
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			String name = fsd.getName();
			path.addPath(name);
			fsd.setFieldPath(path);
			
			
			Collection<FieldDefinition> fc = fsd.getFieldDefinitionMap().values();
			for (FieldDefinition fieldDefinition : fc) {
				FieldPath subPath = new FieldPath();
				for(String pStr : path.getPathList()) subPath.addPath(pStr);
				setPath(fieldDefinition, subPath, debugBuff, msgSet); 
			}
		}else{
			String name = fd.getName();
			path.addPath(name);
			fd.setFieldPath(path);
			
			
		}
		
		
		//=========================================================
		//Solution TF 2014.07
		//field path 로 field 정의를 검색가능하도록 collection 추가함 
		msgSet.getFieldDefinitionPathMap().put(path, fd);
		
		//=========================================================
		//Solution TF 2014.07
		//field id 로 field 정의를 검색가능하도록 collection 추가함 
		msgSet.getFieldDefinitionIdMap().put(fd.getId(), fd);
		
		if(debugBuff != null){
			debugBuff
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] FieldDefinition :")
				.append(Util.toJSONString(fd))
				.append(" set.").append(Util.LINE_SEPARATOR);
		}
	}
}
