<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/24 0024
  Time: 下午 2:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="/type/">

    <title>极验验证-验证样例</title>
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
    <link rel="stylesheet" type="text/css" href="index.css"/>
</head>
<body>
<div id="app">
    <header class="gt-header">
        <div class="wrapper"><a style="font-size:0;" href="/"><img src="http://front.geetest.com/logo/logo.svg" alt="" class="logo"></a><a href="/">前往极验官网</a></div>
    </header>
    <div class="introduce repeat L">
        <h2 lang="zh-cn">两种控件形态 , 多种验证形式</h2>
        <ul class="intr-panel">
            <li class="active">
                <i class="lump"></i>
                <span>独立控件</span>
            </li>
            <li>
                <i class="lump"></i>
                <span>绑定按钮</span>
            </li>
        </ul>
    </div>
    <section class="products">
        <div class="products-content">
            <ul >
                <li class="active">
                    <h2>智能组合验证</h2>
                </li>
                <li class="">
                    <h2>滑动行为验证</h2>
                </li>
                <li class="">
                    <h2>点选行为验证</h2>
                </li>
            </ul>
            <form>

                <div class="fake-input txt-email">test@geetest.com</div>
                <div class="fake-input txt-pwd"> •••••• </div>
                <div id="captcha" class="wait">
                    <div class="loading">
                        <div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div>
                    </div>
                </div>

                <a class="btn-login no-valid"> 登录</a>
            </form>
            <div class="desc-wrap">
                <div class="desc-case active">
                    <p class="desc-header">智能组合验证</p>
                    <p class="desc-msg">根据用户行为轨迹以及其他安全策略，给用户呈现对应的验证形式。滑动行为验证和点选行为验证均有一定概率出现，也有概率直接通过。
                    </p>
                </div>
                <div class="desc-case ">
                    <p class="desc-header">滑动行为验证</p>
                    <p class="desc-msg">验证的过程中会收集用户拖动滑块的行为轨迹和用户设备信息等多维度信息，实时分析这些信息来进行人机识别。
                    </p>
                </div>
                <div class="desc-case">
                    <p class="desc-header">点选行为验证</p>
                    <p class="desc-msg">收集用户行为轨迹信息和设备信息作为人机判别依据，同时利用神经网络进行快速图片风格迁移。保证人的可识别性，让机器的识别难度呈指数型增长。
                    </p>
                </div>

            </div>
        </div>
    </section>
</div>
<script src="//apps.bdimg.com/libs/jquery/1.9.1/jquery.js"></script>
<script src="libs/gt.js"></script>
<script>
    $(function ($) {
        var CaptchaProxy = (function () {
            var type = {
                    A: 0,
                    B: 0,
                    get isBind() { return this.A == 1 },
                    get Id() { return this.A * 3 + this.B }
                },
                typeId = -1,
                $captcha = $('#captcha'),
                captchaObj,
                handler = function (captcha) {
                    if (captchaObj) { captchaObj.destroy() }
                    $('>.geetest_holder', $captcha).remove();
                    captchaObj = captcha;
                    if (!type.isBind) {
                        captchaObj.appendTo('#captcha');
                    }
                    captchaObj.onReady(function () {
                        isCompeted = !0;
                        if (!type.isBind) {
                            $captcha.removeClass('wait')
                        }
                    });
                },
                dispatch = function (t) {
                    if (t == typeId) { return }
                    typeId = t;
                    if (type.isBind) {
                        $captcha.hide();
                    } else {
                        $captcha.addClass('wait').show();
                    }
                    $.ajax({
                        url: 'gt/register',
                        type: 'get',
                        data: { t: (new Date).getTime(), type: type.Id },
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
                                product: type.isBind ? 'bind' : 'popup', // 产品形式，包括：float，popup
                                width: '260px',
                                height: '44px'
                            }, handler);
                        }
                    });

                };

            return {
                setTypeA: function (n) {
                    if (n == type.A) { return }
                    type.A = n;
                    return dispatch(type.Id)
                },
                setTypeB: function (n) {
                    if (n == type.B) { return }
                    type.B = n;
                    return dispatch(type.Id)
                },
                verify: function () {
                    if(captchaObj&&type.isBind){
                        captchaObj.verify();
                    }
                },
                init:function () {
                    dispatch(0);
                }
            }
        })();
        var $panels = $('li','.intr-panel'),
            $products = $('li','.products-content'),
            $case = $('.desc-case', '.desc-wrap'),
            $btn = $('.btn-login'),
            Activate=function ($g,t) {
                var on='active';
                $g.filter('.'+on).not(t).removeClass(on);
                $(t).addClass(on);
            };
        $panels.each(function (i,v) {
            $(v).click(function () {
                $btn[(i > 0 ? 'remove' : 'add') + 'Class']('no-valid');
                Activate($panels,v);
                CaptchaProxy.setTypeA(i);
            });
        });
        $products.each(function (i,v) {
            $(v).click(function () {
                Activate($products,v);
                Activate($case,$case.eq(i));
                CaptchaProxy.setTypeB(i);
            });
        });
        $('.btn-login').click(function () {
            // 建议先通过前端表单校验再调用captcha的verify
            CaptchaProxy.verify();

        });

        CaptchaProxy.init();
    });

</script>
</body>
</html>

