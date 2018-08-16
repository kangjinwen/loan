var Reflux = require('reflux');
var AppActions = require('../actions/AppActions');

var UserMessageStore = Reflux.createStore({
    listenables: [AppActions],
    userMessage: {},
    onInitUserMessageStore() {
        this.queryUserMessage();
    },
    queryUserMessage() {
        Utils.ajaxData({
            url: '/modules/system/getUserMessage.htm',
            method: 'get',
            callback: (result) => {
                this.userMessage = result;
                // window.roleId=result.nid;
                // window.rolename=result.rolename;
                this.update();
            }
        });
    },
    update() {
        this.trigger(this.userMessage);
    }
});

module.exports = UserMessageStore;