import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/authentication/services/auth.service';
import { ValidateService } from "src/app/core/services/validate.service";
import { MatDialogRef } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  public signupForm: FormGroup;

  constructor(public fb: FormBuilder,
    public authService: AuthService,
    public router: Router,
    private validateService: ValidateService,
    public dialogRef: MatDialogRef<RegisterComponent>
  ) {
    this.signupForm = this.fb.group({
      Email: [''],
      Password: [''],
      SurName: [''],
      FirstName: [''],
      Pesel: [''],
      PhoneNumber: ['']
    })
  }


  ngOnInit(): void { }

  public registerUser(): void {
    if(!this.validateService.validatePassword(this.signupForm.value.Password)) {
      this.signupForm.controls['Password'].setErrors({ 'incorrect': true });
      return;
    }

    if (!this.validateService.validateEmail(this.signupForm.value.Email)) {
      this.signupForm.controls['Email'].setErrors({ 'incorrect': true });
      return;
    }

    this.authService.singUp(this.signupForm.value)
      .subscribe(
        {
          next: (res) => {
            if (res.status == 201) {
              this.signupForm.reset();
              this.router.navigate(['/']);
              this.dialogRef.close();
              alert('You have successfully registered! Please login to continue.');
            }
          },
          error: (err) => {
            this.handleError(err);
          }
        }

      );
  }

  public handleError(error: HttpErrorResponse): any {
    if (error.status === 409) {
      this.signupForm.controls['Email'].setErrors({ 'conflict': true });
    }
  }

}
