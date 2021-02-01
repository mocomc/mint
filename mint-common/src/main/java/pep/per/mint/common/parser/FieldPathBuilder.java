package pep.per.mint.common.parser;

import java.util.Collection;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.util.Util;

public class FieldPathBuilder {

	final static Logger logger = LoggerFactory.getLogger(FieldPathBuilder.class);
	
	public static MessageSet buildPath(MessageSet messageSet, Object caller, StringBuffer debugBuff) {
		if(logger.isDebugEnabled()){
			debugBuff
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(caller.getClass().getName())
				.append(" buildPath start.").append(Util.LINE_SEPARATOR);
		}
		
		LinkedHashMap<Object, FieldDefinition> map = messageSet.getFieldDefinitionMap();
		Collection<FieldDefinition> c = map.values();
		String msgsetName = messageSet.getName();
		for (FieldDefinition fd : c) {
			FieldPath path = new FieldPath(msgsetName);
			setPath(fd, path, debugBuff);
		}
		
		if(logger.isDebugEnabled()){
			debugBuff
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] ")
				.append(caller.getClass().getName())
				.append(" buildPath end.").append(Util.LINE_SEPARATOR);
		}
		
		return messageSet;
	}

	public static void setPath(FieldDefinition fd, FieldPath path, StringBuffer debugBuff) {
		if(fd.isFieldSet()){
			FieldSetDefinition fsd = (FieldSetDefinition)fd;
			String name = fsd.getName();
			path.addPath(name);
			fsd.setFieldPath(path);
			Collection<FieldDefinition> fc = fsd.getFieldDefinitionMap().values();
			for (FieldDefinition fieldDefinition : fc) {
				FieldPath subPath = new FieldPath();
				for(String pStr : path.getPathList()) subPath.addPath(pStr);
				setPath(fieldDefinition, subPath, debugBuff); 
			}
		}else{
			String name = fd.getName();
			path.addPath(name);
			fd.setFieldPath(path);
		}
		if(logger.isDebugEnabled()){
			debugBuff
				.append("# [")
				.append(Util.getFormatedDate("yyyyMMdd HH:mm:ss.SSS"))
				.append("] FieldDefinition :")
				.append(Util.toJSONString(fd))
				.append(" set.").append(Util.LINE_SEPARATOR);
		}
	}

}
