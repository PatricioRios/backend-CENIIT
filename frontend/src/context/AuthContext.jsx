import React, { createContext, useState, useContext, useEffect } from 'react';
import { authService } from '../services/authService';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth debe usarse dentro de AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkAuth = () => {
      const token = localStorage.getItem('token');
      const userData = localStorage.getItem('user');
      
      if (token && userData) {
        try {
          setUser(JSON.parse(userData));
          setIsAuthenticated(true);
        } catch (error) {
          console.error('Error parsing user data:', error);
          localStorage.removeItem('token');
          localStorage.removeItem('user');
        }
      }
      setIsLoading(false);
    };

    checkAuth();
  }, []);

  const login = async (email, password) => {
    try {
      const data = await authService.login(email, password);
      
      localStorage.setItem('token', data.access_token);

      // Decode token to get user info (basic, without verification)
      const tokenPayload = JSON.parse(atob(data.access_token.split('.')[1]));
      
      // Role mapping based on the provided token example
      const isBackendAdmin = tokenPayload.resource_access?.['ceniit-backend-develop']?.roles.includes('backend-admin') || false;
      const isAdmin = tokenPayload.realm_access?.roles.includes('realm-admin') || isBackendAdmin;

      const user = {
          name: tokenPayload.name || tokenPayload.preferred_username,
          email: tokenPayload.email,
          role: isAdmin ? 'admin' : 'user'
      };

      localStorage.setItem('user', JSON.stringify(user));
      setUser(user);
      setIsAuthenticated(true);
      
      return { success: true };
    } catch (error) {
      const errorMessage = error.response?.data?.error_description || 'Error al iniciar sesión';
      return { 
        success: false, 
        message: errorMessage
      };
    }
  };

  const logout = () => {
    authService.logout();
    setUser(null);
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider value={{
      user,
      isAuthenticated,
      isLoading,
      login,
      logout
    }}>
      {children}
    </AuthContext.Provider>
  );
};