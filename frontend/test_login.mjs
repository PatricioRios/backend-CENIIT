import { authService } from './src/services/authService.js';

async function runTest() {
  try {
    console.log('Attempting login with user \'patriciorios\'...');
    const data = await authService.login('patriciorios', '1234');
    console.log('Login successful! Token received.');
    
    // Test decoding
    const tokenPayload = JSON.parse(atob(data.access_token.split('.')[1]));
    console.log('\n--- Decoded Token Payload ---');
    console.log(tokenPayload);
    console.log('---------------------------');

    const isBackendAdmin = tokenPayload.resource_access?.['ceniit-backend-develop']?.roles.includes('backend-admin') || false;
    const isAdmin = tokenPayload.realm_access?.roles.includes('realm-admin') || isBackendAdmin;

    console.log(`User 'name' from token: ${tokenPayload.name}`);
    console.log(`Is frontend 'admin' role correctly assigned? ${isAdmin}`);

  } catch (error) {
    console.error('\n--- LOGIN TEST FAILED ---');
    console.error(error.response ? error.response.data : error.message);
    console.error('-------------------------\n');
  }
}

runTest();
