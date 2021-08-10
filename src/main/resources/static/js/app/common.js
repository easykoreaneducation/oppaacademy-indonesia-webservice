let common = {
    init : function() {
        let _this = this;

    },
    loginBtnElement : function(element) {
        const _currentUrl = new URL(window.location.href),
        _loginBtnUrl = _currentUrl.protocol + "//" + _currentUrl.host + "/oauth2/login?return_to=" + encodeURIComponent(_currentUrl);
        element.href = _loginBtnUrl;
    }
};
