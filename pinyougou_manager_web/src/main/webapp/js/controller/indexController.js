//根据模块定义控制器
app.controller("indexController", function ($scope,$controller,loginService) {

    //控制器继承代码；参数一：继承的父控制器名称  参数二：固定写法，共享$scope对象；
    $controller("baseController", {$scope: $scope});
    //获取用户名
    $scope.getName=function () {

        loginService.getName().success(function (response) {
            $scope.loginName=response.loginName;
        })

    }

} );

