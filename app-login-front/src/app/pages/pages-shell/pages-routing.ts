import { Routes } from "@angular/router";
import { roleGuard } from "../../core/guards/role.guard";

export default [
  {
    path: 'dashboard',
    loadComponent: () => import('../dashboard/dashboard.component').then(m => m.DashboardComponent),
    children: [
      {
        path: 'customer',
        loadComponent: () => import('../customer/customer.component').then(m => m.CustomerComponent),
        canActivate: [roleGuard(['CLIENT', 'DIRECT', 'ADMIN'])] // Todos entran
      },
      {
        path: 'report',
        loadComponent: () => import('../report/report.component').then(m => m.ReportComponent),
        canActivate: [roleGuard(['DIRECT', 'ADMIN'])] // Solo Directores y Admin
      },
      {
        path: 'settings',
        loadComponent: () => import('../settings/settings.component').then(m => m.SettingsComponent),
        canActivate: [roleGuard(['ADMIN'])] // Solo Administradores
      },
      { path: '', redirectTo: 'customer', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
] as Routes;
