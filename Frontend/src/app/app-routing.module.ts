import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { PageNotFoundComponent } from "src/app/core/components/page-not-found/page-not-found.component";
import { HomeComponent } from './features/home/home.component';


const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: '404', component: PageNotFoundComponent, pathMatch: 'full' },

  { path: 'cars', loadChildren: () => import('./features/modules/content/content.module').then(m => m.ContentModule) },
  { path: 'locations', loadChildren: () => import('./features/modules/content/content.module').then(m => m.ContentModule) },
  { path: 'dashboard', loadChildren: () => import('./features/modules/dashboard/dashboard.module').then(m => m.DashboardModule) },

  { path: '**', redirectTo: '404' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
