import { Component, OnInit } from '@angular/core';
import { Device } from '../device';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-client-page',
  templateUrl: './client-page.component.html',
  styleUrls: ['./client-page.component.css']
})
export class ClientPageComponent{
  public devices: Device[] = [];
  showAllDevicesComponent: boolean = false;
  showChat: boolean = false;
  showChart: boolean = true;


  constructor(private http: HttpClient){}

  showAllDevices()
  {
    console.log("Sa se vada device urile!!!");
    this.showAllDevicesComponent = true;
    this.showChat = true;
  }

  showChatBox(){
    this.showChat = true;
    this.showAllDevicesComponent = false;
  }

}
