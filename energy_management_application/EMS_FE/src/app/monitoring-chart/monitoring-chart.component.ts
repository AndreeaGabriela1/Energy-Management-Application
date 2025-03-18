import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChartDataset, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { DevicesService } from '../devices.service';

@Component({
  selector: 'app-monitoring-chart',
  templateUrl: './monitoring-chart.component.html',
  styleUrls: ['./monitoring-chart.component.css'],
})
export class MonitoringChartComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  constructor(private http: HttpClient, private deviceService: DevicesService) {}

  public barChartType: ChartType = 'bar';
  public barChartLabels: string[] = [];
  public barChartOptions: ChartOptions = {
    responsive: true,
  };

  public barChartData: ChartDataset[] = [
    {
      data: [],
      label: 'kWh',
      backgroundColor: 'rgba(128, 0, 128, 0.5)',
      borderColor: 'rgba(0, 0, 0, 1)',
      borderWidth: 1,
    },
  ];

  public selectedDate: string; // Holds the selected date in YYYY-MM-DD format

  fetchData(deviceId: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
      }),
    };

    const selectedDate = this.selectedDate; // Format should be YYYY-MM-DD
    console.log("Data selectata:",selectedDate);
    // this.http.get<any>(`http://localhost:8085/monitoring/getsum?device_id=${deviceId}&date=${selectedDate}`, httpOptions).subscribe(
    // ///this.http.get<any>(`http://reverse-proxy/monitoring/getsum?device_id=${deviceId}&date=${selectedDate}`, httpOptions).subscribe(
    // data => {
    //       console.log('Data received:', data);
    //
    //       // Filter data for the selected date
    //       const filteredData = Object.entries(data).filter(([key]) =>
    //         key.startsWith(this.selectedDate)
    //       );
    //
    //       const labels = filteredData.map(([key]) => key); // Extract timestamps
    //       const values: number[] = filteredData.map(([_, value]) => value as number); // Extract consumption values and cast to number
    //
    //       // Update chart data
    //       this.barChartLabels = labels;
    //       this.barChartData[0].data = values;
    //
    //       console.log('Filtered Data:', filteredData);
    //       this.chart.update();
    //
    //       // Check for maximum consumption violations
    //       const maxHourlyConsumption = this.deviceService.getMaximumConsumption();
    //       const exceededTimestamps = filteredData.filter(([key, value]) => (value as number) > maxHourlyConsumption);
    //
    //       if (exceededTimestamps.length > 0) {
    //         alert(
    //           `Warning: Consumption exceeded the device's maximum hourly limit of ${maxHourlyConsumption} at the following times: ${exceededTimestamps
    //             .map(([key]) => key)
    //             .join(', ')}`
    //         );
    //       } else {
    //         console.log('Consumption is within acceptable limits.');
    //       }
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
    ////http://localhost:8085/monitoring/getsum?device_id=${deviceId}&date=${selectedDate}
    this.http.get<any>(`http://monitoring.localhost:8086/monitoring/getsum?device_id=${deviceId}&date=${selectedDate}`, httpOptions).subscribe(
      data => {
        console.log('Data received:', data);

        // Convert selected date to a string format that matches the keys in the data (e.g., "YYYY-MM-DD")
        const formattedSelectedDate = new Date(this.selectedDate).toISOString().split('T')[0];
        console.log("Data inainte sa filtrez datele:",formattedSelectedDate);
        // Filter data for the selected date
        const filteredData = Object.entries(data).filter(([key]) =>
          key.startsWith(formattedSelectedDate)
        );

        const labels = filteredData.map(([key]) => key); // Extract timestamps
        const values: number[] = filteredData.map(([_, value]) => Number(value)); // Extract consumption values and cast to number

        // Update chart data
        this.barChartLabels = labels;
        this.barChartData[0].data = values;

        console.log('Filtered Data:', filteredData);
        this.chart.update();

        // Check for maximum consumption violations
        const maxHourlyConsumption = this.deviceService.getMaximumConsumption();
        const exceededTimestamps = filteredData.filter(([_, value]) => Number(value) > maxHourlyConsumption);

        if (exceededTimestamps.length > 0) {
          alert(
            `Warning: Consumption exceeded the device's maximum hourly limit of ${maxHourlyConsumption} at the following times: ${exceededTimestamps
              .map(([key]) => key)
              .join(', ')}`
          );
        } else {
          console.log('Consumption is within acceptable limits.');
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  fetchDataForSelectedDate() {
    this.deviceService.selectedDeviceId$.subscribe((selectedDeviceId) => {
      if (selectedDeviceId !== null && this.selectedDate) {
        this.fetchData(selectedDeviceId);
      } else {
        console.log('Please select a date and a device ID');
      }
    });
  }

  onDateChange() {
    console.log('Date changed:', this.selectedDate);
  }

  ngOnInit() {
    this.deviceService.selectedDeviceId$.subscribe((selectedDeviceId) => {
      if (selectedDeviceId !== null) {
        const today = new Date().toISOString().slice(0, 10); // Default to today's date
        this.selectedDate = today; // Initialize the selected date
        this.fetchData(selectedDeviceId);
      }
    });
  }
}
