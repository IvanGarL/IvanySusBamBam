(function(ng){
    var mod = ng.module("puntoVentaModule");
    mod.constant("puntoVentaContext", "api/puntosDeVenta");
    mod.controller("puntoVentaCrearCtrl", ["$scope", "$http",
        "puntoVentaContext","$rootScope","$state", 
        function($scope, $http, puntoVentaContext, $rootScope, $state){
                        
            $scope.info = {};
            $scope.crearPuntoVenta = function(){
                console.log($scope.info);
               $http.post(puntoVentaContext, $scope.info).then(function(response){
                  $state.go("adminPuntosVentaGetAll", {}, {reload: true}); 
               });  
            };                   
    }]);
    
})(window.angular);

