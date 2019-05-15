import React, { Component } from 'react';
import './App.css';
import NavBar from "./NavBar";
import {BrowserRouter, Route} from "react-router-dom";
import Home from "./Home";
import Hanoi from "./hanoi";
import ChickenFoxGrain from "./cfg";

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <NavBar/>
          <Route exact path='/' component={Home}/>
          <Route path='/hanoi' component={Hanoi}/>
          <Route path='/cfg' component={ChickenFoxGrain}/>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
