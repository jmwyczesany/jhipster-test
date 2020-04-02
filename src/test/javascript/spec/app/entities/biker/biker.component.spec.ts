import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterTestTestModule } from '../../../test.module';
import { BikerComponent } from 'app/entities/biker/biker.component';
import { BikerService } from 'app/entities/biker/biker.service';
import { Biker } from 'app/shared/model/biker.model';

describe('Component Tests', () => {
  describe('Biker Management Component', () => {
    let comp: BikerComponent;
    let fixture: ComponentFixture<BikerComponent>;
    let service: BikerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [BikerComponent]
      })
        .overrideTemplate(BikerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BikerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BikerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Biker(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bikers && comp.bikers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
