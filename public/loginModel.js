var Backbone = require('backbone');

module.exports = Backbone.Model.extend({
  tagName: 'ul',
  urlRoot: 'http://tiny-tiny.herokuapp.com/collections/mime',
  idAttribute: '_id',
  defaults: {
    username: 'mime555',
    password: '123',
  },
  initialize: function () {},
});
