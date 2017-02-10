var app1 = angular.module('invoices', ['ngResource', 'ui.bootstrap', 'ngMaterial', 'common']);

app1.controller('invoiceFormController', function ($scope, $rootScope, invoiceService, companyService, invoiceService2) {

    $scope.companies = {};
    $scope.customEstablishmentEmissionPoint = {};
    $scope.selectedCompany;
    $scope.invoice = {establishmentEmissionPoint: {}, type: 'ALIMENTACION'};
    invoiceService2.query(function (data) {
        $scope.summaryList = data;
    });


    $scope.findCompanies = function (companyName) {
        return companyService.query({text: companyName}).$promise.then(
            function (data) {
                return data;
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    $scope.findCompaniesChange = function (companyName) {
        // $scope.selectedCompany = companyName;
    };

    $scope.selectCompany = function (company) {
        if (_.isObject(company) && !_.isUndefined(company)) {
            $scope.selectedCompany = _.clone(company);
            $scope.selectedCompany.establishmentEmissionPointList = [];
            // $scope.selectedCompany.establishmentEmissionPointList = company.establishmentEmissionPointList.slice();
            _.forEach(company.establishmentEmissionPointList, function (establishmentEmissionPoint) {
                $scope.selectedCompany.establishmentEmissionPointList.push(establishmentEmissionPoint);
            });
            $scope.selectedCompany.establishmentEmissionPointList.push({
                id: -1,
                input: true
            });

            $scope.invoice.establishmentEmissionPoint.id = $scope.selectedCompany.establishmentEmissionPointList[0].id;

            $scope.invoice.establishmentEmissionPoint.companyId = company.id;
        }
    };

    $scope.addInvoice = function () {
        if ($scope.invoice.establishmentEmissionPoint.id == -1) {
            $scope.invoice.establishmentEmissionPoint.id = null;
        }

        invoiceService.save($scope.invoice).$promise.then(
            function () {
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('companySaved');
                $scope.clearForm();
            },
            function (e) {
                if (e && e.data) {
                    $rootScope.errorMessage = e.data.message + '. ' + e.data.reason;
                }
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        // $scope.invoice = {establishmentEmissionPoint: {}, type: 'ALIMENTACION'};
        $scope.invoice.value = null;
        $scope.invoice.type = 'ALIMENTACION';
        // Resets the form validation state.
        $scope.invoiceForm.$setPristine();
        // // do something awesome
        // focus('email');
        document.querySelector('#autoCompleteId').focus();

        invoiceService2.query(function (data) {
            $scope.summaryList = data;
        });
    };

});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app1.controller('alertMessagesController', function ($rootScope, $scope) {

    // Picks up the event to display a saved message.
    $scope.$on('companySaved', function () {
        $scope.alerts = [
            {type: 'success', msg: 'Record saved successfully!'}
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('companyDeleted', function () {
        $scope.alerts = [
            {type: 'success', msg: 'Record deleted successfully!'}
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        var error;
        if ($rootScope.errorMessage) {
            error = {type: 'danger', msg: $rootScope.errorMessage};
        } else {
            error = {type: 'danger', msg: 'There was a problem in the server! '};
        }
        $scope.alerts = [
            error
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service that provides companies operations
app1.factory('invoiceService', function ($resource) {
    return $resource('resources/invoices/:id');
});

// Service that provides companies operations
app1.factory('invoiceService2', function ($resource) {
    return $resource('resources/invoices/summary');
});

// Service that provides companies operations
app1.factory('companyService', function ($resource) {
    return $resource('resources/companies/all', {}, {query: {isArray: true}});
});
