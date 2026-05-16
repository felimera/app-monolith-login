import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './dashboard.component.html',
  styles: ``
})
export class DashboardComponent implements OnInit {
  private _router = inject(Router);
  private platformId = inject(PLATFORM_ID); // Inyectamos el ID de plataforma

  usuarioNombre: string = 'Usuario';
  usuarioRol: string = 'Invitado';
  userCodeRole: string = ''; // Guardará CLIENT, DIRECT o ADMIN
  isMenuOpen: boolean = false;

  ngOnInit(): void {
    // Verificamos si estamos en el navegador antes de leer localStorage
    if (isPlatformBrowser(this.platformId)) {
      const rawCustomer = localStorage.getItem('current_customer');
      if (rawCustomer) {
        try {
          const customer = JSON.parse(rawCustomer);
          // Mapeo exacto de tu objeto de respuesta
          this.usuarioNombre = customer.fullName || 'Sin Nombre';
          this.usuarioRol = customer.roles || 'Sin Rol';
          this.userCodeRole = customer.codeRole || '';
        } catch (e) {
          this.cerrarSesion();
        }
      } else {
        this.cerrarSesion();
      }
    }
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  cerrarSesion(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      localStorage.removeItem('current_customer');
    }
    // La navegación DEBE ir dentro del IF
    this._router.navigate(['/auth'])
      .then(success => {
        if (!success) {
          console.log('El Guard de Login o una ruta inválida rechazó el cierre de sesión.');
        }
      });
  }
}
