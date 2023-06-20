import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/authentication/models/User';
import { AuthService } from 'src/app/authentication/services/auth.service';
import { StorageService } from 'src/app/authentication/services/storage.service';
import { CarsService } from "src/app/features/services/cars.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {EditProfileComponent} from "../edit-profile/edit-profile.component";
import {ChangePasswordComponent} from "../ChangePassword/ChangePassword.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  private editDialog!: MatDialogRef<EditProfileComponent>;
  private changePasswordDialog!: MatDialogRef<ChangePasswordComponent>;
  public currentUser: any;
  constructor(
    public authService: AuthService,
    private storageService: StorageService,
    private moviesService: CarsService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.currentUser = this.storageService.getUser();
  }

  public openEditDialog() {
    this.editDialog = this.dialog.open(EditProfileComponent, {
      width: '30vw',
      minWidth: '400px',
      maxWidth: '600px',
      minHeight: '550px',
      height: 'auto',
      data: {
        user: this.currentUser
      },
      backdropClass: 'backdropBackground'
    });
  }

  public openChangePasswordDialog() {
    this.changePasswordDialog = this.dialog.open(ChangePasswordComponent, {
      width: '30vw',
      minWidth: '400px',
      maxWidth: '600px',
      minHeight: '550px',
      height: 'auto',
      data: {
        user: this.currentUser
      },
      backdropClass: 'backdropBackground'
    });
  }

}
