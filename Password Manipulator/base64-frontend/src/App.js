import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import axios from "axios";
import './App.css'; // Import the CSS file for styling

function Base() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [responseMessage, setResponseMessage] = useState("");
  const [encodedString, setEncodedString] = useState("");
  const [decodedResult, setDecodedResult] = useState("");
  const [encodedResult, setEncodedResult] = useState("");

  const handleEncode = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/base/encode", {
        username,
        password,
      });
      setResponseMessage("Profile saved successfully!");
      setEncodedResult(response.data);
    } catch (error) {
      setResponseMessage("Error occurred: " + error.message);
    }
  };

  const handleDecode = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(
        `http://localhost:8080/base/decode/${encodedString}`
      );
      setDecodedResult(response.data);
    } catch (error) {
      setDecodedResult("Error occurred: " + error.message);
    }
  };

  return (
    <div className="App">
      <div className="container">
        <h1 className="title">StringGuard Encoder/Decoder</h1>
        
      
        
        <div className="card">
  <h2>Encode Username and Password</h2>
  <form onSubmit={handleEncode} className="form-container">
    <div className="form-group">
      <label>Username:</label>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="input-field"
        placeholder="Enter your username"
        required
      />
    </div>

    <div className="form-group">
      <label>Password:</label>
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="input-field"
        placeholder="Enter your password"
        required
      />
    </div>

    <button type="submit" className="btn encode-btn">
      Encode Data
    </button>
  </form>

  {responseMessage && <p className="message">{responseMessage}</p>}
  {encodedResult && (
    <p className="result">
      <strong>Encoded String :</strong> {encodedResult}
    </p>
  )}
</div>
<div className="card">
  <h2>Decode String</h2>
  <form onSubmit={handleDecode} className="form-container">
    <div className="form-group">
      <label>Encoded String:</label>
      <input
        type="text"
        value={encodedString}
        onChange={(e) => setEncodedString(e.target.value)}
        className="input-field"
        placeholder="Enter encoded string"
        required
      />
    </div>

    <button type="submit" className="btn decode-btn">
      Decode
    </button>
  </form>

  {decodedResult && (
    <p className="result">
      <strong>Decoded Result:</strong> {decodedResult}
    </p>
  )}
</div>
</div>
</div>
  );
}

function Info() {
  return (
    <div className="App">
      <div className="container">
        <h1 className="title">StringGuard Info</h1>
        
        {/* StringGuard Info content */}
        <div className="info-content">
          <h2>About StringGuard</h2>
          <p>
            <strong>StringGuard</strong> is a powerful tool designed to protect sensitive data by encoding and decoding information using Base64 encoding. In today's digital landscape, security is a top priority, and ensuring that usernames and passwords are stored in a secure format is critical. StringGuard provides an easy way to safeguard your credentials by transforming them into encoded strings that are difficult to interpret without proper decoding.
          </p>
          
          <h3>How It Works:</h3>
          <ul>
            <li>
              <strong>Encoding Algorithm:</strong> StringGuard uses Base64 encoding to convert your username and password into a safe, non-human-readable format. This process takes the input (text) and translates it into a string of characters that can be safely stored or transmitted.
            </li>
            <li>
              <strong>Decoding Algorithm:</strong> When needed, the Base64-encoded string is decoded back to its original form, making the data readable and usable again.
            </li>
          </ul>

          <p>By using StringGuard, users can ensure that their sensitive information is protected and encoded to prevent unauthorized access.</p>
        </div>
      </div>
    </div>
  );
}

function App() {
  return (
    <Router>
      <div className="navbar">
        <Link to="/" className="nav-link">StringGuard</Link>
        <Link to="/info" className="nav-link">StringGuard Info</Link>
      </div>

      <Routes>
        <Route path="/" element={<Base />} />
        <Route path="/info" element={<Info />} />
      </Routes>
    </Router>
  );
}

export default App;
