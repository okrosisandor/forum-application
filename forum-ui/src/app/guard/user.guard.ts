import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';
import { inject } from '@angular/core';

export const UserGuard: CanActivateFn = (route, state) => {
  const authService = inject(TokenService);
  const router = inject(Router);

  if (authService.isTokenValid()) {
    return true;
  } else {
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
};
