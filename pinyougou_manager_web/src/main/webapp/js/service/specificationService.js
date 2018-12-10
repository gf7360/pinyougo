//定义服务  抽取请求
app.service("specificationService",function ($http) {
    this.findAll=function () {
        return $http.get("../specification/findAll.do");
    };
    this.findPage=function (pageNum,pageSize) {
        return $http.get("../specification/findPage.do?pageNum="+pageNum+"&pageSize="+pageSize)
    };
    this.add=function (entity) {
        return $http.post("../specification/add.do" ,entity)
    };
    this.update=function (entity) {
        return $http.post("../specification/update.do" ,entity)
    };
    this.findOne=function (id) {
        return $http.get("../specification/findOne.do?id="+id)
    };
    this.dele=function (ids) {
        return $http.get("../specification/delete.do?ids="+ids)
    };  this.search=function (searchEntity, pageNum, pageSize) {
        return $http.post("../specification/search.do?pageNum="+pageNum+"&pageSize="+pageSize,searchEntity)
    };

});