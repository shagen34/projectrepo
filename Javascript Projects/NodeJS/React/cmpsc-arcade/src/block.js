import React, {Component} from "react";
import { DragSource } from "react-dnd";

const style = {
  display: 'block',
  height: "35px",
  border: '1px solid black',
  padding: '0.5rem 1rem',
  marginLeft: 'auto',
  marginRight: 'auto',
  marginTop: '0',
  marginBottom: '0',
  backgroundColor: 'red',
  cursor: 'move'
};

class Block extends Component {
  
  render() {
    const { isDragging, connectDragSource } = this.props;
    const opacity = isDragging ? 0 : 1;
    const w = this.props.length;

    return connectDragSource(
      <div style={{...style, opacity, width: w}}>
      </div>
    );
  }
}

const blockSource = {
  canDrag(props, monitor){
    if(props.index == 0){
      return true;
    }
    else{
      return false;
    }
  },
  beginDrag(props){
    return {
      index: props.index,
      listId: props.listId,
      block: props.block
    };
  },
  endDrag(props, monitor){
    const item = monitor.getItem();
    const dropResult = monitor.getDropResult();
    if(dropResult && dropResult.listId !== item.listId) {
      props.remove(props);
    }
  }
};

export default 
      DragSource("BLOCK", blockSource, (connect,monitor) => ({
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
      }))(Block);