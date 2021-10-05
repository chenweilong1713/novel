
// 管理员登录
function adminLogin(account,pwd,code){
    $.ajax({
        type: "POST",
        url: "/admin/login",
        data: {
            account:account,
            pwd:pwd,
            code:code
        },
        success: function (data) {
            if(data.code == 200)
                window.location.href = "/admin"
            if(data.code == 403)
            lightyear.notify(data.msg, 'danger', 1000);
        },
    })
}

// 管理员密码修改
function adminAlterPwd(oldPwd,newPwd,newPwdS){
    $.ajax({
        type: "POST",
        url: "/admin/alter/pwd",
        data: {
            oldPwd:oldPwd,
            newPwd:newPwd,
            newPwdS:newPwdS
        },
        success: function (data) {
            if(data.code == 200){
                lightyear.notify(data.msg, 'success', 1000);
            }
            if(data.code == 403)
                lightyear.notify(data.msg, 'danger', 1000);
        },
    })
}

// 开启url线程
function urlThreadStartAjax(url){
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function(data){
            if(data.code == 200){
                lightyear.notify(data.msg, 'success', 1000);
                $(".url_message").text("正在运行......")
            }
        }
    });
}
// 关闭url线程
function urlThreadCloseAjax(url){
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function(data){
            if(data.code == 200){
                lightyear.notify(data.msg, 'success', 1000);
                $(".url_message").text("已停止......")
            }
        }
    });
}

// 开启novel线程
function novelThreadStartAjax(url){
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function(data){
            if(data.code == 200){
                lightyear.notify(data.msg, 'success', 1000);
                $(".novel_message").text("正在运行......")
            }
        }
    });
}

// 关闭novel线程
function novelThreadCloseAjax(url){
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function(data){
            if(data.code == 200){
                lightyear.notify(data.msg, 'success', 1000);
                $(".novel_message").text("已关闭......")
            }
        }
    });
}

// 添加任务至url队列
function urlOfferQueueAjax(url,id){
    $.ajax({
        type: "POST",
        url: "/admin/offer/url/queue",
        data: {
            url:url,
            id:id
        },
        success: function (data) {
            if(data.code == 200){
                $("#novel_url").val("");
                lightyear.notify(data.msg, 'success', 1000);
            }
            if(data.code == 403)
                lightyear.notify(data.msg, 'danger', 1000);
        },
    })
}

// 根据采集源和书名添加规则
function searchOfferUrl(name,id){
    $.ajax({
        type: "POST",
        url: "/admin/search",
        data: {
            name:name,
            id:id
        },
        success: function (data) {
            if(data.code == 200){
                $("#input_novel").val("");
                lightyear.notify(data.msg, 'success', 1000);
            }
            if(data.code == 403)
                lightyear.notify(data.msg, 'danger', 1000);
        },
    })
}

// 更新爬虫配置
function updateReptileConfig(failTime,delay,failDelay,urlThreadFlag,novelThreadFlag){
    $.ajax({
        type: "POST",
        url: "/admin/update/config",
        data: {
            failTime:failTime,
            delay:delay,
            failDelay:failDelay,
            urlThreadFlag:urlThreadFlag,
            novelThreadFlag:novelThreadFlag
        },
        success: function (data) {
            if(data.code == 200){
                $("#input_novel").val("");
                lightyear.notify(data.msg, 'success', 1000);
            }
        },
        error:function (){
            lightyear.notify("服务器错误", 'danger', 1000);
        }
    })
}

// 根据规则id将规则中所有的小说添加至任务中
function checkRuleAllAddQueue(id){
    $.ajax({
        type: "POST",
        url: "/admin/add/url/queue",
        data: {
            id:id
        },
        success: function (data) {
            if(data.code == 200){
                $.confirm({
                    title: '成功',
                    content: data.msg,
                    type: 'green',
                    buttons: {
                        omg: {
                            text: '谢谢',
                            btnClass: 'btn-green',
                        }
                    }
                });
            }
        },
        error:function (){
            lightyear.notify("服务器错误", 'danger', 1000);
        }
    })
}

// 清空url队列
function removeAllUrlQueue(){
    $.ajax({
        type: "GET",
        url: "/admin/remove/url/all",
        dataType: "json",
        success: function(data){
            if(data.code == 200){
                $("#url_queue").val(0);
                lightyear.notify(data.msg, 'success', 1000);
            }
        }
    });
}

// 清空novel队列
function removeAllNovelQueue(){
    $.ajax({
        type: "GET",
        url: "/admin/remove/url/novel",
        dataType: "json",
        success: function(data){
            if(data.code == 200){
                $("#novel_queue").val(0)
                lightyear.notify(data.msg, 'success', 1000);
            }
        }
    });
}

