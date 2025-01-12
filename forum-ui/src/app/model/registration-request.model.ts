export interface RegistrationRequest {
    displayName: string;
    email: string;
    dateOfBirth: Date | null;
    password: string;
  }