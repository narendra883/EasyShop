// App.js
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { useEffect, useState } from "react";
import Cart from "./components/Cart";
import Product from "./components/Product";
import Login from "./components/Login";
import Logout from "./components/Logout";
import Registration from "./components/Registration";
import Navbar from "./components/Navbar";
import AuthService from "./service/AuthService";

function App() {
  const [username, setUsername] = useState("");
  const [id,setId] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(AuthService.isAuthenticated());
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const response = await AuthService.getUserDetails();
        setUsername(response.firstName);
        setId(response.id);
      } catch (error) {
        console.error("Error fetching user info:", error);
        setErrorMessage(error.message || "An error occurred");
      }
    };

    // Fetch user info if authenticated
    if (isAuthenticated) {
      fetchUserInfo();
    }
  }, [isAuthenticated]);

  return (
    <Router>
      <Navbar userName={username} />
      {errorMessage && <div className="p-4 text-red-600">{errorMessage}</div>}
      <Routes>
        <Route path="/" element={<Product userId={id}/>} />
        <Route path="/addtocart" element={<Cart userId={id}/>} />
        <Route path="/login" element={<Login setUsername={setUsername} setId={setId} />} />
        <Route path="/register" element={<Registration />} />
        <Route path="/logout" element={<Logout setUsername={setUsername} setIsAuthenticated={setIsAuthenticated} />} />
      </Routes>
    </Router>
  );
}

export default App;
