// Logout.js
import { useNavigate } from "react-router-dom";
import AuthService from "../service/AuthService";

const Logout = ({ setUsername, setIsAuthenticated }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    AuthService.logout();
    setIsAuthenticated(false);
    setUsername(""); // Reset username to an empty string

    // Navigate to the homepage after a delay
    setTimeout(() => navigate('/'), 1000); 
  };

  return (
    <div>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default Logout;
