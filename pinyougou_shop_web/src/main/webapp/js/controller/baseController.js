 app.controller("baseController",function ($scope) {
     //分页配置
     $scope.paginationConf = {
         currentPage:1,  				//当前页
         totalItems:10,					//总记录数
         itemsPerPage:10,				//每页记录数
         perPageOptions:[10,20,30,40,50], //分页选项，下拉选择一页多少条记录
         onChange:function(){			//页面变更后触发的方法
             $scope.reloadList();		//启动就会调用分页组件
         }
     };
     //调用的分页方法
     $scope.reloadList=function () {
         $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
     };
  //定义一个数组，将选中id存入数组中
     $scope.selectIds=[];
     //更新复选框的状态；
     $scope.updateSelection=function ($event,id) {
         if($event.target.checked){
             //选中状态
             $scope.selectIds.push(id);
         }else{
             var index =$scope.selectIds.indexOf(id); //获取索引；
             $scope.selectIds.splice(index,1);//参数一：移除位置的 元素索引值
                                              //参数二：从该位置移除几个元素；
         }
     };


     //定义一个数组，将选中id存入数组中
     $scope.selectIds=[];
     //更新复选框的状态；
     $scope.updateSelection=function ($event,id) {
         if($event.target.checked){
             //选中状态
             $scope.selectIds.push(id);
         }else{
             var index =$scope.selectIds.indexOf(id); //获取索引；
             $scope.selectIds.splice(index,1);//参数一：移除位置的 元素索引值
                                              //参数二：从该位置移除几个元素；
         }
     };
     //解析json数据，根据属性名获取属性值 做字符串拼接操作；
    //jsonString需要解析的json字符串  key json 对象的属性名；
     $scope.jsonStringParse=function (jsonString, key) {
         var value="";
         var jsonArray=JSON.parse(jsonString);
         for(var i=0;i<jsonArray.length;i++){
             //json对象根据属性名获取属性值，有两种方式；
                //如果属性名是确定值，直接  对象.属性名；
                //如果属性名是不确定的值，是变量，需要  对象[属性名]
             if(i>0){
               value+=","+jsonArray[i][key];
             }else{
             value+=jsonArray[i][key];
         }
         }
         return value ;
     }
    //商品录入时规格的勾选
     //基于数组中的对象的属性值，获取对应的该对象并返回；
     //[{"attributeName":"网络","attributeValue":["移动3G","移动4G"]},	{"attributeName":"机身内存","attributeValue":["16G"]}]
     //key = "attributeName" value = 网络 ;
    $scope.getObjectByKey=function (list, key, value) {
        for (var i=0;i<list.length;i++) {  //集合中有多个规格名称，页面传来的规格名称和集合中存在的规格名称一一匹配，
            // 匹配上，证明这个规格对象是存在的，若在勾选规格选项，直接往里加，否则创建；
            //存在对象时
            if(list[i][key]==value){
                return list[i]; // 证明对象存在，返回该对象，直接往对象中加值
                //{"attributeName":"value","attributeValue":["移动3G",把勾选的规格选项加入,"移动4G"]},
            }
        }
        return null; //证明数组中没有这个规格，创建；
    }



 });