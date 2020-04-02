import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGlasses } from 'app/shared/model/glasses.model';

@Component({
  selector: 'jhi-glasses-detail',
  templateUrl: './glasses-detail.component.html'
})
export class GlassesDetailComponent implements OnInit {
  glasses: IGlasses | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ glasses }) => (this.glasses = glasses));
  }

  previousState(): void {
    window.history.back();
  }
}
