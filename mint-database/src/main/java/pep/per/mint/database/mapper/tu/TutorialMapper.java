package pep.per.mint.database.mapper.tu;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.tutorial.Flower;

public interface TutorialMapper {

	List<Flower> getFlowerList(Map params) throws Exception;
	
}
