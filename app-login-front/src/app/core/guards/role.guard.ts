import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return (route, state) => {
    const router = inject(Router);
    const platformId = inject(PLATFORM_ID);

    // Si estamos en el servidor, permitimos continuar temporalmente para evitar que se rompa
    if (!isPlatformBrowser(platformId)) {
      return true;
    }

    // Código exclusivo del Navegador
    const rawCustomer = localStorage.getItem('current_customer');

    if (!rawCustomer) {
      router.navigate(['/login']);
      return false;
    }

    try {
      const customer = JSON.parse(rawCustomer);
      const userRole = customer.codeRole;

      if (allowedRoles.includes(userRole)) {
        return true;
      }

      router.navigate(['/dashboard']);
      return false;
    } catch (e) {
      router.navigate(['/login']);
      return false;
    }
  };
};
