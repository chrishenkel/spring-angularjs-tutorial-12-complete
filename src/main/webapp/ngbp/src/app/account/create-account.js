/**
 * Each section of the site has its own module. It probably also has
 * submodules, though this boilerplate is too simple to demonstrate it. Within
 * `src/app/home`, however, could exist several additional folders representing
 * additional modules that would then be listed as dependencies of this one.
 * For example, a `note` section could have the submodules `note.create`,
 * `note.delete`, `note.edit`, etc.
 *
 * Regardless, so long as dependencies are managed correctly, the build process
 * will automatically take take of the rest.
 *
 * The dependencies block here is also where component dependencies should be
 * specified, as shown below.
 */
angular.module( 'ngBoilerplate.signup', [
  'ui.router',
  'ngResource',
  'ngCookies',
  'hateoas'
])

/**
 * Each section or module of the site can also have its own routes. AngularJS
 * will handle ensuring they are all available at run-time, but splitting it
 * this way makes each module more "self-contained".
 */
.config(function config( $stateProvider ) {
  $stateProvider.state( 'signup', {
    url: '/account/create',
    views: {
      "main": {
        controller: 'AccountCtrl',
        templateUrl: 'account/create-account.tpl.html'
      }
    },
    data:{ pageTitle: 'Account' }
  });
})

/**
 * And of course we define a controller for our route.
 */
.controller( 'AccountCtrl', function AccountController( $scope, $resource, $location) {
  $scope.submit = function() {
    var Account = $resource('http://localhost:8080/basic-web-app/rest/accounts');
    var account =  new Account();
    account.name = $scope.username;
    account.password = $scope.password;
    account = account.$save({}, function(newAccount) {
        $location.path("/about");
        localStorage.loggedIn = true;
        localStorage.accountName = newAccount.name;
        localStorage.self = newAccount.resource("self");
        blah = newAccount.resource("self");
        res = blah.get(null, function() {
            alert(res.name);
        });

    }, function() {
        alert('failure');
    });
  };
})

;

