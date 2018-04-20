(function (ng) {

    var mod = ng.module("quejaReclamoModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/quejaReclamo/';

            $urlRouterProvider.otherwise("/quejaReclamo/getall");

            $stateProvider.state('adminQuejaReclamoGetAll', {
                url: '/quejaReclamo/getall',
                views: {

                    'mainView': {
                        templateUrl: basePath + "quejaReclamo.list.html",
                        controller: 'quejaReclamoGetAllCtrl',
                        controllerAs: "ctrl"
                    }
                }
            }
            );
        }]);


})(window.angular);

