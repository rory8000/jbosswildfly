var app = angular.module('companies', ['ngResource', 'ngGrid', 'ui.bootstrap', 'common']);
// Create a controller with name companiesList to bind to the html page.
app.controller('companiesListController', function ($scope, $rootScope, companyService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.companies = {currentPage: 1};

    $scope.gridOptions = {
        data: 'companies.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            {field: 'id', displayName: 'Id'},
            {field: 'name', displayName: 'Name'},
            {field: 'ruc', displayName: 'RUC'},
            {
                field: '',
                width: 30,
                cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>'
            }
        ],

        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('companySelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        var listCompaniesArgs = {
            page: $scope.companies.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        companyService.get(listCompaniesArgs, function (data) {
            $scope.companies = data;
        })
    };

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteCompany', row.entity.id);
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.companies = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });

});

// Create a controller with name companyFormController to bind to the form section.
app.controller('companyFormController', function ($scope, $rootScope, companyService) {
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.company = null;
        // For some reason, I was unable to clear field values with type 'url' if the value is invalid.
        // This is a workaroud. Needs proper investigation.
        // document.getElementById('imageUrl').value = null;
        // Resets the form validation state.
        $scope.companyForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');

        document.getElementById('name').focus();
        // document.querySelector('#name').focus();
        // focus('name');
    };

    // Calls the rest method to save a company.
    $scope.updateCompany = function () {
        var company = {
            establishmentEmissionPointList: null,
            id: $scope.company.id,
            name: $scope.company.name,
            ruc: $scope.company.ruc
        };
        companyService.save(company).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
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

    // Picks up the event broadcasted when the company is selected from the grid and perform the company load by calling
    // the appropiate rest service.
    $scope.$on('companySelected', function (event, id) {
        $scope.company = companyService.get({id: id});
    });

    // Picks us the event broadcasted when the company is deleted from the grid and perform the actual company delete by
    // calling the appropiate rest service.
    $scope.$on('deleteCompany', function (event, id) {
        companyService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('companyDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope, $rootScope) {
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
app.factory('companyService', ['$resource', function ($resource) {
    return $resource('resources/companies/:id');
}]);