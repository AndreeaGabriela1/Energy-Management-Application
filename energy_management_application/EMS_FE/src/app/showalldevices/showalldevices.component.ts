import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Device } from '../device';
import { JwtHelperService } from '@auth0/angular-jwt';
import { DevicesService } from '../devices.service';
import { UserService } from "../user.service";
import { User } from "../user";

@Component({
  selector: 'app-showalldevices',
  templateUrl: './showalldevices.component.html',
  styleUrls: ['./showalldevices.component.css']
})
export class ShowalldevicesComponent implements OnInit {
  public devices: Device[] = [];
  showChart: boolean = true;
  @Input() showAllDevices: boolean = false;  // Variabila care decide ce tip de date sa incarce
  userName: string;
  users: User[] = [];
  selectedUser: string;
  selectedDate: string; // Proprietate adăugată pentru selectedDate

  constructor(private http: HttpClient, private deviceService: DevicesService, private userService: UserService) { }

  showDeviceChart(deviceId: number | undefined, maximumConsumption: number) {
    if (deviceId == undefined) deviceId = 0;
    this.deviceService.setSelectedDeviceId(deviceId);
    this.deviceService.setMaximumConsumption(maximumConsumption);
    this.showChart = true;
    console.log("showChart:", this.showChart);
    console.log("Graficul vietii");
  }

  fetchData() {
    const jwtHelper = new JwtHelperService();
    const token = localStorage.getItem('accessToken');
    let userEmail: string | null = null;
    if (!this.showAllDevices) {
      if (token) {
        userEmail = jwtHelper.decodeToken(token).sub;
        console.log(userEmail);
      } else {
        console.error('Access token is not available in localStorage.');
      }
    }

    // const url = this.showAllDevices
    //   ? 'http://localhost:8081/device/showall' // Endpoint pentru toate dispozitivele
    //   : `http://localhost:8081/device/showalldevices?userEmail=${userEmail}`; // Endpoint pentru dispozitivele unui utilizator

    const url = this.showAllDevices
      ? 'http://devices.localhost:8086/device/showall' // Endpoint pentru toate dispozitivele
      : `http://devices.localhost:8086/device/showalldevices?userEmail=${userEmail}`; // Endpoint pentru dispozitivele unui utilizator
    console.log(userEmail);
    console.log(localStorage.getItem('accessToken'));

    this.http.get<any>(url).subscribe(
      data => {
        this.devices = data;
        console.log(data);
        console.log(this.devices[0]);
        console.log(url);
      },
      error => {
        console.log(error);
      }
    );
  }

  ngOnInit() {
    this.fetchData();
    console.log("Eroarea ma tii!!");
    this.loadUsers();
    console.log("ngOnInit - showChart:", this.showChart);
  }

  editDevice(device: Device) {
    device.isEditing = true; // Activează modul de editare pentru dispozitivul selectat
  }

  cancelEdit(device: Device) {
    device.isEditing = false; // Dezactivează modul de editare
  }

  updateDevice(device: Device) {
    if (!device.id || !device.description || !device.address || device.maximumConsumption === undefined) {
      alert("Please fill in all fields before saving.");
      return;
    }
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
      })
    };
    console.log(this.selectedUser);
    device.userEmail = this.selectedUser;
    console.log(device.userEmail);

    this.http.put(`http://devices.localhost:8086/device/modifydevice`, device, httpOptions)
      .subscribe(
        (data: any) => {
          console.log(data);
          alert("Device modified successfully.");
        },
        (error) => {
          console.log(error);
          alert("Error modifying device");
        }
      );
  }

  deleteDevice(deviceId: number | undefined) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
      })
    };

    this.http
      .delete(`http://devices.localhost:8086/device/deletedevice?deviceId=${deviceId}`, httpOptions)
      .subscribe(
        (data: any) => {
          console.log("Device deleted!");
          alert("Device deleted successfully.");
        },
        (error) => {
          console.log(error.message);
        }
      );
  }

  loadUsers() {
    this.userService.getUsers().subscribe(
      (data: User[]) => {
        this.users = data;
      },
      (error) => {
        console.error('Eroare la încărcarea utilizatorilor:', error);
      }
    );
  }
}
