import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {MatFormField, MatLabel, MatSuffix} from '@angular/material/select';
import {MatInput} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    MatDialogTitle,
    MatFormField,
    MatInput,
    MatLabel,
    MatSuffix,
    ReactiveFormsModule,
    MatIcon
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  hide = true;
  errorMessage: string = '';
  displayError: boolean = false;
  username = '';
  password = '';

  constructor(
    private dialogRef: MatDialogRef<LoginComponent>,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  login() {
    this.username = this.loginForm.value.username;
    this.password = this.loginForm.value.password;
    this.authService.setCredentials(this.username, this.password);
    this.authService.login().subscribe({
      next: (validated) => {
        this.dialogRef.close({
          validationResult: validated,
          username: this.username
        });
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    });
  }

  isLoginButtonDisabled(): boolean {
    const form = this.loginForm;
    return form.invalid;
  }

  closeModal() {
    this.dialogRef.close();
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'Invalid username or password';
  }
}
