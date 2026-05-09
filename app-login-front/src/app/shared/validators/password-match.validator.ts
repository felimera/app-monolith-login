import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const coincidenContrasenas: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const contrasena = control.get('contrasena');
  const contrasenaDos = control.get('contrasenaDos');

  // Si los valores no coinciden, marcamos el error 'noCoinciden' en el segundo campo
  if (contrasena && contrasenaDos && contrasena.value !== contrasenaDos.value) {
    contrasenaDos.setErrors({ noCoinciden: true });
    return { noCoinciden: true };
  }

  return null;
};
