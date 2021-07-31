let common = {
    init : function() {
        let _this = this;

        _this.getLoginUrl();
    },
    getLoginUrl : function() {
        let _currentUrl = new URL(window.location.href),
        _loginBtnUrl = _currentUrl.protocol + "//" + _currentUrl.host + "/oauth2/login?return_to=" + encodeURIComponent(_currentUrl);

        $('#loginBtn').attr('href', _loginBtnUrl);
    }
};

common.init();
