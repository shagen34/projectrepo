import React, {Component} from 'react';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import update from 'react-addons-update';
import Side from './side';

const win = [{ id: 1, text: "Chicken"},
{ id: 2, text: "Fox"},
{ id: 3, text: "Grain"}];

class ChickenFoxGrain extends Component {
    state = {
        itemList: [
            { id: 1, text: "Chicken"},
            { id: 2, text: "Fox"},
            { id: 3, text: "Grain"}
        ],
        endList: []
    };
    pushItem(childProps, block){

        var id = childProps.id;
        if(id === 1){
            this.setState(update(this.state, {
                itemList: 
                    {
                        $push: [ block ]
                    }
                }
            ));
        }
        if(id === 2){
            this.setState(update(this.state, {
                endList: 
                    {
                        $push: [ block ]
                    }
                }
            ));
            if(this.checkForWin()){
                console.log("YOU WON")
            }
            
        }
        
    };
    removeItem(childProps){
        var id = childProps.listId;
        var list = childProps.block;

           if(id === 1){
            this.setState(update(this.state, {
                itemList: {
                    $splice: [
                        [list, 1]
                    ]
                }
            }));
        }
        if(id === 2){
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
        this.setState(update(this.state, {
            itemList: {
                $set: [
                    { id: 1, text: "Chicken"},
                    { id: 2, text: "Fox"},
                    { id: 3, text: "Grain"}
                ], },
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
        const riverStyle = {
            display: 'flex',
            width: "200px",
            justifyContent: "center",
            paddingTop: "20px",
            backgroundColor: "blue"
        }
        return (
            <div>
                <h2 style={{...headerStyle}}>Chicken-Fox-Grain</h2>
                
                <div style={{...style}}>
                    <Side id={1} 
                        list={this.state.itemList} 
                        pushItem={this.pushItem.bind(this)} 
                        removeItem={this.removeItem.bind(this)}/>
                    <div style={{...riverStyle}}></div>
                    <Side id={2} 
                        list={this.state.endList} 
                        pushItem={this.pushItem.bind(this)} 
                        removeItem={this.removeItem.bind(this)}/>
                </div>
                <button style={{marginTop: "20px"}} onClick={this.restart.bind(this)}>Restart</button>
            </div>
        );
    }
}
export default DragDropContext(HTML5Backend)(ChickenFoxGrain);