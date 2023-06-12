import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material/material.module';
import { RouterModule } from '@angular/router';
import { PosterCardComponent } from './components/poster-card/poster-card.component';
import { ImageMissingDirective } from './directives/ImageMissing.directive';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule
  ],
  exports: [
    MaterialModule,
    CommonModule,
    PosterCardComponent,
    ImageMissingDirective,
  ],
  declarations: [
    PosterCardComponent,
    ImageMissingDirective,
  ]
})
export class SharedModule { }
