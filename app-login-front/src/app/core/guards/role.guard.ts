import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router } from '@angular/router';
import { UserResponse } from '../../auth/interface/signup-response.interface';

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return (route, state) => {
    const router = inject(Router);
    const platformId = inject(PLATFORM_ID);

    if (!isPlatformBrowser(platformId)) {
      return true;
    }

    const rawCustomer = localStorage.getItem('current_customer');

    if (!rawCustomer) {
      console.warn('[Guard] No se encontró la clave current_customer en localStorage.');
      router.navigate(['/login']);
      return false;
    }

    try {
      const customer:UserResponse = JSON.parse(rawCustomer);

      // COLOQUEMOS ESTOS LOGS TEMPORALES PARA DEBUGEAR
      // console.log('[Guard] Objeto recuperado:', customer);
      // console.log('[Guard] codeRole detectado:', customer.user.codeRole);
      // console.log('[Guard] Roles permitidos para esta pantalla:', allowedRoles);

      const userRole = customer.user.codeRole;

      // Forzamos a que compare en mayúsculas por seguridad
      if (userRole && allowedRoles.includes(userRole.toUpperCase())) {
        return true;
      }

      console.warn(`[Guard] Acceso denegado. El rol ${userRole} no está en:`, allowedRoles);
      router.navigate(['/login']); // Si falla, te regresa al login
      return false;
    } catch (e) {
      console.error('[Guard] Error al parsear el JSON del usuario:', e);
      router.navigate(['/login']);
      return false;
    }
  };
};
