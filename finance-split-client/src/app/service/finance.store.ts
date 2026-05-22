import {patchState, signalStore, withComputed, withHooks, withMethods, withState} from '@ngrx/signals';
import {computed, inject} from '@angular/core';
import {FinanceControllerService, FinanceDataDto} from '../core/core-api';
import {PersonStore} from './person.store';

type FinanceState = {
  allFinances: FinanceDataDto[];
  activeFilter: Partial<FinanceDataDto>;
}

const initialState: FinanceState = {
  allFinances: [],
  activeFilter: {}
}

export const FinanceStore = signalStore(
  {providedIn: 'root'},
  withState(initialState),
  withComputed(({allFinances, activeFilter}) => ({
    finances: computed((): FinanceDataDto[] => {
      let filtered = allFinances();
      const f = activeFilter();

      if (f.personId) {
        filtered = filtered.filter(d => d.personId === f.personId);
      }
      if (f.description) {
        filtered = filtered.filter(d => d.description?.includes(f.description!));
      }
      if (f.amount && f.amount > 0) {
        filtered = filtered.filter(d => d.amount === f.amount);
      }
      if (f.dateTime) {
        filtered = filtered.filter(d => d.dateTime === f.dateTime);
      }

      return filtered;
    })
  })),
  withMethods((store, financeService = inject(FinanceControllerService), personStore = inject(PersonStore)) => ({

    findAll(): void {
      financeService.findAll1().subscribe({
        next: (value) => {
          patchState(store, {allFinances: value, activeFilter: {}});
        }
      });
    },

    filter(filterBy: Partial<FinanceDataDto>): void {
      patchState(store, {activeFilter: filterBy});
    },

    clearFilter(): void {
      patchState(store, {activeFilter: {}});
    },

    create(data: FinanceDataDto): void {
      financeService.createFinanceData(data).subscribe({
        next: (value) => {
          patchState(store, {allFinances: [...store.allFinances(), value]});
          personStore.findAll();
        }
      });
    },

    delete(id: string): void {
      financeService.deleteFinanceData(id).subscribe({
        next: () => {
          patchState(store, {allFinances: store.allFinances().filter(d => d.id !== id)});
          personStore.findAll();
        }
      });
    }

  })),
  withHooks({
    onInit(store) {
      store.findAll();
    }
  })
);
