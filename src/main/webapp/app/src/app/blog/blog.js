angular.module('ngBoilerplate.blog', ['ui.router', 'ngResource', 'ngBoilerplate.account', 'hateoas'])
.config(function($stateProvider) {
    $stateProvider.state('manageBlogs', {
            url:'/manage/blogs?accountId',
            views: {
                'main': {
                    templateUrl:'blog/manage-blogs.tpl.html',
                    controller: 'ManageBlogCtrl'
                }
            },
            resolve: {
                account: function(accountService, $stateParams) {
                    return accountService.getAccountById($stateParams.accountId);
                },
                blogs: function(blogService, $stateParams) {
                    return blogService.getBlogsForAccount($stateParams.accountId);
                }
            },
            data : { pageTitle : "Blogs" }
    });
})
.factory('blogService', function($resource, $q) {
      var service = {};
      service.createBlog = function(accountId, blogData) {
        var Blog = $resource("/basic-web-app/rest/accounts/:paramAccountId/blogs");
        return Blog.save({paramAccountId: accountId}, blogData).$promise;
      };
      service.getAllBlogs = function() {
        var Blog = $resource("/basic-web-app/rest/blogs");
        return Blog.get().$promise.then(function(data) {
            return data.blogs;
        });
      };
      service.getBlogsForAccount = function(accountId) {
          var deferred = $q.defer();
          var Account = $resource("/basic-web-app/rest/accounts/:paramAccountId");
          Account.get({paramAccountId:accountId}, function(account) {
              var Blog = account.resource('blogs');
              Blog.get(null,
                  function(data) {
                    deferred.resolve(data.blogs);
                  },
                  function() {
                    deferred.reject();
                  }
              );
          });
          return deferred.promise;
      };
      return service;
})
.controller("ManageBlogCtrl", function($scope, blogs, account, blogService, $state) {
    $scope.name = account.name;
    $scope.blogs = blogs;
    $scope.createBlog = function(blogName) {
        blogService.createBlog(account.rid, {
            title : blogName
        }).then(function() {
            $state.go("manageBlogs", { accountId : account.rid }, { reload : true });
        });
    };
});