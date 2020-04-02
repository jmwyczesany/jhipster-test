import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICar, Car } from 'app/shared/model/car.model';
import { CarService } from './car.service';
import { IDriver } from 'app/shared/model/driver.model';
import { DriverService } from 'app/entities/driver/driver.service';

@Component({
  selector: 'jhi-car-update',
  templateUrl: './car-update.component.html'
})
export class CarUpdateComponent implements OnInit {
  isSaving = false;
  drivers: IDriver[] = [];

  editForm = this.fb.group({
    id: [],
    model: [],
    engine: [],
    serialNo: [],
    drivers: []
  });

  constructor(
    protected carService: CarService,
    protected driverService: DriverService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ car }) => {
      this.updateForm(car);

      this.driverService.query().subscribe((res: HttpResponse<IDriver[]>) => (this.drivers = res.body || []));
    });
  }

  updateForm(car: ICar): void {
    this.editForm.patchValue({
      id: car.id,
      model: car.model,
      engine: car.engine,
      serialNo: car.serialNo,
      drivers: car.drivers
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const car = this.createFromForm();
    if (car.id !== undefined) {
      this.subscribeToSaveResponse(this.carService.update(car));
    } else {
      this.subscribeToSaveResponse(this.carService.create(car));
    }
  }

  private createFromForm(): ICar {
    return {
      ...new Car(),
      id: this.editForm.get(['id'])!.value,
      model: this.editForm.get(['model'])!.value,
      engine: this.editForm.get(['engine'])!.value,
      serialNo: this.editForm.get(['serialNo'])!.value,
      drivers: this.editForm.get(['drivers'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICar>>): void {
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

  trackById(index: number, item: IDriver): any {
    return item.id;
  }

  getSelected(selectedVals: IDriver[], option: IDriver): IDriver {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
