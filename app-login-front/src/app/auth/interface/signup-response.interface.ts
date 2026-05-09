import { UserDto } from "./user-dto.interface";

export interface UserResponse {
  token: string;
  tokenType: string;
  message: string;
  user: UserDto;
}
