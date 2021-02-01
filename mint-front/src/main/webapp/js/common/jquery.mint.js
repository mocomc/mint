/*!
 * 제이쿼리를 확장한 공통 스크립트 파일
 *
 */
(function( $ ) {
	/**
	 * bootstrap-inspinia Collapse Event Extended
	 */
	$.fn.panelCollapseEvent = function() {
		$(this).off("click");
		$(this).on("click", function() {
			{
		        var ibox = $(this).closest('div.ibox');
		        var button = $(ibox).find('i');
		        var content = ibox.find('div.ibox-content');
		        content.slideToggle(200);
		        button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
		        ibox.toggleClass('').toggleClass('border-bottom');
		        setTimeout(function () {
		            ibox.resize();
		            ibox.find('[id^=map-]').resize();
		        }, 50);
			}
		});
	};

	/**
	 * bootstrap-inspinia FullScreen Event Extended
	 */
	$.fn.panelFullScreenEvent = function() {
		$(this).off("click");
		$(this).on("click", function() {
			{
				var ibox = $(this).closest('div.ibox');
				var button = $(ibox).find('i');
				$('body').toggleClass('fullscreen-ibox-mode');
				if( $(button).is('.fa-expand') || $(button).is('.fa-compress') ) {
					button.filter($('.fa-expand, .fa-compress')).toggleClass('fa-expand').toggleClass('fa-compress');
				}
				ibox.toggleClass('fullscreen');
				setTimeout(function () {
				    $(window).trigger('resize');
				}, 100);
			}

			{
				var mintPanel = $(this).closest('div.mint-panel');
				var mintPanelButton = $(mintPanel).find('i');
				$('body').toggleClass('fullscreen-mint-panel-mode');
				if( $(mintPanelButton).is('.fa-expand') || $(mintPanelButton).is('.fa-compress') ) {
					mintPanelButton.filter($('.fa-expand, .fa-compress')).toggleClass('fa-expand').toggleClass('fa-compress');
				}
				mintPanel.toggleClass('fullscreen');
				setTimeout(function () {
				    $(window).trigger('resize');
				    if( mintPanel.is('.fullscreen') ) {
				    	$(mintPanel).find('.mint-center-container').css('height','100%');
				    } else {
				    	$(mintPanel).find('.mint-center-container').css('height',$(mintPanel).find('.mint-center-container').css('min-height'));
				    }
				}, 100);
			}
		});
	};

	/**
	 * bootstrap-inspinia Close Event Extended
	 */
    $.fn.panelCloseEvent = function (callback, options) {
    	var self = this;

    	$(this).off("click");
        $(this).on('click', function (e) {
            var content = $(this).closest('div.mint-widget-div');

            var fn_callback = mint.common.fn_callback(callback);
            if( fn_callback ) {
            	fn_callback.apply(content, [e, options]);
            } else {
            	content.remove();
            }
        });
    };

	/**
	 * bootstrap-inspinia Close Event Extended
	 */
    $.fn.panelPlayEvent = function (callback, options) {
    	var self = this;

    	$(this).off("click");
        $(this).on('click', function (e) {

			var widget = $(this).closest('div.mint-widget-div');
			if( widget.is('.items') ) {
				var button = $(widget).find('i');
				if( $(button).is('.fa-play') || $(button).is('.fa-pause') ) {
					button.filter($('.fa-play, .fa-pause')).toggleClass('fa-play').toggleClass('fa-pause');
				}
				widget.toggleClass('stop').toggleClass('start');

				var fn_callback = mint.common.fn_callback(callback);
				if( fn_callback ) {
					fn_callback.apply(widget, [e, options]);
				} else {
					//doSometing..
				}
			}
        });
    };

    $.fn.countup = function(params) {
 		// make sure dependency is present
        if (typeof CountUp !== 'function') {
        	return;
        }

        var defaults = {
        	startVal: 0,
        	decimals: 0,
        	duration: 2,
        };

        if (typeof params === 'number') {
        	defaults.endVal = params;
        }
        else if (typeof params === 'object') {
        	$.extend(defaults, params);
        }
        else {
        	return;
        }

        this.each(function(i, elem) {
        	var countUp = new CountUp(elem, defaults.startVal, defaults.endVal, defaults.decimals, defaults.duration, defaults.options);

        	countUp.start();
        });

        return this;

    };


	$.expr[':'].containsIgnoreCase = function (el, idx, meta) {
		//console.log(jQuery(el).text().toUpperCase() + ', ' + meta[3] + ', ' + jQuery(el).text().toUpperCase().indexOf(meta[3].toUpperCase()));
	    return jQuery(el).text().toUpperCase().indexOf(meta[3].toUpperCase()) >= 0;
	};


}( jQuery ));

/**
 * Mapping Handler
 * @param $
 * @returns
 */
(function ( $ ) {
	//------------------------------------------------------
	// Const
	//------------------------------------------------------
	const title = '[Mint Mapping Handler]';
	const errMsg = 'mintMapper error : ';
	const componentType = {kendo:'kendo'};

	//------------------------------------------------------
	// Variables
	//------------------------------------------------------
	// Selector
	var factory;

	// Canvas
	var $leftDiv, $midDiv, $rightDiv, $canvas;
	var canvasId = "";
	var canvasCtx = null;
	var canvasPtr = null;
	var canvasWidth = 0;
	var canvasHeight = 0;
	var canvasTopMargin = 0;
	var isDisabled = false;

	// Mapping DataSet
	var data = {}; // Input 으로 받은 데이터
	var moveItem = null; // 드래그시 링크 정보 임시 저장
	var mappingInfo = []; // Source, Target 간 맵핑 정보를 저장

	// Mapping Options
	var _options;

	// Mandatory
	var mandatories = [];
	var mandatoryErrorMessage = "This field is mandatory";
	var mandatoryTooltips = true;
	var onError = false;

	// Current ComponentType
	var ccType = 'kendo';

	//------------------------------------------------------
	// Method
	//------------------------------------------------------

	/**
	 * _setOptions
	 * option 설정
	 */
	var _setOptions = function(options) {
		_options = $.extend({
			autoDetect : 'off',
			associationMode : 'oneToOne', //oneToOne, manyToMany
			className : 'mintMapper',
			lineStyle : 'square-ends', // straight or square-ends
			handleColor : '#CF0000,#00AD00,#0000AD,#FF4500,#00ADAD,#AD00AD,#582900,#FFCC00,#000000,#33FFCC'.split(','),
			lineColor : 'black',
			globalAlpha : 1,
			isDisabled: false
		}, options);
		isDisabled = _options.isDisabled;
	}

	/**
	 * _draw
	 * line draw
	 */
	var _draw = function () {

		//------------------------------------------------------
		// Canvas Clear
		//------------------------------------------------------
		{
			canvasCtx.globalAlpha = _options.globalAlpha;
			canvasCtx.beginPath();
			canvasCtx.fillStyle = 'white';
			canvasCtx.strokeStyle = _options.lineColor;
			canvasCtx.clearRect(0, 0, canvasWidth, canvasHeight);
		}

		//------------------------------------------------------
		// 기존 맵핑 정보 추출
		//------------------------------------------------------
		//var tablesAB = chosenListA+"|"+chosenListB; // existingMapping
		var links = mappingInfo.filter(function(x){
			//return x.tables == tablesAB;
			return true;
		});

		//------------------------------------------------------
		// 기존 맵핑 정보 순차적으로 redraw()
		//------------------------------------------------------
		links.forEach(function(item, i) {
			//console.log(item);
			// Source Position
			var Ax = 0;
			var Ay = _itemMidOffset(item.src, 'src');
			// Target Position
			var Bx = canvasWidth;
			var By = _itemMidOffset(item.tag, 'tag');

			canvasCtx.beginPath();
			canvasCtx.moveTo(Ax, Ay);

            var handleCurrentColor = _options.handleColor[i % _options.handleColor.length];
            if(_options.lineStyle == "square-ends" || _options.lineStyle == "square-ends-dotted") {
                canvasCtx.fillStyle = handleCurrentColor;
                canvasCtx.strokeStyle = handleCurrentColor;
                canvasCtx.rect(Ax, Ay - 4, 8, 8);
                canvasCtx.rect(Bx - 8, By - 4, 8, 8);
                canvasCtx.fill();
				canvasCtx.stroke();
                canvasCtx.moveTo(Ax + 8, Ay);
                canvasCtx.lineTo(Ax + 16, Ay);
                canvasCtx.lineTo(Bx - 16, By);
                canvasCtx.lineTo(Bx - 8, By);
                canvasCtx.stroke();
            } else {
                canvasCtx.strokeStyle = handleCurrentColor;
                canvasCtx.lineTo(Bx, By);
                canvasCtx.stroke();
            }
			canvasCtx.closePath();
			canvasCtx.lineWidth = 1;
		});
	}


	/**
	 * _itemMidOffset
	 * - 맵핑 아이템의 중간 위치를 리턴
	 */
	var _itemMidOffset = function(itemOffset, layer) {
		//------------------------------------------------------
		// TODO : kendo 이외 컴포넌트일때 로직 분기
		//------------------------------------------------------
		if( ccType === componentType.kendo ) {

			if(layer == 'src') {
				var layerDiv = $leftDiv.selector;
			}

			if(layer == 'tag') {
				var layerDiv = $rightDiv.selector;
			}

			var itemObj = $(layerDiv + ' tr[data-offset="' + itemOffset + '"]');
			var toolbarH = $(layerDiv + ' .k-header.k-grid-toolbar').outerHeight();
			var headerH  = $(layerDiv + ' .k-grid-header').outerHeight();
			var inlineHeaderH = $(layerDiv + ' table .k-grid-header').outerHeight();

			//var itemObj = $('tr[data-offset="' + itemOffset + '"]');
			var position = itemObj.position();
			var hInner = itemObj.height();
			var hOuter = itemObj.outerHeight();
			var delta = Math.floor(0.5 + (hOuter - hInner)/2);
			var midInner = Math.floor(0.5 + hInner/2);
			var midHeight = !mint.common.isEmpty(position) ? (position.top + midInner - delta -1) : 0;
			{
				midHeight = !mint.common.isEmpty(inlineHeaderH) ? midHeight - inlineHeaderH : midHeight;
				midHeight = midHeight - canvasTopMargin;
			}

			return midHeight + (toolbarH + headerH);
		} else {
			/*
	 		var itemObj = $('.mappingContainer tr').each(function(i, el){
			console.log(el);
			if( $(el).data('offset') == itemOffset )
				return el;
			else
				return null;
			});
			*/
		}
	}

	/**
	 * _makeLink
	 * - Target Event 종료시 호출됨
	 */
	var _makeLink = function(linkInfo) {
		//var tablesAB = chosenListA+"|"+chosenListB;
		var already = false;
		var test = mappingInfo.filter(function(x) {
			//console.log('mappingInfo.size()>0');
			//return x.tables == tablesAB && x.tag == linkInfo.nameB && x.src == linkInfo.nameA;
			//return x.tables == tablesAB && x.tag == linkInfo.offsetB && x.src == linkInfo.offsetA;
			return x.tag == linkInfo.offsetB && x.src == linkInfo.offsetA;
		});

		if(test.length > 0) already = true;

		if(!already) {

			if(_options.associationMode == "oneToOne") {
				for(var i = mappingInfo.length-1; i >=0 ;i--){
					//if(mappingInfo[i].tables == tablesAB && mappingInfo[i].tag == linkInfo.offsetB){
					if(mappingInfo[i].tag == linkInfo.offsetB){
						mappingInfo.splice(i,1);
					}
				}
				for(var i = mappingInfo.length-1; i >=0 ;i--){
					//if(mappingInfo[i].tables == tablesAB && mappingInfo[i].src == linkInfo.offsetA){
					if(mappingInfo[i].src == linkInfo.offsetA){
						mappingInfo.splice(i,1);
					}
				}
			}
			//mappingInfo.push({"tables":tablesAB,"src":linkInfo.nameA,"tag":linkInfo.nameB});
			//mappingInfo.push({"tables":tablesAB,"src":linkInfo.offsetA,"tag":linkInfo.offsetB});
			var pushItem = {"src":linkInfo.offsetA,"tag":linkInfo.offsetB, items: [linkInfo.itemA, linkInfo.itemB]};
			if(_options.addAttributes) {
				pushItem = $.extend(pushItem, _options.addAttributes);
			}
			mappingInfo.push(pushItem);
			//console.log(mappingInfo);
		}
		_draw();
	}

	/**
	 * dreawImmediate
	 * - canvas 안에서 라인 draw 유지시키기 위한 이벤트 처리
	 * - canvas mousemove 이벤트에서 호출
	 */
	var _drawImmediate = function(e) {
		canvasCtx.clearRect(0, 0, canvasWidth, canvasHeight);
		_draw();

		canvasCtx.beginPath();
		var _src = moveItem.offsetA;
		var color= _options.handleColor[_src % _options.handleColor.length];
		canvasCtx.fillStyle = 'white';
		canvasCtx.strokeStyle = color;

		var Ax = 0;
		var Ay = _itemMidOffset(moveItem.offsetA, 'src');

		var relativePosition = _getItemPosition(e);
		var Bx = relativePosition.x;
		var By = relativePosition.y;

		canvasCtx.moveTo(Ax, Ay);
		canvasCtx.lineTo(Bx, By);
		canvasCtx.stroke();
	}

	/**
	 * _getItemPosition
	 * item 의 positoion 리턴
	 */
	var _getItemPosition = function( e) {
	  var rect = canvasPtr.getBoundingClientRect();
	  return {
	    x: e.clientX - rect.left,
	    y: e.clientY - rect.top
	  };
	}

	/**
	 * _eraseLinkA
	 */
	var _eraseLinkA = function (nameA) {
		//var tablesAB = chosenListA+"|"+chosenListB;
		for(var i = mappingInfo.length-1; i >=0 ;i--){
			//if(mappingInfo[i].tables == tablesAB && mappingInfo[i].src == nameA){
			if(mappingInfo[i].src == nameA){
				mappingInfo.splice(i,1);
			}
		}
		_draw();
	}

	/**
	 * _eraseLinkB
	 */
	var _eraseLinkB = function (nameB) {
		//var tablesAB = chosenListA+"|"+chosenListB;
		for(var i = mappingInfo.length-1; i >=0 ;i--){
			//if(mappingInfo[i].tables == tablesAB && mappingInfo[i].tag == nameB){
			if(mappingInfo[i].tag == nameB){
				mappingInfo.splice(i,1);
			}
		}
		_draw();
	}


	/**
	 * _fillChosenLists
	 */
	var _fillChosenLists = function() {
		//listNames = [];
		listA = [];
		listB = [];

		if(chosenListA == "" || chosenListB == "") {
			chosenListA = data.Lists[0].name;
			chosenListB = data.Lists[1].name;

			//console.log('chosenListA[' + chosenListA + '] chosenListB[' + chosenListB + ']');
		}


		data.Lists.forEach(function(x){
			//listNames.push(x.name);
			if(x.name == chosenListA){
				x.list.forEach(function(y){
					listA.push(y);
				});
			}
			if(x.name == chosenListB){
				if(x.mandatories !=undefined){
					mandatories = x.mandatories;
				}
				x.list.forEach(function(y){
					listB.push(y);
				});
			}
		});
		//console.log("listNames:" + listNames);
		//console.log("listA:" + listA);
		//console.log("listB:" + listB);
	}

	/**
	 * _createCanvas
	 * Canvas
	 */
	var _createCanvas = function() {
		canvasId = "cnv_"+Date.now();
		var w = $midDiv.width();
		var h2 = $rightDiv.height();
		var h1 = $leftDiv.height();
		var h = h1 >= h2 ? h1 : h2;
		//이전 Canvas Remove
		$midDiv.find('canvas').each(function() {
			$(this).remove();
		});
		$canvas =  $("<canvas></canvas>");
		$canvas.appendTo($midDiv)
		       .attr("id",canvasId)
			   .css({"width": w+"px","height":h+"px"});
		canvasWidth = w;
		canvasHeight = h;
		canvasPtr = $('#'+canvasId)[0];
		canvasPtr.width = canvasWidth;
		canvasPtr.height = canvasHeight;
		canvasCtx = canvasPtr.getContext("2d");

//		if( ccType === componentType.kendo ) {
//
//			if(layer == 'src') {
//				var layerDiv = $leftDiv.selector;
//			}
//
//			if(layer == 'tag') {
//				var layerDiv = $rightDiv.selector;
//			}
//
//			var itemObj = $(layerDiv + ' tr[data-offset="' + itemOffset + '"]');
//			var toolbarH = $(layerDiv + ' .k-header.k-grid-toolbar').outerHeight();
//			var headerH  = $(layerDiv + ' .k-grid-header').outerHeight();
//			var inlineHeaderH = $(layerDiv + ' table .k-grid-header').outerHeight();
//
//			//var itemObj = $('tr[data-offset="' + itemOffset + '"]');
//			var position = itemObj.position();
//			var hInner = itemObj.height();
//			var hOuter = itemObj.outerHeight();
//			var delta = Math.floor(0.5 + (hOuter - hInner)/2);
//			var midInner = Math.floor(0.5 + hInner/2);
//			var midHeight = !mint.common.isEmpty(position) ? (position.top + midInner - delta -1) : 0;
//			midHeight = !mint.common.isEmpty(inlineHeaderH) ? midHeight - inlineHeaderH : midHeight;
//			return midHeight + (toolbarH + headerH);
//		}


		var headerH = $('.mappingSource .k-grid-header').outerHeight();
		//canvasTopMargin = mint.common.isEmpty(headerH) ? canvasTopMargin : headerH;
		$canvas.css("margin-top", canvasTopMargin + "px");
	}

	/**
	 * _setListeners(Event 붙이기)
	 */
	var _setListeners = function() {
		//-----------------------------------------------------------------------
		// Canvas Event
		//-----------------------------------------------------------------------
		{
			/**
			 * Canvas 에서 Mousemove 이벤트 처리
			 */
			$(factory).find("canvas").on("mousemove", function(e) {
				//console.log('dhkim::canvas-mousemove');
				if(isDisabled) return;
				if(moveItem != null){
					_drawImmediate(e);
				}
			});

			/**
			 * Mapping Div 안에서 MouseUp 이벤트 처리
			 */
			$(factory).find(".mappingContainer").on("mouseup", function(e) {
				//console.log('dhkim::mappingContainer-mouseup');
				if(isDisabled) return;
				if(moveItem != null) {
					moveItem = null;
					_draw();
				}
			});
		}

		//-----------------------------------------------------------------------
		// Source Event
		//-----------------------------------------------------------------------
		{
			//-----------------------------------------------------------------------
			// Source item mousedown 이벤트(드레그 시작전 준비)
			//-----------------------------------------------------------------------
			$(factory).find(".mappingContainer .mappingSource tbody tr").off("mousedown").on("mousedown",function(e) {
				//console.log('dhkim::mappingContainer-mappingSource-mousedown');
				//console.log(e);

				if(isDisabled) return;
				{
					moveItem = {};
					moveItem.offsetA = $(this).data('offset');
					moveItem.nameA   = $(this).data('name');
					moveItem.itemA   = $(this).data('item');
					moveItem.offsetB = -1;
					moveItem.nameB   = -1;
					moveItem.itemB   = -1;
				}

				//console.log(moveItem);

				//console.log('dhkim::mappingContainer-mappingSource-mousedown-moveItem:' + JSON.stringify(moveItem));
			});

			//-----------------------------------------------------------------------
			// Source item mouseup 이벤트(취소)
			//-----------------------------------------------------------------------
			$(factory).find(".mappingContainer .mappingSource tbody tr").off("mouseup").on("mouseup" , function(e) {
				//console.log('dhkim::mappingContainer-mappingSource-mouseup');

				if (isDisabled) return;
				//console.log('dhkim::cancel....');
				moveItem = null;
			});

		}

		//-----------------------------------------------------------------------
		// Target Event
		//-----------------------------------------------------------------------
		{
			//-----------------------------------------------------------------------
			// 드래그 상태에서 Target 에 mousemove 이벤트 발생시 Line 을 이동 시켜준다
			//-----------------------------------------------------------------------
			$(factory).find(".mappingContainer .mappingTarget tbody tr").off("mousemove").on("mousemove",function(e) {
				//console.log('dhkim::mappingContainer-mappingTarget-mousemove');

				if(isDisabled) return;

				if(moveItem != null) { // drag occuring
					//console.log('--------------------');
					//console.log(moveItem);
					//console.log('--------------------');

					var src = moveItem.offsetA;
					var tag   = $(this).data("offset");

					var Ax = 0;
					var Bx = canvasWidth;

					var Ay = _itemMidOffset(src, 'src');
					var By = _itemMidOffset(tag, 'tag');

					_draw();

					//console.log(Ax + ' ' + Ay + ' ' + Bx + ' ' +  By);

					{
						canvasCtx.beginPath();
						var color= _options.handleColor[src % _options.handleColor.length];
						canvasCtx.fillStyle = 'white';
						canvasCtx.strokeStyle = color;
						canvasCtx.moveTo(Ax, Ay);
						canvasCtx.lineTo(Bx, By);
						canvasCtx.stroke();
					}
				}

			});

			//-----------------------------------------------------------------------
			// Target item mouseup 이벤트(드레그 종료)
			//-----------------------------------------------------------------------
			$(factory).find(".mappingContainer .mappingTarget tbody tr").off("mouseup").on("mouseup", function(e) {
				//console.log('dhkim::mappingContainer-mappingTarget-mouseup');

				if (isDisabled) return;

				if(moveItem != null) { // no drag
					if(_options.associationMode == 'oneToOne') {
						_eraseLinkB($(this).data('name')); // erase an existing link
					}

					moveItem.offsetB = $(this).data('offset');
					moveItem.nameB   = $(this).data('name');
					moveItem.itemB   = $(this).data('item');

					// Object Copy
					var linkInfo = JSON.parse(JSON.stringify(moveItem));
					moveItem = null;

					//Line Draw
					_makeLink(linkInfo);
				}
			});

			//-----------------------------------------------------------------------
			// Target item 을 더블 클릭하면 맵핑을 Clear 시킨다
			//-----------------------------------------------------------------------
			$(factory).find(".mappingContainer .mappingTarget tr").off("dblclick").on("dblclick", function(e) {
				//console.log('dhkim::mappingContainer-mappingTarget-dblclick');

				if (isDisabled) return;

				_eraseLinkB($(this).data("offset"));
				_draw();
			});
		}

		//-----------------------------------------------------------------------
		// Etc Event
		//-----------------------------------------------------------------------
		{
			//-----------------------------------------------------------------------
			// Clear Event
			//-----------------------------------------------------------------------
			$(factory).find(".mappingContainer .mappingSource tr .unlink").off("click").on("click", function(e) {
				if (isDisabled) return;
				_eraseLinkA($(this).parent().data("name"));
				_draw();
			});

			//-----------------------------------------------------------------------
			// Clear Button Event
			//-----------------------------------------------------------------------
			if (_options.buttonErase) {
				$(factory).find(".mappingContainer .eraseLink").on("click", function(e) {
					if (isDisabled) return;
					mappingInfo.length = 0;
					_draw();
				});
			}
			//-----------------------------------------------------------------------
			// Kendo Scroll Event
			//-----------------------------------------------------------------------

			if( ccType === componentType.kendo ) {

				var scrollElements = $(factory).find(".k-auto-scrollable");
				scrollElements.each(function(e) {
					var scrollElement = this;
					var headerWrap = $(scrollElement).closest('.k-grid').find('.k-grid-header-wrap');

					$(scrollElement).off("scroll").on("scroll", function(e) {
						if (isDisabled) return;

						var scrollLeft = $(scrollElement).scrollLeft();
						var scrollTop  = $(scrollElement).scrollTop();
						headerWrap.scrollLeft(scrollLeft);
						_draw();
					});
				});
			}

		}// end etc event
	}// end event


	/**
	 * Module Main - MapperObject Constructor Function
	 */
	var MapperObject = function(placeholder, input, options) {
		var _self = this;
		factory = placeholder;
		//TODO: class 접근이 아닌 다른 방법으로 생각해 봐야할까??
		$leftDiv  = $('.mappingSource');
		$midDiv   = $('.mappingDiv');
		$rightDiv = $('.mappingTarget');

		var _init = function() {
	        data = JSON.parse(JSON.stringify(input));
	      	//option setting
	      	if(options) {
	        	_setOptions(options);
	      	}
			//Create Canvas
			_createCanvas();
			//Event Listener Add
			_setListeners();
			//Mapping 정보가 있으면 Mapping
	        if(data.existingLinks) {
	        	//console.log('mapping existing....');
	        	mappingInfo = data.existingLinks;
				mappingInfo.forEach(function(x){
					//x.tables = tablesAB;
				});
				//console.log(mappingInfo);
	        } else {
	        	//console.log('mapping init....');
	        	mappingInfo =[];
	        }

	        $(window).resize(function() {
	        	if($('.mappingContainer').length > 0 ) {
		            canvasWidth = $(factory).find(".mappingContainer .mappingDiv").width();
		            canvasPtr.width = canvasWidth;
		            $("#"+canvasId).css("width",canvasWidth+"px");
		            _draw();
	        	}
	        });

	        _draw();
	    };

	    _self.getMappingInfo = function() {
            if(!onError) {
                var isMandatoryError = false;
                var links = null;
                var errorMessage = mandatoryErrorMessage + " : ";
                var fieldInErrorName = "";
                links = mappingInfo;
                mandatories.forEach(function (m, i) {
                    if (!isMandatoryError) {
                        var match = mappingInfo.filter(function (link) {
                            return link.tag == m;
                        });
                        if (match.length == 0) {
                            isMandatoryError = true;
                            fieldInErrorName = m;
                        }
                    }
                });
                if (isMandatoryError) {
                    return {
                        error: true,
                        errorMessage: errMsg + fieldInErrorName,
                        links: []
                    };
                } else {
					links.forEach(function(x){
						delete x.tables;
					});
                    return {
                        error: false,
                        errorMessage: '',
                        links: links
                    };
                }
            } else {
                return [];
            }
        };

	    _self.change = function() {
			_init();
        };

	    _self.initMapping = function() {
			mappingInfo =[];
			_draw();
        };

	    _init();

	}// end function

	$.mintMapper = function(placeholder, input, options) {
		var mapper = new MapperObject($(placeholder), input, options);
		$(placeholder).data('mintMapper', mapper);
		return mapper;
	};

	$.fn.mintMapper = function(input, options) {
		return this.each(function() {
			$.mintMapper(this, input, options)
		});
	};

}( jQuery ));


/**
 * ModelObject Handler
 * @param $
 * @returns
 */
(function ( $ ) {
	//------------------------------------------------------
	// Const
	//------------------------------------------------------
	const title = '[Mint ModelObject Handler]';
	const errMsg = 'mintModel error : ';
	const componentType = {kendo:'kendo'};
	const directionType = {ONEWAY: '0', TWOWAY: '1'};

	//------------------------------------------------------
	// Variables
	//------------------------------------------------------
	var factory;
	var modelObject;
	var selectedIoType;
	var selectedItem;
	var options;

	//------------------------------------------------------
	// Method
	//------------------------------------------------------
	/**
	 * setOptions
	 */
	var setOptions = function(opt) {
		options = $.extend({
			interfaceDirection : directionType.ONEWAY,
		}, opt);
	}

	/**
	 * getModelObject
	 */
    var getModelObject = function() {
    	return modelObject;
    };

	/**
	 * getAppModel
	 */
    var getAppModel = function(systemId, systemType, seq) {
		var appModel = modelObject.appModelList.filter(function(v, i, a){
			if(v.systemId == systemId && v.systemType == systemType && v.seq == seq)
				return true;
		});

		if(mint.common.isEmpty(appModel[0])) {
			appModel[0] = new AppModelObject({systemId: systemId, systemType: systemType, seq: seq});
			modelObject.appModelList.push(appModel[0]);
		}

		return appModel[0];
    };

	/**
	 * getSrcAppModel
	 */
    var getSrcAppModel = function() {
		return getAppModel(selectedItem.sourceSystemId, selectedItem.sourceSystemType, selectedItem.sourceSystemSeq);
    };

	/**
	 * getTagAppModel
	 */
    var getTagAppModel = function() {
		return getAppModel(selectedItem.targetSystemId, selectedItem.targetSystemType, selectedItem.targetSystemSeq);
    };

	/**
	 * getMsgModel
	 */
    var getMsgModel = function(appModel) {
		var msgModel = appModel.msgModelList.filter(function(v, i, a) {
			if(v.msgIoType == selectedIoType)
				return true;
		});

		return mint.common.isEmpty(msgModel) ? new MsgModelObject() : msgModel;
    };

	/**
	 * getSrcMsgModel
	 */
    var getSrcMsgModel = function() {
    	var appModel = getAppModel(selectedItem.sourceSystemId, selectedItem.sourceSystemType, selectedItem.sourceSystemSeq);
		return getMsgModel(appModel);
    };

	/**
	 * getTagMsgModel
	 */
    var getTagMsgModel = function() {
    	var appModel = getAppModel(selectedItem.targetSystemId, selectedItem.targetSystemType, selectedItem.targetSystemSeq);
		return getMsgModel(appModel);
    };

	/**
	 * getMapModel
	 */
    var getMapModel = function(appModel) {
		var mapModel = appModel.mapModelList.filter(function(v, i, a) {
			if(v.mapIoType == selectedIoType)
				return true;
		});

		return mint.common.isEmpty(mapModel) ? new MapModelObject() : mapModel;
    };

	/**
	 * getSrcMapModel
	 */
    var getSrcMapModel = function() {
    	var appModel = getAppModel(selectedItem.sourceSystemId, selectedItem.sourceSystemType, selectedItem.sourceSystemSeq);
		return getMapModel(appModel);
    };

	/**
	 * getTagMapModel
	 */
    var getTagMapModel = function() {
    	var appModel = getAppModel(selectedItem.targetSystemId, selectedItem.targetSystemType, selectedItem.targetSystemSeq);
		return getMapModel(appModel);
    };

	/**
	 * setSelectedItem
	 */
    var setSelectedItem = function(ioType, item) {
    	selectedIoType = ioType;
    	selectedItem = item;
    };

	/**
	 * msgModelCheck
	 */
	var msgModelCheck = function(layer, appModel, dataSetItem) {
		var isBinding = true;
		var patternCheck = false;

		//-----------------------------------------------------------------------
		// 기준정보의 레이아웃 변경시, 맵핑 초기화 이때 사용자 취사 선택 하도록 Alerting
		//-----------------------------------------------------------------------
		if( layer == 'source' && selectedItem.interfacePattern == '1:N' ) {
			patternCheck = true;
		}

		if( layer == 'target' && selectedItem.interfacePattern == 'N:1' ) {
			patternCheck = true;
		}

		if(patternCheck) {
			var msgModel = appModel.msgModelList.filter(function(v, i, a) {
				if(v.msgIoType == selectedIoType)
					return true;
			});

			if(msgModel.length > 0 && msgModel[0].dataSetId != dataSetItem.dataSetId) {
				var confirm = mint.common.confirm('[' + appModel.systemNm + '] 에 설정된 레이아웃 정보가 변경됩니다. 계속하시겠습니까?');
				if(confirm) {
					// 관련 맵핑 정보 초기화
					modelObject.appModelList.forEach(function(v, i, a) {
						var filter = v.mapModelList.filter(function(vv, ii, aa) {
							if(vv.mapIoType != selectedIoType) {
								return true;
							}
						});
						v.mapModelList = filter;
					});
				} else {
					isBinding = false;
				}
			}
		}

		return isBinding;
	}

	/**
	 * setMsgModel
	 */
    var setMsgModel = function(layer, appModel, dataSet, mapData) {
    	var isBinding = true;

		if( !msgModelCheck(layer, appModel, dataSet) ) {
			isBinding = false;
		} else {
			// selectedIoType 에 해당하는 레이아웃 제외후 rebinding(삭제 의미)
			var msgModelFilter = appModel.msgModelList.filter(function(v, i, a) {
				if(v.msgIoType != selectedIoType)
					return true;
			});
			appModel.msgModelList = msgModelFilter;

			var	msgModelObject = new MsgModelObject({
				interfaceMid: modelObject.interfaceMid,
				appMid: appModel.appMid,
				msgModelNm: dataSet.name1,
				msgIoType: selectedIoType,
				dataSetId: dataSet.dataSetId,
				dataSet: dataSet
			});
			appModel.msgModelList.push(msgModelObject);
			isBinding = true;
		}

		return isBinding;
    };

	/**
	 * setSrcMsgModel
	 */
    var setSrcMsgModel = function(dataSet, mapData) {
	    return setMsgModel('source', getSrcAppModel(), dataSet, mapData);
    };

	/**
	 * setTagMsgModel
	 */
    var setTagMsgModel = function(dataSet, mapData) {
	    return setMsgModel('target', getTagAppModel(), dataSet, mapData);
    };

	/**
	 * setMapModel
	 */
    var setMapModel = function(mapData) {
    	//-----------------------------------------------------------------------
    	// TODO: mapModel 의 parnet Model 고민 필요
    	//-----------------------------------------------------------------------
    	var baseAppModel = null;
    	var pairAppModel = null;
    	//-----------------------------------------------------------------------
    	// 단방향
    	//-----------------------------------------------------------------------
    	if( options.interfaceDirection == directionType.ONEWAY ) {
    		//-----------------------------------------------------------------------
    		// 1:1, 1:N 일때 target 기준 맵핑
    		//-----------------------------------------------------------------------
    		if( selectedItem.interfacePattern == '1:1' || selectedItem.interfacePattern == '1:N') {
    			baseAppModel = getTagAppModel();
    			pairAppModel = getSrcAppModel();
    		}

    		//-----------------------------------------------------------------------
    		// N:1 일때 source 기준 맵핑
    		//-----------------------------------------------------------------------
    		if( selectedItem.interfacePattern == 'N:1' ) {
    			baseAppModel = getSrcAppModel();
    			pairAppModel = getTagAppModel();
    		}
    	}
    	//-----------------------------------------------------------------------
    	// 양방향
    	//-----------------------------------------------------------------------
    	if( options.interfaceDirection == directionType.TWOWAY ) {
    		//-----------------------------------------------------------------------
    		// 1:1, N:1 일때 source 기준 맵핑
    		//-----------------------------------------------------------------------
    		if( selectedItem.interfacePattern == '1:1' || selectedItem.interfacePattern == 'N:1' ) {
    			baseAppModel = getSrcAppModel();
    			pairAppModel = getTagAppModel();
    		}

    		//-----------------------------------------------------------------------
    		// 1:N 일때 N/A
    		//-----------------------------------------------------------------------
    		if( selectedItem.interfacePattern == '1:N') {
    			//Invalide Case
    		}
    	}
    	//-----------------------------------------------------------------------
    	// Case Error
    	// TODO : 정리 필요..
    	//-----------------------------------------------------------------------
    	if( mint.common.isEmpty(baseAppModel) || baseAppModel.length == 0 ) {
    		mint.common.alert('[개발자 참고] AppModel 설정에 오류가 있습니다.');
    		return;
    	}

		//-----------------------------------------------------------------------
		// 기 등록된 맵핑정보 Clear
		//-----------------------------------------------------------------------
    	{
			var oldMapModelFilter = baseAppModel.mapModelList.filter(function(v, i, a) {
				if(v.mapIoType == selectedIoType)
					return true;
			});

			// selectedIoType 에 해당하는 맵핑정보 제외후 rebinding(삭제 의미)
			var newMapModelFilter = baseAppModel.mapModelList.filter(function(v, i, a) {
				if(v.mapIoType != selectedIoType)
					return true;
			});
			baseAppModel.mapModelList = newMapModelFilter;

			// 삭제 대상 맵핑이 있으면, pairAppModel 에서도 삭제
			if( oldMapModelFilter.length > 0 ) {
				var deleteIndex = -1;
				pairAppModel.mapModelList.forEach(function(v, i, a) {
					if( v.mapIoType == oldMapModelFilter[0].mapIoType && v.mapId == oldMapModelFilter[0].mapId ) {
						deleteIndex = i;
					}
				});
				if( deleteIndex >= 0 ) {
					pairAppModel.mapModelList.splice(deleteIndex, 1);
				}
			}
    	}

		//-----------------------------------------------------------------------
		// 맵핑 add
		//-----------------------------------------------------------------------
		if(!mint.common.isEmpty(mapData.mapId)) {
			// baseAppModel-mapModel binding
			{
				var baseMapModelObject = new MapModelObject({
					interfaceMid: modelObject.interfaceMid,
					appMid: baseAppModel.appMid,
					mapModelNm: mapData.name,
					mapIoType: selectedIoType,
					mapId: mapData.mapId,
					dataMap: mapData
				});
				baseAppModel.mapModelList.push(baseMapModelObject);
			}

			// pairAppModel-mapModel binding
			{
				var pairMapModelObject = new MapModelObject({
					interfaceMid: modelObject.interfaceMid,
					appMid: pairAppModel.appMid,
					mapModelNm: mapData.name,
					mapIoType: selectedIoType,
					mapId: mapData.mapId,
					dataMap: mapData
				});
				pairAppModel.mapModelList.push(pairMapModelObject);
			}

		}

    };

	/**
	 * getLayoutModel
	 */
	var getLayoutModel = function(layoutType) {
		try {

			var MapModel = {
				mapId: '',
				name: '',
				cd: '',
				dataMapItemList: []
			};

			var DataSetModel = {
				dataSetId: '',
				cd: '',
				name1: '',
				name2: '',
				dataFieldList: []
			};

			var LayoutModel = {
				mapData: MapModel,
				srcDataSet: DataSetModel,
				tagDataSet: DataSetModel
			};

			if(layoutType == 'map') {
				return MapModel;
			} if(layoutType == 'layout') {
				return DataSetModel;
			} else {
				return LayoutModel;
			}


		} catch( e ) {
			mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "aaa"});
		}
	};

	/**
	 * getLayoutItem
	 */
    var getLayoutItem = function() {

		var srcMsgModel = getSrcMsgModel();
		var tagMsgModel = getTagMsgModel();

		var srcMapModel = getSrcMapModel();
		var tagMapModel = getTagMapModel();

		var layoutItem = getLayoutModel();

		// 요청이면 default
		if(selectedIoType == 0) {
			if(srcMsgModel) {
				layoutItem.srcDataSet = srcMsgModel.length > 0 ? srcMsgModel[0].dataSet : layoutItem.srcDataSet;
			}
			if(tagMsgModel) {
				layoutItem.tagDataSet = tagMsgModel.length > 0 ? tagMsgModel[0].dataSet : layoutItem.tagDataSet;
			}
		}

		// 응답이면, src/tag 위치를 바꾼다
		if(selectedIoType == 1) {
			if(tagMsgModel) {
				layoutItem.srcDataSet = tagMsgModel.length > 0 ? tagMsgModel[0].dataSet : layoutItem.srcDataSet;
			}
			if(srcMsgModel) {
				layoutItem.tagDataSet = srcMsgModel.length > 0 ? srcMsgModel[0].dataSet : layoutItem.tagDataSet;
			}
		}

		if(srcMapModel.length > 0 && tagMapModel.length > 0 ) {
			//-----------------------------------------------------------------------
			// 1:1, 1:N 일때 target 기준 맵핑 정보 참조
			//-----------------------------------------------------------------------
			if( selectedItem.interfacePattern == '1:N' || selectedItem.interfacePattern == '1:1' ) {
				var isPairs = srcMapModel.filter(function(v, i, a) {
					if(v.mapId == tagMapModel[0].mapId)
						return true;
				});
				if(isPairs.length > 0)
					layoutItem.mapData = tagMapModel[0].dataMap;
			}
			//-----------------------------------------------------------------------
			// N:1 일때 source 기준 맵핑 정보 참조
			//-----------------------------------------------------------------------
			if( selectedItem.interfacePattern == 'N:1' ) {
				layoutItem.mapData = srcMapModel[0].dataMap;
			}
		}

		return layoutItem;
    };

	/**
	 * Module Main - ModelObject Constructor Function
	 */
	var ModelObject = function(placeholder, input, options) {
		var _self = this;


		var _init = function() {
			factory = placeholder;

			if(mint.common.isEmpty(input)) {
				modelObject = new InterfaceModelObject();
			} else {
				modelObject = input;
			}

	      	if(options) {
	        	setOptions(options);
	      	}
		};

	    _self.getModelObject = function() {
			return getModelObject();
        };

	    _self.getSrcAppModel = function() {
			return getAppModel(selectedItem.sourceSystemId, selectedItem.sourceSystemType, selectedItem.sourceSystemSeq);
        };

	    _self.getTagAppModel = function() {
			return getAppModel(selectedItem.targetSystemId, selectedItem.targetSystemType, selectedItem.targetSystemSeq);
        };

        _self.setSelectedItem = function(ioType, item) {
        	setSelectedItem(ioType, item);
        };

        _self.setSrcMsgModel = function(dataSet, mapData) {
        	return setSrcMsgModel(dataSet, mapData);
        };

        _self.setTagMsgModel = function(dataSet, mapData) {
        	return setTagMsgModel(dataSet, mapData);
        };

        _self.setMapModel = function(mapData) {
        	setMapModel(mapData);
        };

        _self.getLayoutItem = function() {
        	return getLayoutItem();
        };

        _init();

//	    return {
//	    	getModelObject : getModelObject,
//	    	setSelectedItem : setSelectedItem
//	    };

	}// end function

	$.mintModel = function(placeholder, input, options) {
		var model = new ModelObject($(placeholder), input, options);
		$(placeholder).data('mintModel', model);
		return model;
	};

	$.fn.mintModel = function(input, options) {
		return this.each(function() {
			$.mintModel(this, input, options)
		});
	};

}( jQuery ));



var InterfaceModelObject = function(options) {
	var model = {
		objectType: "InterfaceModelObject",
		interfaceMid: "",
		interfaceModelNm: "",
		interfaceId: "",
		stage: "",
		stageNm: "",
		deployStatus: "",
		deployStatusNm: "",
		appModelList: [],
		comments: "",
		delYn: "N",
		regDate: "",
		regId: "",
		modDate: "",
		modId: "",
		regUser: null,
		modUser: null
	};

	if(options) {
		model = $.extend(model, options);
	}

	return model;
};

var AppModelObject = function(options) {
	var model = {
		objectType: "AppModelObject",
		interfaceMid: "",
		appMid: "",
		appModelNm: "",
		appModelCd: "",
		appModelType: "",
		appModelTypeNm: "",
		systemId: "",
		systemNm: "",
		systemCd: "",
		systemType: "",
		systemTypeNm: "",
		seq: "",
		serverId: "",
		serverNm: "",
		serverCd: "",
		hostNm: "",
		ip: "",
		msgModelList:[],
		mapModelList:[],
		comments: "",
		delYn: "N",
		regDate: "",
		regId: "",
		modDate: "",
		modId: "",
		regUser: null,
		modUser: null
	};

	if(options) {
		model = $.extend(model, options);
	}

	return model;
};

var MsgModelObject = function(options) {
	var model = {
		objectType: "MsgModelObject",
		interfaceMid: "",
		appMid: "",
		msgMid: "",
		msgModelNm: "",
		dataSetId: "",
		msgIoType: "",
		dataSet:
		{
			objectType: "DataSet",
			dataSetId: "",
			name1: "",
			name2: "",
			cd: "",
			dataFormat: "",
			dataFormatNm: "",
			encryptType: "",
			encryptTypeNm: "",
			length: -1,
			isStandard: "N",
			use: "N",
			isMapped: "N",
			isRoot: "Y",
			dataFieldList: [],
			complexTypeMap: {},
			comments: "",
			delYn: "N",
			regDate: "",
			regId: "",
			modDate: "",
			modId: "",
			regUser: null,
			modUser: null,
			dataSetInterfaceMap: null
		},
		comments: "",
		delYn: "N",
		regDate: "",
		regId: "",
		modDate: "",
		modId: "",
		regUser: null,
		modUser: null
	};

	if(options) {
		model = $.extend(model, options);
	}

	return model;
};

var MapModelObject = function(options) {
	var model = {
		objectType: "MapModelObject",
		interfaceMid: "",
		appMid	: "",
		mapMid: "",
		mapModelNm: "",
		mapId: "",
		mapIoType: "",
		dataMap:
		{
			objectType: "DataMap",
			mapId: "",
			name: "",
			cd: "",
			srcDataSetId: "",
			srcDataSetNm1: "",
			srcDataSetNm2: "",
			srcSystemId: "",
			srcSystemCd: "",
			srcSystemNm1: "",
			srcSystemNm2: "",
			srcDataSetCd: "",
			tagDataSetId: "",
			tagDataSetNm1: "",
			tagDataSetNm2: "",
			tagDataSetCd: "",
			tagSystemId: "",
			tagSystemCd: "",
			tagSystemNm1: "",
			tagSystemNm2: "",
			delYn: "N",
			regDate: "",
			regId: "",
			modDate: "",
			modId: "",
			regUser: null,
			modUser: null,
			interfaceId: "",
			isInterfaceMapped: "N",
			dataMapItemList:[]
		},
		comments: "",
		delYn: "N",
		regDate: "",
		regId: "",
		modDate: "",
		modId: "",
		regUser: null,
		modUser: null
	};

	if(options) {
		model = $.extend(model, options);
	}

	return model;
};