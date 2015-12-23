var React = require('react');
var Reflux = require('reflux');

var LogicPuzzleStore = require('../store/logic-puzzle-store');
var LogicPuzzleActions = require('../actions/logic-puzzle-actions');

var LogicPuzzleLeft = require('./logic-puzzle-left.component');
var LogicPuzzleSidebar = require('./logic-puzzle-sidebar.component');

var LogicPuzzle = React.createClass({
  mixins: [Reflux.connect(LogicPuzzleStore, 'item')],

  getInitialState: function () {
    return {
      item: {
        initializedBox: [],
        chartPath: '',
        descriptionZh: [],
        isFirstOne: true,
        isLastOne: false
      }
    };
  },

  componentDidMount: function () {
    LogicPuzzleActions.loadItem();
  },

  render: function () {
    return (

        <div className="container-fluid">
          <div className="row">
            <div className="col-md-9 col-sm-8 ">
              <LogicPuzzleLeft boxes={this.state.item.initializedBox}
                               chartPath={this.state.item.chartPath}
                               description={this.state.item.descriptionZh}/>
            </div>

            <div className="col-md-3 col-sm-4">
              <LogicPuzzleSidebar isFirstOne={this.state.item.isFirstOne}
                                  isLastOne={this.state.item.isLastOne}
                                  id={this.state.item.id}
                                  />
            </div>
          </div>
        </div>
    )
  }
});

module.exports = LogicPuzzle;