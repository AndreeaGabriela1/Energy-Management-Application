import { Device } from "./device";
import { User } from "./user";


export interface AddDeviceRequest {
    device: Device;
    user: User;
}