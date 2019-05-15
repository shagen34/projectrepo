import React from 'react'
import {NavLink} from "react-router-dom";

const NavBar = () => {
    return (
    <nav className="nav-wrapper blue darken-3">
        <ul className="left">
            <li><NavLink to="/">Home</NavLink></li>
            <li><NavLink to="/hanoi">Towers of Hanoi</NavLink></li>
            <li><NavLink to="/cfg">Chicken Fox Grain</NavLink></li>
        </ul>
    </nav>
)
};
export default NavBar;