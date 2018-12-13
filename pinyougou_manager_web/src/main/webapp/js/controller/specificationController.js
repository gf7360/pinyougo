//根据模块定义控制器
app.controller("specificationController", function ($scope,$controller,specificationService) {

    //控制器继承代码；参数一：继承的父控制器名称  参数二：固定写法，共享$scope对象；
    $controller("baseController",{$scope:$scope});


    //定义查询所有规格列表的方法
    $scope.findAll=function() {
        specificationService.findAll().success(function (response) {
            $scope.list = response;
        });
    };
    //定义封装查询条件的对象；
    $scope.searchEntity={};

    //规格根据条件分页查询；
    $scope.search=function (pageNum,pageSize) {
        specificationService.search($scope.searchEntity,pageNum,pageSize).success(function(response){
            $scope.paginationConf.totalItems=response.total;  //总页数
            $scope.list=response.rows;
        });
    };
    //分页请求
    $scope.findPage=function (pageNum,pageSize) {
        specificationService.findPage(pageNum,pageSize).success(function(response){
            $scope.paginationConf.totalItems=response.total;  //总页数
            $scope.list=response.rows;
        });
    };
    //添加规格数据的方法
    $scope.save=function () {
        var method=null;
        if($scope.entity.specification.id!=null){
            //修改方法
            method=specificationService.update($scope.entity);
        }else{
            //增加规格
            method=specificationService.add($scope.entity);
        }
        method.success(function (response) {
            if(response.success){
                $scope.reloadList();
            }else{
                alert(response.message);
            }
        });

    };
    //修改规格数据
    $scope.findOne=function (id) {
        specificationService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };

    //删除规格；
    $scope.dele=function () {
        if(confirm("您确定要删除吗?")){
            specificationService.dele($scope.selectIds).success(function (response) {
                if(response.success){
                    $scope.reloadList();
                }else{
                    alert(response.message);
                }
                $scope.entity = response;
            });
        }
    };
    //定义初始化entity对象；
    $scope.entity={specificationOptions:[]};
    //新增规格选项
    $scope.addRow=function () {
        $scope.entity.specificationOptions.push({});
    };
    //删除规格选项
    $scope.deleRow=function (index) {
        $scope.entity.specificationOptions.splice(index,1);
    };

});
