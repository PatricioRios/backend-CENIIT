import api from './api';
import axios from 'axios';
import { AUTH_ENDPOINT, AUTH_REGISTER_ENDPOINT } from './apiEndpoints';

export const authService = {
  login: async (email, password) => {
    const params = new URLSearchParams();
    params.append('client_id', 'ceniit-backend-develop');
    params.append('grant_type', 'password');
    params.append('username', email);
    params.append('password', password);
    params.append('client_secret', 'Oe8hlZYGa0xJwksIZqGCGJQTpGiou4Iy');

    const response = await axios.post(AUTH_ENDPOINT, params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
    return response.data;
  },

  register: async (userData) => {
    const response = await api.post(AUTH_REGISTER_ENDPOINT, userData);
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
};
