import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Device } from '../device';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DeviceService } from '../device.service';
import { HttpClient } from '@angular/common/http';
import { AddDeviceRequest } from '../add-device-request';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent {
  addDeviceRequest: AddDeviceRequest = {
    device: new Device(),
    user: new User()
  }

  form!: FormGroup;
  loading = false;
  showAllUsersComponent: boolean = false;
  showAllDevicesComponent: boolean = false;
  showChat: boolean = false;
  submitted = false;
  description: string;
  address: string;
  maxConsumption: number;
  userName: string;
  userEmail: string;
  userPassword: string;

  users: User[] = []; // Lista de utilizatori

  selectedUser: string; // Variabila pentru email-ul utilizatorului selectat

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private deviceService: DeviceService,
    private userService: UserService,
    private http: HttpClient
  ) { }

  ngOnInit() {
    this.loadUsers(); // Încarcă utilizatorii când componenta se inițializează
  }

  loadUsers() {
    // Metodă pentru a obține utilizatorii din serviciul userService
    this.userService.getUsers().subscribe(
      (data: User[]) => {
        this.users = data;
      },
      (error) => {
        console.error('Eroare la încărcarea utilizatorilor:', error);
      }
    );
  }
  // Metodă pentru a adăuga un dispozitiv
  addDevice() {
    this.deviceService.addDevice(this.addDeviceRequest.device).subscribe(
      (response) => {
        console.log('Device added successfully:', response);
        alert('Device added successfully!');
      },
      (error) => {
        console.error('Error adding device:', error);
        alert('Error adding device. Please try again.');
      }
    );
  }

// Metodă pentru a adăuga un utilizator
  addUser() {
    this.userService.addUser(this.addDeviceRequest.user).subscribe(
      (response) => {
        console.log('User added successfully:', response);
        alert('User added successfully!');
      },
      (error) => {
        console.error('Error adding user:', error);
        alert('Error adding user. Please try again.');
      }
    );
  }

  onSubmit() {
     //this.addDeviceRequest.device.userEmail = this.userEmail;
     this.addDeviceRequest.device.description = this.description;
     this.addDeviceRequest.device.address = this.address;
     this.addDeviceRequest.device.maximumConsumption = this.maxConsumption;
     this.addDeviceRequest.device.userEmail = this.selectedUser;

     this.addDeviceRequest.user.name = this.userName;
     this.addDeviceRequest.user.email = this.userEmail;
     this.addDeviceRequest.user.password = this.userPassword;

     if (this.description != null && this.maxConsumption != null && this.address != null)
     {
       this.addDevice();
     }
     else if (this.userName != null && this.userPassword != null && this.userEmail != null)
     {
       this.addUser();
     }
     else if (this.userName != null && this.userPassword != null && this.userEmail != null && this.description != null && this.maxConsumption != null && this.address != null)
     {
       this.addDeviceRequest.device.userEmail = this.userEmail;
       this.addUser();
       this.addDevice();
     }
     else
     {
       alert('Complete the form correctly!');
     }
     this.showAllUsersComponent = false;
   }

   showAllUsers(){
    this.showAllUsersComponent = true;
    this.showChat = false;
   }
   showAllDevices(){
    this.showAllDevicesComponent = true;
   }
  showChatBox(){
    this.showChat = true;
    this.showAllUsersComponent = false;
  }

}
