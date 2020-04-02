import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestTestModule } from '../../../test.module';
import { GlassesComponent } from 'app/entities/glasses/glasses.component';
import { GlassesService } from 'app/entities/glasses/glasses.service';
import { Glasses } from 'app/shared/model/glasses.model';

describe('Component Tests', () => {
  describe('Glasses Management Component', () => {
    let comp: GlassesComponent;
    let fixture: ComponentFixture<GlassesComponent>;
    let service: GlassesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [GlassesComponent]
      })
        .overrideTemplate(GlassesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GlassesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GlassesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Glasses(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.glasses && comp.glasses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
