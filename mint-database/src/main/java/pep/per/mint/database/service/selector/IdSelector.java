/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.selector;


import java.util.Map;

/**
 * 사이트별로 상이한 엔터티 ID 채번룰을 적용하기 위한 인터페이스
 * @author Solution TF
 *
 *
 */
public interface IdSelector {

	//---------------------------------------------------------
	//chg-what : 삼성전기소스에서 쓰던 함수 제거
	//---------------------------------------------------------
	//chg-when : 170921
	//chg-who  : whoana
	//---------------------------------------------------------
	//public Map selectInterfaceId(Object []params) throws Throwable;
	//public Map selectSystemId(Object []params) throws Throwable;
	//public Map selectBusinessId(Object []params) throws Throwable;
	//public Map getChangedInterfaceInfo(Object []params) throws Throwable;
	//---------------------------------------------------------

	public String getInterfaceId(Object []params) throws Throwable;
}
