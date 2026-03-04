import {Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SplitEntry} from './split-entry';
import {StatsControllerService} from './stats-controller.service';
import {FinanceFilter} from './finance-filter';
import {Person} from './person';
import {environment} from '../../envrionment/environment';

@Injectable({
  providedIn: 'root',
})
export class SplitController {
  private readonly _splitEntryListSignal: WritableSignal<SplitEntry[]> = signal([]);
  public splitEntryList$ = this._splitEntryListSignal.asReadonly();

  constructor(private http: HttpClient,
              private statsController: StatsControllerService) {
  }

  public findAll(): void {
    this.http.get(environment.apiUrl + 'all').subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
      }
    });
  }

  public filter(filter: FinanceFilter) {
    let url: string = '';
    if (filter.person === Person.CENDRINE) {
      url += 'CENDRINE'
    } else if (filter.person === Person.PATRICK) {
      url += 'PATRICK';
    } else {
      url += 'BOTH';
    }
    url += '/';
    if (filter.description) {
      url += filter.description;
    } else {
      url += null;
    }
    console.log(url)
    this.http.get(environment.apiUrl + '/filter/' + url).subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
      }
    })
  }

  public add(entry: SplitEntry): void {
    this.http.post(environment.apiUrl + 'add', entry).subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
        this.statsController.getStats();
      }
    })
  }

  public remove(id: string) {
    this.http.delete(environment.apiUrl + 'remove/' + id).subscribe({
      next: () => {
        this._splitEntryListSignal.update(e => {
          this.statsController.getStats();
          return e.filter(v => v.id !== id);
        })
      }
    });
  }
}
