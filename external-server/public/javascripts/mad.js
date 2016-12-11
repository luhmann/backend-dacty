console.log('hello from mad')

const mad = angular.module('app')

mad.component('madComponent', {
  template: `
    <div>Hello {{ $ctrl.message }}</div>
  `,
  bindings: {
    message: '@'
  }
})

