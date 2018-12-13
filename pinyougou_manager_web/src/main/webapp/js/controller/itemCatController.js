 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService, typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	};
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			//新增分类时，指定该分类的父id；
            $scope.entity.parentId=$scope.parentId;
            serviceObject=itemCatService.add( $scope.entity  );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.findByParentId($scope.parentId);//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	};
	
	 
	//批量删除 
	$scope.dele=function() {
        //获取选中的复选框
        if($scope.selectIds.length<=0){
            alert("请您选择要删除的选项")
        }else {
            if (confirm("您确定要删除吗！")) {
                itemCatService.dele($scope.selectIds).success(
                    function (response) {
                        if (response.success) {
                            $scope.findByParentId($scope.parentId);//刷新列表
                            //alert("是否正确");
                        } else {
                            alert("删除错误");
                        }
                    }
                );
            }
        }
    }
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	};
	//定义父id
	$scope.parentId=0;
	//根据父id查询子分类；
	$scope.findByParentId=function (parentId) {
		//为父id赋值；
        $scope.parentId=parentId;
		itemCatService.findByParentId(parentId).success(function (response) {
			$scope.list=response;
        });

    };
    //	设置分类级别，默认查询一级分类数据；
    $scope.grade=1;
    //设置点击查询下级后，级别加1 的方法；  grade是加一后的结果；
	$scope.setGrade=function (grade) {
		$scope.grade=grade;
    };
	//面包屑导航栏展示效果实现； entity_p 为父类对象；
	$scope.selectItemCatList=function (entity_p) {
		//如果是一级分类
		if($scope.grade==1){
			$scope.entity_1=null;
			$scope.entity_2=null;
		}
        //如果是二级分类
        if($scope.grade==2){
            $scope.entity_1=entity_p;
            $scope.entity_2=null;
        }
        //如果是三级分类
        if($scope.grade==3){
            $scope.entity_2=entity_p;
        }
        $scope.findByParentId(entity_p.id);
    };
	//查询分类关联的模板列表数据；
	$scope.findTypeTemplateList=function () {
        typeTemplateService.findAll().success(function (response) {
			$scope.templateList=response;
        });
    };











});	
