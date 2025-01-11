import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

export const AdminGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
    const router = inject(Router);
  
    if (tokenService.isTokenValid() && tokenService.hasAuthority("ROLE_ADMIN")) {
      return true;
    } else {
      router.navigate(['/'], { queryParams: { returnUrl: state.url } });
      return false;
    }
};
