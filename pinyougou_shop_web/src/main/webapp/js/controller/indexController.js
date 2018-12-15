//根据模块定义控制器  参数一 是本控制器的$scope,参数二 用来引入要继承的控制器，参数三 引入的服务；
app.controller("indexController",function ($scope, $controller, loginService) {
    //控制器继承baseController
    $controller("baseController",{$scope:$scope});
    //获取用户名
    $scope.getName=function () {
        loginService.getName().success(function (response) {
            $scope.loginName=response.loginName;
        });
    };
    //根据用户名查询用户信息回显到修改资料上；
    $scope.findByName=function () {
        loginService.findByName().success(function (response) {
            $scope.list = response;
        })
    }
});
