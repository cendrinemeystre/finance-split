import {Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StatDto} from './statDto';

@Injectable({
  providedIn: 'root',
})
export class StatsControllerService {
  private readonly _statDto: WritableSignal<StatDto> = signal({
    total: 0,
    cendrine: 0,
    patrick: 0
  });
  public statDto$ = this._statDto.asReadonly();

  constructor(private http: HttpClient) {
  }

  public getStats(): void {
    this.http.get('http://localhost:8080/stats').subscribe({
      next: value => {
        this._statDto.set(<StatDto>value)
      }
    })
  }
}
