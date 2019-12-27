window.onload = function(){
    // 这段代码是固定的，必须要放到js中
    function setupWebViewJavascriptBridge(callback) {
        
        //android
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            document.addEventListener(
                                      'WebViewJavascriptBridgeReady'
                                      , function() {
                                      callback(WebViewJavascriptBridge)
                                      },
                                      false
                                      );
        }
        
        //ios使用
        if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
        if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
        window.WVJBCallbacks = [callback];
        var WVJBIframe = document.createElement('iframe');
        WVJBIframe.style.display = 'none';
        WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
        document.documentElement.appendChild(WVJBIframe);
        setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0)
    }
    
    // 与OC交互的所有JS方法都要在这里注册,才能让OC和JS之间相互调用
    setupWebViewJavascriptBridge(function(bridge) {
        var image = document.getElementById('drivingCenter');
        image.onclick = function () {
                bridge.callHandler('clickDrivingCenter', {'click':'1'}, function responseCallback(responseData) {
                    console.log("OC中返回的参数:", responseData)
                });
            }

    })
    
    setupWebViewJavascriptBridge(function(bridge) {
                                 var image = document.getElementById('signUp');
                                 image.onclick = function () {
                                 bridge.callHandler('clickSignUp', {'url':'signUp.html'}, function responseCallback(responseData) {
                                                    console.log("OC中返回的参数:", responseData)
                                                    });
                                 }
                                 
                                 })
    
    
    //driveDlistCoach.html
    
    setupWebViewJavascriptBridge(function(bridge) {
                                 var ele = document.getElementById('messageAll');
                                 ele.onclick = function () {
                                 bridge.callHandler('clickMessageAll', {'url':'driveDlistCoach.html'}, function responseCallback(responseData) {
                                                    console.log("OC中返回的参数:", responseData)
                                                    });
                                 }
                                 
                                 })
    //driveDlistCoach.html
    setupWebViewJavascriptBridge(function(bridge) {
                                 var ele = document.getElementById('messageCellList');
                                 $.each(ele, function(i, item) {
                                        var obj = item.getElementsByTagName('input')[0]
                                        var value = obj.value
                                        item.onclick =function(){
                                        bridge.callHandler('clickMessageCell', {'url':'driveDynamic.html','messageId':value}, function responseCallback(responseData) {
                                                           console.log("OC中返回的参数:", responseData)
                                                           });
                                        }
                                        })
                                 
                                 })
   
};

