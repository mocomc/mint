/*****************************************************************************
 * Program Name : mint.ws.js
 * Description
 *   - WebSocket
 *     mint.ws.{함수명};
 *     mint.ws.connect();
 *   - readState
 *      0 = CONNECTING
 *      1 = OPEN
 *      2 = CLOSING
 *      3 = CLOSED
 *
 ****************************************************************************/
//var wsUrl = 'ws://' + location.host + mint.baseServiceUrl + '/push/dashboard/prototype';
var wsUrl = 'ws://' + location.host + mint.baseServiceUrl + '/push/front';

var mint_ws = function(options) {

	this.title = 'WebSocket Module';
	this.options = null;
	this.ws = null;
	this.retryCount = 60;
	this.retryInterval = 5000; //5초
	this.deferredObject = null;
	this.dataType = this._prop(this.options, 'dataType', 'json');
	this.listeners = new Map();
	this.notifyObject = null;

	if (this._isNotEmpty(options, 'url')) {
		this.options = options;
	} else {
		throw new Error("Missing argument, example usage: mint.ws({ url: 'ws://127.0.0.1:3000' }); ");
	}

	var self = this;
	this.api = (function() {
		return {
			msgType : {ON : 'ON', OFF : 'OFF', REQ : 'REQ', RES : 'RES', PUSH : 'PUSH', ACK : 'ACK'},
			//-----------------------------------------------------------------------
			// DisplayTitle
			//-----------------------------------------------------------------------
			displayTitle: function() {
				return self.title;
			},
			//-----------------------------------------------------------------------
			// Connect
			//-----------------------------------------------------------------------
			connect: function() {
				return $.extend(self.api, self._connect.apply(self, []));
			},
			//-----------------------------------------------------------------------
			// isConnected
			//-----------------------------------------------------------------------
			isConnected: function() {
				return self._isConnected.apply(self, []);
			},
			//-----------------------------------------------------------------------
			// sendMessage
			//-----------------------------------------------------------------------
			sendMessage: function(data) {
				return $.extend(self.api, self._send.apply(self, [data]));
			},
			//-----------------------------------------------------------------------
			// receiveMessage
			//-----------------------------------------------------------------------
			receiveMessage: function(listenerKey, listenerName, listener, options) {
				return $.extend(self.api, self._receive.apply(self, [listenerKey, listenerName, listener, options]));
			},
			//-----------------------------------------------------------------------
			// close
			//-----------------------------------------------------------------------
			close: function() {
				self._removeListenerAll.apply(self, []);
				self._close.apply(self, []);
				self._reset.apply(self, []);
			},
			//-----------------------------------------------------------------------
			// getListenerKeys
			//-----------------------------------------------------------------------
			getListenerKeys: function() {
				return self._getListenerKeys.apply(self, []);
			},
			//-----------------------------------------------------------------------
			// getListenerValues
			//-----------------------------------------------------------------------
			getListenerValues: function(listenerKey) {
				return self._getListenerValues.apply(self, [listenerKey]);
			},
			//-----------------------------------------------------------------------
			// removeListener
			//-----------------------------------------------------------------------
			removeListener: function(listener) {
				self._removeListener.apply(self, [listener]);
			},
			//-----------------------------------------------------------------------
			// removeListenerAll
			//-----------------------------------------------------------------------
			removeListenerAll: function() {
				self._removeListenerAll.apply(self, []);
			}
		};
	})();

    return this.api;
};
/*****************************************************************************
 * mint_ws private function list
 *****************************************************************************/

/**
 * _isConnected()
 */
mint_ws.prototype._isConnected = function() {
	var fnName = 'mint.ws._isConnected()';
	try{
		return this.ws !== null && this.ws.readyState === 1;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * [ CALL-STEP-01 ]
 * _connect()
 */
mint_ws.prototype._connect = function() {
	var fnName = 'mint.ws._connect()';
	try{
		var self = this;

		if (!this.deferredObject || this.deferredObject.state() !== 'pending') {
			this._reset();
		}

		if (this.ws && this.ws.readyState === 1) {
			mint.common.debugLog('WebSocket.connect(reused)');
			this.deferredObject.resolve(this.ws);
		} else {
			mint.common.debugLog('WebSocket.connect(new)');
			this._connectTry();
		}

		return self.deferredObject.promise.apply(self, []);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * [ CALL-STEP-02 ]
 * _connectTry()
 */

mint_ws.prototype._connectTry = function() {
	var fnName = 'mint.ws._connectTry()';
	try{
		var self = this;

		this._initSocket()
		.done(function() {
			self.deferredObject.resolve.apply(self, [self.ws]);
		})
		.fail(function(e) {
			mint.common.debugLog('WebSocket connection retry....(retryCount:' + self.retryCount +')');
			self.retryCount--;
			if (self.retryCount > 0) {
				mint.common.setTimeout(function() {
					self._connect.apply(self, []);
				}, self._prop.apply(self, [self.options, 'retryInterval', 5000]));
			} else {
				self.deferredObject.rejectWith.apply(self, [e]);
			}
		});

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * [ CALL-STEP-03 ]
 * _initSocket()
 */
mint_ws.prototype._initSocket = function() {
	var fnName = 'mint.ws._initSocket()';
	try {

		var deferred = $.Deferred();
		if (this.ws) {
			if (this.ws.readyState === 2 || this.ws.readyState === 3) {
				this.ws.close();
			} else if (this.ws.readyState === 0) {
				return deferred.promise();
			} else if (this.ws.readyState === 1) {
				deferred.resolve(this.ws);
				return deferred.promise();
			}
		}
		var eventHandler = this._socketEventHandler(deferred);
		this.ws = this._createWebSocket($.extend(this.options, eventHandler));

		return deferred.promise();

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}

};

/**
 * [ CALL-STEP-04 ]
 * _createWebSocket
 * @param options
 * @returns {WebSocket}
 */
mint_ws.prototype._createWebSocket = function(options) {
	var fnName = 'mint.ws._createWebSocket()';
	try {

		var self = this;
		var ws = new WebSocket(options.url);

		//-----------------------------------------------------------------------
		// eventHandler binding (open/close/message/error)
		//-----------------------------------------------------------------------
		{
			$(ws)
			.bind('open', options.open)
			.bind('close', options.close)
			.bind('message', options.message)
			.bind('error', options.error);
		}

		return ws;

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _socketEventHandler
 * @param deferred
 * @returns {eventHandler}
 */
mint_ws.prototype._socketEventHandler = function(deferred) {
	var fnName = 'mint.ws._socketEventHandler()';
	try{
		var self = this;
		var eventHandler = {
			open: function(event) {
				mint.common.debugLog('WebSocket.onopen()' );
				var socket = this;
				if (deferred) {
					deferred.resolve(socket);
				}

				//-----------------------------------------------------------------------
				// Notification 이 있으면 clear 하고 웹소켓을 재호출 한다
				//-----------------------------------------------------------------------
				if( !mint.common.isEmpty(self.notifyObject) ) {
					// (1) Notification Clear
					{
						toastr.clear(self.notifyObject);
						self.notifyObject = null;
					}

					// (2) Listener 에 등록된 serviceCd 를 조회해서 serviceOn 재요청을 한다
					{
						var keys = self.listeners.keys();
						for(var i = 0; i < keys.length; i++ ) {
							var key = keys[i];
							mint.wsSend(null, 'reConnect.serviceOn', key, mint.ws.msgType.ON, null, null);
						}
					}
				}
			},
			close: function(event) {
				var notiMsg = '';
				var logMsg = [];
				logMsg.push('WebSocket.close()');

				var evt = event.originalEvent;
				if (deferred) {
				    deferred.rejectWith(event);
				}

				logMsg.push(' > code[' + evt.code + '] resson[' + evt.reason +']');

				//-----------------------------------------------------------------------
				// WebSocket Close Notify
				// TODO: 이벤트를 포워딩할 수 있는 방법을 찾아 볼것.
				//-----------------------------------------------------------------------
				var notifyType = 'error';
				var notifyMsg = [];
				notifyMsg.push('<span><h4>' + mint.message.getMessage('CE10001', null) + '</h4></span>');
				notifyMsg.push('<span><h5>code[' + evt.code + '] reaseon[' + evt.reason + ']</h5></span>');

				switch( evt.code ) {
					case 1001: // WAS Stopped
					case 4888: // HttpSession Closed ( IIP Define Error )
						break;
					case 1006: // Network fail ( e.g. Connection Reset ... )
						if ( self.deferredObject ) {
							logMsg.push(' > deferredObject.state : ' + self.deferredObject.state());
							if ( self.deferredObject.state() !== 'pending') {
								if( self.retryCount > 0 ) {
									self._connect();
								}
							} else {
								//Nothing
							}
						} else {
							self._connect();
							logMsg.push(' > deferredObject is null');
						}

						notifyType = 'warning';
						notifyMsg.push('<span class="wsRetryCountDisplay"></span>');
					break;
					default:
						break;
				}

				//-----------------------------------------------------------------------
				// Notify
				//-----------------------------------------------------------------------
				var notify = $('.wsNotification');
				if( !mint.common.isEmpty(notify) && notify.size() > 0 ) {
					var notifyObject = mint.common.jobNotification(notifyMsg.join(''), notifyType);
					if( !mint.common.isEmpty(notifyObject) ) {
						self.notifyObject = notifyObject;
					}
				}
				//-----------------------------------------------------------------------
				// Connection Retry 이면 dispay
				//-----------------------------------------------------------------------
				var wsRetryCountDisplay = $('.wsRetryCountDisplay');
				if( !mint.common.isEmpty(wsRetryCountDisplay) && wsRetryCountDisplay.size() > 0 ) {
					wsRetryCountDisplay.html('<h5>Connection retry...( ' + (60 - self.retryCount) + ' )</h5>');
				}

				mint.common.debugLog(logMsg.join('\n'));

			},
			message: function(event) {

				var dataType = mint.common.isEmpty(self.dataType) ? '' : self.dataType.toLowerCase();
				var msg = event.originalEvent.data;
				//-----------------------------------------------------------------------
				// ping message 는 전달하지 않는다
				//-----------------------------------------------------------------------
				if( msg === 'ping' ) {
					return;
				}

				var listenerKey = '';
				try {
					if( ! mint.common.isEmpty(dataType) ) {
						if( dataType === 'json' ) {
							msg = JSON.parse(msg);
							listenerKey = msg.extension.serviceCd;
						} else if( dataType === 'xml' ) {
							var domParser = new DOMParser();
							msg = domParser.parseFromString(msg, 'text/xml');
						} else {
							//Nothing
						}
					}
				} catch(exception) {
					msg = event.originalEvent.data;
					listenerKey = (!mint.common.isEmpty(msg.extension) ? msg.extension.serviceCd : '');
				} finally {
					//-----------------------------------------------------------------------
					// Propagate
					//-----------------------------------------------------------------------
					var listenerList = self.listeners.get(listenerKey);
					if( !mint.common.isEmpty(listenerList) ) {
						for(var i=0; i<listenerList.length; i++) {
							listenerList[i].deferred.notify.apply(self, [msg]);
						}
					}
				}
			},
			error: function(event) {
				mint.common.debugLog('WebSocket.error()');
				self.ws = null;
				var keys = self.listeners.keys();
				for(var i = 0; i < keys; i++ ) {
					var key = keys[i];
					var listenerList = self.listeners.get(keys);
					for(var j = 0; j < listenerList.length; j++ ) {
						listenerList[j].deferred.reject.apply(self, []);
					}
				}

				if(deferred) {
					deferred.rejectWith.apply(self, [event]);
				}
			}
		};

		return eventHandler;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _reset()
 */
mint_ws.prototype._reset = function() {
	var fnName = 'mint.ws._reset()';
	try{
		this.retryCount = this._prop(this.options, 'retryCount', 60);
		this.deferredObject = $.Deferred();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * Close
 */
mint_ws.prototype._close = function() {
	var fnName = 'mint.ws._close()';
	try{
		if (this.ws) {
			this.ws.close();
			this.ws = null;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _send
 * @param data
 * @returns
 */
mint_ws.prototype._send = function(data) {
	var fnName = 'mint.ws.send()';
	try{
		var self = this;
		var deferred = $.Deferred();

		(function(sendData) {
			self._connect.apply(self, [])
			.done(function(ws) {
				ws.send(sendData);
				deferred.resolve.apply(self, [sendData]);
			})
			.fail(function(exception) {
				deferred.rejectWith.apply(self, [exception]);
			});
		})(data);

		return deferred.promise();

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _receive
 * @param listenerKey
 * @param listenerName
 * @param listener
 * @param options
 * @returns
 */
mint_ws.prototype._receive = function(listenerKey, listenerName, listener, options) {
	var fnName = 'mint.ws._receive()';
	try{
		var self = this;
		var deferred = $.Deferred();

		this._initListener(listenerKey, listenerName, listener, options)
		.done(function() {
			deferred.resolve();
		})
		.fail(function() {
			deferred.notify(arguments);
		    self._receive.apply(self, [listener]);
		});

		return deferred.promise();

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _initListener
 * @param listenerKey
 * @param listenerName
 * @param listener
 * @param options
 * @returns
 */
mint_ws.prototype._initListener = function(listenerKey, listenerName, listener, options) {
	var fnName = 'mint.ws._addListener()';
	try{
		var self = this;
		var deferred = $.Deferred();

		self._connect.apply(self, [])
		.done(function() {
			deferred.progress(function() {
				listener.apply(this, arguments);
			});

			self._addListener(listenerKey, { 'deferred': deferred, 'listenerName': listenerName, 'listener': listener, 'options': options});

		})
		.fail(function(e) {
			deferred.reject(e);
		});

		return deferred.promise();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _addListener
 * @param key
 * @param value
 */
mint_ws.prototype._addListener = function(key, value) {
	var fnName = 'mint.ws._addListener()';
	try {

		var self = this;
		var listenerList = self.listeners.get(key);
		if( mint.common.isEmpty(listenerList) ) {
			listenerList = new Array();
		}

		//remove
		var index = this._indexOfListener(listenerList, value);
		if( index !== -1 ) {
			listenerList[index].deferred.resolve();
			listenerList.splice(index,1);
		}

		//add
		listenerList.push(value);

		if( !self.listeners.containsKey(key) ) {
			self.listeners.put(key, listenerList);
		}

		mint.common.debugLog('WebSocket.Listener['+ self.listeners.size() + '][' + self.listeners.keys()+']');

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
}

/**
 * _removeListener
 * @param listenerKey
 */
mint_ws.prototype._removeListener = function(listenerKey) {
	var fnName = 'mint.ws._removeListener()';
	try{
		this.listeners.remove(listenerKey);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _removeListenerAll
 */
mint_ws.prototype._removeListenerAll = function() {
	var fnName = 'mint.ws._removeListenerAll()';
	try{
		this.listeners = new Map();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _getListenerKeys
 * @returns {Map}
 */
mint_ws.prototype._getListenerKeys = function() {
	var fnName = 'mint.ws._getListenerKey()';
	try{
		return this.listeners.keys();
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _getListenerValues
 * @returns {Map}
 */
mint_ws.prototype._getListenerValues = function(listenerKey) {
	var fnName = 'mint.ws._getListenerValues()';
	try{
		return this.listeners.get(listenerKey);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _indexOfListener
 * @param listenerList
 * @param value
 * @returns {Number}
 */
mint_ws.prototype._indexOfListener = function(listenerList, value) {
	var fnName = 'mint.ws._indexOfListener()';
	try{
		var checkValue = value.listenerName.toString();
		for( var i=0; i<listenerList.length; i++) {
			if( listenerList[i].listenerName.toString() === checkValue ) {
				return i;
			}
		}
		return -1;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _prop
 * @param obj
 * @param property
 * @param defaultValue
 * @returns
 */
mint_ws.prototype._prop = function(obj, property, defaultValue) {
	var fnName = 'mint.ws._prop()';
	try{
		if (this._isNotEmpty(obj, property)) {
			return obj[property];
		}
		return defaultValue;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};

/**
 * _isNotEmpty
 * @param obj
 * @param property
 * @returns {Boolean}
 */
mint_ws.prototype._isNotEmpty = function(obj, property) {
	var fnName = 'mint.ws._isNotEmpty()';
	try{
		return typeof obj !== 'undefined' &&
		obj !== null &&
		typeof property !== 'undefined' &&
		property !== null &&
		property !== '' &&
		typeof obj[property] !== 'undefined' &&
		obj[property] !== null &&
		obj[property] !== '';
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : fnName});
	}
};


/**
 * mint 객체에 ws 추가<p>
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.ws === "undefined") {
	        mint.ws = new mint_ws({url : wsUrl});
	    }
	} catch( e ) {
		console.log(e);
	}
});