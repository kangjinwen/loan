var Reflux = require('reflux'); 
var AppActions = require('../actions/AppActions');

export default Reflux.createStore({
    listenables: [AppActions],
    activeId: '',
    onSetActiveTab(activeId) {
        this.activeId =activeId;
        this.update(); 
    }, 
    update() {
        this.trigger(this.activeId);
    }
});