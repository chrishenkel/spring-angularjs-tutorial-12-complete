angular.module('ngBoilerplate.account', ['ui.router', 'ngResource', 'base64'])
.config(function($stateProvider) {
    $stateProvider.state('login', {
        url:'/login',
        views: {
            'main': {
                templateUrl:'account/login.tpl.html',
                controller: 'LoginCtrl'
            }
        },
        data : { pageTitle : "Login" }
    })
    .state('register', {
            url:'/register',
            views: {
                'main': {
                    templateUrl:'account/register.tpl.html',
                    controller: 'RegisterCtrl'
                }
            },
            data : { pageTitle : "Registration" }
            }
    )
    .state('accountSearch', {
            url:'/accounts/search',
            views: {
                'main': {
                    templateUrl:'account/search.tpl.html',
                    controller: 'AccountSearchCtrl'
                }
            },
            data : { pageTitle : "Search Accounts" },
            resolve: {
                accounts: function(accountService) {
                    return accountService.getAllAccounts();
                }
            }
    });
})
.factory('sessionService', function($http, $base64) {
    var session = {};
    session.login = function(data) {
        return $http.post("/basic-web-app/login", "username=" + data.name +
        "&password=" + data.password, {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        } ).then(function(data) {
            alert("login successful");
            localStorage.setItem("session", {});
        }, function(data) {
            alert("error logging in");
        });
    };
    session.logout = function() {
        localStorage.removeItem("session");
    };
    session.isLoggedIn = function() {
        return localStorage.getItem("session") !== null;
    };
    return session;
})
.factory('blogService', function($resource) {
    var service = {};
    return service;
})
.factory('accountService', function($resource) {
    var service = {};
    service.register = function(account, success, failure) {
        var Account = $resource("/basic-web-app/rest/accounts");
        Account.save({}, account, success, failure);
    };
    service.getAccountById = function(accountId) {
        var Account = $resource("/basic-web-app/rest/accounts/:paramAccountId");
        return Account.get({paramAccountId:accountId}).$promise;
    };
    service.userExists = function(account, success, failure) {
        var Account = $resource("/basic-web-app/rest/accounts");
        var data = Account.get({name:account.name, password:account.password}, function() {
            var accounts = data.accounts;
            if(accounts.length !== 0) {
                success(account);
            } else {
                failure();
            }
        },
        failure);
    };
    service.getAllAccounts = function() {
          var Account = $resource("/basic-web-app/rest/accounts");
          return Account.get().$promise.then(function(data) {
            return data.accounts;
          });
      };
    return service;
})
.controller("LoginCtrl", function($scope, sessionService, accountService, $state) {
    $scope.login = function() {
        accountService.userExists($scope.account, function(account) {
            sessionService.login($scope.account).then(function() {
                $state.go("home");
            });
        },
        function() {
            alert("Error logging in user");
        });
    };
})
.controller("RegisterCtrl", function($scope, sessionService, $state, accountService) {
    $scope.register = function() {
        accountService.register($scope.account,
        function(returnedData) {
            sessionService.login($scope.account).then(function() {
                $state.go("home");
            });
        },
        function() {
            alert("Error registering user");
        });
    };
})
.controller("AccountSearchCtrl", function($scope, accounts) {
    $scope.accounts = accounts;
});