import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from 'src/app/authentication/services/auth.service';
import { Router } from '@angular/router';
import { ValidateService } from "src/app/core/services/validate.service";
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  signInForm: FormGroup;
  constructor(
    public fb: FormBuilder,
    public authService: AuthService,
    public router: Router,
    private validateService: ValidateService,
    public dialogRef: MatDialogRef<LoginComponent>
  ) {
    this.signInForm = this.fb.group({
      Email: [''],
      Password: [''],
    });
  }

  ngOnInit(): void { }
  public signIn(): void {
    if (this.validateService.validateEmail(this.signInForm.value.Email)) {
      this.authService.signIn(this.signInForm.value);
      this.dialogRef.close();
    } else {
      this.signInForm.controls['Email'].setErrors({ 'incorrect': true });
    }
  }
}
