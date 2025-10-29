import { describe, it, expect } from 'vitest';
import { authService } from './authService';

describe('authService - Integration Test', () => {
  it('should get a token from the real auth server with valid credentials', async () => {
    try {
      // Use credentials from login_example.txt
      const result = await authService.login('patriciorios', '1234');

      console.log('Received token response:', result);

      expect(result).toBeTypeOf('object');
      expect(result.access_token).toBeTypeOf('string');
      expect(result.access_token.length).toBeGreaterThan(0);
      expect(result.token_type).toBe('Bearer');

      console.log('✅ Integration test passed: Token received successfully.');

    } catch (error) {
      console.error('❌ Integration test failed. Is the Docker environment with Nginx and Keycloak running?');
      console.error('Error details:', error.response?.data || error.message);
      // Force the test to fail if an error is caught
      expect(error).toBeNull();
    }
  }, 10000); // 10 second timeout for network request

  it('should fail with invalid credentials', async () => {
    // This test will fail if the server is not running, which is expected.
    await expect(authService.login('wronguser', 'wrongpassword'))
      .rejects.toThrow();
  });
});
