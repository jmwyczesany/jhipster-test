import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGlasses, Glasses } from 'app/shared/model/glasses.model';
import { GlassesService } from './glasses.service';

@Component({
  selector: 'jhi-glasses-update',
  templateUrl: './glasses-update.component.html'
})
export class GlassesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    model: [],
    front: [],
    temples: [],
    lenses: [],
    size: []
  });

  constructor(protected glassesService: GlassesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ glasses }) => {
      this.updateForm(glasses);
    });
  }

  updateForm(glasses: IGlasses): void {
    this.editForm.patchValue({
      id: glasses.id,
      model: glasses.model,
      front: glasses.front,
      temples: glasses.temples,
      lenses: glasses.lenses,
      size: glasses.size
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const glasses = this.createFromForm();
    if (glasses.id !== undefined) {
      this.subscribeToSaveResponse(this.glassesService.update(glasses));
    } else {
      this.subscribeToSaveResponse(this.glassesService.create(glasses));
    }
  }

  private createFromForm(): IGlasses {
    return {
      ...new Glasses(),
      id: this.editForm.get(['id'])!.value,
      model: this.editForm.get(['model'])!.value,
      front: this.editForm.get(['front'])!.value,
      temples: this.editForm.get(['temples'])!.value,
      lenses: this.editForm.get(['lenses'])!.value,
      size: this.editForm.get(['size'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGlasses>>): void {
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
