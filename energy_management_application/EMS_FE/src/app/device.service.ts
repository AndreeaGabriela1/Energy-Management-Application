import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Device } from './device';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  ///private baseUrl_device = "http://localhost:8081/device/adddevice";
  private baseUrl_device = "http://devices.localhost:8086/device/adddevice";
  constructor(private httpClient: HttpClient) { }

  public addDevice(device: Device): Observable<Device> {
    console.log(device);
    console.log(localStorage.getItem('accessToken'));
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
      })
    };
    console.log(httpOptions);
    const url = `${this.baseUrl_device}`;
    return this.httpClient.post<Device>(`${this.baseUrl_device}`, device, httpOptions);
  }
}
