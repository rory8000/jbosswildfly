var common = angular.module('common', []);

common.controller('loginController', function ($scope, $rootScope, SessionService) {

    $scope.username = '';
    $scope.password = '';

    $scope.login = function () {
        SessionService.readToken($scope.username, $scope.password).then(function(data){
            console.log('SUCCESS', data.data);
        }, function(reason) {
            console.log('ERROR', reason);
        });

    };

    $scope.logout = function () {
        SessionService.logout($scope.username, $scope.password).then(function(data){
            console.log('SUCCESS', data);
        }, function(reason) {
            console.log('ERROR', reason);
        });

    };
});

common.factory('sessionInjector', ['SessionService', function (SessionService) {
    var sessionInjector = {
        request: function (config) {
            config.headers['service_key'] = 'f80ebc87-ad5c-4b29-9366-5359768df5a1';
            if (!SessionService.isAnonymous()) {
                config.headers['auth_token'] = SessionService.getToken();
                // config.headers['service_key'] = '3b91cab8-926f-49b6-ba00-920bcf934c2a';
                // config.headers['auth_token'] = '80f57af1-9734-4f8e-880e-0b31662066ee';
            }
            return config;
        }
    };
    return sessionInjector;
}]);
common.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('sessionInjector');
}]);

common.factory('SessionService', ['$injector', '$q', function ($injector, $q) {
    var token = null;
    var sessionService = {};
    var deferred = $q.defer();

    sessionService.readToken = function (username, password) {
        var $http = $injector.get('$http');
        var $location = $injector.get('$location');
        return $http({
            method: 'POST',
            url: $location.protocol() + '://'+ $location.host() +':'+  $location.port()+ '/resources/security/login',
            data: $.param({username: username, password: password}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (res) {
                console.log('Auth Success and token received: ' + JSON.stringify(res));

                // Extract the token details from the received JSON object
                token = res.auth_token;
                deferred.resolve(res);
            }, function (res) {
                console.log('Error occurred : ' + JSON.stringify(res));
                deferred.reject(res);
            }
        )
    };

    sessionService.logout = function () {
        var $http = $injector.get('$http');
        return $http({
            method: 'POST',
            url: 'http://localhost:8080/jbosswildfly-1.0/resources/security/logout'
        }).success(function (res) {
                console.log('Logout Success');

                // Extract the token details from the received JSON object
                token = null;
                deferred.resolve(res);
            }, function (res) {
                console.log('Error occurred : ' + JSON.stringify(res));
                deferred.reject(res);
            }
        )
    };

    sessionService.getToken = function () {
        return token;
    };

    sessionService.isAnonymous = function () {
        return !token;
    };

    return sessionService;
}]);