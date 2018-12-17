//服务层
app.service('uploadService',function($http){
	//现在请求发送到后台的对象不在是基本类型或者json类型，而是文件类型，所以请求方式要改变；
	//get发送的是基本类型，请求路径后直接拼接参数，post可以发送基本类型和json对象的类型，但是这两种都不能发送文件类型；
	//基于Angularjs结合HTML5 提供的对象完成图片的上传；
	this.uploadFile= function () {
		//创建html5提供的表单数据对象；
		var formData = new FormData();
        //参数一和后端用来接收上传文件的变量名称一致，
		//参数二 要提交的文件对象  固定写法；file.files[0] 第一个file指的是页面<input type="file" id="file"/>标签的id值；
        formData.append("file",file.files[0]);//这个意思是获取输入的文件数组的第一个值，实际上只能传一个；
		return $http({ //返回的是http对象
			method:"post", //提交方式
			url:"../upload/uploadFile.do",//提交的路径
			data:formData,          //提交的数据 就是HTML5new的表单；
            // anjularjs 对于 post 和 get 请求默认的 Content-Type header 是 application/json。
            // 通过设置‘Content-Type’: undefined，这样浏览器会帮我们把 enctype设置为 multipart/form-dat
            headers:{'Content-Type':undefined},
            transformRequest: angular.identity //它的作用是把要提交的formData序列化成表单对象提交各后台；

		});
    }

});










