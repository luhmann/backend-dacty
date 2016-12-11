console.log('Hello from maw');

const maw = angular.module('app')
console.log(maw);
maw.component('mawComponent', {
  template: `
    <div>Hello {{ $ctrl.message }}</div>
  `,
  bindings: {
    message: '@'
  }
})
