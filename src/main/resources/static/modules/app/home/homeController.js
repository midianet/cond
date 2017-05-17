'use strict';

angular.module('Home')

    .controller('HomeController', ['$scope','$http','$rootScope',
            function ($scope,$http,$rootScope) {

                if ($rootScope.globals.currentUser.username) {
                    //alert($rootScope.globals.currentUser.username);
                    $scope.user = $rootScope.globals.currentUser.username;
                }

            }])