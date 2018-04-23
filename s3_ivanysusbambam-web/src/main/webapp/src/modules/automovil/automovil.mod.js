/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function(ng){
    var mod = ng.module("automovilModule" , ['ui.router']);
    mod.config (['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider){
            var basePath = 'src/modules/automovil/';
            $urlRouterProvider.otherwise("/listAutomoviles");
            
            $stateProvider.state('listAutomoviles', {
                url: '/automoviles/list',
                views :{
                    'mainView':{
                templateUrl: basePath + 'automovil.list.html',
                controller: 'automovilGetAllCtrl',
                controllerAs: 'ctrl'
                    }
                }
                    
                }).state("automovilDetail",{
                    url: "/{automovilId: int}/detail ",
                    parent:"listAutomoviles",
                    params: {
                        automovilId:null
                    },
                    viwes: {
                        'automovilDetailView': {
                            templateUrl: basePath + "automovil.detail.html",
                            controller:"automovilDetailCtrl",
                            controllerAs:"ctrl"
                        }
                    }
                });
                 }
        ]);
    
    })(window.angular);
           