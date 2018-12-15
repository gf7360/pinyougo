//定义服务  抽取请求
app.service("loginService",function ($http) {
    this.getName=function () {
        return $http.get("../login/getName.do");
    };
    this.findByName=function () {
        return $http.get("../login/findByName.do");
    };
});