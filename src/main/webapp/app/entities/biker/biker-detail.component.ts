import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiker } from 'app/shared/model/biker.model';

@Component({
  selector: 'jhi-biker-detail',
  templateUrl: './biker-detail.component.html'
})
export class BikerDetailComponent implements OnInit {
  biker: IBiker | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biker }) => (this.biker = biker));
  }

  previousState(): void {
    window.history.back();
  }
}
