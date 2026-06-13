import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../data-access/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginResponse } from '../../interface/login.interface';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

interface LoginForm {
  identifier: FormControl<null | string>;
  password: FormControl<null | string>;
}

@Component({
  selector: 'app-auth-log-in',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './auth-log-in.component.html',
  styleUrl: './auth-log-in.component.css'
})
export default class AuthLogInComponent implements OnInit {
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);

  private _router = inject(Router);
  private _toastr = inject(ToastrService);

  public form: FormGroup = this._formBuilder.group({
    identifier: [null, [Validators.required]],
    password: [null, [Validators.required]],
  });

  ngOnInit(): void {

    this.form.get('identifier')?.valueChanges.subscribe(value => {
      const control = this.form.get('identifier');
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
    if (this.form.invalid) {
      this.form.markAllAsTouched(); // Para mostrar errores visuales
      return;
    }

    // Enviamos el formulario al servicio
    this._authService.login(this.form.value).subscribe({
      next: (response: LoginResponse) => {
        console.log('¡Bienvenido!', response);
        // Aquí guardarías el token (localStorage.setItem('token', response.token))
        localStorage.setItem('token', `${response.tokenType} ${response.token}`);
        localStorage.setItem('current_customer', JSON.stringify(response));
        this._toastr.success(`¡Bienvenido de nuevo, ${response.fullName}!`, 'Inicio de sesión.');
        this._router.navigate(['/dashboard']);
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
  }
}

