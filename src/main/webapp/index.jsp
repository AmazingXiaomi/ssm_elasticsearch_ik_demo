<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/common/js/jquery-1.8.3.min.js"></script>
    <script>
        function submit() {
            $("#result").text("");
            var reason=$("#reason").val();
            $.ajax({
                type: "post",
                url: 'user/queryList.do',
                dataType: "json",
                data:{
                    "reason":reason
                },
                success: function (data) {
                    if (data){
                        for (key in data) {
                      var  user=  data[key];
                      $("#result").append("<li>"+user.id+"</li>");
                        }
                    }
                }
            })
        }
    </script>
</head>
<body>
<h2>Hello World!</h2>
<input type="text" width="200px"  id="reason"/><input type="button" onclick="submit()" style="width: 40px" value="提交" id="submit">
<div  style="background-color: antiquewhite;width: 173px">
<ul id="result" style="list-style:none;margin: 0px">

</ul>
</div>
</body>
</html>
