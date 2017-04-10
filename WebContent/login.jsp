<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登录</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/admin.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/admin.js?t=1"></script>
</head>
<body>
	<script type="text/javascript">
		$(function() {
			if(window.localStorage.getItem('rememberUserName','')){
				$('#userName').val(window.localStorage.getItem('rememberUserName',''));
				$('#userPwd').val(window.localStorage.getItem('rememberUserPwd',''));
				$('#remember')[0].checked = true;
			}
			$('#form').submit(function(e) {
				e.preventDefault();
				var userName = $('#userName').val();
				var userPwd = $('#userPwd').val();
				if (userName == '') {
					alert('用户名不能为空');
					return;
				}
				if (userPwd == '') {
					alert('密码不能为空');
					return;
				}
				$.ajax({
					method : 'post',
					url : '/USR/AdminLogin',
					data : {
						userName : userName,
						userPwd : userPwd
					},
					dataType : 'json',
					success : function(res) {
						if (res.status == 200) {
							if($('#remember')[0].checked){
								window.localStorage.setItem('rememberUserName',userName);
								window.localStorage.setItem('rememberUserPwd',userPwd);
							}else{
								window.localStorage.setItem('rememberUserName','');
								window.localStorage.setItem('rememberUserPwd','');
							}
							location.href = 'userManage.jsp';
						} else {
							alert(res.error);
						}
					}
				})

			});
		})
	</script>
	<div class="container">
		<div class="login ">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">登录</h3>
				</div>
				<div class="panel-body">
					<form id="form" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="firstname" class="col-sm-3 control-label">用户名</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="userName"
									placeholder="请输入用户名">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" id="userPwd"
									placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<div class="checkbox">
									<label> <input id="remember" type="checkbox">请记住我
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<button type="submit" class="btn btn-default">登录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>




</body>
</html>