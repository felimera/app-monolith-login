import { Routes } from '@angular/router';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/features/auth-shell/auth-routing')
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./pages/pages-shell/pages-routing')
  },
];
