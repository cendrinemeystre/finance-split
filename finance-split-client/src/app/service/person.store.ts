import {patchState, signalStore, withComputed, withHooks, withMethods, withState} from '@ngrx/signals';
import {computed, inject} from '@angular/core';
import {PersonControllerService, PersonDto} from '../core/core-api';

type PersonState = {
  persons: PersonDto[];
}

const initialState: PersonState = {
  persons: []
}

export const PersonStore = signalStore(
  {providedIn: 'root'},
  withState(initialState),
  withComputed(({persons}) => ({
    totalSum: computed(() => persons().reduce((sum, p) => sum + (p.total ?? 0), 0)),
    diff: computed(() => {
      let map: number[] = persons().map(p=>p.total || 0);
      let min = Math.min(...map);
      let max = Math.max(...map);
      return max - min;
    })
  })),
  withMethods((store, personService = inject(PersonControllerService)) => ({
    findAll(): void {
      personService.findAll().subscribe({
        next: (value: PersonDto[]) => {
          patchState(store, {persons: value});
        }
      });
    },

    create(person: PersonDto): void {
      personService.createPerson(person).subscribe({
        next: (value) => {
          patchState(store, {persons: [...store.persons(), value]});
        }
      });
    },

    update(id: string, personUpdate: PersonDto): void {
      personService.updatePerson(id, personUpdate).subscribe({
        next: (value) => {
          patchState(store, {
            persons: store.persons().map(p => p.id === id ? value : p)
          });
        }
      });
    },

    delete(id: string): void {
      personService.deletePerson(id).subscribe({
        next: () => {
          patchState(store, {persons: store.persons().filter(p => p.id !== id)});
        }
      });
    }

  })),
  withHooks({
    onInit(store) {
      store.findAll();
    }
  }),
);
