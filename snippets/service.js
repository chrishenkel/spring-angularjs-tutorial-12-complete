
.factory("accountService", function AccountService($resource) {
    var service = {};
    service.register = function(accountData, success, failure) {
        var Account = $resource("/basic-web-app/rest/accounts");
        var newAccount = new Account(accountData);
        newAccount.$save({}, function() {
            service.beginSession(accountData);
            success();
        }, failure);
    };
    service.getSession = function() {
        return localStorage.getItem("session");
    };
    service.isLoggedIn = function() {
        return localStorage.getItem("session") !== null;
    };
    service.beginSession = function(accountData) {
        var session = {};
        session.name = accountData.name;
        localStorage.setItem("session", session);
    };
    service.logout = function() {
        localStorage.removeItem("session");
    };
    service.login = function(accountData, success, failure) {
        var Account = $resource("/basic-web-app/rest/accounts");
        var data = Account.get({name:accountData.name},
        function() {
            if(data.accounts.length !== 0)
            {
                service.beginSession(accountData);
                success();
            } else {
                failure();
            }
        },
        function() {
            failure();
        });

    };
    return service;
})