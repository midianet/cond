'use strict';

angular.module('Authentication', []);
angular.module('Home', []);
angular.module('Resident', []);
angular.module('Tabela',['datatables', 'datatables.light-columnfilter'])
angular.module('App', ['Authentication', 'Home', 'Resident','ngRoute', 'ngCookies'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'modules/authentication/login.html'
            })
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'modules/app/home/home.html'
            })
            .when('/public', {
                controller: 'TabelaController',
                templateUrl: 'modules/app/resident/public.html'
            })
            .otherwise({redirectTo: '/login'});
    }])
    .run(['$rootScope', '$location', '$cookieStore', '$http' , function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
    }])
    .controller('menuController',['$scope', '$rootScope', 'AuthenticationService', function ($scope, $rootScope, AuthenticationService) {

        $scope.userLogged = function () {
            return $rootScope.globals.currentUser
        }
        $scope.logout = function(){
            AuthenticationService.ClearCredentials();
        }

    }]);