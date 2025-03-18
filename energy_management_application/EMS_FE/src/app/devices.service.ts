import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DevicesService {

  public maximumConsumption: number = 0; // Default value for maximumConsumption
  constructor() { }
  private selectedDeviceIdSubject = new BehaviorSubject<number | null>(null);
  selectedDeviceId$ = this.selectedDeviceIdSubject.asObservable();

  setSelectedDeviceId(deviceId: number | null) {
    this.selectedDeviceIdSubject.next(deviceId);
  }
  // Setter for maximumConsumption
  setMaximumConsumption(consumption: number) {
    this.maximumConsumption = consumption;
  }

  // Getter for maximumConsumption (optional, if you need to access the value)
  getMaximumConsumption(): number {
    return this.maximumConsumption;
  }
}
