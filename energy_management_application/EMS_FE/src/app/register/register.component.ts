import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterUserService } from  '../register-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private registerUserService: RegisterUserService, private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log('Form data being sent:', this.registerForm.value);
      this.registerUserService.register(this.registerForm.value).subscribe(
        response => {
          // Handle successful registration, e.g., redirect to login page
          alert("Register succesfully!");
          this.router.navigate(['/login']);
        },
        error => {
          // Handle error, e.g., show error message
          console.error('Registration failed', error);
          alert("Meduza");
        }
      );
    }
    else
    {
      alert("Register fara succes si multi pumni");
    }
  }
}
