<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>gt-node-sdk-demo</title>
    <style>
        body {
            text-align: center;
        }
        ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }
        a {
            text-decoration:none;
        }
    </style>

    <!-- 注意，验证码本身是不需要 jquery 库，此处使用 jquery 仅为了在 demo 使用，减少代码量 -->
    <script src="http://apps.bdimg.com/libs/jquery/1.9.1/jquery.js"></script>

    <!-- 引入 gt.js，既可以使用其中提供的 initGeetest 初始化函数 -->
    <script type="text/javascript" src="/common/js/gt.js"></script>
    <link rel="stylesheet" type="text/css" href="/common/css/index.css"/>
</head>
<body>
<div id="app">
    <section class="products">
        <div class="products-content">
            <form>

                <div class="fake-input txt-email">eeeeeeeeeee</div>
                <div class="fake-input txt-pwd"> eeeeeee</div>
                <div id="captcha" class="wait">
                    <div class="loading">
                        <div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div>
                    </div>
                </div>

                <a class="btn-login "> 登录</a>
            </form>
        </div>
    </section>
</div>
<script>
    $(function ($) {
        var CaptchaProxy = (function () {
            var  $captcha = $('#captcha'),
                captchaObj,
                handler = function (captcha) {
                    $(".btn-login").on('click', function () {
                        captcha.verify();
                    });
                    captcha.onSuccess(function () {
                        var result = captcha.getValidate();
                        $.ajax({
                                url: '/gt/getGeetest2.do',
                                type: 'POST',
                                dataType: 'json',
                                data: {
                                    username:'23213',
                                    password: '2222',
                                    geetest_challenge: result.geetest_challenge,
                                    geetest_validate: result.geetest_validate,
                                    geetest_seccode: result.geetest_seccode
                                },
                                success: function (data) {
                                    if (data.status === 'success') {
                                        alert('登录成功');
                                    } else if (data.status === 'fail') {
                                        alert('登录失败');
                                    }
                                }
                            });
                    })
                    if (captchaObj) {
                        captchaObj.destroy()
                    }
                    $('>.geetest_holder', $captcha).remove();
                    captchaObj = captcha;
                        captchaObj.appendTo('#captcha');
                    captchaObj.onReady(function () {
                        isCompeted = !0;
                            $captcha.removeClass('wait')
                    });
                },
                dispatch = function () {
                    $captcha.hide();
                    $.ajax({
                        url: '/gt/getGeetest.do?t=" + (new Date()).getTime(),',
                        type: 'get',
                        data: {t: (new Date).getTime()},
                        dataType: 'json',
                        success: function (data) {
                            // 调用 initGeetest 进行初始化
                            // 参数1：配置参数
                            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
                            initGeetest({
                                // 以下 4 个配置参数为必须，不能缺少
                                gt: data.gt,
                                challenge: data.challenge,
                                offline: !data.success, // 表示用户后台检测极验服务器是否宕机
                                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                                product: 'bind' , // 产品形式，包括：float，popup
                                width: '260px',
                                height: '44px'
                            }, handler);
                        }
                    });

                };
            return {
                setTypeA: function (n) {
                    return dispatch(type.Id)
                },
                verify: function () {
                        captchaObj.verify();
                },
                init: function () {
                    dispatch();
                },
            }
        })();
        CaptchaProxy.init();
    });
</script>
</body>
</html>