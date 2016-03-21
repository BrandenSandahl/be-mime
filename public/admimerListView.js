var Backbone = require('backbone');
var _ = require('underscore');
var $ = require('jquery');
var ProfileView = require('./profileModelView');

module.exports =  Backbone.View.extend({
  el: '#admimirer-list',
  initialize: function () {
    this.addAll();
  },

  addOne: function (el) {
      var modelView = new ProfileView({model: el});
      console.log("userModel: " + el);
      console.log("ProfileView " + modelView);
      this.$el.append(modelView.render().el);
  },
  addAll: function () {
    this.$el.html('');
    _.each(this.collection.models, this.addOne, this);
  }
});
