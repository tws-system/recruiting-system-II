var $ = global.jQuery = require('jquery');
var request = require('superagent');
var React = require('react');
var ReactDOM = require('react-dom');
require("bootstrap");

var LogicPuzzle = require('./component/logic-puzzle.component');

$('#submitModal').on('show.bs.modal', function () {
  $('.modal-content').css('margin-top', '230px');
});

ReactDOM.render(<LogicPuzzle/>, document.getElementById('InitializedBox'));
