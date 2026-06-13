export interface LoginResponse {
  token: string;
  tokenType: string;
  username: string;
  email: string;
  fullName: string;
  roles: string;
}
