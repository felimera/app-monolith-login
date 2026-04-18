import { Component } from '@angular/core';
import AuthLogInComponent from '../auth-log-in/auth-log-in.component';
import AuthSignUpComponent from '../auth-sign-up/auth-sign-up.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auth-session',
  standalone: true,
  imports: [CommonModule, AuthLogInComponent, AuthSignUpComponent],
  templateUrl: './auth-session.component.html',
  styles: ``
})
export default class AuthSessionComponent {
  isLogin = true; // Controla qué div se muestra

  toggleView() {
    this.isLogin = !this.isLogin;
  }
}
