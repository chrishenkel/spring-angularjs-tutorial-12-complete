angular.module( 'ngBoilerplate.account', [
  'ui.router',
  'placeholders',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'register', {
    url: '/register',
    views: {
      "main": {
        controller: 'RegisterCtrl',
        templateUrl: 'account/register.tpl.html'
      }
    },
    data:{ pageTitle: 'Registration' }
  })
  .state( 'login', {
    url: '/login',
    views: {
      "main": {
        controller: 'LoginCtrl',
        templateUrl: 'account/login.tpl.html'
      }
    },
    data:{ pageTitle: 'Login' }
  });
})

.controller( 'RegisterCtrl', function RegisterCtrl( $scope) {
})

.controller( 'LoginCtrl', function LoginCtrl( $scope) {
})
;