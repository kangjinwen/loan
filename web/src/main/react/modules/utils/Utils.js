import reqwest from 'reqwest';
import React from 'react';
import {
	Modal
} from 'antd';
(function (win) {
	//Date.prototype.toJSON重写JSON.stringify()转换日期，格式为如下自定义
    //Date.prototype.toJSON = function () { return DateFormat.formatTime('yyyy-MM-dd hh:mm:ss',this); }
	Array.prototype.indexOf = function (val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	Array.prototype.remove = function (val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};

	function jumpLoginPage() {
		localStorage.clear();
		location.reload()
	}

	function error() {
		Modal.error({
			title: "系统忙，请稍后重试"
		});
	}
	function handleUrl(obj){
        if (process.env.mode === 'dev') {
            return obj.url = '/api' + obj.url;
        }else{
        	return obj.url;
		}
	}
	//author李锋 2016年11月22日
	//拖拽滚动 调用方法 dragAll.dragItem()
	//一个参数（方法作用域）
	var dragAll = {
		dragItem: function (scope) {

			var drag = function drag(scope) {
				//console.log(scope)
				this.dragWrap = scope;
				this.init.apply(this, arguments);
			};
			drag.prototype = {
				constructor: drag,
				_dom: {},
				_x: 0,
				_y: 0,
				_top: 0,
				_left: 0,
				move: false,
				down: false,
				init: function () {
					this.bindEvent();
				},
				bindEvent: function () {
					var t = this;
					t.dragWrap.onmousedown = function (e) {
						e && e.preventDefault();
						if (!t.move) {
							t.mouseDown(e);
						}
					};
					t.dragWrap.onmouseup = function (e) {
						t.mouseUp(e);
					};

					t.dragWrap.onmousemove = function (e) {
						if (t.down) {
							t.mouseMove(e);
						}
					};
				},
				mouseMove: function (e) {
					e && e.preventDefault();
					this.move = true;
					var x = this._x - e.clientX,
						y = this._y - e.clientY,
						dom = this.dragWrap;
					if (dom) {
						dom.scrollLeft = (this._left + x);
						dom.scrollTop = (this._top + y);
					}
				},
				mouseUp: function (e) {
					e && e.preventDefault();
					this.move = false;
					this.down = false;
					//var dragWrap = document.getElementById('dragWrap');
					//dragWrap.style.cursor='';
				},
				mouseDown: function (e) {
					this.move = false;
					this.down = true;
					this._x = e.clientX;
					this._y = e.clientY;
					var dragWrap = this.dragWrap;
					if (dragWrap) {
						this._top = dragWrap.scrollTop;
						this._left = dragWrap.scrollLeft;
					}
					//dragWrap.style.cursor='move';
				}
			};
			var drag = new drag(scope);
		}
	};
	var Cookies = {
		set: function (name, value) {
			var argv = arguments;
			var argc = arguments.length;
			var expires = (argc > 2) ? argv[2] : null;
			var path = (argc > 3) ? argv[3] : '/';
			var domain = (argc > 4) ? argv[4] : null;
			var secure = (argc > 5) ? argv[5] : false;
			document.cookie = name + "=" + escape(value) +
				((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
				((path == null) ? "" : ("; path=" + path)) +
				((domain == null) ? "" : ("; domain=" + domain)) +
				((secure == true) ? "; secure" : "");
		},
		get: function (name) {
			var arg = name + "=";
			var alen = arg.length;
			var clen = document.cookie.length;
			var i = 0;
			var j = 0;
			while (i < clen) {
				j = i + alen;
				if (document.cookie.substring(i, j) == arg)
					return Cookies.getCookieVal(j);
				i = document.cookie.indexOf(" ", i) + 1;
				if (i == 0)
					break;
			}
			return null;
		},
		clear: function (name) {
			if (Cookies.get(name)) {
				document.cookie = name + "=" +
					"; expires=Thu, 01-Jan-70 00:00:01 GMT";
			}
		},
		getCookieVal: function (offset) {
			var endstr = document.cookie.indexOf(";", offset);
			if (endstr == -1) {
				endstr = document.cookie.length;
			}
			return unescape(document.cookie.substring(offset, endstr));
		}
	};
	var NavLine = {
		_isInView: function (el, target) {
			var container = target;
			var winH = container.clientHeight,
				scrollTop = container.scrollTop,
				scrollBottom = scrollTop + winH,
				elTop = el.offsetTop,
				elBottom = elTop + el.offsetHeight - 20;

			return (elTop < scrollBottom) && (elBottom > scrollTop);
		},
		_handleSpy: function (navLineData, e) {
			var items = Object.keys(navLineData).map((key, i) => {
				return navLineData[key].substring(1);
			});
			var targetItems = items.map(function (item) {
				return document.getElementById(item);
			});
			var hasInViewAlready = false;
			targetItems.forEach((el, index) => {
				if (!hasInViewAlready) {
					if (NavLine._isInView(el, e.target)) {
						this.refs.NavLine.handleClickItem('#' + items[index]);
						hasInViewAlready = true;
					}
				}
			});
		}
	};
	var DateFormat = {
		formatTen(num) {
			return num > 9 ? (num + "") : ("0" + num);
		},

		formatDate(date) {
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var day = date.getDate();
			var hour = date.getHours();
			var minute = date.getMinutes();
			var second = date.getSeconds();
			return year + "-" + this.formatTen(month) + "-" + this.formatTen(day);
		},
		formatTime(format, value) {
			var o = {
				"M+": value.getMonth() + 1,
				"d+": value.getDate(),
				"h+": value.getHours(),
				"m+": value.getMinutes(),
				"s+": value.getSeconds(),
				"q+": Math.floor((value.getMonth() + 3) / 3),
				"S": value.getMilliseconds()
			}
			if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (value.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o) {
				if (new RegExp("(" + k + ")").test(format)) {
					format = format.replace(RegExp.$1,
						RegExp.$1.length == 1 ? o[k] :
							("00" + o[k]).substr(("" + o[k]).length));
				}
			}
			return format;
		},
	};
	var Utils = {
		//Utils.longWords()限制文字长度，超出部分显示省略号
		//2个参数width控制的长度，value值
		longWords(width,value){
           return (<a className="longWords" style={{width:String(width)}} title={value} >{value}</a>)
		},
		transfer(arry) {
			arry.forEach(function (element) {
				element["label"] = element["text"];
				delete element.text;
				element["value"] = element["id"];
				element["key"] = element["id"];
				delete element.id;
				if (element.children) {
					this.transfer(element.children)
				}
			}, this);
		},
		getData: function (obj) {
			reqwest({
				url: handleUrl(obj),
				contentType: 'application/json;charset=UTF-8',
				method: 'get',
				data: obj.data,
				headers: {
					Token: localStorage.Token || null
				},
				type: 'json',
				success: function (result) {
					if (result.code == 200 || result.code == 400) {
						obj.callback(result);
					} else {
						Modal.error({
							title: result.msg,
							onOk: () => {
								if (result.code == "800") {
									jumpLoginPage();
								}
							}
						});
					}

				},
				error: function (err) {
					error();
				}
			});
		},
		sendForm: function (obj) {
			reqwest({
				url: handleUrl(obj),
				method: obj.method || 'post',
				data: obj.data,
				headers: {
					Token: localStorage.Token || null
				},
				processData: false,
				contentType: false,
				success: function (result) {
					if (result.code == 200 || result.code == 400) {
						obj.callback(result);
					} else {
						Modal.error({
							title: result.msg,
							onOk: () => {
								if (result.code == "800") {
									jumpLoginPage();
								}
							}
						});
					}
				},
				error: function (err) {
					error();
				}
			});
		},
		//发送请求
		sendAjax: function (obj) {
			reqwest({
				url: handleUrl(obj),
				contentType: obj.contentType || 'application/json;charset=UTF-8',
				method: obj.method || 'post',
				data: obj.data,
				type: 'json',
				success: function (result) {
					if (result.code == 200 || result.code == 400) {
						obj.callback(result);
					} else {
						Modal.error({
							title: result.msg,
							onOk: () => {
								if (result.code == "800") {
									jumpLoginPage();
								}
							}
						});
					}

				},
				error: function (err) {
					error();
				}
			});
		},
		//公用请求方法
		ajaxData: function (obj) {
			reqwest({
				url: handleUrl(obj),
				method: obj.method || 'post',
				data: obj.data,
				success: function (result) {
					if (typeof result == 'string' && /<!DOCTYPE html>/.test(result)) {
						Modal.error({
							title: '与后台服务连接超时,请重新登录',
							onOk: () => {
								jumpLoginPage();
							}
						});
					}
					else if (result.code == 200 || result.code == 400) {
						obj.callback(result);
					} else {
						Modal.error({
							title: result.msg,
							onOk: () => {
								if (result.code == "800") {
									jumpLoginPage();
								}
							}
						});
					}

				},
				error: function (err) {
					error();
				}
			});
		},
		//提交-加了按钮loading状态
		ajaxSubmit: function (obj) {
			var me = obj.me;
			me.setState({ loading: true });
			reqwest({
				url: handleUrl(obj),
				method: obj.method || 'post',
				data: obj.data,
				success: function (result) {
					me.setState({ loading: false });
					if (result.code == 200 || result.code == 400) {
						obj.callback(result);
					} else {
						Modal.error({
							title: result.msg,
							onOk: () => {
								if (result.code == "800") {
									jumpLoginPage();
								}
							}
						});
					}

				},
				error: function (err) {
					error();
				}
			});
		},
		toThousands(num) {
			var str = Math.abs(num);
			str = String(str);
			var newStr = "";
			var count = 0;

			if (str.indexOf(".") == -1) {
				for (var i = str.length - 1; i >= 0; i--) {
					if (count % 3 == 0 && count != 0) {
						newStr = str.charAt(i) + "," + newStr;
					} else {
						newStr = str.charAt(i) + newStr;
					}
					count++;
				}
				str = newStr //+ ".00"; //自动补小数点后两位
			}
			else {
				for (var i = str.indexOf(".") - 1; i >= 0; i--) {
					if (count % 3 == 0 && count != 0) {
						newStr = str.charAt(i) + "," + newStr;
					} else {
						newStr = str.charAt(i) + newStr; //逐个字符相接起来
					}
					count++;
				}
				str = newStr + (str + "00").substr((str + "00").indexOf("."), 3);
			}
			if (num < 0) {
				return '-' + str
			} else {
				return str
			}
		},
		//验证页面配置的子组件
		validateForms(me, listValid) {
			var a = true;
			for (var i = 0, length = listValid.length; i < length; i++) {
				if (me.refs[listValid[i]]) {
					me.refs[listValid[i]].validateFieldsAndScroll((errors, values) => {
						if (!!errors) {
							a = false
						} else {
							return;
						}
					})
				}
			}
			return a
		},
		//重置页面配置的子组件
		resetForms(me, listValid) {
			for (var i = 0, length = listValid.length; i < length; i++) {
				if (me.refs[listValid[i]]) {
					me.refs[listValid[i]].resetFields();
				}
			}
		},
		//赋值页面配置的子组件
		addTestData(me, assemblyList, data) {
			Utils.setFormsValue(me, assemblyList, data)
		},
		//本地存储 key,val
		localSaveJsonStorage: function (key, val) {
			var data = {
				val: val
			};
			var str = JSON.stringify(data);
			localStorage.setItem(key, str);
		},

		//提取本地存储数据
		localLoadJsonStorage: function (key) {
			var str = localStorage.getItem(key);
			var data = JSON.parse(str);
			return data.val;
		}

	}
    //验证各类字段方法
	var validator = {
		//手机验证
        checkMobile(rule, value, callback) {
			var re = /^1\d{10}$/;
            if (!value || re.test(value)) {
				callback();
            } else {
				callback([new Error('手机格式错误')]);
            }
        },
        //邮箱验证
        checkEmail(rule, value, callback) {
			var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
            if (!value || re.test(value)) {
				callback();
            } else {
				callback([new Error('邮箱格式错误')]);
            }
        },
        //处理意见长度验证
        checkRemark(rule, value, callback) {
            if (!value || value.length < 512) {
				callback();
            } else {
				callback([new Error('需小于512个字符')]);
            }
        },
        //严格身份证验证
        checkIdCode(rule, value, callback) {
            if (!value || validator.IdCardValidate(value)) {
				callback();
            } else {
				callback([new Error('身份证格式错误')]);
            }
        },
        Wi: [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1],    // 加权因子
        ValideCode: [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2],           // 身份证验证位值.10代表X
        IdCardValidate(idCard) {
			idCard = validator.trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
			if (idCard.length == 15) {
				return validator.isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
			} else if (idCard.length == 18) {
				var a_idCard = idCard.split("");                // 得到身份证数组
				if (validator.isValidityBrithBy18IdCard(idCard) && validator.isTrueValidateCodeBy18IdCard(a_idCard)) {   //进行18位身份证的基本验证和第18位的验证
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
        },
        /**
         * 判断身份证号码为18位时最后的验证位是否正确
         * @param a_idCard 身份证号码数组
         * @return
         */
        isTrueValidateCodeBy18IdCard(a_idCard) {
			var sum = 0;                             // 声明加权求和变量
			if (a_idCard[17].toLowerCase() == 'x') {
				a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
			}
			for (var i = 0; i < 17; i++) {
				sum += validator.Wi[i] * a_idCard[i];            // 加权求和
			}
			var valCodePosition = sum % 11;                // 得到验证码所位置
			if (a_idCard[17] == validator.ValideCode[valCodePosition]) {
				return true;
			} else {
				return false;
			}
        },
        /**
         * 验证18位数身份证号码中的生日是否是有效生日
         * @param idCard 18位书身份证字符串
         * @return
         */
        isValidityBrithBy18IdCard(idCard18) {
			var year = idCard18.substring(6, 10);
			var month = idCard18.substring(10, 12);
			var day = idCard18.substring(12, 14);
			var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
			// 这里用getFullYear()获取年份，避免千年虫问题
			if (temp_date.getFullYear() != parseFloat(year)
				|| temp_date.getMonth() != parseFloat(month) - 1
				|| temp_date.getDate() != parseFloat(day)) {
				return false;
			} else {
				return true;
			}
        },
        /**
         * 验证15位数身份证号码中的生日是否是有效生日
         * @param idCard15 15位书身份证字符串
         * @return
         */
        isValidityBrithBy15IdCard(idCard15) {
			var year = idCard15.substring(6, 8);
			var month = idCard15.substring(8, 10);
			var day = idCard15.substring(10, 12);
			var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
			// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
			if (temp_date.getYear() != parseFloat(year)
				|| temp_date.getMonth() != parseFloat(month) - 1
				|| temp_date.getDate() != parseFloat(day)) {
				return false;
			} else {
				return true;
			}
        },
        //去掉字符串头尾空格
        trim(str) {
			return str.replace(/(^\s*)|(\s*$)/g, "");
        }
	};
    win.dragAll = dragAll;
	win.Utils = Utils;
	win.Cookies = Cookies;
	win.NavLineUtils = NavLine;
	win.DateFormat = DateFormat;
	win.validator = validator;
})(window)
