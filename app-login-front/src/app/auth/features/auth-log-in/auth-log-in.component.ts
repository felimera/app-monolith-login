import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../data-access/auth.service';

interface LoginForm {
  email: FormControl<null | string>;
  password: FormControl<null | string>;
}

@Component({
  selector: 'app-auth-log-in',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './auth-log-in.component.html',
  styleUrl: './auth-log-in.component.css'
})
export default class AuthLogInComponent {
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);

  public form: FormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control(null, [Validators.required, Validators.email]),
    password: this._formBuilder.control(null, [Validators.required]),
  });

  onSubmit(): void {
    if (this.form.invalid) return;

    console.log(this.form.value);
    this._authService.login(this.form.value).subscribe({
      next: (response) => {
        // 1. Lo que pasa cuando el login es EXITOSO
        console.log('¡Bienvenido!', response);
        // Aquí podrías guardar el token y navegar a la plataforma
        // this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        // 2. Lo que pasa si el backend responde con ERROR (401, 500, etc.)
        console.error('Error en el login:', err);
        // Aquí puedes mostrar un mensaje: "Usuario o contraseña incorrectos"
      },
      complete: () => {
        // 3. (Opcional) Se ejecuta cuando termina el flujo, pase lo que pase
        console.log('Proceso de login finalizado');
      }
    });

  }
}
