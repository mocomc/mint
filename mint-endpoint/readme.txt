
1 xsd type 매핑

	외부 데이터 유형을 표준 XSD 유형과 매핑하기 위한 명세서를 작성합니다.

	1.1 XSD 표준 Type
		 1) xs:string 				:string data type can take characters, line feeds, carriage returns, and tab characters.
		 2) xs:byte 				:A signed 8 bit integer
		 3) xs:decimal				:A decimal value
		 4) xs:int					:A signed 32 bit integer
		 5) xs:integer				:An integer value
		 6) xs:long					:A signed 64 bit integer
		 7) xs:float                :The float datatype is patterned after the IEEE single-precision 32-bit floating point datatype
		 8) xs:double               :The double datatype is patterned after the IEEE double-precision 64-bit floating point datatype
		 9) xs:negativeInteger		:An integer having only negative values (..,-2,-1)
		10) xs:nonNegativeInteger	:An integer having only non-negative values (0,1,2,..)
		11) xs:positiveInteger		:An integer having only positive values (1,2,..)
		12) xs:nonPositiveInteger	:An integer having only non-positive values (..,-2,-1,0)
		13) xs:short				:A signed 16 bit integer
		14) xs:unsignedLong			:An unsigned 64 bit integer
		15) xs:unsignedInt			:An unsigned 32 bit integer
		16) xs:unsignedShort		:An unsigned 16 bit integer
		17) xs:unsignedByte			:An unsigned 8 bit integer
		18) xs:boolean				:The <xs:boolean> data type is used to represent true, false, 1 (for true) or 0 (for false) value.
		19) xs:base64Binary			:represents base64 encoded binary data
		20) xs:hexBinary			:represents hexadecimal encoded binary data
		21) xs:complex				:complex element which can contain other elements and/or attributes
		22) xs:date					:date type is used to represent date in YYYY-MM-DD format.
		23) xs:time					:time type is used to represent time in hh:mm:ss format.
		24) xs:datetime				:datetime type is used to represent date and time in YYYY-MM-DDThh:mm:ss format.
	1.2 iip(신한 eims) 화면 표현 및 공통코드 등록은 외부 DATA TYPE 기준으로 등록 됩니다.
	1.3 형상관리 및 프레임워크 배포 시에는 XSD 표준 TYPE 으로 표현합니다.
	1.4 nexacro 전문 레이아웃은 /mint-bridge-apps 내 인하우스 변환 소스에서 직접 타입변환을 작성하도록 합니다.
			인하우스 타입 변환 코드 작성 소스 NexacroLayoutService.java
	1.5 매핑 예
		---------------------------------------------------------------------------
		XSD 표준 DATA TYPE 	외부 DATA TYPE
		---------------------------------------------------------------------------
		xs:string 			string, varchar, varchar2, char, longVarchar
		xs:byte
		xs:decimal			number, numeric, decimal
		xs:int
		xs:integer
		xs:long
		xs:float
		xs:double
		xs:negativeInteger
		xs:nonNegativeInteger
		xs:positiveInteger
		xs:nonPositiveInteger
		xs:short
		xs:unsignedLong
		xs:unsignedInt
		xs:unsignedShort
		xs:unsignedByte
		xs:boolean			boolean
		xs:base64Binary		clob, blob
		xs:hexBinary
		xs:complex			gf, gs, gm
		xs:date
		xs:time				time
		xs:datetime			date, timestamp, datetime

	1.6 매핑 정보 등록
		포탈환경설정[TSU0302] 내에 package='laout' 카테고리에 데이타 유형 매핑정보를 등록해 줍니다.
		--------------------------------------------------------------------------------------------------------------------------------
		XSD 표준 DATA TYPE  		ATTRIBUTE_ID                		ATTRIBUTE_VALUE
																	(공통코드에 등록한 데이터 유형 CD 값 등록, SELECT * FROM tsu0301 WHERE LEVEL1 = 'AN' AND LEVEL2 = '09')
		--------------------------------------------------------------------------------------------------------------------------------
		xs:string 				xsd.type.mapping.string				0
		xs:byte 				xsd.type.mapping.byte
		xs:decimal				xsd.type.mapping.decimal    		1
		xs:int					xsd.type.mapping.int
		xs:integer				xsd.type.mapping.integer
		xs:long					xsd.type.mapping.long
		xs:float				xsd.type.mapping.float
		xs:double				xsd.type.mapping.double
		xs:negativeInteger		xsd.type.mapping.negativeInteger
		xs:nonNegativeInteger	xsd.type.mapping.nonNegativeInteger
		xs:positiveInteger		xsd.type.mapping.positiveInteger
		xs:nonPositiveInteger	xsd.type.mapping.nonPositiveInteger
		xs:short				xsd.type.mapping.short
		xs:unsignedLong			xsd.type.mapping.unsignedLong
		xs:unsignedInt			xsd.type.mapping.unsignedInt
		xs:unsignedShort		xsd.type.mapping.unsignedShort
		xs:unsignedByte			xsd.type.mapping.unsignedByte
		xs:boolean				xsd.type.mapping.boolean			2
		xs:base64Binary			xsd.type.mapping.base64Binary		3
		xs:hexBinary			xsd.type.mapping.hexBinary
		xs:complex				xsd.type.mapping.complex			4
		xs:complex				xsd.type.mapping.complex			5
		xs:complex				xsd.type.mapping.complex			6
		xs:complex				xsd.type.mapping.complex			7
		xs:date					xsd.type.mapping.date
		xs:time					xsd.type.mapping.time
		xs:datetime				xsd.type.mapping.datetime
		--------------------------------------------------------------------------------------------------------------------------------
		* 상기 내용은 현재 기준 코통코드 값으로 매핑정보를 등록한 예시입니다.
		* 공통코드 값을 0,1,2,3, .... 숫자로 표현하지 않고 VARCHAR2, CHAR 등 식별 가능한 값으로 변경해도 좋을 듯


		[insert sql 등록 예]
		-- string mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.string',       	1, 'layout.xsd.type.mapping.string',        '0', '공통코드 string 유형 매핑',  		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.string',       	2, 'layout.xsd.type.mapping.string',       '11', '공통코드 VARCHAR2 유형 매핑',		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.string',      	3, 'layout.xsd.type.mapping.string',       '12', '공통코드 CHAR 유형 매핑',    		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.string', 		4, 'layout.xsd.type.mapping.string', 	   '19', '공통코드 LONG VARCHAR 유형 매핑',  'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.string',         5, 'layout.xsd.type.mapping.string',       '17', '공통코드 CLOB 유형 매핑',  		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- decimal mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.decimal',      	1, 'layout.xsd.type.mapping.decimal',       '1', '공통코드 number 유형 매핑',  		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.decimal',      	2, 'layout.xsd.type.mapping.decimal',      '13', '공통코드 NUMBER 유형 매핑',  		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.decimal',      	3, 'layout.xsd.type.mapping.decimal',      '14', '공통코드 NUMERIC 유형 매핑', 		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.decimal',      	4, 'layout.xsd.type.mapping.decimal',      '15', '공통코드 DECIMAL 유형 매핑', 		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.decimal',      	5, 'layout.xsd.type.mapping.decimal',      '16', '공통코드 INTEGER 유형 매핑',  	    'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- boolean mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.boolean',     	1, 'layout.xsd.type.mapping.boolean',       '2', '공통코드 boolean 유형 매핑', 		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- base64Binary mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.base64Binary', 	1, 'layout.xsd.type.mapping.base64Binary',  '3', '공통코드 binary 유형 매핑',  		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.base64Binary',   2, 'layout.xsd.type.mapping.base64Binary', '18', '공통코드 BLOB 유형 매핑', 			'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- complex mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.complex',     	1, 'layout.xsd.type.mapping.complex',       '4', '공통코드 complex 유형 매핑', 		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.complex',      	2, 'layout.xsd.type.mapping.complex',       '5', '공통코드 gs 유형 매핑',      		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.complex',      	3, 'layout.xsd.type.mapping.complex',       '6', '공통코드 gm 유형 매핑',      		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.complex',      	4, 'layout.xsd.type.mapping.complex',       '7', '공통코드 gf 유형 매핑',      		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- date mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.date',      		1, 'layout.xsd.type.mapping.date',      	'20', '공통코드 DATE 유형 매핑', 		'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- dateTime mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.dateTime',      	1, 'layout.xsd.type.mapping.dateTime',      '21', '공통코드 TIMESTAMP 유형 매핑',    'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.dateTime',      	2, 'layout.xsd.type.mapping.dateTime',      '22', '공통코드 DATETIME 유형 매핑',     'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- time mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.time',      		1, 'layout.xsd.type.mapping.time',      	'23', '공통코드 TIME 유형 매핑',     	'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- float mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.float',      	1, 'layout.xsd.type.mapping.float',     	'24', '공통코드 float 유형 매핑',      	'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);
		-- double mapping
		INSERT INTO TSU0302 ("PACKAGE", ATTRIBUTE_ID, IDX, ATTRIBUTE_NM, ATTRIBUTE_VALUE, COMMENTS, DEL_YN, REG_DATE, REG_USER, MOD_DATE, MOD_USER) VALUES('layout', 'xsd.type.mapping.double',      	1, 'layout.xsd.type.mapping.double',      	'25', '공통코드 double 유형 매핑',      	'N', to_char(sysdate,'yyyymmddhh24miss'), 'shl', NULL, NULL);


2 패치 /mint-endpoint
	2.1 배포 포맷 필드 추가
		패치 일시 :
			20201224
		패치 내역 :
			배포 포맷 필드 추가 내역

			[model : 인터페이스 모델]
				deployUserId : 배포자ID
				regDate      : 등록일자
				regUserId    : 등록자
				modDate      : 수정일자
				modUserId    : 수정자

			[layout : 레이아웃]
				regDate      : 등록일자
				regUserId    : 등록자
				modDate      : 수정일자
				modUserId    : 수정자

			추가 예시
			<?xml version="1.0" encoding="UTF-8"?>
			<model xmlns="http://www.w3.org/2001/XMLSchema-instance">
			    <id>143</id>
			    <name>Nexacro_001_Model</name>
			    <stage>0</stage>
			    <createDate>20201223222644</createDate>
			    <version>9</version>
			    <description>Nexacro_001_Model</description>
			    <interface id="Nexacro_001" name="넥사크로001"/>
			    <deployUserId>iip</deployUserId>   -------------> 배포자ID
			    <regDate>20201130160517</regDate>  -------------> 등록일자(YYYYMMDDhh24miss)
			    <regUserId>iip</regUserId>         -------------> 등록자
			    <modDate>20201220172515</modDate>  -------------> 수정일자(YYYYMMDDhh24miss)
			    <modUserId>iip</modUserId>         -------------> 수정자
			    <apps>
			    	....
			    </apps>

			    <layouts>
        			<layout
        				id="NCS12345_I_1"
        				modDate="20201209100346954" -------------> 수정일자(YYYYMMDDhh24miss)
        				modUserId="iip"             -------------> 수정자
        				name="고객계좌정보조회 요청"
        				regDate="20201126125530793" -------------> 등록일자(YYYYMMDDhh24miss)
        				regUserId="iip">            -------------> 등록자
        			<![CDATA[<?xml version="1.0" encoding="UTF-8"?><xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
   		 				<xs:element name="NCS12345_I_1">
   		 				....
  					]]></layout>
					....
				</layouts>

	2.2 배포 오브젝트(ModelDeployment) 필드 추가
		패치 일시 :
			20210112
		패치 내역 :
			1) pep.per.mint.common.data.basic.runtime.ModelDeployment 에 DeploymentInfo 오브젝트 필드 추가
			   (기존 프론트 프로그램 -> 서버 데이터 전송시 영향도 없음 : @JsonIgnoreProperties(ignoreUnknown = true))

			2) pep.per.mint.endpoint.service.deploy.ModelDeployService.deploy 메소드 변경
				/mint-bridge-apps 에 전달할 ModelDeployment 내에 DeploymentInfo 오브젝트 추가

		변경된 모듈 프로젝트
			/mint-endpoint
			/mint-common

		재배포 대상 프로젝트
			/mint-front
			/mint-bridge-apps

		/mint-bridge-apps 에서 배포 정보 수신 시 정보 처리 가이드
			수신 받은 ComMessage 내에서 ModelDeployment 값을 참조하여 신규 추가된 필드 DeploymentInfo 오브젝트를 통해 신규 추가된 배포 타겟 정보를 참조할 수 있다.
				ModelDeployment md = comMessage.getRequestObject();
				DeploymentInfo di = md.getDeploymentInfo();
				String integrationId = di.getIntegrationId();
				String interfaceNm	 = di.getInterfaceNm();
				String businessCd	 = di.getBusinessCd()
				String sndSystemCd 	 = di.getSenderSystemCd()
				String sndSystemNm 	 = di.getSenderSystemNm()
				String rcvSystemCd   = di.getReceiverSystemCd()
				String rcvSystemNm 	 = di.getReceiveSystemNm();






