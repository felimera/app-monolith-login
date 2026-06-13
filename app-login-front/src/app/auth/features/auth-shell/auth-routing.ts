import { Routes } from "@angular/router";

export default [
  {
    path: 'session',
    loadComponent: () => import('../auth-session/auth-session.component')
  },
  {
    path: '**',
    redirectTo: 'session'
  }
] as Routes;
