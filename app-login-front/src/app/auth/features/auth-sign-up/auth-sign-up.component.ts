import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../data-access/auth.service';
import { UserResponse } from '../../interface/signup-response.interface';
import { HttpErrorResponse } from '@angular/common/http';

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
  imports: [ReactiveFormsModule],
  templateUrl: './auth-sign-up.component.html',
  styleUrl: './auth-sign-up.component.css'
})
export default class AuthSignUpComponent implements OnInit {
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);

  public form: FormGroup = this._formBuilder.group({
    nombreUsuario: [null, [Validators.required]],
    contrasena: [null, [Validators.required]],
    contrasenaDos: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    apellido: [null, [Validators.required]],
    correo: [null, [Validators.required]],
    telefonoUno: [null, [Validators.required]],
    telefonoDos: [null],
  });

  ngOnInit(): void {

    this.form.get('correo')?.valueChanges.subscribe(value => {
      const control = this.form.get('correo');
      if (!value) return;

      if (value.includes('@')) {
        control?.setValidators([Validators.required, Validators.email]);
      } else {
        control?.setValidators([Validators.required]);
      }
      // Esto refresca la validación sin disparar un ciclo infinito
      control?.updateValueAndValidity({ emitEvent: false });
    });
  }

  onSubmit(): void {
    console.log('invalid', this.form.invalid);

    // 1. Esto te dirá qué campo está fallando y por qué
    Object.keys(this.form.controls).forEach(key => {
      const controlErrors = this.form.get(key)?.errors;
      if (controlErrors != null) {
        console.log('Campo:', key, 'Error:', controlErrors);
      }
    });

    console.log('Estado del formulario:', this.form.invalid);

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this._authService.signUp(this.form.value)
      .subscribe({
        next: (response: UserResponse) => {
          console.log('¡Bienvenido!', response);
          // Aquí guardarías el token (localStorage.setItem('token', response.token))
        },
        error: (err: HttpErrorResponse) => {
          // Verificamos si el error viene del backend con formato JSON
          if (err.status === 401) {
            // Si el back ya envía JSON: { "message": "..." }
            const errorMessage = err.error?.message || 'Usuario o contraseña incorrectos';
            alert(errorMessage); // O usa un toast/notificación
          } else {
            console.error('Ocurrió un error inesperado:', err.message);
          }
        }

      });

    console.log("Value :", this.form.value);
  }
}
