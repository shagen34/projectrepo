import React, { Component } from 'react';
import { DropTarget } from 'react-dnd';

import Item from './item';


class ChickenFoxGrain extends Component {
    
    render() {
        const items = this.props.list;
        console.log(this.props)
        const { canDrop, isOver, connectDropTarget } = this.props;
        const isActive = canDrop && isOver;
        const style = {
            display: "flex",
            alignItems: 'flex-start',
            justifyContent: "flex-end",
            flexDirection: 'column',
            width: "300px",
            height: "200px",
            border: "1px solid black"
        };
        const backgroundColor = isActive ? 'lightgreen' : 'gray';
        return connectDropTarget(
            <div style={{...style,backgroundColor}}>
                {items.map((item, i) => {
                    return(
                        <Item
                            key={item.id}
                            listId = {this.props.id}
                            item={item}
                            remove={this.props.removeItem.bind(this)}
                            />
                    );
                })}
            </div>
        );
    }
}

const itemTarget = {
    drop(props,monitor,component){
        const {id} = props;
        console.log(props)
        const sourceObj = monitor.getItem();
        if(id !== sourceObj.listId) {
            component.props.pushItem(props, sourceObj.item);
        }
        return {
            listId: id
        };
    }
}

export default DropTarget("ITEM", itemTarget, (connect, monitor) => ({
	connectDropTarget: connect.dropTarget(),
	isOver: monitor.isOver(),
	canDrop: monitor.canDrop()
}))(ChickenFoxGrain);