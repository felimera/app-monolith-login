import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../data-access/auth.service';
import { UserResponse } from '../../interface/signup-response.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { coincidenContrasenas } from '../../../shared/validators/password-match.validator';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

interface SignupForm {
  nombreUsuario: FormControl<null | string>;
  contrasena: FormControl<null | string>;
  contrasenaDos: FormControl<null | string>;
  nombre: FormControl<null | string>;
  apellido: FormControl<null | string>;
  correo: FormControl<null | string>;
  telefonoUno: FormControl<null | string>;
  telefonoDos: FormControl<null | string>;
}

@Component({
  selector: 'app-auth-sign-up',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './auth-sign-up.component.html',
  styleUrl: './auth-sign-up.component.css'
})
export default class AuthSignUpComponent {
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);
  private _toastr = inject(ToastrService);
  private _router = inject(Router);
  private _platformId = inject(PLATFORM_ID);

  public form: FormGroup = this._formBuilder.group({
    nombreUsuario: ['', [Validators.required]],
    contrasena: ['', [Validators.required, Validators.minLength(6)]],
    contrasenaDos: ['', [Validators.required, Validators.minLength(6)]],
    nombre: ['', [Validators.required]],
    apellido: ['', [Validators.required]],
    correo: ['', [Validators.required, Validators.email]],
    telefonoUno: ['', [Validators.required]],
    telefonoDos: [''],
  }, {
    validators: [coincidenContrasenas]
  });


  onSubmit(): void {
    console.log('invalid', this.form.invalid);

    // 1. Esto te dirá qué campo está fallando y por qué
    Object.keys(this.form.controls).forEach(key => {
      const controlErrors = this.form.get(key)?.errors;
      if (controlErrors != null) {
        console.log('Campo:', key, 'Error:', controlErrors);
      }
    });

    this.form.markAllAsTouched();

    // 2. Si el formulario es inválido, detenemos la ejecución aquí
    if (this.form.invalid) {
      this._toastr.warning('Por favor, revisa los campos en rojo', 'Formulario incompleto');
      return; // <--- VITAL: Esto evita que se llame al servicio
    }

    this._authService.signUp(this.form.value)
      .subscribe({
        next: (response: UserResponse) => {
          // VALIDACIÓN CRÍTICA: Solo operar si estamos del lado del cliente
          if (isPlatformBrowser(this._platformId)) {

            // 1. Almacenar los datos de forma segura
            localStorage.setItem('token', `${response.tokenType} ${response.token}`);
            localStorage.setItem('current_customer', JSON.stringify(response));

            // 2. Mostrar la alerta visual
            this._toastr.success(`¡Bienvenido de nuevo, ${response.user.nombre}!`, 'Inicio de sesión exitoso.');

            // 3. Ejecutar la redirección absoluta con barra inclinada
            this._router.navigate(['/dashboard']).then(navegado => {
              if (!navegado) {
                console.error('La redirección falló. Verifica si el Guard está bloqueando la ruta.');
              }
            });

          }
        },
        error: (err: HttpErrorResponse) => {
          // Verificamos si el error viene del backend con formato JSON
          if (err.status === 401) {
            const errorMessage = err.error?.message || 'Usuario o contraseña incorrectos';
            this._toastr.error(`Comuniquese con el administrador ${errorMessage}`, `Error del servidor`);
          } else {
            this._toastr.error(err.error.message, 'Ocurrió un error inesperado');
          }
        }

      });
  }
}
