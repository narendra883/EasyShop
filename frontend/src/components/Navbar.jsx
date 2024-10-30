import { Link } from "react-router-dom";
import AuthService from "../service/AuthService";

const Navbar = ({ userName }) => {
  return (
    <nav className="p-4 bg-gradient-to-r from-blue-500 to-indigo-600 text-white shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        
        <div className="flex items-center space-x-6">
          <Link to="/" className="font-semibold text-lg hover:text-gray-200 transition duration-200">
            Home
          </Link>
          {!AuthService.isAuthenticated() ? (
            <>
              <Link
                to="/register"
                className="font-semibold hover:text-gray-200 transition duration-200"
              >
                Register
              </Link>
              <Link
                to="/login"
                className="font-semibold hover:text-gray-200 transition duration-200"
              >
                Login
              </Link>
            </>
          ) : (
            <>
              <Link
                to="/addtocart"
                className="font-semibold hover:text-gray-200 transition duration-200"
              >
                Cart
              </Link>
              <Link
                to="/logout"
                className="font-semibold hover:text-gray-200 transition duration-200"
              >
                Logout
              </Link>
            </>
          )}
        </div>

        
        {AuthService.isAuthenticated() && (
          <div className="text-right">
            <span className="text-lg font-semibold">Welcome, {userName}</span>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
