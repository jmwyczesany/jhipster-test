import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPerson, Person } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { IGlasses } from 'app/shared/model/glasses.model';
import { GlassesService } from 'app/entities/glasses/glasses.service';

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  glasses: IGlasses[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    lastName: [],
    glasses: []
  });

  constructor(
    protected personService: PersonService,
    protected glassesService: GlassesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.updateForm(person);

      this.glassesService
        .query({ filter: 'person-is-null' })
        .pipe(
          map((res: HttpResponse<IGlasses[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGlasses[]) => {
          if (!person.glasses || !person.glasses.id) {
            this.glasses = resBody;
          } else {
            this.glassesService
              .find(person.glasses.id)
              .pipe(
                map((subRes: HttpResponse<IGlasses>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGlasses[]) => (this.glasses = concatRes));
          }
        });
    });
  }

  updateForm(person: IPerson): void {
    this.editForm.patchValue({
      id: person.id,
      name: person.name,
      lastName: person.lastName,
      glasses: person.glasses
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  private createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      glasses: this.editForm.get(['glasses'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
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

  trackById(index: number, item: IGlasses): any {
    return item.id;
  }
}
