import { RoleResponse } from "./role-response";

export interface UserResponse {
    userId: number;
    displayName: string;
    email: string;
    dateOfBirth: string;
    roles: RoleResponse[];
    accountLocked: boolean;
    enabled: boolean;
  }