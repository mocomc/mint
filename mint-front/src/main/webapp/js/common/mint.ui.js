/*****************************************************************************
 * Program Name : mint.ui.js
 * Description
 *   - UI Components 에 관련된 Function 을 정의 한다
 *   - Access 방법
 *     mint.ui.{함수명};
 *     mint.ui.setComboBoxWithValue(p1,p2,p3,p4);
 *
 *
 ****************************************************************************/
var mint_ui = function() {

};



/**
 *
 * @param radioButtonId	: radio button id
 * @param jsonData		: radio button에 들어갈 json 형태의 데이터
 * @param selectIndex	: radio button 선택 인덱스 0 ~ n
 * @param dataValueField	: radio button 표시 Value
 * @param dataTextField	: radio button 표시 텍스트
 * @param groupName		: radio button 그룹명
 */
mint_ui.prototype.setRadioButton = function(radioButtonId, jsonData, selectIndex, dataValueField, dataTextField, groupName, options) {
	try {
		var dataArr = [];
		if(selectIndex == "") {
			selectIndex = 0;
		}

		if(!mint.common.isEmpty(options)) {
			dataArr.push(options);
		}

		for (var idx = 0; idx < jsonData.length; idx++) {
			 dataArr.push(jsonData[idx]);
        }

		var radioButtonGroup = $("#" + radioButtonId).kendoExtRadioButtonGroup({
			dataSource: dataArr,
			dataValueField: dataValueField,
			dataTextField: dataTextField,
			groupName: groupName,
			orientation: "horizontal"
		}).data("kendoExtRadioButtonGroup");

		radioButtonGroup.value(dataArr[selectIndex][dataValueField]);

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setRadioButton"});
	}
};

/**
 * 삭제대상??? 확인후 삭제할 것
 * @param comboBoxId	: combo box id
 * @param jsonData		: combo box에 들어갈 json 형태의 데이터
 * @param selectIndex	: combo box 선택 인덱스 0 ~ n
 * @param width			: combo box 가로 사이즈
 * @param height		: combo box 세로 사이즈
 * 공통 사용하고 난 후 삭제 예정
 */
mint_ui.prototype.setComboBox = function(comboBoxId, jsonData, selectIndex, width, height) {
	try {
		if( mint.common.isEmpty(selectIndex) ) {
			selectIndex = 0;
		}

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + comboBoxId).data('kendoComboBox');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		$("#" + comboBoxId).width(width).height(height).kendoComboBox({
			dataTextField: "text",
			dataValueField: "value",
			dataSource: jsonData,
			filter: "contains",
			suggest: false,
			index: selectIndex,
			height:300
		});
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setComboBox"});
	}
};


/**
 * 삭제대상??? 확인후 삭제할 것
 * @param comboBoxId	: combo box id
 * @param jsonData		: combo box에 들어갈 json 형태의 데이터
 * @param selectIndex	: combo box 선택 인덱스 0 ~ n
 * @param width			: combo box 가로 사이즈
 * @param height		: combo box 세로 사이즈
 */
mint_ui.prototype.setComboBoxWithValue = function(comboBoxId, jsonData, selectIndex, dataTextField, dataValueField) {
	try {
		if( mint.common.isEmpty(selectIndex) ) {
			selectIndex = 0;
		}

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + comboBoxId).data('kendoComboBox');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		$("#" + comboBoxId).kendoComboBox({
			dataTextField: dataTextField,
			dataValueField: dataValueField,
			autoBind: false,
			dataSource: jsonData,
			filter: "contains",
			highlightFirst: false,
			suggest: false,
			index: '',
			autoWidth: true,
			height:300,
			open: function() {
				/*
				var width = determineWidth(this.dataSource.data(), dataTextField);
				var combobox = $("#" + comboBoxId).data("kendoComboBox");

				if(combobox.list.width() < width) {
					combobox.list.width(width);
				}
				*/
			},
			/*
            virtual: {
                itemHeight: 26,
            },
            */
		});

		function determineWidth(ds, dataValue) {
		    var l = ds.length;
		    var selement = document.createElement('span');
		    var maxwidth = 0;
		    var curwidth = 0;
		    selement.style = 'position: fixed; left: -500px; top: -500px;';
		    document.body.appendChild(selement);
		    for (var i = 0; i < l; i++) {
		        $(selement).html(ds[i][dataValue]);

		        curwidth = $(selement).width();

		        if (curwidth < maxwidth) {
		            continue;
		        } else {
		        	maxwidth = curwidth;
		        }
		    }
		    document.body.removeChild(selement);

		    return maxwidth + 50;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setComboBoxWithValue"});
	}
};


/**
 * 삭제대상??? 확인후 삭제할 것
 * @param comboBoxId	: combo box id
 * @param jsonData		: combo box에 들어갈 json 형태의 데이터
 * @param selectIndex	: combo box 선택 인덱스 0 ~ n
 * @param width			: combo box 가로 사이즈
 * @param height		: combo box 세로 사이즈
 */
mint_ui.prototype.setComboBoxWithValueSort = function(comboBoxId, jsonData, selectIndex, dataTextField, dataValueField, options) {
	try {
		if( mint.common.isEmpty(selectIndex) ) {
			selectIndex = 0;
		}

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + comboBoxId).data('kendoComboBox');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		var dataSource1 = new kendo.data.DataSource({
			  data:jsonData,
			  sort: { field: dataTextField, dir: "asc" }
			});
		$("#" + comboBoxId).kendoComboBox({
			dataTextField: dataTextField,
			dataValueField: dataValueField,
			autoBind: false,
			dataSource: dataSource1,
			filter: "contains",
			highlightFirst: false,
			suggest: false,
			index: '',
			autoWidth: (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.autoWidth)) ? options.autoWidth : true,
			height:300,
			open: function() {
				/*
				var width = determineWidth(this.dataSource.data(), dataTextField);
				var combobox = $("#" + comboBoxId).data("kendoComboBox");

				if(combobox.list.width() < width) {
					combobox.list.width(width);
				}
				*/
			},
			/*
            virtual: {
                itemHeight: 26,
            },
            */
		});

		function determineWidth(ds, dataValue) {
		    var l = ds.length;
		    var selement = document.createElement('span');
		    var maxwidth = 0;
		    var curwidth = 0;
		    selement.style = 'position: fixed; left: -500px; top: -500px;';
		    document.body.appendChild(selement);
		    for (var i = 0; i < l; i++) {
		        $(selement).html(ds[i][dataValue]);

		        curwidth = $(selement).width();

		        if (curwidth < maxwidth) {
		            continue;
		        } else {
		        	maxwidth = curwidth;
		        }
		    }
		    document.body.removeChild(selement);

		    return maxwidth + 50;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setComboBoxWithValue"});
	}
};


/**
 * @param comboBoxId	: combo box id
 * @param jsonData		: combo box에 들어갈 json 형태의 데이터
 * @param selectIndex	: combo box 선택 인덱스 0 ~ n
 * @param dataTextField	: combo box 표시 텍스트
 * @param dataValueField: combo box 표시 Value
 */
mint_ui.prototype.setDropDownList = function(comboBoxId, jsonData, selectIndex, dataTextField, dataValueField, options) {
	try {
		if( mint.common.isEmpty(selectIndex) ) {
			selectIndex = 0;
		}

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + comboBoxId).data('kendoDropDownList');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		$("#" + comboBoxId).kendoDropDownList({
			dataTextField: dataTextField,
			dataValueField: dataValueField,
			autoBind: true,
			dataSource: jsonData,
			index: selectIndex,
			height:300,
			optionLabel: (!mint.common.isEmpty(options) && !mint.common.isEmpty(options.optionLabel)) ? options.optionLabel : ""
		});
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setDropDownList"});
	}
};

/**
 * @param comboBoxId	: combo box id
 * @param jsonData		: combo box에 들어갈 json 형태의 데이터
 * @param columns		: grid 제목이 될 json형태의 데이터
 * @param selectIndex	: combo box 선택 인덱스 0 ~ n
 * @param dataTextField	: combo box 표시 텍스트
 * @param dataValueField: combo box 표시 Value
 */
mint_ui.prototype.setMultiColumnComboBox = function(comboBoxId, jsonData, columns, dataTextField, dataValueField, options) {
	try {

		var filterFields = [];
		var obj = $('#' + comboBoxId).data('kendoMultiColumnComboBox');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		if(mint.common.isEmpty(columns)) {
			columns = []
		} else {
			for(var i=0; i<columns.length; i++) {
				filterFields.push(columns[i].field)
			}
		}

		$("#" + comboBoxId).kendoMultiColumnComboBox({
			dataTextField: dataTextField,
			dataValueField: dataValueField,
			columns: columns,
			autoBind: false,
			filter: "contains",
			filterFields: filterFields,
			dataSource: jsonData,
			footerTemplate: 'Total #: instance.dataSource.total() # items'
		});

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setMultiColumnComboBox"});
	}
};

/**
 * @param gridId		: grid를 사용할 id
 * @param dataSource	: grid에 노출할 json형태의 데이터
 * @param columns		: grid 제목이 될 json형태의 데이터
 */
mint_ui.prototype.setGrid = function(gridId, dataSource, columns) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + gridId).data('kendoGrid');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		$("#" + gridId).kendoGrid({
			dataSource: {
	            data:dataSource,
	            pageSize: 20
	        },
	        height: 550,
	        groupable: true,
	        sortable: true,
	        pageable: {
	            refresh: true,
	            pageSizes: true,
	            buttonCount: 5
	        },
	        columns: columns
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setGrid"});
	}
};

/**
 * @param gridId		: grid를 사용할 id
 * @param dataSource	: grid에 노출할 json형태의 데이터
 * @param columns		: grid 제목이 될 json형태의 데이터
 * @param excelName		: excel file name
 */
mint_ui.prototype.setGridExcel = function(gridId, dataSource, columns, excelName) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + gridId).data('kendoGrid');
		if( obj ) {
			obj.setDataSource(jsonData);
			return;
		}

		$("#" + gridId).kendoGrid({
			toolbar: ["excel"],
	        excel: {
	            fileName: excelName,
	            proxyURL: "http://demos.telerik.com/kendo-ui/service/export",
	            filterable: true
	        },
	        dataSource: {
	            data:dataSource,
	            pageSize: 20
	        },
	        height: 550,
	        groupable: true,
	        sortable: true,
	        pageable: {
	            refresh: true,
	            pageSizes: true,
	            buttonCount: 5
	        },
	        columns: columns
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setGridExcel"});
	}
};

/**
 * @param gridId		: grid를 사용할 id
 * @param btnId			: btn의 A태그 ID
 * @param fileName		: 다운 받을 파일명
 */
mint_ui.prototype.downloadExcel = function(gridId, btnId, fileName) {
	try {

	    var grid = $("#" + gridId).data("kendoGrid");

	    grid.bind(btnId, function(e) {
	        e.workbook.fileName = fileName + ".xlsx";
	    });

	    grid.saveAsExcel();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.downloadExcel"});
	}
};

/**
 * @param gridId		: grid를 사용할 id
 * @param btnId			: btn의 A태그 ID
 * @param fileName		: 다운 받을 파일명
 */
mint_ui.prototype.downloadPDF = function(gridId, btnId, fileName) {
	try {
	    var grid = $("#" + gridId).data("kendoGrid");

	    grid.options.pdf.fileName = fileName;
	    grid.saveAsPDF();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.downloadPDF"});
	}
};

/**
 * @param id		: id
 * @param min		: 최소값
 * @param step		: 증가값
 * @param format	: 숫자 포맷('n0 : 정수 소수점 0자리, n1:정수 소수점 1자리...', 'p0 : 퍼센트 소수점 0자리. p1 : 소수점 1자리' )
 */
mint_ui.prototype.setNumericWithoutMax = function(id, min, step, format) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + id).data('kendoNumericTextBox');
		if( obj ) {
			return;
		}


		$("#" + id).kendoNumericTextBox({
	        min: min,
	        step: step,
	        format: format,
	        decimals: 1
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setNumericWithoutMax"});
	}
};

/**
 * @param id		: id
 * @param min		: 최소값
 * @param max		: 최대값
 * @param step		: 증가값
 * @param format	: 숫자 포맷('n0 : 정수 소수점 0자리, n1:정수 소수점 1자리...', 'p0 : 퍼센트 소수점 0자리. p1 : 소수점 1자리' )
 */
mint_ui.prototype.setNumericWithMax = function(id, min, max, step, format) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + id).data('kendoNumericTextBox');
		if( obj ) {
			return;
		}

		$("#" + id).kendoNumericTextBox({
	        min: min,
	        max: max,
	        step: step,
	        format: format,
	        decimals: 1
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setNumericWithMax"});
	}
};

/**
 * Kendo - NumericTextBox
 * @param id
 * @param options
 * @param changeCallback
 */
mint_ui.prototype.setNumericTextBox = function(id, options, changeCallback) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + id).data('kendoNumericTextBox');
		if( obj ) {
			return;
		}

		$("#" + id).kendoNumericTextBox({
	        min: (mint.common.isEmpty(options) || mint.common.isEmpty(options.min) ) ? 0 : options.min,
	        step: (mint.common.isEmpty(options) || mint.common.isEmpty(options.step) ) ? 0 : options.step,
	        format: (mint.common.isEmpty(options) || mint.common.isEmpty(options.format) ) ? 0 : options.format,
	        decimals: (mint.common.isEmpty(options) || mint.common.isEmpty(options.decimals) ) ? 0 : options.decimals,
			change: function(e) {
				var fn_callback   = mint.common.fn_callback(changeCallback);
				if( fn_callback ) {
					fn_callback(e);
				} else {
					//TODO
				}
			}
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setNumericTextBox"});
	}
};

/**
 * @param autoCompleteId	: auto complete을 사용할 input id
 * @param data				: auto complete의 data(형태 : ["Albania","Andorra",....])
 * @param defaultMsg		: default로 나타날 메시지
 */
mint_ui.prototype.setAutoComplete = function(autoCompleteId, data, defaultMsg) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + autoCompleteId).data('kendoAutoComplete');
		if( obj ) {
			return;
		}


	     //create AutoComplete UI component
	     $("#" + autoCompleteId).kendoAutoComplete({
	         dataSource: data,
	         select: onSelect,
	         filter: "startswith",
	         placeholder: defaultMsg
	     });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setAutoComplete"});
	}
};

/**
 * @param autoCompleteId	: auto complete을 사용할 input id
 * @param data				: auto complete의 data
 * @param dataField			: data 중 auto complete을 적용할 필드 명--
 * @param dataValue			: 매팅된 데이터에 대한 Value값
 * @param defaultMsg		: default 메시지
 */
mint_ui.prototype.setAutoCompleteWithDataField = function(autoCompleteId, data, dataField, dataValue, defaultMsg) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + autoCompleteId).data('kendoAutoComplete');
		if( obj ) {
			return;
		}

	    //create AutoComplete UI component
	    $("#" + autoCompleteId).kendoAutoComplete({
	    	dataTextField:dataField,
	    	dateValueField:dataValue,
	        dataSource: data,
	        select: onSelect,
	        filter: "startswith",
	        placeholder: defaultMsg
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setAutoCompleteWithDataField"});
	}
};


/**
 * @param autoCompleteId	: DatePicker을 사용할 input id
 */
mint_ui.prototype.setDatePicker = function(datePickerId) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + datePickerId).data('kendoDatePicker');
		if( obj ) {
			return;
		}

		$("#" + datePickerId).kendoDatePicker({
	        format: "yyyy/MM/dd",
	        culture: "ko-KR"
	    });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setDatePicker"});
	}
};

/**
 * Kendo - DateTimePicker
 * @param id
 * @param date
 * @param options
 */
mint_ui.prototype.setDateTimePicker = function(id, date, options) {
	try {

		// 컴포넌트가 중복으로 생성되는 문제로 로직 추가
		var obj = $('#' + id).data('kendoDateTimePicker');
		if( obj ) {
			return;
		}

        $('#' + id).kendoDateTimePicker({
            format: !mint.common.isEmpty( options ) && !mint.common.isEmpty( options.format )? options.format : "yyyy/MM/dd HH:mm",
            timeFormat: "HH:mm",
            culture: "ko-KR",
            interval: 1,
            value : date,
            dateInput : true
        });
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setDateTimePicker"});
	}
};


/**
 * @param id
 */
mint_ui.prototype.setGridClearNull = function(id){
	try {
		$("#" + id).kendoGrid({dataSource:{data:[]}});
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setGridClearNull"});
	}
};



/**
 * 사이드메뉴 전체접힘/해제
 * @param flag
 * @param isMinimalize
 */
mint_ui.prototype.fixedSideBar = function(flag,isMinimalize) {
	try {

        if ( flag === true ) {
			$("body").addClass('fixed-sidebar');
			$('.sidebar-collapse').slimScroll({
				height: '100%',
				railOpacity: 0.9
			});
	        //-----------------------------------------------------------------------
	        // 삼성전기 요구사항에 따라 메뉴 숨김/보임 버튼 이미지 변경
			// inspinia.js 도 수정해야함
	        //-----------------------------------------------------------------------
        	if ( $('body').hasClass('fixed-sidebar') ) {
        		if( $('body').hasClass('mini-navbar') ) {
                	$('#mint-menu-bar').removeClass('fa-angle-double-right');
                	$('#mint-menu-bar').addClass('fa-angle-double-left');
        		} else {
                	$('#mint-menu-bar').removeClass('fa-angle-double-left');
        			$('#mint-menu-bar').addClass('fa-angle-double-right');
        		}
        	}
        } else {
            //-----------------------------------------------------------------------
            // 상시 fixedsidebar 로 변경하기 위해 주석처리함(2015/11/09)
            //-----------------------------------------------------------------------

			//$('.sidebar-collapse').slimscroll({destroy: true});
			//$('.sidebar-collapse').attr('style', '');
			//$("body").removeClass('fixed-sidebar');
        }
        if( isMinimalize ) {
			$("body").toggleClass("mini-navbar");
			this.smoothlyMenu();
        }

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.fixedSideBar"});
	}

};

mint_ui.prototype.smoothlyMenu = function() {
	try {
        if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
            // Hide menu in order to smoothly turn on when maximize menu
            $('#side-menu').hide();
            // For smoothly turn on menu
            setTimeout(
                function () {
                    $('#side-menu').fadeIn(500);
                }, 100);
        } else if ($('body').hasClass('fixed-sidebar')) {
            $('#side-menu').hide();
            setTimeout(
                function () {
                    $('#side-menu').fadeIn(500);
                }, 900);
        } else {
            // Remove all inline style from jquery fadeIn function to reset menu state
            $('#side-menu').removeAttr('style');
        }
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.smoothlyMenu"});
	}
};


/**
 * 화면에 Tooltip 을 Attach 한다
 * @param targetId : 툴팁을 표시할 위치, tag 에 정의되어 있는 ID 값을 입력한다
 * @param filter   : 툴팁 반응을 보일 범위, ID 가 속해있는 html tag 범위에서 선택한다
 * @param content  : 툴팁에 표시할 내용
 * @param options  : 향후 확장성을 고려한 옵션항목
 */
mint_ui.prototype.setTooltip = function( targetId, filter, content, options ) {
	try {
		var tooltip = $(targetId).kendoTooltip({
			autoHide : true,
			callout  : false,
			position : "top",
			showOn   : "mouseenter",
			filter   : filter,
			content  : function(e) {
				var style = "text-align:left; padding:5px 10px 5px 10px;";
				return "<div style='" + style + "'>" + content + "</div>";
			}
		});
		//tooltip.show();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.setTooltip"});
	}
}

/**
 * appId 와 helpId 로 도움말 정보를 찾아서 helpWindow 를 Open 한다
 * @param attachElement  도움말을 호출한 Element 정보 (helpWindow 가 open 될때 position 을 설정하기 위해 사용)
 * @param appId          화면별로 부여된 Application ID
 * @param helpId         도움말 ID
 * @param options        확장을 고려한 옵션 파라메터
 */
mint_ui.prototype.help = function(attachElement, appId, helpId, options ) {
	try {
		var helpContents = '';

		if( appId === 'PREVIEW' || helpId === 'PREVIEW' ) {
			if( ! mint.common.isEmpty( options) && ! mint.common.isEmpty( options.contents ) ) {
				helpContents = options.contents;
			} else {
				helpContents = '';
			}
			mint.ui.helpWindow(attachElement, helpContents);
		} else {
			mint.callService(null, screenName, 'REST-R01-SU-02-15-000',
					function(jsonData) {
						if( ! mint.common.isEmpty( jsonData ) && ! mint.common.isEmpty( jsonData.contents ) ) {
							helpContents = jsonData.contents;
						} else {
							helpContents = '';
						}
						mint.ui.helpWindow(attachElement, helpContents, {subject : jsonData.subject});
					}
					,{ errorCall : true ,params : { appId : appId, helpId : helpId, langId : mint.lang } }
			);
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.help"});
	}
}

/**
 * 도움말을 팝업창에 display 한다
 * @param attachElement 도움말을 호출한 Element 정보 (helpWindow 가 open 될때 position 을 설정하기 위해 사용)
 * @param helpContents  도움말 contents
 * @param options       확장을 고려한 옵션 파라메터
 */
mint_ui.prototype.helpWindow = function(attachElement, helpContents, options ) {
	try {
		if( mint.common.isEmpty(helpContents)  ) {
			mint.common.alert('BW00043', null);
		} else {
//			$("#helpWindow").remove();
			$(".k-window").remove();
			var obj = $('<div id="helpWindow"><div id="helpContents"></div></div>');
			$('body').prepend(obj);

			var window = $('#helpWindow');

			var thisWidth = $(attachElement).offset().left;
		    var thisHeight = $(attachElement).offset().top;

			window.kendoWindow({
				actions    : ['Minimize','Maximize', 'Close'],
				animation  : false,
				autoFocus  : false,
				draggable  : true,
				modal      : false,
				finned     : false,
				resizable  : true,
				scrollable : false,
				minWidth   : '500px',
				//minHeight  : '400px',
				iframe     : false,
				//width   : '100%',
				//height  : '100%',
				title   : !mint.common.isEmpty( options ) && !mint.common.isEmpty( options.subject ) ? options.subject : 'Integrated Interface Portal Help',
				//content : helpContents
			}).data("kendoWindow").center().open();

			//helpContents attach
			$('#helpContents').html( helpContents );

//			예전버전
//			var imageWidth = window.data("kendoWindow").wrapper[0].clientWidth;
//			var imageHeight = window.data("kendoWindow").wrapper[0].clientHeight;
//			var imageWidth = window.outerWidth();
//			var imageHeight = window.outerHeight();

			var clientWidth = window.data("kendoWindow").appendTo[0].clientWidth;
			var clientHeight = window.data("kendoWindow").appendTo[0].clientHeight;

			var imageWidth = window.width();
			var imageHeight = window.height();

//			alert(
//				'scorll = ' + $('#main_contents').scrollTop()
//				+'\n clientHeight = ' + $(document).scrollTop()
//			);

			var showWidth = 0;

			if(thisWidth + imageWidth + 20 > clientWidth) {
				if(thisWidth - imageWidth > 0) {
					showWidth = thisWidth - imageWidth - 20;
				} else {
					showWidth = 0;
				}
			} else {
				showWidth = thisWidth
			}

			window.data("kendoWindow").setOptions({
				position : {
					top  : thisHeight,
					left : showWidth
				}
			});
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.helpWidnow"});
	}
}


/**
 * js/css 등 화면 로딩시 필요한 초기화 작업들 정리( 순수 UI 관점의 초기화 )
 * @param options : 팝업일경우, 스크롤이 필요하면 {isPopupScroll:true} 로 설정하여 호출한다
 *                   ex) mint.ui.initScreen({isPopupScroll:true});
 */
mint_ui.prototype.initScreen = function( options ) {
	try {

		//패널 접히고 펼침기능
		//TODO 이미 이벤트가 걸려있으면..skip 하는 로직 필요
		$('.collapse-link').panelCollapseEvent();
		$('.fullscreen-link').panelFullScreenEvent();
		$('.close-link').panelCloseEvent();

		if( options && options.isPopupScroll === true ) {
			//팝업 화면 Scroll 기능
		    $('.full-height-scroll').slimscroll({
		    	width: '100%',
		        height: '100%',
		        axis: 'y',
		        alwaysVisible: true,
		        railVisible: true,
		        size: '10px',
				railOpacity: 0.1,
				opacity: 0.3,
		    });
		}

		//-----------------------------------------------------------------------
		// 현재 화면에 Tooltip 이 존재하면 attach 한다
		// 2015/11/25 주석처리
		//-----------------------------------------------------------------------
		/*
		if( ! mint.common.isEmpty( screenName ) ) {
			mint.tooltip.initTooltip( screenName );
		}
		*/
		//-----------------------------------------------------------------------
		// 도움말 CSS 가 적용되어 있으면 아이콘을 붙인다
		//-----------------------------------------------------------------------
		//mint.ui.initHelp();

		//-----------------------------------------------------------------------
		// 팝업창 드레그 여부 설정
		//-----------------------------------------------------------------------
		if( options && options.isDraggable === false ) {

		} else {
			$('.modal-dialog').draggable();
			$('.modal-dialog-original').draggable();
		}

		//-----------------------------------------------------------------------
		// editor 가 textmode 일때, 개행문자 처리
		//-----------------------------------------------------------------------
		var editorMode = mint.common.getEnvValue('ui.editor.mode');
		if( ! mint.common.isEmpty( editorMode ) && editorMode == 'text' ) {
			//-----------------------------------------------------------------------
			// View
			//-----------------------------------------------------------------------
			$('p').css('white-space', 'pre');
			//-----------------------------------------------------------------------
			// Kendo Editor
			//-----------------------------------------------------------------------
			{
				var isLineBreak = true;//TODO :: env ?
				if(isLineBreak) {
					var defaultTools = kendo.ui.Editor.defaultTools;
					defaultTools["insertLineBreak"].options.shift = false;
					delete defaultTools["insertParagraph"].options;
				}
				kendo.ui.Editor.fn.setOptions({stylesheets : ['../../css/common/wa.editor.css']});
			}
		}

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.initScreen"});
	}
}

/**
 * 그리드 페이지 설정
 * @param options
 * @returns
 */
mint_ui.prototype.getPageSizesXXS = function( options ) {
	try {
		var sizes = [5,10,20,50];
		if( mint.common.isEmpty(options) ) {
			return sizes;
		} else {
			if( mint.common.isEmpty(options.currentPage) ) {
				return sizes;
			} else {
				return sizes[0];
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.getPageSizesXS"});
	}
}

/**
 * 그리드 페이지 설정
 * @param options
 * @returns
 */
mint_ui.prototype.getPageSizesXS = function( options ) {
	try {
		var sizes = [10,20,50,100];
		if( mint.common.isEmpty(options) ) {
			return sizes;
		} else {
			if( ! mint.common.isEmpty(options.allView) && options.allView === true) {
				sizes.push('ALL');
				return sizes;
			}

			if( !mint.common.isEmpty(options.currentPage) ) {
				if( !mint.common.isEmpty(options.reverseCurrentPage) && options.reverseCurrentPage === true ) {
					return sizes[sizes.length];
				} else {
					return sizes[0];
				}
			} else {
				mint.common.alert('개발단계에 있어서, 그리드 페이지 설정의 부적절한 파라메터!');
				return sizes;
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.getPageSizesXS"});
	}
}

/**
 * 그리드 페이지 설정
 * @param options
 * @returns
 */
mint_ui.prototype.getPageSizesS = function( options ) {
	try {
		var sizes = [20,50,100,200];
		if( mint.common.isEmpty(options) ) {
			return sizes;
		} else {
			if( ! mint.common.isEmpty(options.allView) && options.allView === true) {
				sizes.push('ALL');
				return sizes;
			}

			if( !mint.common.isEmpty(options.currentPage) ) {
				if( !mint.common.isEmpty(options.reverseCurrentPage) && options.reverseCurrentPage === true ) {
					return sizes[sizes.length];
				} else {
					return sizes[0];
				}
			} else {
				mint.common.alert('개발단계에 있어서, 그리드 페이지 설정의 부적절한 파라메터!');
				return sizes;
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.getPageSizesS"});
	}
}

/**
 * 그리드 페이지 설정
 * @param options
 * @returns
 */
mint_ui.prototype.getPageSizesM = function( options ) {
	try {
		var sizes = [50,100,200,500];
		if( mint.common.isEmpty(options) ) {
			return sizes;
		} else {
			if( ! mint.common.isEmpty(options.allView) && options.allView === true) {
				sizes.push('ALL');
				return sizes;
			}

			if( !mint.common.isEmpty(options.currentPage) ) {
				if( !mint.common.isEmpty(options.reverseCurrentPage) && options.reverseCurrentPage === true ) {
					return sizes[sizes.length];
				} else {
					return sizes[0];
				}
			} else {
				mint.common.alert('개발단계에 있어서, 그리드 페이지 설정의 부적절한 파라메터!');
				return sizes;
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.getPageSizesL"});
	}
}

/**
 * 그리드 페이지 설정
 * @param options
 * @returns
 */
mint_ui.prototype.getPageSizesL = function( options ) {
	try {
		var sizes = [100,200,500,1000];
		if( mint.common.isEmpty(options) ) {
			return sizes;
		} else {
			if( ! mint.common.isEmpty(options.allView) && options.allView === true) {
				sizes.push('ALL');
				return sizes;
			}

			if( !mint.common.isEmpty(options.currentPage) ) {
				if( !mint.common.isEmpty(options.reverseCurrentPage) && options.reverseCurrentPage === true ) {
					return sizes[sizes.length];
				} else {
					return sizes[0];
				}
			} else {
				mint.common.alert('개발단계에 있어서, 그리드 페이지 설정의 부적절한 파라메터!');
				return sizes;
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.getPageSizesXL"});
	}
}

mint_ui.prototype.initHelp = function( options ) {
	try {
		//rgba(51 , 122, 182, 1) - 파란색
		//rgba(195, 195, 195, 1) - 회색

		var cssDefault = {'color' : 'rgba(51 , 122, 182, 1)', 'cursor' : 'pointer'};

		//label
		$('.help-over-label').html('<i class="fa fa-question-circle"></i>');
		$('.help-over-label').css(cssDefault);
		$('.help-over-label').css('font-size','14px');
		$('.help-over-label').css('padding'  ,'0px 3px 0px 0px');
		$('.help-over-label').css('float'    ,'right')
		;
		//title
		$('.help-over-title').html('<i class="fa fa-question-circle"></i>');
		$('.help-over-title').css(cssDefault);
		$('.help-over-title').css('font-size','14px');
		$('.help-over-title').css('padding'  ,'0px 0px 0px 3px');

		//pannel
		$('.help-over-pannel').html('<i class="fa fa-question-circle fa-lg"></i>');
		$('.help-over-pannel').css(cssDefault);

		//tab
		$('.help-over-tab').html('<i class="fa fa-question-circle"></i>');
		$('.help-over-tab').css(cssDefault);
		$('.help-over-tab').css('font-size','14px');

		$('.help-over-tab').css('position','absolute');
		$('.help-over-tab').css('right','13px');
		$('.help-over-tab').css('top','2px');


	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.initHelp"});
	}
}



/**
 * kendo treeview filter function
 * @param options
 * @returns
 */
mint_ui.prototype.treeViewFilter = function( treeViewId, query ) {
	try {
		/*
	    var hasVisibleChildren = false;
	    var data = dataSource instanceof kendo.data.HierarchicalDataSource && dataSource.data();

	    for (var i = 0; i < data.length; i++) {
	        var item = data[i];
	      	var text = item.text.toLowerCase();
	      	var itemVisible =
	          query === true // parent already matches
	          || query === "" // query is empty
	          || text.indexOf(query) >= 0; // item text matches query

	      	var anyVisibleChildren = this.treeViewFilter(item.children, itemVisible || query); // pass true if parent matches

	      	hasVisibleChildren = hasVisibleChildren || anyVisibleChildren || itemVisible;

	      	item.hidden = !itemVisible && !anyVisibleChildren;
	    }

	    if (data) {
	      // re-apply filter on children
	      dataSource.filter({ field: "hidden", operator: "neq", value: true });
	    }

	    return hasVisibleChildren;
	    */


        var treeView = $('#' + treeViewId).data('kendoTreeView');
        treeView.dataSource.filter({ field: 'text', operator: 'contains', value: query });

        $('#'+ treeViewId + ' .k-in:containsIgnoreCase("' + query + '")').each(function () {
        	var firstElementChild = $(this).context.firstElementChild;
        	var prefix = !mint.common.isEmpty( firstElementChild ) ? firstElementChild.outerHTML : '';
            var index = $(this).text().toLowerCase().indexOf(query.toLowerCase());
            var length = query.length;
            var original = $(this).text().substr(index, length);
            var newText = $(this).text().replace(original, "<span class='query-match'>" + original + "</span>");
            $(this).html(prefix + newText);
        });

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.treeViewFilter"});
	}
}


/**
 * kendo treeview sort function
 * @param options
 * @returns
 */
mint_ui.prototype.treeViewSort = function( items ) {
	try {
        for(var i=0; i < items.length; i++){
            if(items[i].hasChildren){
              items[i].children.sort({field: "text", dir: "asc"});
              mint.ui.treeViewSort(items[i].children.view());
            }
        }
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.treeViewSort"});
	}
}


/**
 * kendo treeview set hasChildren
 * @param node
 * @param options
 * @param callBack
 * @returns
 */
mint_ui.prototype.treeViewHasChildren = function( node, options, callBack ) {
	try {
		var hasChildren =  node.hasChild;
		if (hasChildren === true) {
			if(node.root === true) {
				node.spriteCssClass = "fa fa-plus-square text-custom-01";
			} else {
				node.spriteCssClass = "fa fa-plus-square text-custom-01";
			}
		} else {
			node.spriteCssClass = "fa fa-dot-circle-o text-custom-01";
		}

		// 20181004 add
		if( !mint.common.isEmpty(node.draggable) && node.draggable === true ) {
			node.spriteCssClass = node.spriteCssClass + ' mint-widget-draggable';
		}

		return hasChildren;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.treeViewHasChildren"});
	}
}

/**
 * kendo treeview collapse event
 * @param node
 * @param options
 * @param callBack
 */
mint_ui.prototype.treeViewCollapse = function( node, options, callBack ) {
	try {
		var element = $(node).find('.k-sprite:first');
		element.removeClass('fa-minus-square');
		element.addClass('fa-plus-square');

		var fn_callback   = mint.common.fn_callback(callBack);
		if( fn_callback ) {
			fn_callback(node, options);
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.treeViewCollapse"});
	}
}

/**
 * kendo treeview expand event
 * @param node
 * @param options
 * @param callBack
 */
mint_ui.prototype.treeViewExpand = function( node, options, callBack ) {
	try {
		var element = $(node).find('.k-sprite:first');
		element.removeClass('fa-plus-square');
		element.addClass('fa-minus-square');

		var fn_callback   = mint.common.fn_callback(callBack);
		if( fn_callback ) {
			fn_callback(node, options);
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.treeViewExpand"});
	}
}


/**
 * kendo grid init height
 * @param gridElement
 */
mint_ui.prototype.gridInitHeight = function(gridElement, isScrollable) {
	try {
		var gridElSize = mint.ui.elementSize(gridElement);

		var gridHeader = 30;
		var footbarH = 36;
		var bufferH = 50;

		var gridHeight = gridElSize.screenH - gridElSize.offsetTop - gridHeader - footbarH - bufferH;
		return isScrollable ? gridHeight : 'auto';
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.gridInitHeight"});
	}
}

/**
 * kendo grid(grid/treelist..) vertical scrollbar enable/disable
 * @param e (dataBound event)
 * @param options
 * @param callBack
 */
mint_ui.prototype.gridVerticalScrollBar = function( e, options, callBack ) {
	try {
        var gridWrapper = e.sender.wrapper;
        var gridDataTable = e.sender.table;
        var gridDataArea = gridDataTable.closest(".k-grid-content.k-auto-scrollable");

        if( gridDataTable.length > 0 && gridDataArea.length > 0 ) {
        	gridWrapper.toggleClass("no-scrollbar", gridDataTable[0].offsetHeight <= gridDataArea[0].offsetHeight);
        }

        // resize event
        {
        	var padding = 'padding-right';
        	//rtl(grid mode Right to Left)
        	if(options && options.rtl == true) {
        		padding = 'padding-left';
        	}

            e.sender.thead.closest(".k-grid-header").css(padding, kendo.support.scrollbar(true));
            e.sender.resize();
        }

        // kendoTreeList lockedColumn - workaround
        {
        	var componentName = e.sender.options.name;
        	if( componentName == 'TreeList' ) {
        		if(e.sender.lockedHeader) {
			        var headerW = e.sender.thead.closest(".k-grid-header").width();
			        var lockedHeaderW = e.sender.lockedHeader.width();
			        var newW = headerW - lockedHeaderW -2;

			        gridWrapper.toggleClass("no-overflow", true);
        			e.sender.thead.closest(".k-grid-header-wrap").width(newW);
        		}
        	}
        }

        // callback
		var fn_callback   = mint.common.fn_callback(callBack);
		if( fn_callback ) {
			fn_callback(e, options);
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.ui.gridVerticalScrollBar"});
	}
}

/**
 * kendo gridEditor - roleTypeComboBox
 * @param container
 * @param options
 * @param changeCallBack
 *        - change Event 함수를 화면별로 구현할 필요가 있을 경우 콜백함수를 명시한다.
 */
mint_ui.prototype.gridEditorRoleTypeComboBox = function(container, options, changeCallBack) {
	try {

	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "nm"
			, dataSource: {data: mint.common.commonCodeMap.get("cds").IM09}
			, index: -1
			, change: function(e) {
				var selectedItem = this.dataItem(this.selectedIndex);
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, selectedItem);
				} else {
					options.model.roleTypeNm = selectedItem.nm;
					options.model.roleTypeCd = selectedItem.cd;
				}
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.gridEditorRoleTypeComboBox"});
	}
}//end gridEditorRoleTypeComboBox()


/**
 * kendo gridEditor - nodeTypeComboBox
 * @param container
 * @param options
 * @param changeCallBack
 *        - change Event 함수를 화면별로 구현할 필요가 있을 경우 콜백함수를 명시한다.
 */
mint_ui.prototype.gridEditorNodeTypeComboBox = function(container, options, changeCallBack) {
	try {

	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "nm"
			, dataSource: {data: mint.common.commonCodeMap.get("cds").IM08}
			, index: -1
			, change: function(e) {
				var selectedItem = this.dataItem(this.selectedIndex);
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, selectedItem);
				} else {
					options.model.nodeTypeNm = selectedItem.nm;
					options.model.nodeTypeCd = selectedItem.cd;
				}
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.gridEditorNodeTypeComboBox"});
	}
}//end gridEditorNodeTypeComboBox()


/**
 * kendo gridEditor - resourceTypeComboBox
 * @param container
 * @param options
 * @param changeCallBack
 *        - change Event 함수를 화면별로 구현할 필요가 있을 경우 콜백함수를 명시한다.
 */
mint_ui.prototype.gridEditorResourceTypeComboBox = function(container, options, changeCallBack) {
	try {

	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "nm"
			, dataSource: {data: mint.common.commonCodeMap.get("cds").IM04}
			, index: -1
			, change: function(e) {
				var selectedItem = this.dataItem(this.selectedIndex);
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, selectedItem);
				} else {
					options.model.resourceNm = selectedItem.nm;
					options.model.resourceCd = selectedItem.cd;
				}
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.gridEditorResourceTypeComboBox"});
	}
}//end gridEditorResourceTypeComboBox()


/**
 * kendo gridEditor - FieldTypeComboBox
 * @param options
 * @returns
 */
mint_ui.prototype.gridEditorFieldTypeComboBox = function(container, options, changeCallBack) {
	try {

	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "cd"
			, dataSource: mint.common.commonCodeMap.get("cds").AN09
			, index: -1
			, change: function(e) {
				var selectedItem = this.dataItem(this.selectedIndex);
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, selectedItem);
				} else {
					if(selectedItem) {
						options.model.typeNm = selectedItem.nm;
						options.model.type = selectedItem.cd;
					}
				}
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.gridEditorFieldTypeComboBox"});
	}
}//end gridEditorFieldTypeComboBox()




/**
 * kendo gridEditor - EncryptTypeComboBox
 * @param options
 * @returns
 */
mint_ui.prototype.gridEditorEncryptTypeComboBox = function(container, options, changeCallBack) {
	try {

	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "cd"
			, dataSource: mint.common.commonCodeMap.get("cds").AN08
			, index: -1
			, change: function(e) {
				var selectedItem = this.dataItem(this.selectedIndex);
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, selectedItem);
				} else {
					if(selectedItem) {
						options.model.encryptTypeNm = selectedItem.nm;
						options.model.encryptType = selectedItem.cd;
					}
				}
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.gridEditorEncryptTypeComboBox"});
	}
}//end gridEditorEncryptTypeComboBox()


/**
 * kendo gridEditor - gridEditorYesOrNoComboBox
 * @param options
 * @returns
 */
mint_ui.prototype.gridEditorYesOrNoComboBox = function(container, options) {
	try {
		var YesNoValue = [{ nm: 'Yes', value: 'Y' }, { nm: 'No', value: 'N' }];
	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "value"
			, dataSource: YesNoValue
			, index: 0
		});
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.gridEditorYesOrNoComboBox"});
	}
}//end gridEditorYesOrNoComboBox()



/**
 * kendo gridEditor - gridEditorJustifyComboBox
 * @param options
 * @returns
 */
mint_ui.prototype.gridEditorJustifyComboBox = function(container, options, changeCallBack) {
	try {

	    var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoDropDownList({
	    	  dataTextField: "nm"
			, dataValueField: "cd"
			, dataSource: mint.common.commonCodeMap.get("cds").AN13
			, index: -1
			, change: function(e) {
				var selectedItem = this.dataItem(this.selectedIndex);
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, selectedItem);
				} else {
					if(selectedItem) {
						options.model.justifyNm = selectedItem.nm;
						options.model.justify = selectedItem.cd;
					}
				}
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.gridEditorJustifyComboBox"});
	}
}//end gridEditorJustifyComboBox()

/**
 * kendo gridEditor - NumbericTextBox
 * @param options
 * @returns
 */
mint_ui.prototype.gridEditorNumbericTextBox = function(container, options, changeCallBack) {
	try {

		var input = $("<input/>");
	    input.attr("name", options.field);
	    input.appendTo(container);
	    input.kendoNumericTextBox({
	    	selectOnFocus: mint.common.isEmpty(options.selectOnFocus) ? true : options.selectOnFocus,
			min: mint.common.isEmpty(options.min) ? 0 : options.min,
			step: mint.common.isEmpty(options.step) ? 1 : options.step,
			format: mint.common.isEmpty(options.format) ? 'n' : options.format,
			decimals: mint.common.isEmpty(options.decimals) ? 0 : options.decimals,
			change: function(e) {
				var fn_callback   = mint.common.fn_callback(changeCallBack);
				if( fn_callback ) {
					fn_callback(container, options, e);
				} else {
					//TODO
				}
			},
		});

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.gridEditorNumbericTextBox"});
	}
}//end gridEditorNumbericTextBox()

/**
 * kendo gridEditor - resourceTypeComboBox
 * @param container
 * @param options
 * @param changeCallBack
 *        - change Event 함수를 화면별로 구현할 필요가 있을 경우 콜백함수를 명시한다.
 */

/**
 * kendo grid - left delete button
 * @param gridId           그리드ID 를 명기한다.
 * @param visibleYn        Delete 아이콘을 표시할지 여부를 설정한다.(Y : 삭제, N : 삭제불가능 )
 * @param notifyFieldId    Confirm Message 에 표시할 필드명을 설정한다.
 * @param clickCallBack    클릭이벤트를 화면별로 설정할 필요가 있으면 콜백함수를 명시한다.
 * @returns {String}
 */
mint_ui.prototype.gridRowDeleteButton = function(gridId, visibleYn, notifyFieldId, clickCallBack) {
	try {
		if( visibleYn === 'N') {
			return "<span></span>";
		} else {

			return "<span class='"+gridId+"' onclick='mint.ui.deleteGridRow(this, \"" + notifyFieldId + "\",\"" + clickCallBack +  "\");' style='cursor: pointer;'><i class='fa fa-times'></i></span>";
		}
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.gridRowDeleteButton"});
	}
}//end gridRowDeleteButton()

/**
 * kendo grid - delete button event
 * @param node            그리드에서 삭제할 Row 를 넘겨받는다.
 * @param notifyFieldId   Confirm Message 에 표시할 필드명을 설정한다.
 * @param clickCallBack   클릭이벤트를 화면별로 설정할 필요가 있으면 콜백함수를 명시한다.
 */
mint_ui.prototype.deleteGridRow = function(node, notifyFieldId, clickCallBack) {
	try {

		var fn_callback   = mint.common.fn_callback(clickCallBack);
		if( fn_callback ) {
			fn_callback(node, notifyFieldId);
		} else {
			var grid = $("#" + node.className).data("kendoGrid");
			var row = $(node).closest("tr");
		    var	dataItem = grid.dataItem(row);
		    var field = dataItem[notifyFieldId];
		    var confirm = mint.common.confirm('BI00003', field );

			if (confirm == true) {
	        	grid.dataSource.remove(dataItem);
			}
		}
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.deleteGridRow"});
	}
}//end deleteGridRow()


/**
 * Component resize
 */
mint_ui.prototype.resize = function() {
	try {

        window.setTimeout(function () {
    		//Kendo Grid
    		kendo.resize($('.k-grid'));
    		//Kendo Chart
    		kendo.resize($('.k-chart'));
        }, 600);

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.resize"});
	}
}

/**
 * div Height 재설정
 * @param div
 * @param height
 * @param options
 */
mint_ui.prototype.divReHeight = function(div, height, options) {
	try {
		if( !mint.common.isEmpty(options) && options.isAnimate === true ) {
			$(div).animate({'height': height});
			//TODO
		} else {
			$(div).height(height);
		}

	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.divReHeight"});
	}
}


mint_ui.prototype.attachScrollOptions = function(axis, options) {
	try {

		opt = {
			size: mint.common.isEmpty(options) ? '7px' : mint.common.isEmpty(options.size) ? '7px' : options.size,
			height:'100%',
			width:'100%',
			axis: axis,
			railOpacity: 0.3,
			opacity: 0.3,
			color: '#908FCD'
		};

		if( !mint.common.isEmpty(options) ) {

			//axis is x or both
			if( !mint.common.isEmpty(options.positionX) ) {
				//top, bottom :: default bottom
				opt.positionX = options.positionX;
			}

			//axis is y or both
			if( !mint.common.isEmpty(options.startY) ) {
				//top, bottom :: default top
				opt.startY = options.startY;
			}

			//axis is y or both
			if( !mint.common.isEmpty(options.positionY) ) {
				//left, right :: default right
				opt.positionY = options.positionY;
			}

			//axis is y or both
			if( !mint.common.isEmpty(options.scrollByY) ) {
				opt.scrollByY = options.scrollByY;
			}

			//axis is x y both
			if( !mint.common.isEmpty(options.disableFadeOut) ) {
				//true, false :: default false
				opt.disableFadeOut = options.disableFadeOut;
			}

			//axis is x y both
			if( !mint.common.isEmpty(options.alwaysVisible) ) {
				//true, false :: default false
				opt.alwaysVisible = options.alwaysVisible;
			}

		}

		return opt;
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.attachScrollX"});
	}
}


/**
 * scrollX attach
 * @param obj
 * @param options
 */
mint_ui.prototype.attachScrollX = function(obj, options) {
	try {
		if( obj ) {
			obj.slimScroll( mint.ui.attachScrollOptions('x', options ) );
		}
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.attachScrollX"});
	}
}

/**
 * scrollY attach
 * @param obj
 * @param options
 */
mint_ui.prototype.attachScrollY = function(obj, options) {
	try {
		if( obj ) {
			obj.slimScroll( mint.ui.attachScrollOptions('y', options ) );
		}
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.attachScrollY"});
	}
}

/**
 * scrollBoth attach
 * @param obj
 * @param options
 */
mint_ui.prototype.attachScrollAll = function(obj, options) {
	try {
		if( obj ) {
			obj.slimScroll( mint.ui.attachScrollOptions('both', options ) );
		}
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.attachScrollAll"});
	}
}

/**
 * 엘리먼트 사이즈 리턴
 * @param element
 * @returns {}
 */
mint_ui.prototype.elementSize = function(element) {
	try {
		//-----------------------------------------------------------------------
		// screen size 구하기
		//-----------------------------------------------------------------------
		var bd = document.documentElement ? document.documentElement : document.body;
		var screenW = bd.scrollWidth;
		var screenH = window.innerHeight || bd.clientHeight; //screen.height;

		var scrollX = (window.pageXOffset || window.scrollX || bd.scrollLeft);
		var scrollY = (window.pageYOffset || window.scrollY || bd.scrollTop);

		var offsetTop    = element.offset().top;
		var offsetLeft   = element.offset().left;
		var clientWidth  = element[0].clientWidth;
		var clientHeight = element[0].clientHeight;

		var scrollWidth  = element[0].scrollWidth;
		var scrollHeight = element[0].scrollHeight;

		return {
			'screenW' : screenW,
			'screenH' : screenH,
			'scrollX' : scrollX,
			'scrollY' : scrollY,
			'offsetTop' : offsetTop,
			'offsetLeft' : offsetLeft,
			'clientWidth' : clientWidth,
			'clientHeight' : clientHeight,
			'scrollWidth' : scrollWidth,
			'scrollHeight' : scrollHeight
		};
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.elementSize"});
	}
}

/**
 * 대시보드에서 사용하는 전체화면 윈도우
 * @param attachDiv
 * @param windowDiv
 * @param layout
 * @param title
 * @param destroyItemClass
 * @param options
 */
mint_ui.prototype.windowOpen = function(attachDiv, windowDiv, title, closeCallback, options) {
	try {
		//-----------------------------------------------------------------------
		// screen size 구하기
		//-----------------------------------------------------------------------
		var elementSize = mint.ui.elementSize(attachDiv);

		//-----------------------------------------------------------------------
		// Window 초기화
		//-----------------------------------------------------------------------
		windowDiv.kendoWindow({
			title      : '',
			actions    : ['Close'],
			animation  : false,
			autoFocus  : true,
			draggable  : false,
			modal      : false,
			finned     : false,
			resizable  : false,
			scrollable : false,
			iframe     : false,

			//minWidth   : '500px',
			//minHeight  : '400px',
			//width   : clientWidth,
			//height  : clientHeight,
			close: function(e) {
				//attachDiv.html($('.' + destroyItemClass));
				var fn_callback = mint.common.fn_callback(closeCallback);
				if( fn_callback ) {
					fn_callback(e);
				}
				mint.ui.resize();
				this.destroy();
			}
		});

		//-----------------------------------------------------------------------
		// Window Position
		//-----------------------------------------------------------------------
		windowDiv.data("kendoWindow").setOptions({
			position: {
				top: elementSize.offsetTop,
				left: elementSize.offsetLeft
			}
		});

		//-----------------------------------------------------------------------
		// Window Maximize Event
		//-----------------------------------------------------------------------
		windowDiv.data("kendoWindow").maximize();

		//-----------------------------------------------------------------------
		// Window Scroll
		//-----------------------------------------------------------------------
		if( mint.common.isEmpty(options) ) {
			mint.ui.attachScrollY( windowDiv );
		} else {
			if( !mint.common.isEmpty(options.windowScroll) && options.windowScroll === false ) {
				//Nothing
			} else {
				mint.ui.attachScrollY( windowDiv );
			}
		}


		//-----------------------------------------------------------------------
		// Window Title
		// - window title 에 html을 더이상 지원하지 않아 아래와 같은 방법으로 구현
		//-----------------------------------------------------------------------
		$(".k-window-title").append( title );

		//-----------------------------------------------------------------------
		// Window Resize
		//----------------------------------------------------------------------
		 mint.ui.resize();

	} catch (e) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.windowOpen"});
	}
}



/**
 *
 */
mint_ui.prototype.draggablePannels = function() {
	try {
	    var element = "[class*=col]";
	    var handle = ".ibox-title";
	    var connect = "[class*=col]";
	    $(element).sortable(
	        {
	            handle: handle,
	            connectWith: connect,
	            tolerance: 'pointer',
	            forcePlaceholderSize: true,
	            opacity: 0.8
	        })
	        .disableSelection();
	} catch( e ) {
		mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "mint.ui.draggablePannels"});
	}
}


/**
 * mint 객체에 추가한다.<p>
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.ui === "undefined") {
	        mint.ui = new mint_ui();
	    }
	} catch( e ) {

	}
});
