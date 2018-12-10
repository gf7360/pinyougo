//根据模块定义控制器
 app.controller("brandController", function ($scope,$controller,brandService) {

     //控制器继承代码；参数一：继承的父控制器名称  参数二：固定写法，共享$scope对象；
     $controller("baseController",{$scope:$scope});


    //定义查询所有品牌列表的方法
    $scope.findAll=function() {
        brandService.findAll().success(function (response) {
            $scope.list = response;
        });
    };
     //定义封装查询条件的对象；
     $scope.searchEntity={};

     //规格根据条件分页查询；
     $scope.search=function (pageNum,pageSize) {
         brandService.search($scope.searchEntity,pageNum,pageSize).success(function(response){
             $scope.paginationConf.totalItems=response.total;  //总页数
             $scope.list=response.rows;
         });
     };
    //分页请求
    $scope.findPage=function (pageNum,pageSize) {
        brandService.findPage(pageNum,pageSize).success(function(response){
            $scope.paginationConf.totalItems=response.total;  //总页数
            $scope.list=response.rows;
        });
    };
    //添加品牌数据的方法
    $scope.save=function () {
        var method=null;
        if($scope.entity.id!=null){
            //修改方法
            method=brandService.update($scope.entity);
        }else{
            //增加品牌
            method=brandService.add($scope.entity);
        }
        method.success(function (response) {
            if(response.success){
                $scope.reloadList();
            }else{
                alert(response.message);
            }
        });

    };
    //修改品牌数据
    $scope.findOne=function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };

    //删除品牌；
    $scope.dele=function () {
        if(confirm("您确定要删除吗?")){
            brandService.dele($scope.seleteIds).success(function (response) {
                if(response.success){
                    $scope.reloadList();
                }else{
                    alert(response.message);
                }
                $scope.entity = response;
            });
        }
    }
});
