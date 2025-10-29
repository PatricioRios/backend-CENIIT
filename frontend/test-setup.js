import axios from 'axios';

// Point axios to the Nginx proxy for integration tests
axios.defaults.baseURL = 'http://localhost';
