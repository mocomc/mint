/*****************************************************************************
 * Program Name : mint.common.js
 * Description
 *   - 공통모듈
 *   - 공통적으로 사용되는 변수나 함수( Util 성격의 function ) 를 정의한다
 *   - Access 방법
 *     mint.common.{함수명};
 *     mint.common.getCommonCodeValue();
 *
 ****************************************************************************/
var mint_common = function() {

};

/*****************************************************************************
 * mint_common variable list
 * (주의) 직접 Access 하지 말고 set/get function 으로 접근 바랍니다
 *****************************************************************************/
/**
 * 로그레벨
 * INFO ERROR DEBUG
 */
mint_common.prototype.logLevel = "DEBUG";

/**
 * 공통 코드가 json형태로 담겨질 Map
 */
mint_common.prototype.commonCodeMap = new Map();

/**
 * 화면간 데이터 전달이 필요할때 key/value 형식으로 담겨질 Parameter Map
 * (주의) key 값은 화면간 규약이므로 연계 되는 화면끼리 맞춰야함
 */
mint_common.prototype.screenParam = new Map();

/**
 * 화면 Label 이 담겨질 Map
 */
mint_common.prototype.screenLabel = new Map();


/**
 * 대시보드에서 사용하는 setInterval() obj 관리
 */
mint_common.prototype.intervalHandler = [];
/**
 * 대시보드에서 사용하는 setTimeout() obj 관리
 */
mint_common.prototype.timeoutHandler = [];

/*****************************************************************************
 * mint_common function list
 *****************************************************************************/

/**
 * screenLabel 에 key/value 값을 설정
 * 중복허용
 * @param key
 * @param value
 */
mint_common.prototype.setScreenLabel = function(key, value) {
	try {
		this.screenLabel.put(key,value);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.setScreenLabel"});
	}
};

/**
 * screenLabel 에서 key 값에 해당하는 value 를 return
 * @param key
 * @returns
 */
mint_common.prototype.getScreenLabel = function(key) {
	try {
		return this.screenLabel.get(key);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getScreenLabel"});
	}
};

/**
 * screenParam 에 key/value 값을 설정
 * 중복허용
 * @param key
 * @param value
 */
mint_common.prototype.setScreenParam = function(key, value) {
	try {
		this.screenParam.put(key,value);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.setScreenParam"});
	}
};

/**
 * screenParam 에서 key 값에 해당하는 value 를 return
 * @param key
 * @returns
 */
mint_common.prototype.getScreenParam = function(key) {
	try {
		return this.screenParam.get(key);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getScreenParam"});
	}
};

/**
 * screenParam 에 설정된 key 값을 모두 초기화 한다.
 * 특별한 경우가 아니면, getScreenParam() 을 호출한 후에는 clearScreenParam() 을 호출하여
 * 화면간 전달시 사용된 정보를 초기화 시킨다
 */
mint_common.prototype.clearScreenParam = function() {
	try {
		this.screenParam.clear();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.clearScreenParam"});
	}
};

/**
 * screenParam 에 설정된 key 값을 초기화 한다.
 */
mint_common.prototype.removeScreenParam = function(key) {
	try {
		this.screenParam.remove(key);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.removeScreenParam"});
	}
};


/**
 * @param jsonData		: parsing할 json data
 * @param key			: json data에서 가져올 key
 * @param defaultStr	: array에 같이 사용할 String
 * @returns {Array}		: value의 배열값
 */
mint_common.prototype.getJsonValueFromKey = function(jsonData, key, defaultStr) {
	try {
		var array = [jsonData.length];

		$.each(jsonData, function(index, element) {
			array[index] = element[key] + defaultStr;
	    });

		return array;
	} catch( e ){
		mint.common.errorLog(e, {"fnName" : "mint.common.getJsonValueFromKey"});
	}
};

/**
 * @param jsonData	: json 형태의 데이터
 * @param key		: return 받을 data의 key
 * @returns		: value값의 연속으로 된 json data(["Albania","Andorra",....])
 */
mint_common.prototype.jsonToAutoCompleteData = function(jsonData, key) {
	try {
		var str = "";

		if(jsonData.length > 0) {
			var arrStr = this.getJsonValueFromKey(jsonData, key);

			str += "[";

			for(var i = 0; i < arrStr.length; i++) {
				str += "\"";
				str += arrStr[i];
				str += "\"";

				if(i < arrStr.length - 1) {
					str += ",";
				}
			}
			str += "]";
		}
		return eval("(" + str + ")");
	} catch( e ){
		mint.common.errorLog(e, {"fnName" : "mint.common.jsonToAutoCompleteData"});
	}
};

/**
 *
 */
mint_common.prototype.changeJsonToArray = function(jsonData) {
	try {
		return obj = $.parseJSON(jsonData);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.changeJsonToArray"});
	}
};

/**
 * @param jsonData		: json 형태의 데이터
 * @returns {Array}	: json의 key 배열
 */
mint_common.prototype.getKeyArrayFromJson = function(jsonData) {
	try {
		var arr = [];
		var index = 0;

		if(jsonData.length > 0) {
			for(var prop in jsonData[0]){
		        if(jsonData[0].hasOwnProperty(prop)){
		            arr[index++] = prop;
		        }
		    }
		}
		return arr;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getKeyArrayFromJson"});
	}
};

/**
 * @param jsonData		: parsing할 json data
 * @param key			: json data에서 가져올 key
 * @returns {Array}	: 시스템의 현재 날짜 (형식 : yyyy-mm-dd)
 */
mint_common.prototype.getTodayDate = function() {
	try {

		var today = new Date();
	    var dd = today.getDate();
	    var mm = today.getMonth()+1; 	//January is 0!
	    var yyyy = today.getFullYear();

	    var todayDate = "";

	    if(dd<10) {
	        dd='0'+dd;
	    }

	    if(mm<10) {
	        mm='0'+mm;
	    }

	    todayDate = yyyy + '-' + mm + '-' + dd;

	    return todayDate;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getTodayDate"});
	}
};


/**
 * @param tempDate		: 새로 만들 날짜 (ex new Date())
 * @returns {Array}		: 새로 만들 날짜 (형식 : yyyymmddHHMMSSMIS)
 */
mint_common.prototype.remakeYYMMDD = function(tempDate) {
	try {

		var rtnTime;

		if(null != tempDate) {
			var yyyy = tempDate.getFullYear().toString();
		    var mm = (tempDate.getMonth() + 1).toString();
		    var dd = tempDate.getDate().toString();
		    var hours = tempDate.getHours().toString();
		    var minutes = tempDate.getMinutes().toString();
		    var seconds = tempDate.getSeconds().toString();
		    var mseconds = tempDate.getMilliseconds().toString();

			rtnTime = yyyy
			+ (mm[1] ? mm : '0'+mm[0])
			+ (dd[1] ? dd : '0'+dd[0])
			+ (hours [1] ? hours : '0'+hours [0])
			+ (minutes[1] ? minutes : '0'+ minutes[0])
			+ (seconds [1] ? seconds : '0'+ seconds [0])
			+ (mseconds [2] ? mseconds : mseconds [1] ? '0'+ mseconds  :'00'+ mseconds);
		} else {
			rtnTime = '';
		}

		return rtnTime;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.remakeYYMMDD"});
	}
};


/**
 * @param addTime	: 현재시간에 더해줄 값
 * @returns			: 현재시간
 */
mint_common.prototype.currentHour = function(addTime) {
	try {
	    var today = new Date();
	    var h = today.getHours() + addTime;

	    return h;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.currentHour"});
	}
};

/**
 *
 * @returns {String}
 */
mint_common.prototype.getStartTime = function() {
	try {
		var d = new Date();

		var s =
		  this.leadingZeros(d.getFullYear(), 4) +
		  this.leadingZeros(d.getMonth() + 1, 2) +
		  this.leadingZeros(d.getDate(), 2) +
		  this.leadingZeros(d.getHours(), 2) +
		  this.leadingZeros(d.getMinutes(), 2) +
		  this.leadingZeros(d.getSeconds(), 2) +
		  d.getMilliseconds();

		return s;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getStartTime"});
	}
};

/**
 *
 * @param n
 * @param digits
 * @returns {String}
 */
mint_common.prototype.leadingZeros = function(n, digits) {
	try {
		var zero = '';
		n = n.toString();

		if (n.length < digits) {
			for (i = 0; i < digits - n.length; i++)
			    zero += '0';
		}
		return zero + n;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.leadingZeros"});
	}
};


/**
 * mint.common.formatDate(new Date(),'yyyyMMddHHmm')
 * @param date
 * @param format
 * @returns
 */
mint_common.prototype.formatDate = function(date, format) {
	try {
		var oldFormat = 'yyyyMMdd';
		if( mint.common.isEmpty(date) ) {
			return date;
		} else {
			if( date.length < 8 ) {
				return date;
			} else if ( date.length == 8 ) {
				oldFormat = 'yyyyMMdd';
			} else if ( date.length == 12 ) {
				oldFormat = 'yyyyMMddHHmm';
			} else {
				oldFormat = 'yyyyMMddHHmm';
			}
		}
		if( !mint.common.isEmpty(format) ) {
			return kendo.toString( kendo.parseDate(date, oldFormat), format );
		} else {
			return kendo.toString( kendo.parseDate(date, oldFormat), 'yyyy/MM/dd' );
		}

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.formatDate"});
	}
}

/**
 *
 * @param date
 * @param format
 * @returns
 */
mint_common.prototype.formatDateTime = function(date, format) {
	try {
		var oldFormat = 'yyyyMMdd';
		if( mint.common.isEmpty(date) ) {
			return date;
		} else {
			if( date.length < 12 ) {
				return date;
			} else if ( date.length == 12 ) {
				oldFormat = 'yyyyMMddHHmm';
			} else if ( date.length == 14 ) {
				oldFormat = 'yyyyMMddHHmmss';
			} else {
				oldFormat = 'yyyyMMddHHmm';
			}
		}
		if( !mint.common.isEmpty(format) ) {
			return kendo.toString(kendo.parseDate(date, oldFormat), format);
		} else {
			return kendo.toString(kendo.parseDate(date, oldFormat), 'yyyy/MM/dd HH:mm');
		}

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.formatDate"});
	}
}



/**
 * 공통 코드 정보를 Map 에 설정한다.<p>
 * @param jsonData
 */
mint_common.prototype.setCommonCode = function(jsonData) {
	try {
	    var arrCommonCode = this.getCommonCodeNameFromJson(jsonData);

	    for(var i=0; i < arrCommonCode.length; i++) {
	        this.commonCodeMap.put(arrCommonCode[i], jsonData[arrCommonCode[i]]);
	    }
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.setCommonCode"});
	}
};

/**
 * 공통 코드 정보를 리턴한다.
 * @param key
 */
mint_common.prototype.getCommonCode = function(key) {
	try {
	    return this.commonCodeMap.get(key);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getCommonCode"});
	}
};

/**
 * 코드값을 가져온다<p>
 */
mint_common.prototype.getCommonCodeValue = function(codeGroupName, code) {
	try {
		var codeGroup = this.commonCodeMap.get(codeGroupName);
		for(var i in codeGroup) {
			if(code == codeGroup[i].cd) {
				return codeGroup[i].nm;
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getCommonCodeValue"});
	}
};

/**
 *
 * @param codeGroupName
 * @param searchCodeName
 * @param targetCodeName
 * @param val
 * @returns
 */
mint_common.prototype.getCodeValueByName = function(codeGroupName, searchCodeName, targetCodeName, val) {
	try {

		var codeGroup = this.commonCodeMap.get(codeGroupName);

	    for(var i in codeGroup) {
	        if(val == codeGroup[i][searchCodeName]) {

	            return codeGroup[i][targetCodeName];
	        }
	    }
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getCodeValueByName"});
	}
};

/**
 *
 * @param jsonData
 * @returns {Array}
 */
mint_common.prototype.getCommonCodeNameFromJson = function(jsonData) {
	try {
		var arr = [];
	    var index = 0;

	    for(var prop in jsonData){
	        if(jsonData.hasOwnProperty(prop)){
	            if(prop != "objectType") {
	            	arr[index++] = prop;
	            }
	        }
	    }

	    return arr;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getCommonCodeNameFromJson"});
	}
};

/**
 * Enviroment Info
 * @returns
 */
mint_common.prototype.getEnvorinment = function() {
	try {
		return  mint.common.getCommonCode('environmentalValues');
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getEnvorinment"});
	}
};


/**
 * Environment Value return
 * @returns
 */
mint_common.prototype.getEnvValue = function(key) {
	try {
		//오타가...
		var env = mint.common.getEnvorinment();
		if(env) {
			return  env[key];
		} else {
			return null;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getEnvValue"});
	}
};


/**
 * Empty 체크
 * @param obj
 * @returns {Boolean}
 */
mint_common.prototype.isEmpty = function(obj) {
	try {
		if( obj !== undefined && obj !== null && obj !== "" ) {
			return false;
		} else {
			return true;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.isEmpty"});
	}
};


/**
 *
 * @param str
 * @param searchStr
 * @param replaceStr
 * @returns
 */
mint_common.prototype.replaceAll = function(str, searchStr, replaceStr) {
	try {
		return str.split(searchStr).join(replaceStr);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.replaceAll"});
	}
};

/**
 * callback 이 함수인지 여부 체크후 함수로 리턴한다
 * @param callback
 * @returns
 */
mint_common.prototype.fn_callback = function(callback) {
	try {
		if( !this.isEmpty(callback) && $.isFunction(eval(callback)) ) {
			return eval(callback);
		} else {
			//alert(callback + '는 함수가 아니거나 지정된 Scope 에서 찾을수 없는 함수입니다');
			return false;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.fn_callback"});
	}
};

/**
 * 사용하지 않을 예정
 * @param obj
 * @returns
 */
mint_common.prototype.fn_object = function(obj) {
	try {
		if( typeof obj == "function" ) {
			return obj;
		} else {
			var fn = window[eval(obj)];
			if( typeof fn == "function" ) {
				return fn;
			} else {
		    	mint.common.alert('CW00006', obj );
		    	return;
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.fn_object"});
	}
};


/**
 * 주어진 정보가 null 인지를 확인한다.<p>
 * @param obj
 * @return null 일 경우  true
 * @returns {Boolean}
 */
mint_common.prototype.isUndefined = function(obj) {
	try {
		return obj === undefined;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.isUndefined"});
	}
};

/**
 * window interval object 를 intervalHandler 에 add
 * @param func
 * @param milliseconds
 */
mint_common.prototype.addInterval = function(func, milliseconds) {
	try {
		var intervalObj = window.setInterval( func, milliseconds );
		this.intervalHandler.push( intervalObj );
		mint.common.debugLog("interval object add[" + intervalObj + "], TCNT : "  + this.intervalHandler.length , {"fnName" : "mint.common.addInterval"});
		return intervalObj;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.addInterval"});
	}
};

/**
 * window timeout object 를 timeoutHandler 에 add
 * @param func
 * @param milliseconds
 */
mint_common.prototype.setTimeout = function(func, milliseconds) {
	try {
		var timeoutObj = window.setTimeout( func, milliseconds );
		this.timeoutHandler.push( timeoutObj );
		mint.common.debugLog("timeout object add[" + timeoutObj + "], TCNT : "  + this.timeoutHandler.length , {"fnName" : "mint.common.setTimeout"});
		return timeoutObj;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.setTimeout"});
	}
};

/**
 * 메인대쉬보드 setInterval Control Object
 * @returns {Boolean}
 */
mint_common.prototype.terminateInterval = function(caller) {

	var terminateIntervalResult = false;
	try {

		debugMsg = "\n";
		debugMsg = debugMsg + " > Caller :: " + caller + "\n";
		debugMsg = debugMsg + " > Before Current Interval Count :: " + this.intervalHandler.length + "\n";
		debugMsg = debugMsg + " > Clear Interval List" + "\n";

		for (var i=0; i<this.intervalHandler.length; i++) {
			window.clearInterval(this.intervalHandler[i]);
			debugMsg = debugMsg + "    IntervalHandler[" + i + "] value<" + this.intervalHandler[i] + "> clearInterval complete!!" + "\n";
		}
		terminateIntervalResult = true;
	} catch (e) {
		mint.common.errorLog(e, {"fnName" : "mint.common.terminateInterval"});
		terminateIntervalResult = false;
	} finally {
		this.intervalHandler = []; // 초기화
		debugMsg = debugMsg + " > After Current Interval Count :: " + this.intervalHandler.length + "\n";
		mint.common.debugLog( debugMsg, {"fnName" : "mint.common.terminateInterval"} );
		return terminateIntervalResult;
	}

};

/**
 * 메인대쉬보드 setTimeout Control Object
 * @returns {Boolean}
 */
mint_common.prototype.terminateTimeout = function(caller) {

	var terminateTiemoutResult = false;
	try {

		debugMsg = "\n";
		debugMsg = debugMsg + " > Caller :: " + caller + "\n";
		debugMsg = debugMsg + " > Before Current Timeout Count :: " + this.timeoutHandler.length + "\n";
		debugMsg = debugMsg + " > Clear Timeout List" + "\n";

		for (var i=0; i<this.timeoutHandler.length; i++) {
			window.clearTimeout(this.timeoutHandler[i]);
			debugMsg = debugMsg + "    timeoutHandler[" + i + "] value<" + this.timeoutHandler[i] + "> clearTimeout complete!!" + "\n";
		}
		terminateTiemoutResult = true;
	} catch (e) {
		mint.common.errorLog(e, {"fnName" : "mint.common.terminateTimeout"});
		terminateTiemoutResult = false;
	} finally {
		this.timeoutHandler = []; // 초기화
		debugMsg = debugMsg + " > After Current Timeout Count :: " + this.timeoutHandler.length + "\n";
		mint.common.debugLog( debugMsg, {"fnName" : "mint.common.terminateTimeout"} );
		return terminateTiemoutResult;
	}

};

/**
 * 입력된 필드값이 Null 인지 체크
 * @param fieldObj      체크할 필드 Object
 * @param isShowMsg     Alert Message 를 Open 할것인지 여부
 * @param fieldNm       체크할 필드의 명칭
 * @param focusObj      foucus 를 이동할 필드 Object, 보통은 fieldObj
 * @returns {Boolean}   null 인 경우 true, 그외 false
 */
mint_common.prototype.isFieldEmpty = function(fieldObj, isShowMsg, fieldNm, focusObj) {
	try {
		var str = null;
		if( typeof fieldObj == "string") {
			str = fieldObj;
		} else {
			try {
				str = fieldObj.val();
			} catch( e ) {
				str = null;
			}

		}

		if( !focusObj ) { focusObj = fieldObj; }

		if( ! mint.common.isEmpty( str ) ) {
			return false;
		} else {
			if( isShowMsg ) {
				if( fieldNm ) {
					mint.common.alert("CW00001", fieldNm); // [${}] 값을 입력하여 주십시오.
				} else {
					mint.common.alert("CW00002");          // "값을 입력하여 주십시오."
				}

				if( focusObj ) {
					try {
						var position = focusObj.offset();
						$('html,body').animate({scrollTop:position.top - 100},20);
					} catch( e ) {}
					focusObj.focus();
				}
			}
			return true;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.isFieldEmpty"});
		return false;
	}
};


/**
 * Alert Dialog
 * @param msg     Alert 창에 뿌려줄 Message
 * @param param   Message 내용중에 치환할 문자
 */
mint_common.prototype.alert = function(msg, param) {
	try {
		var formatMsg = mint.message.getMessage(msg, param);
		if( formatMsg ) {
			alert( formatMsg );
		} else {
			alert( msg );
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.alert"});
	}
};

/**
 * Confirm Dialog
 * @param msg
 * @param param
 * @returns
 */
mint_common.prototype.confirm = function(msg, param) {
	try {
		var formatMsg = mint.message.getMessage(msg, param);
		if( formatMsg ) {
			return confirm( formatMsg );
		} else {
			return confirm( msg );
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.confirm"});
	}
}

/**
 * Notification
 * @param msg     조회결과 Message
 * @param param   Message 내용중에 치환할 문자
 */
mint_common.prototype.notification = function(msg, options) {
	try {
		var obj;
		toastr.options = {
			"onclick"           : null,
			"closeButton"       : true,
			"debug"             : false,
			"progressBar"       : false,
			"preventDuplicates" : true,
			"positionClass"     : "toast-bottom-right",
			"showDuration"      : "400",
			"hideDuration"      : "1000",
			"timeOut"           : (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.timeOut)) ? options.timeOut : "1000",
			"extendedTimeOut"   : (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.extendedTimeOut)) ? options.extendedTimeOut : "1000",
			"showEasing"        : "swing",
			"hideEasing"        : "linear",
			"showMethod"        : "fadeIn",
			"hideMethod"        : "fadeOut"
		};

		var type = (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.type)) ? options.type : '';
		if( mint.common.isEmpty(type) ) {
			obj = toastr.success(msg,'');
		} else {
			if( type == 'info')
				obj = toastr.info(msg,'');
			if( type == 'success')
				obj = toastr.success(msg,'');
			if( type == 'warning')
				obj = toastr.warning(msg,'');
			if( type == 'error')
				obj = toastr.error(msg,'');
		}
		return obj;

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.notification"});
	}
};

/**
 * Job-Notification
 * @param msg     Job Message
 * @param param   Message 내용중에 치환할 문자
 */
mint_common.prototype.jobNotification = function(msg, type, options) {
	try {
		var obj;
		toastr.options = {
				"onclick"           : null,
				"closeButton"       : (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.closeButton)) ? options.closeButton : false,
				"debug"             : false,
				"progressBar"       : false,
				"preventDuplicates" : (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.preventDuplicates)) ? options.preventDuplicates : true,
				"positionClass"     : "toast-bottom-right",
				"showDuration"      : "400",
				"hideDuration"      : "1000",
				"timeOut"           : "0",
				"extendedTimeOut"   : "0",
				"showEasing"        : "swing",
				"hideEasing"        : "linear",
				"showMethod"        : "fadeIn",
				"hideMethod"        : "fadeOut",
				"tapToDismiss"      : false,
				"newestOnTop"       : false,
			};
			if( mint.common.isEmpty(type) ) {
				obj = toastr.info(msg,'');
			} else {
				if( type == 'info')
					obj = toastr.info(msg,'');
				if( type == 'success')
					obj = toastr.success(msg,'');
				if( type == 'warning')
					obj = toastr.warning(msg,'');
				if( type == 'error')
					obj = toastr.error(msg,'');
			}
			return obj;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.jobNotification"});
	}
};

/**
 * notification clear
 * @param element
 */
mint_common.prototype.notificationClear = function(element) {
	try {
		if(!mint.common.isEmpty(element)) {
			toastr.clear(element);
		} else {
			toastr.clear();
		}

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.notificationClose"});
	}
};

/**
 * Info Message Dialog
 * @param msg
 * @param options
 */
mint_common.prototype.infoDialog = function(msg, options) {

};

/**
 * Warning Message Dialog
 * @param msg
 * @param options
 */
mint_common.prototype.warningDialog = function(msg, options) {

};


/**
 * Error Message Dialog
 * @param errorType
 * @param errorCd
 * @param errorMsg
 * @param errorDetail
 */
mint_common.prototype.errorDialog = function(errorType, errorCd, errorMsg, errorDetail) {
	try {

		var errorDetailView = false;
		var attachDiv = 'errorPopup';
		$(".modal-backdrop").remove();
		$('#' + attachDiv).remove();
		$('body').append($('<div id="' + attachDiv + '"></div>'));

		$.ajax({
			url: "../sub-co/CO-01-00-103.html",
     		success:function(data){
     			$('#' + attachDiv).html('');
         		$('#' + attachDiv).html(data);

         		$("#CO-01-00-103-Title").html(errorType + " Error");
         		$("#CO-01-00-103-errorCd").html(errorCd);
         		$("#CO-01-00-103-errorMsg").html(errorMsg);
         		if( errorDetailView ) {
         			$("#CO-01-00-103-errorDetail").html(errorDetail);
         		}

         		//등록된 사용자가 아닐경우(1100), 권한이 없을 경우(1104)login.jsp 로 이동.
         		if( errorCd == 1100 || errorCd == 1104) {
         			$('.btn-pop-close').click(function(){
         				window.location = '../main/login.jsp';
         			});

         		}
         		//웹서비스 세션이 종료되었을때 초기화면으로 이동
         		if( errorCd == 1102 ) {
         			$('.btn-pop-close').click(function(){
         				window.location = '../../do';
         			});
         		}

         		//로그인된 계정외에 다른 계정으로 로그인을 시도했을경우
         		//관련 정보를 display 하고 사용자 의사결정을 가이드한다

         		if( errorCd == 1103 ) {
         			/*
         			$('.btn-pop-logout').show();
         			$('.btn-pop-logout').click(function(){
         				mint.session.logOut();
         			});
         			*/
         			$('.btn-pop-close').click(function(){
         				window.location = '../../do';
         			});
         		}



         		$('#errorDialog').modal({
    				backdrop: 'static'
    			}).on('hidden.bs.modal', function(event) {
    				$('#' + attachDiv).html('');
    			});

         		$('.modal-backdrop').css('z-index', mint.common.getTopZindex());
         		$('#errorDialog').css('z-index', mint.common.getTopZindex());
     		}
 		});

		mint.ui.initScreen({isPopupScroll:true, isDraggable:false});
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.errorDialog"});
	}
};

/**
 * 팝업호출[검색용 팝업]
 * @param screenPath
 * @param screenId
 */
mint_common.prototype.searchPopup = function(screenPath, screenId) {
	try {
		//팝업에 필요한 div 를 동적으로 생성하도록 한다.
		var attachSearchDiv = 'attachSearchPopup';
		$('#' + attachSearchDiv).remove();
		$('#page-wrapper').append($('<div id="' + attachSearchDiv + '"></div>'));

		$.ajax({
     		url: screenPath,
     		success:function(data){
     			$('#' + attachSearchDiv).html('');
         		$('#' + attachSearchDiv).html(data);

         		$('#' + screenId).modal({
    				backdrop: 'static'
    			}).on('hidden.bs.modal', function(event) {
    				$('#' + attachSearchDiv).html('');
    			});

         		$('.modal-backdrop').css('z-index', mint.common.getTopZindex());
         		$('#' + screenId).css('z-index', mint.common.getTopZindex());
     		}
 		});
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.searchPopup"});
	}
}


mint_common.prototype.focus = function(focusObj) {
	try {
		if(focusObj) {
			var position = focusObj.offset();
			$('html,body').animate({scrollTop:position.top - 100},20);
			focusObj.focus();
		}
		return;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.focus"});
	}
}




/**
 * InfoLog
 * @param msg
 * @param options
 */
mint_common.prototype.infoLog = function(msg, options) {
	try {
		if( this.logLevel == "INFO" || this.logLevel == "DEBUG" ) {
			console.log("-----------------------------------------------------------");
			console.log("[Info]");
			if( ! this.isEmpty( options ) ) {
				var paramsKeys = Object.keys(options);
				if( paramsKeys && paramsKeys.length > 0 ) {
					// url 값으로 변경한다.
					for(var i = 0; i < paramsKeys.length; i++) {
						console.log(paramsKeys[i] + " : " + options[paramsKeys[i]]);
					}
				}
			}
			console.log(msg);
			console.log("-----------------------------------------------------------");
		}
	} catch( e ) {
		console.log(e);
	}
};

/**
 * ErrorLog
 * @param msg
 * @param options
 */
mint_common.prototype.errorLog = function(msg, options) {
	try {
		console.log("-----------------------------------------------------------");
		console.log("[Error]");
		if( ! this.isEmpty( options ) ) {
			var paramsKeys = Object.keys(options);
			if( paramsKeys && paramsKeys.length > 0 ) {
				// url 값으로 변경한다.
				for(var i = 0; i < paramsKeys.length; i++) {
					console.log(paramsKeys[i] + " : " + options[paramsKeys[i]]);
				}
			}
		}
		console.log(msg);
		console.log("-----------------------------------------------------------");


		var envVal = mint.common.getEnvValue('ui.script.error.alert');
		var isAlertView = (!mint.common.isEmpty(envVal) && envVal ==='Y') ? true : false;
		if( isAlertView ) {
			mint.common.alert(msg);
		}
	} catch( e ) {
		console.log(e);
	}
};

/**
 * DebugLog
 * @param msg
 * @param options
 */
mint_common.prototype.debugLog = function(msg, options) {
	try {
		if( this.logLevel == "DEBUG" ) {
			console.log("-----------------------------------------------------------");
			console.log("[Debug]");
			if( ! this.isEmpty( options ) ) {
				var paramsKeys = Object.keys(options);
				if( paramsKeys && paramsKeys.length > 0 ) {
					// url 값으로 변경한다.
					for(var i = 0; i < paramsKeys.length; i++) {
						console.log(paramsKeys[i] + " : " + options[paramsKeys[i]]);
					}
				}
			}
			console.log(msg);
			console.log("-----------------------------------------------------------");
		}
	} catch( e ) {
		console.log(e);
	}
};

mint_common.prototype.replaceNull = function(str) {
	try {
		if(typeof str != "undefined" && str != null) {
            return str;
        } else {
            return "";
        }
    } catch ( e ) {
    	mint.common.errorLog(e, {"fnName" : "mint.common.replaceNull"});
    }
}

mint_common.prototype.addComma = function(val) {
	try {
		return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    } catch ( e ) {
    	mint.common.errorLog(e, {"fnName" : "mint.common.addComma"});
    }
}


/**
 * 공통코드 값 찾기
 * @param msg
 * @param options
 */
mint_common.prototype.findCommonCode = function(commonCode, findValue, findName) {
	try {
		var rtnCommonCode;

		for(var i=0; i<commonCode.length; i++) {
			if(findName == commonCode[i][findValue]) {
				rtnCommonCode = commonCode[i];
			}
		}
		return rtnCommonCode;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.findCommonCode"});
	}
}

/**
 * Top z-index 얻어오기.
 */
mint_common.prototype.getTopZindex = function(){
	try {
	    var maxZ = Math.max.apply(
	    			null,
	    			$.map( $('body > *'),
	    			function(e,n) {
	    				return parseInt($(e).css('z-index')) || 1;
	    			})
	    );
	    return Number(maxZ) + 10;

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.getTopZindex"});
	}
}


/**
 *Sitempath
 */
mint_common.prototype.siteMenuPath = function(screenName){
	try {
		mint.callService(null, screenName, 'REST-R01-CO-03-00-002', function(jsonData) {
			if( ! mint.common.isEmpty(jsonData) ) {
				var htmlTemplate = kendo.template($('#template-menuPath').html());
               $('#menuPath').html( htmlTemplate(jsonData) );
               $("#oldPath").hide();
			};
		}, { errorCall : true, waitingView: false, params : { appCd : screenName} }
		);
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.common.siteMenuPath"});
	}
}

/**
 * 입력값 체크
 * @param str
 * @returns
 */
mint_common.prototype.validate = function(str) {
	try {
		if( mint.common.isEmpty(str) ) {
			return true;
		} else {
			//----------------------------------------------------------------------------
			//validate + 영문으로 시작 정규표현식
			//var validateKey = /^[a-zA-Z][a-zA-Z0-9_-]*(?:\[(?:\d*|[a-zA-Z0-9_-]+)\])*$/;
			//----------------------------------------------------------------------------
			var validateKey = /^[a-zA-Z0-9_-].*(?:\[(?:\d*|[a-zA-Z0-9_-]+)\])*$/;
			return validateKey.test(str);
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.validate"});
	}
}

/**
 * 첨부파일이 유효한 파일명인지 체크
 * @param str
 * @returns
 */
mint_common.prototype.invalidFileName = function(str) {
	try {
		var validateKey = /[!|@|#|$|%|^|&]/;
		return validateKey.test(str);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.invalidFileName"});
	}
}

/**
 * 패스워드 유효 체크
 * 영문자 + 숫자 + 특수문자, 자릿수 8자리 이상일 경우 true
 * 포털 환경변수 env['implement.user.password.check'][0] = true 일 경우에만 유효성 체크 수행
 * @param String password
 * @returns boolean true/false
 * @since 201709
 */
mint_common.prototype.isValidPassword = function(password) {
	try {

		var valid = true;
		var env = mint.common.getEnvorinment();
		var flag = env['implement.user.password.check'];
		var expression = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,18}/;
		if ( !mint.common.isEmpty(flag) && flag[0] === 'true' ) {
			valid = expression.test(password);
		}
		return valid;

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.common.isValidPassword"});
	}

}

/**
 * mint 객체에 common 추가<p>
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.common === "undefined") {
	        mint.common = new mint_common();
	    }
	} catch( e ) {
		console.log(e);
	}
});