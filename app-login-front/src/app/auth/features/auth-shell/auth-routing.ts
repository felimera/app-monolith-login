import { Routes } from "@angular/router";

export default [
  {
    path: 'sign-up',
    loadComponent: () => import('../auth-sign-up/auth-sign-up.component')
  },
  {
    path: 'log-in',
    loadComponent: () => import('../auth-log-in/auth-log-in.component')
  },
  {
    path: 'session',
    loadComponent: () => import('../auth-session/auth-session.component')
  },
  {
    path: '**',
    redirectTo: 'session'
  }
] as Routes;
