import React, { Component } from 'react';
import { DropTarget } from 'react-dnd';

import Block from './block';


class Container extends Component {
    
    render() {
        const blocks = this.props.list;
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
                {blocks.map((block, i) => {
                    return(
                        <Block
                            key={block.id}
                            index={i}
                            length={block.length}
                            listId = {this.props.id}
                            block={block}
                            remove={this.props.removeBlock.bind(this)}
                            />
                    );
                })}
            </div>
        );
    }
}

const blockTarget = {
    canDrop(props,monitor){
        var top = props.list[0];
        var expect = monitor.getItem().block;
        if(top == undefined){
            return true;
        }
        if(expect.length > top.length){
            return false;
        }
        else {
            return true;
        }
    },
    drop(props,monitor,component){
        const {id} = props;
        const sourceObj = monitor.getItem();
        if(id !== sourceObj.listId) {
            component.props.unshiftBlock(props, sourceObj.block);
        }
        return {
            listId: id
        };
    }
}

export default DropTarget("BLOCK", blockTarget, (connect, monitor) => ({
	connectDropTarget: connect.dropTarget(),
	isOver: monitor.isOver(),
	canDrop: monitor.canDrop()
}))(Container);