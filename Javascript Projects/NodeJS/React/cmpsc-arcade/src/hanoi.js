import React, {Component} from 'react';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import update from 'react-addons-update';
import Container from './container';

const win = [{ id: 1, length: 100, text: "ID:1"},
{ id: 2, length: 150, text: "ID:2"},
{ id: 3, length: 200, text: "ID:3"},
{ id: 4, length: 250, text: "ID:4"},
{ id: 5, length: 300, text: "ID:5"}];
var moves = 0;

class Hanoi extends Component {
    state = {
        blockList: [
            { id: 1, length: 100, text: "ID:1"},
            { id: 2, length: 150, text: "ID:2"},
            { id: 3, length: 200, text: "ID:3"},
            { id: 4, length: 250, text: "ID:4"},
            { id: 5, length: 300, text: "ID:5"}
        ],
        midList: [],
        endList: []
    };
    unshiftBlock(childProps, block){
        var id = childProps.id;
        moves++;
        if(id === 1){
            this.setState(update(this.state, {
                
                blockList: 
                    {
                        $unshift: [ block ]
                    }
                }
            ));
        }
        if(id === 2){
            this.setState(update(this.state, {
                midList: 
                    {
                        $unshift: [ block ]
                    }
                }
            ));
        }
        if(id === 3){
            this.setState(update(this.state, {
                endList: 
                    {
                        $unshift: [ block ]
                    }
                }
            ));
            if(this.checkForWin()){
                alert("You won in " + moves + " moves!");
            }
            
        }
        
    };
    removeBlock(childProps){
        var id = childProps.listId;
        var list = childProps.block;

           if(id === 1){
            this.setState(update(this.state, {
                blockList: {
                    $splice: [
                        [list, 1]
                    ]
                }
            }));
        }
        if(id === 2){
            this.setState(update(this.state, {
                midList: 
                    {
                        $splice: [
                            [list, 1]
                        ]
                    }
                }));
        }
        if(id === 3){
            this.setState(update(this.state, {
                endList: 
                    {
                        $splice: [
                            [list, 1]
                        ]
                    }
                }));
        }
    };
    checkForWin(){
        let end = this.state.endList;
        if(end.length == win.length)
                return true;
        return false;
    }
    restart(){
        moves = 0;
        this.setState(update(this.state, {
            blockList: {
                $set: [
                { id: 1, length: 100, text: "ID:1"},
                { id: 2, length: 150, text: "ID:2"},
                { id: 3, length: 200, text: "ID:3"},
                { id: 4, length: 250, text: "ID:4"},
                { id: 5, length: 300, text: "ID:5"}
            ] },
            midList: { $set: [] },
            endList: { $set: [] },
        }));
    }
    render() {
        const style = {
            display: 'flex',
            justifyContent: "center",
            paddingTop: "20px"
        }
        const headerStyle = {
            display: "block"
        }
        return (
            <div>
                <h2 style={{...headerStyle}}>Towers of Hanoi</h2>
                
                <div style={{...style}}>
                    <Container id={1} 
                        list={this.state.blockList} 
                        unshiftBlock={this.unshiftBlock.bind(this)} 
                        removeBlock={this.removeBlock.bind(this)}/>
                    <Container id={2} 
                        list={this.state.midList} 
                        unshiftBlock={this.unshiftBlock.bind(this)} 
                        removeBlock={this.removeBlock.bind(this)}/>
                    <Container id={3} 
                        list={this.state.endList} 
                        unshiftBlock={this.unshiftBlock.bind(this)} 
                        removeBlock={this.removeBlock.bind(this)}/>
                </div>
                <button style={{marginTop: "20px"}} onClick={this.restart.bind(this)}>Restart</button>
            </div>
        );
    }
}
export default DragDropContext(HTML5Backend)(Hanoi);