(function (ng) {
    var app = angular.module('mainApp', [
        // External dependencies
        'ui.router',
       //'ui.bootstrap',

        // Internal modules dependencies   

        // Modulo de los automoviles,
        'automovilModule',

        // Modulo de las compras 
        "compraModule",
        "medioDePagoModule",
        'ventaModule',
        'quejaReclamoModule',

        // Modulo de los puntos de venta
        'puntoVentaModule',

        // Modulo de las calificaciones de carro
        'calificacionCarroModule',

        // Modulo de las calificaciones de tienda
        'calificacionTiendaModule',
        //Módulo del cliente
        'clienteModule',
        //Modulo del vendedor
        'vendedorModule',
        'modeloModule',
        'marcaModule',
        //Módulo del prospecto de compra
        'pcModule',
        'loginModule',
        //Modulo de las quejas/reclamos
        // Modulo de las ventas,
        'ventaModule'

    ]);

    // Resuelve problemas de las promesas
    app.config(['$qProvider', function ($qProvider) {
        $qProvider.errorOnUnhandledRejections(false);
        }]);

    //Servicio para transferencia de datos entre templates.
    //adaptado de: 
    //https://stackoverflow.com/questions/22408790/angularjs-passing-data-between-pages
    app.service("dataTransfer", function () {
        var savedData = {};
        this.set = function (data) {
            savedData = data;
        };
        this.get = function () {
            return savedData;
        };
    });
   
   app.run(['$rootScope', '$transitions', function ($rootScope, $transitions) {

            $transitions.onSuccess({to: '*'}, function (trans) {

                var $state = trans.router.stateService;
                var requireLogin = $state.current.params.requireLogin;
                var roles = $state.current.params.roles;
                
                

                /**
                 * @ngdoc function
                 * @name isAuthenticated
                 * @methodOf mainApp.module:mainApp
                 * @description Esta función define si el usuario se encuentra
                 * dentro de su cuenta.
                 * @returns {Boolean} Verdadero si está dentro de su cuenta.
                 */
                $rootScope.isAuthenticated = function () {

                    if (sessionStorage.getItem("username") !== null) {
                        $rootScope.currentUser = sessionStorage.getItem("name");
                        return true;
                    } else {
                        return false;
                    }
                };
                
                /**
                 * @ngdoc function
                 * @name hasPermissions
                 * @methodOf mainApp.module:mainApp
                 * @description Esta función define si el usuario tiene permisos
                 * para acceder a la aplicación.
                 * @returns {Boolean} Verdadero si el usuario tiene permisos.
                 */
                $rootScope.hasPermissions = function () {
                    if (($rootScope.isAuthenticated) && (roles.indexOf(sessionStorage.getItem("rol")) > -1)) {
                        return true;
                    } else {
                        return false;
                    }
                };

                
                
                if (requireLogin && (sessionStorage.getItem("username") === null)) {
                    event.preventDefault();
                    $state.go('login', $state.params);
                }

            });

        }]);

})(window.angular);
