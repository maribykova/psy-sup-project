import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/authService.js";

import Login from "./components/login.js";
import Home from "./components/home.js";
import Profile from "./components/profile.js";

const App = () => {
    return (
        <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/home"} className="navbar-brand">
              Главная страница
            </Link>
          <Link to={"/login"} className="navbar-brand">
            Войти
            </Link>
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route exact path={"/"} element={<Home />} />
          <Route exact path={"/home"} element={<Home />} />
          <Route exact path={"/login"} element={<Login />} />
          <Route exact path={"/profile"} element={<Profile />} />
        </Routes>
      </div>
    </div>
    );
}

export default App;