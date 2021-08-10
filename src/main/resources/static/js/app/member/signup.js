let signup = {
    init : function() {
        let _this = this;

        _this.input.init();
        _this.submit.init();
    },
    input : {
        init : function() {
            let _this = this;

            $('.eke-text-type').focus(_this.inputFocus);
            $('.eke-text-type').blur(_this.inputBlur);
            $('#id-eke-nickname').on('input', function() {
                $('#eke-text-length').html($(this).val().length + ' / 10');
            });
            $('input[name=user_gender]').on('change', function() {
                $(this).closest('.op-field-container').css('border-bottom', 'none');
            });

        },
        inputFocus : function() {
            var _fieldCtEle = $(this).closest('.op-field-container'),
            _fieldEle = $(this).closest('.op-field'),
            _iEle = $(_fieldEle).find('i'),
            _pEle = $(_fieldCtEle).find('.eke-error-message');

            $(_fieldCtEle).removeClass('tf-error');
            $(_iEle).attr('class', 'fas fa-arrow-right');
            $(_pEle).html('');
        },
        inputBlur : function() {
            var _fieldCtEle = $(this).closest('.op-field-container'),
                _fieldEle = $(this).closest('.op-field'),
                _iEle = $(_fieldEle).find('i');

            if(!signup.input.inputValid(this, _fieldCtEle)) {
                $(_fieldCtEle).addClass('tf-error');
                $(_iEle).attr('class', 'fas fa-times');
                return;
            }

            $(_iEle).attr('class', 'fas fa-check');
        },
        inputValid : function(ele) {
            var _fieldCtEle = $(ele).closest('.op-field-container'),
            _pEle = $(_fieldCtEle).find('.eke-error-message'),
            _eleId = $(ele).attr('id'),
            _regEx;

            if(!$(ele).val()) {
                $(_fieldCtEle).find('.eke-error-message').html(ele.dataset.emptyError);
                return false;
            }

            switch (_eleId) {
                case 'id-eke-wa' :
                    _regEx = /08[0-9]{10,11}$/g;
                    break;
                case 'id-eke-nickname' :
                    _regEx = /^[a-zA-Z]{3,10}$/g;
                    break;
            }

            if(!_regEx.test($(ele).val())) {
                $(_fieldCtEle).find('.eke-error-message').html(ele.dataset.invalidError);
                return false;
            }

            return true;
        },
        radioValid : function(ele) {
            const _regEx = /^(M|F)$/g;
            let _checkedCnt = 0;

            $(ele).each(function(index, item) {
                if($(item).is(':checked')) {
                    _checkedCnt += 1;
                    if(!_regEx.test($(item).val())) {
                        alert('Invalid value');
                        location.reload();
                    }
                }
            });

            if(_checkedCnt != 1) {
                return false;
            }

            return true;
        }
    },
    submit : {
        init : function() {
            let _this = this;

            $('#signupForm').on('submit', _this.sendValid);
        },
        sendValid : function(e) {
            e.preventDefault();

            let _formData = new FormData(this),
            _formDataObj = {};

            if(!_formData.has('user_gender')) {
                alert('Please choose gender');
                //var _thisEle = $('input[name="user_gender"]'),
                //_fieldCtEle = $(_thisEle).closest('.op-field-container');

                //$(_fieldCtEle).css('border-bottom', '1px solid var(--color-text-error)');

                return false;
            }

            for(var _keyVal of _formData.entries()) {
                switch (_keyVal[0]) {
                    case 'user_whatsapp':
                    case 'user_nickname':
                        var _thisEle = $('input[name="' + _keyVal[0] + '"]');
                        if(!signup.input.inputValid(_thisEle[0])) {
                            var _fieldCtEle = $(_thisEle).closest('.op-field-container'),
                            _fieldEle = $(_thisEle).closest('.op-field'),
                            _iEle = $(_fieldEle).find('i');

                            $(_fieldCtEle).addClass('tf-error');
                            $(_iEle).attr('class', 'fas fa-times');
                            return;
                        }

                        break;
                    case 'user_gender':
                        var _thisEle = $('input[name="' + _keyVal[0] + '"]');
                        if(!signup.input.radioValid(_thisEle)) return;

                        break;
                }
            }

            _formData.forEach((value, key) => {
                _formDataObj[key] = value;
            });

            $.ajax({
                type: 'PUT',
                url: '/api/v1/user/signup',
                beforeSend : function(xhr) {
                    xhr.setRequestHeader($("meta[name='_csrf_header']").attr('content'), $("meta[name='_csrf']").attr('content'));
                },
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(_formDataObj)
            }).done(function() {
                const _queryString = $(location).attr('search'),
                _urlParams = new URLSearchParams(_queryString),
                _continue = _urlParams.get('return_to');

                location.href = _continue;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
};

signup.init();