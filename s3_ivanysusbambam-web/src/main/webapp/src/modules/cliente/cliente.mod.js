(function (ng){

    var mod = ng.module("clienteModule",["ui.router"]);
    
    mod.config(["$stateProvider","$urlRouterProvider", function($stateProvider, $urlRouterProvider){
        
        var basePath = "src/modules/cliente/";
        
        //TODO - Tal vez el otherwise debería enviar a index?
        $urlRouterProvider.otherwise("/clientes");
        
        $stateProvider.state("admin.clienteGetAll", {
            
            views: {
                mainView:{
                    templateUrl: basePath + "cliente.list.html",
                    controller: "clienteGetAllCtrl",
                    controllerAs:"ctrl"
                }
            }
            
        }).state("vendedor.clienteGetAll",{
            
            views:{
                mainView:{
                    templateUrl: basePath + "cliente.list.html",
                    controller:"clienteGetAllCtrl",
                    controllerAs:"ctrl"
                }
            }
        });
    }]);

})(angular.view);


