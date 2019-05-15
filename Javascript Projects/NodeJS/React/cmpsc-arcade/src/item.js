import React, {Component} from "react";
import { DragSource } from "react-dnd";

const style = {
  display: 'block',
  height: "35px",
  border: '1px solid black',
  padding: '0.5rem 1rem',
  margin: 'auto',
  backgroundColor: 'green',
  cursor: 'move'
};

class Item extends Component {
  
  render() {
    const { isDragging, connectDragSource } = this.props;
    const opacity = isDragging ? 0 : 1;
    return connectDragSource(
      <div style={{...style, opacity}}>
        {this.props.item.text}
      </div>
    );
  }
}

const itemSource = {
  beginDrag(props){
    return {
      listId: props.listId,
      item: props.item
    };
  },
  endDrag(props, monitor){
    const item = monitor.getItem();
    console.log(item);
    const dropResult = monitor.getDropResult();
    if(dropResult && dropResult.listId !== item.listId) {
      props.remove(props);
    }
  }
};

export default 
      DragSource("ITEM", itemSource, (connect,monitor) => ({
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
      }))(Item);