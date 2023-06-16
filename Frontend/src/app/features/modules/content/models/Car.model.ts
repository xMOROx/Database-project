
export interface CarModel {
  id: string;
  vehicleParametersId: string;
  registration: string;
  brand: string;
  model: string;
  dailyFee: number;
  bestOffer: boolean;
  photoURL?: string;
  bodyType: string;
  productionYear: number;
  fuelType: string;
  power: number;
  gearbox: string;
  frontWheelDrive: boolean;
  doorsNumber: number;
  seatsNumber: number;
  color: string;
  metallic: boolean;
  description: string;
}
