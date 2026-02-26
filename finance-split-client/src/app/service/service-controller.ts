import {Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SplitEntry} from '../home/split-entry';

@Injectable({
  providedIn: 'root',
})
export class ServiceController {
  private readonly _splitEntryListSignal: WritableSignal<SplitEntry[]> = signal([]);
  public splitEntryList$ = this._splitEntryListSignal.asReadonly();

  constructor(private http: HttpClient) {
  }

  public findAll(): void {
    this.http.get('http://localhost:8080/all').subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
      }
    });
  }

  public findPerson(cendrine: boolean) {
    this.http.get('http://localhost:8080/' + cendrine).subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
      }
    })
  }

  public findDescription(description: string) {
    this.http.get('http://localhost:8080/' + description).subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
      }
    })
  }

  public add(entry: SplitEntry): void {
    this.http.post('http://localhost:8080/add', entry).subscribe({
      next: value => {
        this._splitEntryListSignal.set(<SplitEntry[]>value);
      }
    })
  }

  public remove(id: string) {
    this.http.delete('http://localhost:8080/remove/' + id);
    this._splitEntryListSignal.update(e => {
      return e.filter(v => v.id !== id);
    })
  }
}
