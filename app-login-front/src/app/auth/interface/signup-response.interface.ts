import { UserDto } from "./user-dto.interface";

export interface UserResponse {
  token: String;
  tokenType: String;
  message: String;
  user: UserDto;
}
