(function(ng){
    
    var mod = ng.module("medioDePagoModule");
    
    mod.constant("medioDePagoContext", "api/mediosDePago");
    mod.controller("medioDePagoClienteDeleteCtrl", ["$scope", "$http", "medioDePagoContext", "$state", "dataTransfer", 
    
      function ($scope, $http, medioDePagoContext, $state) {
            var numero = $state.params.numero;
            /**
             * @ngdoc function
             * @name deleteAuthor
             * @methodOf authors.controller:authorDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el autor.
             * @param {String} id El ID del autor a eliminar.
             */
            $scope.deleteMdp = function () {
                $http.delete(medioDePagoContext + '/' + numero, {}).then(function (response) {
                    $state.go('mediosDePagoCliente({clienteId:$state.params.clienteId})', {numero: response.data.numero}, {reload: true});
                });
            };
        }
        
    ]);
    
})(window.angular);


