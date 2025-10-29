/// <reference types="vitest" />
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      // Proxy all backend and auth requests through the Nginx reverse proxy
      '/realms': {
        target: 'http://localhost',
        changeOrigin: true,
      },
      '/api/backend': {
        target: 'http://localhost',
        changeOrigin: true,
      },
      '/api/resources': {
        target: 'http://localhost',
        changeOrigin: true,
      }
    }
  },
  test: {
    environment: 'jsdom',
    setupFiles: ['./test-setup.js'],
  },
})
