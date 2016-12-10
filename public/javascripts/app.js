const app = angular.module('app', [])

app.config(($sceDelegateProvider) => {
    $sceDelegateProvider.resourceUrlWhitelist([
    // Allow same origin resource loads.
    'self',
    // Allow loading from our assets domain.  Notice the difference between * and **.
    'http://localhost:3000/**'
]);
})

app.component('rootComponent', {
        template: `
          <div>Hello {{ $ctrl.message }}</div>
        `,
        bindings: {
            message: '@'
        },
        controller: ($scope) => {

    }
})
