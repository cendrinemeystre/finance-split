import {Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StatDto} from './statDto';
import {environment} from '../../envrionment/environment';

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
    this.http.get(environment.apiUrl + '/stats').subscribe({
      next: value => {
        this._statDto.set(<StatDto>value)
      }
    })
  }
}
