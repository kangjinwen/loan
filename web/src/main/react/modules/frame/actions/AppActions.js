var Reflux = require('reflux');

var AppActions = Reflux.createActions([
	"initStore",
	"initUserMessageStore",
	"setTabActiveKey",
	"setTabActiveKeyBytabId",
	"selectRecord",
	"removeTab",
	"setActiveTab"
]);

module.exports = AppActions;