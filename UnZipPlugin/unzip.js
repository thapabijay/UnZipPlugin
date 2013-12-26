var exec = require('cordova/exec');

/**
* Provides an Android file chooser.
*/
module.exports = {
    unzip : function(successCallback, errorCallback, options) {
        exec(successCallback, errorCallback, "UnZip", "unzip", options);
    }
};