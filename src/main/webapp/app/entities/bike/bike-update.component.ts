import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBike, Bike } from 'app/shared/model/bike.model';
import { BikeService } from './bike.service';
import { IBiker } from 'app/shared/model/biker.model';
import { BikerService } from 'app/entities/biker/biker.service';

@Component({
  selector: 'jhi-bike-update',
  templateUrl: './bike-update.component.html'
})
export class BikeUpdateComponent implements OnInit {
  isSaving = false;
  bikers: IBiker[] = [];

  editForm = this.fb.group({
    id: [],
    model: [],
    serialNo: [],
    biker: []
  });

  constructor(
    protected bikeService: BikeService,
    protected bikerService: BikerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bike }) => {
      this.updateForm(bike);

      this.bikerService.query().subscribe((res: HttpResponse<IBiker[]>) => (this.bikers = res.body || []));
    });
  }

  updateForm(bike: IBike): void {
    this.editForm.patchValue({
      id: bike.id,
      model: bike.model,
      serialNo: bike.serialNo,
      biker: bike.biker
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bike = this.createFromForm();
    if (bike.id !== undefined) {
      this.subscribeToSaveResponse(this.bikeService.update(bike));
    } else {
      this.subscribeToSaveResponse(this.bikeService.create(bike));
    }
  }

  private createFromForm(): IBike {
    return {
      ...new Bike(),
      id: this.editForm.get(['id'])!.value,
      model: this.editForm.get(['model'])!.value,
      serialNo: this.editForm.get(['serialNo'])!.value,
      biker: this.editForm.get(['biker'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBike>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBiker): any {
    return item.id;
  }
}
