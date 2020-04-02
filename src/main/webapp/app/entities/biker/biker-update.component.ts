import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBiker, Biker } from 'app/shared/model/biker.model';
import { BikerService } from './biker.service';

@Component({
  selector: 'jhi-biker-update',
  templateUrl: './biker-update.component.html'
})
export class BikerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    lastName: [],
    mileage: []
  });

  constructor(protected bikerService: BikerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ biker }) => {
      this.updateForm(biker);
    });
  }

  updateForm(biker: IBiker): void {
    this.editForm.patchValue({
      id: biker.id,
      name: biker.name,
      lastName: biker.lastName,
      mileage: biker.mileage
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const biker = this.createFromForm();
    if (biker.id !== undefined) {
      this.subscribeToSaveResponse(this.bikerService.update(biker));
    } else {
      this.subscribeToSaveResponse(this.bikerService.create(biker));
    }
  }

  private createFromForm(): IBiker {
    return {
      ...new Biker(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      mileage: this.editForm.get(['mileage'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiker>>): void {
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
}
