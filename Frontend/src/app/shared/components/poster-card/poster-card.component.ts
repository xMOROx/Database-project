import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-poster-card',
  templateUrl: './poster-card.component.html',
  styleUrls: ['./poster-card.component.scss']
})
export class PosterCardComponent implements OnInit {

  @Input() public model: any;
  @Input() public isCar: boolean = true;


  constructor() { }

  ngOnInit() {
  }

}
