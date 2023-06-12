import {VehicleParametersModel} from "./VehicleParameters.model";

export interface CarModel {
  id: string;
  vehicleParametersId: string;
  registration: string;
  brand: string;
  model: string;
  dailyFee: number;
  bestOffer: boolean;
  photoURL?: string;
  vehicleParameters?: VehicleParametersModel;
}
