import { CarModel } from "src/app/features/modules/content/models/Car.model";
import { Location } from "src/app/features/modules/content/models/Location";

export interface PaginationModel {
    dates?: Object;
    page: number;
    results: Array<CarModel | Location>;
    total_pages: number;
    total_results: number;
}
