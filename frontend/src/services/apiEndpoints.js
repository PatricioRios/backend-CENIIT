// API Prefixes
export const SPRING_API_PREFIX = '/api/backend';
export const GO_API_PREFIX = '/api/resources/v1';

// Auth Endpoints
export const AUTH_ENDPOINT = '/realms/develop-spring-realm/protocol/openid-connect/token';
export const AUTH_REGISTER_ENDPOINT = `${SPRING_API_PREFIX}/auth/register`;

// User Endpoints
export const USERS_ENDPOINT = `${SPRING_API_PREFIX}/users`;
export const USERS_SEARCH_ENDPOINT = `${USERS_ENDPOINT}/search`;

// Resource Endpoints
export const RESOURCES_ENDPOINT = `${GO_API_PREFIX}/recursos`;

// Reservation Endpoints
export const RESERVATIONS_ENDPOINT = `${SPRING_API_PREFIX}/reservas`;
export const RESERVATIONS_AGENDA_ENDPOINT = `${RESERVATIONS_ENDPOINT}/agenda`;
